package com.example.helperinolympics.adapter;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Acertos;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Erros;
import com.example.helperinolympics.model.Pontuacao;
import com.example.helperinolympics.model.questionario.Alternativas;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterAlternativasQuestionario extends RecyclerView.Adapter<AdapterAlternativasQuestionario.AlternativasQuestionarioViewHolder> {
    private List<Alternativas> listaAlternativas;
    static Context context;
    private Aluno alunoCadastrado;
    private static Date dataAtual;

    public AdapterAlternativasQuestionario(List<Alternativas> listaAlternativas, Context context, Aluno alunoCadastrado, Date dataAtual) {
        this.listaAlternativas = listaAlternativas != null ? listaAlternativas : new ArrayList<>(); //Verificando se lista é nula
        this.context = context;
        this.alunoCadastrado = alunoCadastrado;
        this.dataAtual = dataAtual;
    }


    @NonNull
    @Override
    public AlternativasQuestionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaAlternativas = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_alternativas_questionario, parent, false);
        return new AlternativasQuestionarioViewHolder(viewItemListaAlternativas);
    }

    @Override
    public void onBindViewHolder(@NonNull AlternativasQuestionarioViewHolder holder, int position) {
        holder.listaAlternativas=  listaAlternativas;
        holder.alunoCadastrado = alunoCadastrado;
        Alternativas alternativa = listaAlternativas.get(position);

          holder.textoAlternativa.setText(alternativa.getTextoAlternativa());
          holder.id = alternativa.getId();
          holder.idQuestionarioPertencente= alternativa.getIdQuestionarioPertencente();
          holder.idQuestaoPertencente = alternativa.getIdQuestaoPertencente();
          holder.corretaOuErrada = alternativa.isCorretaOuErrada();
    }

    @Override
    public int getItemCount() {
        return listaAlternativas != null ? listaAlternativas.size() : 0;
    }

    public static class AlternativasQuestionarioViewHolder extends RecyclerView.ViewHolder {
        TextView textoAlternativa;
        int id, idQuestionarioPertencente, idQuestaoPertencente;
        boolean corretaOuErrada;
        List<Alternativas> listaAlternativas;
        Aluno alunoCadastrado;

        public AlternativasQuestionarioViewHolder(@NonNull View itemView) {
            super(itemView);

            textoAlternativa = itemView.findViewById(R.id.textoAlternativa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Estabelecer que, para cada acerto, são +10 pontos para o aluno
                    if(corretaOuErrada){

                        //cadastrar acerto
                        Acertos acerto = new Acertos(id, idQuestionarioPertencente, idQuestaoPertencente, dataAtual, alunoCadastrado.getEmail());
                        new CadastrarAcertos().execute(acerto);

                        //adicionar pontuação
                        Pontuacao pontuacao = new Pontuacao(10, alunoCadastrado.getEmail());
                        new AtualizarPontuacao().execute(pontuacao);


                    }//Para cada erro são -2 pontos
                    else{
                        int idAlternativaCorreta = 0;

                        for(int i=0; i<listaAlternativas.size(); i++){
                            if(listaAlternativas.get(i).isCorretaOuErrada()==true){
                                idAlternativaCorreta = listaAlternativas.get(i).getId();
                            }
                        }

                        //cadastrar erro
                        Erros erro = new Erros(id, idAlternativaCorreta, idQuestionarioPertencente, idQuestaoPertencente, dataAtual, alunoCadastrado.getEmail());
                        new CadastrarErros().execute(erro);

                        //diminuir pontuacao
                        Pontuacao pontuacao = new Pontuacao(-2, alunoCadastrado.getEmail());
                        new AtualizarPontuacao().execute(pontuacao);

                    }
                }
            });
        }
    }



    public void atualizarOpcoes(List<Alternativas> alternativas) {
        if (this.listaAlternativas != null) {
            this.listaAlternativas.clear();
            this.listaAlternativas.addAll(alternativas);
            this.notifyDataSetChanged();
        }
    }

    private static class CadastrarAcertos extends AsyncTask<Acertos, Void, String> {

        @Override
        protected String doInBackground(Acertos... acertos) {
            StringBuilder result = new StringBuilder();
            Acertos acerto = acertos[0];

            try {
                URL url = new URL("http://192.168.1.11:8086/phpHio/cadastraAcertosAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                conexao.connect();

                String parametros = "idAlternativaMarcada=" + acerto.getIdAlternativaMarcada() +
                        "&idQuestionarioPertencente=" + acerto.getIdQuestionarioPertencente() +
                        "&idQuestaoPertencente=" + acerto.getIdQuestaoPertencente()+
                        "&dataAcerto" +acerto.getDataAcerto()+
                        "&emailAluno" +acerto.getEmailAluno();

                OutputStream os = conexao.getOutputStream();
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
                return null;
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");
                    Log.e("Msg", message);

                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }

    private static class CadastrarErros extends AsyncTask<Erros, Void, String> {

        @Override
        protected String doInBackground(Erros... erros) {
            StringBuilder result = new StringBuilder();
            Erros erro = erros[0];

            try {
                URL url = new URL("http://192.168.1.11:8086/phpHio/cadastraErrosAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                conexao.connect();

                String parametros = "idAlternativaMarcada=" + erro.getIdAlternativaMarcada() +
                        "&idAlternativaCorreta="+ erro.getIdAlternativaCorreta()+
                        "&idQuestionarioPertencente=" + erro.getIdQuestionarioPertencente() +
                        "&idQuestaoPertencente=" + erro.getIdQuestaoPertencente()+
                        "&dataErro"+ erro.getDataErro()+
                        "&emailAluno" + erro.getEmailAluno();

                OutputStream os = conexao.getOutputStream();
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
                return null;
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");
                    Log.e("Msg", message);

                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }

    private static class AtualizarPontuacao extends AsyncTask<Pontuacao, Void, String> {

        @Override
        protected String doInBackground(Pontuacao... pontuacoes) {
            StringBuilder result = new StringBuilder();
            Pontuacao pontuacao = pontuacoes[0];

            try {
                URL url = new URL("http://192.168.1.11:8086/phpHio/alteraPontuacaoAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                conexao.connect();

                String parametros = "emailAluno=" + URLEncoder.encode(pontuacao.getEmailAluno(), "UTF-8") +
                        "&pontuacao=" + pontuacao.getPontuacao();

                OutputStream os = conexao.getOutputStream();
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
                return null;
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");
                    Log.e("Msg", message);

                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }


}
