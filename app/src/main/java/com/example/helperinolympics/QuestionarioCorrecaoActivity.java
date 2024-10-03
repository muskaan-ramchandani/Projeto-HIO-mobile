package com.example.helperinolympics;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.adapter.AdapterCorrecao;
import com.example.helperinolympics.databinding.ActivityQuestionarioCorrecaoBinding;
import com.example.helperinolympics.materiais.QuestionarioActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.Correcao;
import com.example.helperinolympics.model.questionario.Questionario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class QuestionarioCorrecaoActivity extends Activity {
    ActivityQuestionarioCorrecaoBinding binding;
    List<Correcao> listaCorrecao = new ArrayList<>();

    AdapterCorrecao correcaoAdapter;
    int qntdTotal, qntdAcertos;
    double metadeValor;

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String siglaOlimpiada;
    private Questionario quest;
    private Date dataAtual;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionarioCorrecaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        quest = getIntent().getParcelableExtra("questionario");
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");
        dataAtual = (Date) getIntent().getSerializableExtra("dataAtual");

        listaCorrecao();
//
//        binding.txtNumeroQuestaoCertas.setText(String.valueOf(qntdAcertos));
//        binding.txtQuestoesTotais.setText("Questões de " + String.valueOf(qntdTotal));
//
//        if (qntdAcertos == 0) {
//            binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipocomraiva);
//        } else if (qntdAcertos < metadeValor) {
//            binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipoemo);
//        } else if (qntdAcertos > metadeValor) {
//            binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipoalegredeolhosabertos);
//        }

        binding.btnEntendiCorrecao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioCorrecaoActivity.this, QuestionarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });
    }

    public void listaCorrecao(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewListaCorrecao.setLayoutManager(layoutManager);
        binding.recyclerViewListaCorrecao.setHasFixedSize(true);


        new CarregaCorrecao(alunoCadastrado.getEmail(), dataAtual, quest.getId()).execute();

        correcaoAdapter = new AdapterCorrecao(listaCorrecao);
        Log.d("Activity", "Lista de correções passada para o adapter: " + listaCorrecao.size());

        binding.recyclerViewListaCorrecao.setAdapter(correcaoAdapter);
    }

    private class CarregaCorrecao extends AsyncTask<Void, Void, List<Correcao>> {
        String email;
        Date dataErro;
        int idQuestionarioPertencente;

        public CarregaCorrecao(String email, Date dataErro, int idQuestionarioPertencente) {
            this.email = email;
            this.dataErro = dataErro;
            this.idQuestionarioPertencente = idQuestionarioPertencente;
        }

        @Override
        protected List<Correcao> doInBackground(Void... voids) {
            List<Correcao> correcoes = new ArrayList<>();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dataErroFormatada = dateFormat.format(dataErro);
                String urlString = "http://192.168.1.11:8086/phpHio/carregaCorrecao.php?emailAluno=" + URLEncoder.encode(email, "UTF-8") +
                        "&dataErro=" + URLEncoder.encode(dataErroFormatada, "UTF-8") +
                        "&idQuestionarioPertencente=" + URLEncoder.encode(String.valueOf(idQuestionarioPertencente), "UTF-8");

                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(5000);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    Log.d("DADOS", jsonString);

                    correcoes.addAll(converterParaCorrecao(jsonString));
                    listaCorrecao.addAll(converterParaCorrecao(jsonString));
                } else {
                    Log.d("ERRO_CONEXAO", "Erro ao conectar, código de resposta: " + conexao.getResponseCode());
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return correcoes;
        }

        @Override
        protected void onPostExecute(List<Correcao> correcoes) {
            metadeValor = (double) qntdTotal / 2;
            binding.txtNumeroQuestaoCertas.setText(String.valueOf(qntdAcertos));
            binding.txtQuestoesTotais.setText("Questões de " + String.valueOf(qntdTotal));

            if (qntdAcertos == 0) {
                binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipocomraiva);
            } else if (qntdAcertos < metadeValor) {
                binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipoemo);
            } else {
                //qntdAcertos > metadeValor
                binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipoalegredeolhosabertos);
            }

            correcaoAdapter.notifyDataSetChanged();

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

        private List<Correcao> converterParaCorrecao(String jsonString) {
            List<Correcao> correcoes = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                int totalErros = jsonObject.getInt("totalErros");
                qntdTotal = jsonObject.getInt("totalQuestoes");
                qntdAcertos = qntdTotal - totalErros;

                JSONArray jsonArray = jsonObject.getJSONArray("questoesComErros");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject correcaoJSON = jsonArray.getJSONObject(i);

                    Correcao correcao = new Correcao();
                    correcao.setExplicacao(correcaoJSON.getString("explicacaoResposta"));
                    correcao.setPergunta(correcaoJSON.getString("txtPergunta"));
                    correcao.setAlternativaCorreta(correcaoJSON.getString("alternativaCorreta"));

                    Log.d("Correcao", correcao.toString());
                    correcoes.add(correcao);
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return correcoes;
        }
    }


}
