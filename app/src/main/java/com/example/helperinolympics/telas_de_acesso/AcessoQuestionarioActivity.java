package com.example.helperinolympics.telas_de_acesso;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helperinolympics.QuestionarioCorrecaoActivity;
import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterAlternativasQuestionario;
import com.example.helperinolympics.databinding.ActivityQuestionarioAcessoBinding;
import com.example.helperinolympics.materiais.FragmentPerguntaRespostasQuestionario;
import com.example.helperinolympics.materiais.QuestionarioActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.Erros;
import com.example.helperinolympics.model.Pontuacao;
import com.example.helperinolympics.model.questionario.Questao;
import com.example.helperinolympics.model.questionario.Questionario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AcessoQuestionarioActivity extends AppCompatActivity {

    private ActivityQuestionarioAcessoBinding binding;

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String siglaOlimpiada;
    private Questionario quest;
    private Date dataAtual;

    private ArrayList<Questao> listaDeQuestoes = new ArrayList<>();

    private int contNumeroQuestoes = 0, totalQuestoes;
    int qntdErrosCancelados, qntdAcertosCancelados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionarioAcessoBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        //recebendo os dados da outra tela
        quest = getIntent().getParcelableExtra("questionario");
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");

        Calendar calendar = Calendar.getInstance();
        dataAtual = calendar.getTime();

        //configurações de exibição
        binding.txtTema.setText(quest.getTitulo());
        binding.txtProf.setText("Por: "+ quest.getProfessorCadastrou());

        Integer idQuestionario = quest.getId();

        if (idQuestionario != null) {
            new QuestoesDownload().execute(idQuestionario);
        } else {
            Log.d("ERRO_ID_QUESTIONARIO", "O id do questionário está nulo");
        }

        binding.btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("RESPONDER", "Responder foi clicado ");

                FragmentPerguntaRespostasQuestionario fragment = (FragmentPerguntaRespostasQuestionario) getSupportFragmentManager().findFragmentById(R.id.fragmentPerguntasERespostas);

                if (fragment != null) {
                    boolean alternativaMarcadaOuNao = fragment.verificarSeAlternativaMarcada();

                    if(alternativaMarcadaOuNao){
                        //true
                        //passa pra próxima questão, atualiza a barra de progresso
                        proximaQuestao();

                    }else{
                        //false
                        Toast.makeText(AcessoQuestionarioActivity.this, "É preciso selecionar uma alternativa para prosseguir!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        binding.btnDesistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DESISTIR", "Desistir foi clicado ");

                //apagar acertos e erros, restaurar pontuação
                //exibir telinha de processamento da informacao

                new RestauraPontuacaoAcertosErros(dataAtual, quest.getId(), alunoCadastrado.getEmail()).execute();
            }
        });

    }

    private void atualizarProgresso(int questoesRespondidas) {
        binding.progressBar.setProgress(questoesRespondidas);
    }

    public void proximaQuestao() {
        contNumeroQuestoes++;

        if (contNumeroQuestoes < totalQuestoes) {
            atualizarProgresso(contNumeroQuestoes);
            configurarQuestaoASerExibida(listaDeQuestoes.get(contNumeroQuestoes), contNumeroQuestoes + 1);
        }else{

            Intent intent = new Intent(AcessoQuestionarioActivity.this, QuestionarioCorrecaoActivity.class);
            intent.putExtra("questionario", quest);
            intent.putExtra("alunoCadastrado", alunoCadastrado);
            intent.putExtra("conteudo", conteudo);
            intent.putExtra("olimpiada", siglaOlimpiada);
            intent.putExtra("dataAtual", dataAtual);
            startActivity(intent);
            finish();
        }
    }

    private void configurarQuestaoASerExibida(Questao questao, int numeroQuestao) {
        setFragment(FragmentPerguntaRespostasQuestionario.newInstance(questao, quest.getId(), questao.getId(), alunoCadastrado, dataAtual, numeroQuestao));
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentPerguntasERespostas, fragment);
        fragmentTransaction.commit();
    }

    private class QuestoesDownload extends AsyncTask<Integer, Void, List<Questao>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Questao> doInBackground(Integer... params) {
            int idQuestionario = params[0];
            Log.d("ID_QUESTIONARIO_RECEBIDO", "Id questionário Recebido: " + idQuestionario);

            List<Questao> questoes = new ArrayList<>();
            try {
                String urlString = "https://hio.lat/carregaQuestoesPorQuestionario.php?idQuestionarioPertencente=" +
                        URLEncoder.encode(String.valueOf(idQuestionario), "UTF-8");
                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    Log.d("DADOS", jsonString);
                    listaDeQuestoes.addAll(converterParaList(jsonString));
                    questoes.addAll(converterParaList(jsonString));

                    //exibindo primeira questao ao ter o acesso de um questionario
                    totalQuestoes = listaDeQuestoes.size();
                    configurarQuestaoASerExibida(listaDeQuestoes.get(contNumeroQuestoes), contNumeroQuestoes + 1);

                    binding.progressBar.setMax(totalQuestoes);
                    atualizarProgresso(contNumeroQuestoes);
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questoes;
        }

        @Override
        protected void onPostExecute(List<Questao> questoes) {
            super.onPostExecute(questoes);

            if (questoes != null && !questoes.isEmpty()) {
                listaDeQuestoes.clear();
                listaDeQuestoes.addAll(questoes);

                totalQuestoes = listaDeQuestoes.size();

                binding.progressBar.setMax(totalQuestoes);
                atualizarProgresso(contNumeroQuestoes);

                configurarQuestaoASerExibida(listaDeQuestoes.get(contNumeroQuestoes), contNumeroQuestoes + 1);
            } else {
                Log.e("ERRO_QUESTOES", "Nenhuma questão carregada");
            }
        }


        private String converterParaJSONString(InputStream in) {
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream dados = new ByteArrayOutputStream();
            try {
                int qtdBytesLido;
                while ((qtdBytesLido = in.read(buffer)) != -1) {
                    dados.write(buffer, 0, qtdBytesLido);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return dados.toString();
        }

        private List<Questao> converterParaList(String jsonString) {
            List<Questao> questoes = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("questoesDoQuestionario");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject questaoJSON = jsonArray.getJSONObject(i);
                    Questao questao = new Questao();

                    questao.setId(questaoJSON.getInt("id"));
                    questao.setTxtPergunta(questaoJSON.getString("txtPergunta"));
                    questao.setIdQuestionarioPertencente(quest.getId());
                    questao.setExplicacaoResposta(questaoJSON.getString("explicacaoResposta"));


                    Log.d("Questão  ", questao.toString());
                    questoes.add(questao);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questoes;
        }
    }

    private class RestauraPontuacaoAcertosErros extends AsyncTask<Void, Void, String> {
        Date data;
        int idQuestionario;
        String emailAluno;

        public RestauraPontuacaoAcertosErros(Date data, int idQuestionario, String emailAluno) {
            this.data = data;
            this.idQuestionario = idQuestionario;
            this.emailAluno = emailAluno;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataErroFormatada = sdf.format(data);

            try {
                URL url = new URL("https://hio.lat/apagaAcertosErrosAoDesistirDoQuestionario.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                conexao.connect();

                String parametros = "&data=" + URLEncoder.encode(dataErroFormatada, "UTF-8") +
                        "&idQuestionario=" + idQuestionario +
                        "&emailAluno=" + emailAluno;

                OutputStream os = conexao.getOutputStream();
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                int responseCode = conexao.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    return response.toString();
                } else {
                    return "Error: " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String status = jsonResponse.getString("status");
                if (status.equals("success")) {
                    qntdErrosCancelados = jsonResponse.getInt("qntdErros");
                    qntdAcertosCancelados = jsonResponse.getInt("qntdAcertos");

                    //Restaura pontuação calculando com: qntdErrosCancelados, qntdAcertosCancelados
                    int pontosAcertosARetirar = -(qntdAcertosCancelados * 10);
                    int pontosErrosAAdicionar = qntdErrosCancelados * 2;

                    int pontuacaoARepor =pontosErrosAAdicionar + pontosAcertosARetirar;

                    Pontuacao pontuacao = new Pontuacao(pontuacaoARepor, alunoCadastrado.getEmail());
                    new AtualizarPontuacao().execute(pontuacao);

                } else {
                    String message = jsonResponse.getString("message");
                    Log.e("Msg", message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private class AtualizarPontuacao extends AsyncTask<Pontuacao, Void, String> {

        @Override
        protected String doInBackground(Pontuacao... pontuacoes) {
            StringBuilder result = new StringBuilder();
            Pontuacao pontuacao = pontuacoes[0];

            try {
                URL url = new URL("https://hio.lat/alteraPontuacaoAluno.php");
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
                    String status = jsonResponse.getString("status");
                    Log.e("Msg", status);

                    if (status.equals("success")) {
                        Intent intent = new Intent(AcessoQuestionarioActivity.this, QuestionarioActivity.class);
                        intent.putExtra("alunoCadastrado", alunoCadastrado);
                        intent.putExtra("conteudo", conteudo);
                        intent.putExtra("olimpiada", siglaOlimpiada);
                        startActivity(intent);
                        finish();

                    } else {
                        String message = jsonResponse.getString("message");
                        Log.e("Msg", message);
                    }
                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }
}