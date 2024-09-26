package com.example.helperinolympics.materiais;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterAlternativasQuestionario;
import com.example.helperinolympics.databinding.FragmentQuestionarioBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.questionario.Alternativas;
import com.example.helperinolympics.model.questionario.Questao;
import com.example.helperinolympics.model.questionario.Questionario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FragmentPerguntaRespostasQuestionario  extends Fragment {
    private AdapterAlternativasQuestionario adapter;

    private ArrayList<Alternativas> listaAlternativas;
    private FragmentQuestionarioBinding binding;
    private Questao questao;
    private Aluno alunoCadastrado;

    private int idQuestionarioPertencente, idQuestaoPertencente;
    private Context context;

    public FragmentPerguntaRespostasQuestionario(Questao questao, int idQuestionarioPertencente, int idQuestaoPertencente, Context context, Aluno alunoCadastrado){
        this.questao = questao;
        this.idQuestionarioPertencente = idQuestionarioPertencente;
        this.idQuestaoPertencente= idQuestaoPertencente;
        this.context = context;
        this.alunoCadastrado = alunoCadastrado;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionarioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pergunta = binding.txtPerguntaQuestionario;
        String valorPergunta= "<b>Pergunta " +questao.getId()+"</b><br>"+questao.getTxtPergunta();
        pergunta.setText(Html.fromHtml(valorPergunta, Html.FROM_HTML_MODE_COMPACT));

        configurarRecyclerAlternativas();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void configurarRecyclerAlternativas(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerAlternativas.setLayoutManager(layoutManager);
        binding.recyclerAlternativas.setHasFixedSize(true);

        new AlternativasDownload().execute(idQuestionarioPertencente, idQuestaoPertencente);

        adapter=new AdapterAlternativasQuestionario(listaAlternativas, context, alunoCadastrado);
        binding.recyclerAlternativas.setAdapter(adapter);
    }

    private class AlternativasDownload extends AsyncTask<Integer, Void, List<Alternativas>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Alternativas> doInBackground(Integer... params) {
            int idQuestionarioPertencente = params[0];
            int idQuestaoPertencente = params[1];

            Log.d("ID_QUESTIONARIO_RECEBIDO", "Id questionário Recebido: " + idQuestionarioPertencente);
            Log.d("ID_QUESTAO_RECEBIDA", "Id questão Recebida: " + idQuestaoPertencente);


            List<Alternativas> alternativas = new ArrayList<>();
            try {
                String urlString = "http://192.168.1.9:8086/phpHio/carregaAlternativasPorQuestao.php?"+
                        "idQuestionarioPertencente=" + URLEncoder.encode(String.valueOf(idQuestionarioPertencente), "UTF-8")+
                        "idQuestaoPertencente=" + URLEncoder.encode(String.valueOf(idQuestaoPertencente), "UTF-8");

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
                    listaAlternativas.addAll(converterParaList(jsonString));
                    alternativas.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return alternativas;
        }

        @Override
        protected void onPostExecute(List<Alternativas> alternativas) {
            super.onPostExecute(alternativas);
            if (alternativas != null) {
                adapter.atualizarOpcoes(alternativas);
                adapter.notifyDataSetChanged();
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

        private List<Alternativas> converterParaList(String jsonString) {
            List<Alternativas> alternativas = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("alternativasDaQuestao");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject alternativaJSON = jsonArray.getJSONObject(i);
                    Alternativas alternativa = new Alternativas();


                    alternativa.setId(alternativaJSON.getInt("id"));
                    alternativa.setIdQuestionarioPertencente(idQuestionarioPertencente);
                    alternativa.setIdQuestaoPertencente(idQuestaoPertencente);
                    alternativa.setTextoAlternativa(alternativaJSON.getString("textoAlternativa"));

                    if(alternativaJSON.getInt("corretaOuErrada")==1){
                        alternativa.setCorretaOuErrada(true);
                    }else{
                        alternativa.setCorretaOuErrada(false);
                    }


                    Log.d("Alternativas  ", alternativa.toString());
                    alternativas.add(alternativa);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return alternativas;
        }
    }

}
