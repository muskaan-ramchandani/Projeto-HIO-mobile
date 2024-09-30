package com.example.helperinolympics;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterCorrecao;
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
    List<Correcao> listaCorrecao = new ArrayList<>();

    RecyclerView rVListaCorrecao;
    AdapterCorrecao correcaoAdapter;
    ImageView hipoTristeOUFeliz;
    TextView txtViewNumeroCertas, txtViewQuestoesTotais;
    int qntdTotal, qntdAcertos, metadeValor;

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String siglaOlimpiada;
    private Questionario quest;
    private Date dataAtual;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_correcao);


        quest = getIntent().getParcelableExtra("questionario");
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");
        dataAtual = (Date) getIntent().getSerializableExtra("dataAtual");


        hipoTristeOUFeliz = findViewById(R.id.imgHipoTristeOuFeliz);
        txtViewNumeroCertas= findViewById(R.id.txtNumeroQuestaoCertas);
        txtViewQuestoesTotais= findViewById(R.id.txtQuestoesTotais);


        qntdTotal= 20;
        metadeValor= qntdTotal/2;
        qntdAcertos=Integer.parseInt(txtViewNumeroCertas.getText().toString());

        if(qntdAcertos>metadeValor){
            hipoTristeOUFeliz.setImageResource(R.drawable.hipoalegredeolhosabertos);
        }else if(qntdAcertos<metadeValor){
            hipoTristeOUFeliz.setImageResource(R.drawable.hipoemo);
        }

        listaCorrecao();

        findViewById(R.id.btnEntendiCorrecao).setOnClickListener(new View.OnClickListener() {
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
        rVListaCorrecao = findViewById(R.id.recyclerViewListaCorrecao);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rVListaCorrecao.setLayoutManager(layoutManager);
        rVListaCorrecao.setHasFixedSize(true);

        correcaoAdapter = new AdapterCorrecao(listaCorrecao);
        rVListaCorrecao.setAdapter(correcaoAdapter);

        new CarregaCorrecao(alunoCadastrado.getEmail(), dataAtual, quest.getId());

        correcaoAdapter.notifyDataSetChanged();
    }

    private class CarregaCorrecao extends AsyncTask<Void, Void, Correcao> {
        String email;
        Date dataErro;
        int idQuestionario;

        public CarregaCorrecao(String email, Date dataErro, int idQuestionario){
            this.email = email;
            this.dataErro=dataErro;
            this.idQuestionario=idQuestionario;
        }

        @Override
        protected Correcao doInBackground(Void... voids) {
            Correcao correcao = new Correcao();


            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String dataErroFormatada = dateFormat.format(dataErro);
                String urlString = "http://192.168.1.11:8086/phpHio/carregaCorrecao.php?emailAluno=" + URLEncoder.encode(email, "UTF-8") +
                        "&dataErro=" + URLEncoder.encode(dataErroFormatada, "UTF-8") +
                        "&idQuestionario=" + URLEncoder.encode(String.valueOf(idQuestionario), "UTF-8");

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

                    correcao = converterParaCorrecao(jsonString);
                } else {
                    Log.d("ERRO_CONEXAO", "Erro ao conectar, código de resposta: " + conexao.getResponseCode());
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return correcao;
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

        private Correcao converterParaCorrecao(String jsonString) {
            Correcao correcao = new Correcao();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                int totalErros = jsonObject.getInt("totalErros");

                qntdTotal= jsonObject.getInt("totalQuestoes");;
                metadeValor= qntdTotal/2;
                qntdAcertos=qntdTotal - totalErros;

                List<Correcao> correcoes = new ArrayList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("questoesComErros");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject correcaoJSON = jsonArray.getJSONObject(i);

                    correcao.setExplicacao(correcaoJSON.getString("explicacaoResposta"));
                    correcao.setPergunta(correcaoJSON.getString("txtPergunta"));
                    correcao.setAlternativaCorreta(correcaoJSON.getString("txtAlternativaCorreta"));

                    Log.d("Correcao", correcao.toString());
                    correcoes.add(correcao);
                    listaCorrecao.add(correcao);
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return correcao;
        }
    }

}
