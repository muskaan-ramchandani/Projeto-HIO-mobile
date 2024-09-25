package com.example.helperinolympics.telas_de_acesso;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityQuestionarioAcessoBinding;
import com.example.helperinolympics.materiais.FragmentPerguntaRespostasQuestionario;
import com.example.helperinolympics.materiais.QuestionarioActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
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

public class AcessoQuestionarioActivity extends AppCompatActivity {

    private ActivityQuestionarioAcessoBinding binding;

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String siglaOlimpiada;
    private Questionario quest;

    private ArrayList<Questao> listaDeQuestoes = new ArrayList<>();

    private int contNumeroQuestoes;

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

        //configurações de exibição
        binding.txtTema.setText(quest.getTitulo());
        binding.txtProf.setText("Por: "+ quest.getProfessorCadastrou());

        Integer idQuestionario = quest.getId();

        if (idQuestionario != null) {
            new QuestoesDownload().execute(idQuestionario);
        } else {
            Log.d("ERRO_ID_QUESTIONARIO", "O id do questionário está nulo");
        }

        //exibindo primeira questao ao ter o acesso de um questionario
        configurarQuestaoASerExibida(listaDeQuestoes.get(0));
        contNumeroQuestoes = 0;

        binding.btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });



    }

    private void configurarQuestaoASerExibida(Questao questao) {
        setFragment(new FragmentPerguntaRespostasQuestionario(questao, quest.getId(), questao.getId(), AcessoQuestionarioActivity.this));
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
                String urlString = "http://192.168.1.9:8086/phpHio/carregaQuestoesPorQuestionario.php?idQuestionarioPertencente=" +
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
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questoes;
        }

        @Override
        protected void onPostExecute(List<Questao> questoes) {
            super.onPostExecute(questoes);

            if (questoes != null) {
                listaDeQuestoes.clear();
                listaDeQuestoes.addAll(questoes);
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


                    Log.d("Questão  ", questao.toString());
                    questoes.add(questao);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questoes;
        }
    }

}