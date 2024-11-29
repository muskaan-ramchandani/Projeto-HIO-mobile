package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterQuestionario;
import com.example.helperinolympics.databinding.ActivityQuestionarioBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.questionario.Questionario;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class QuestionarioActivity extends AppCompatActivity {
    AdapterQuestionario adapter;
    List<Questionario> listaQuestionario = new ArrayList<>();

    ActivityQuestionarioBinding binding;

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String siglaOlimpiada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recebendo os dados da outra tela
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");

        configurarDetalhesTela(siglaOlimpiada, conteudo);

        configurarRecyclerQuestionario();

        findViewById(R.id.imgButtonVoltarConteudo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, InicioOlimpiadaActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("siglaOlimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnTextoPeloQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, TextoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideoPeloQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, VideoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, FlashcardActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarDetalhesTela(String siglaOlimpiada, Conteudo conteudo) {

        binding.txtTema.setText(conteudo.getTituloConteudo());

        switch (siglaOlimpiada){
            case "OBA":
                binding.txtNomeOlimp.setText("OBA");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgtelescopio);
                break;
            case "OBF":
                binding.txtNomeOlimp.setText("OBF");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgmacacaindo);
                break;
            case "OBI":
                binding.txtNomeOlimp.setText("OBI");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgcomputador);
                break;
            case "OBMEP":
                binding.txtNomeOlimp.setText("OBMEP");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgoperacoesbasicas);
                break;
            case "ONHB":
                binding.txtNomeOlimp.setText("ONHB");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgpapiro);
                break;
            case "OBQ":
                binding.txtNomeOlimp.setText("OBQ");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgtubodeensaio);
                break;
            case "OBB":
                binding.txtNomeOlimp.setText("OBB");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgdna);
                break;
            case "ONC":
                binding.txtNomeOlimp.setText("ONC");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgatomo);
                break;
        }

    }

    private void configurarRecyclerQuestionario() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerviewQuestionario.setLayoutManager(layoutManager);

        Integer idConteudo = conteudo.getId();

        if (idConteudo != null) {
            new QuestionariosDownload().execute(idConteudo);
        } else {
            Log.d("ERRO_ID_CONTEUDO", "O id do conteúdo está nulo");
        }

        adapter = new AdapterQuestionario(listaQuestionario, alunoCadastrado, conteudo, siglaOlimpiada);
        binding.recyclerviewQuestionario.setAdapter(adapter);
    }

    private class QuestionariosDownload extends AsyncTask<Integer, Void, List<Questionario>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Questionario> doInBackground(Integer... params) {
            int idConteudo = params[0];
            Log.d("ID_CONTEUDO_RECEBIDO", "Id conteúdo Recebido: " + idConteudo);

            List<Questionario> questionarios = new ArrayList<>();
            try {
                String urlString = "http://10.100.51.3:8086/phpHio/carregaQuestionarioPorConteudo.php?idConteudoPertencente=" +
                        URLEncoder.encode(String.valueOf(idConteudo), "UTF-8");
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
                    listaQuestionario.addAll(converterParaList(jsonString));
                    questionarios.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questionarios;
        }

        @Override
        protected void onPostExecute(List<Questionario> questionarios) {
            super.onPostExecute(questionarios);
            if (questionarios != null) {
                adapter.atualizarOpcoes(questionarios);
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

        private List<Questionario> converterParaList(String jsonString) {
            List<Questionario> questionarios = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("questionarios");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject questionarioJSON = jsonArray.getJSONObject(i);
                    Questionario questionario = new Questionario();

                    questionario.setId(questionarioJSON.getInt("id"));

                    int idConteudo = conteudo.getId();

                    questionario.setIdConteudoPertencente(idConteudo);
                    questionario.setTitulo(questionarioJSON.getString("titulo"));
                    questionario.setProfessorCadastrou(questionarioJSON.getString("profQuePostou"));

                    Log.d("Questionário ", questionario.toString());
                    questionarios.add(questionario);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questionarios;
        }
    }
}
