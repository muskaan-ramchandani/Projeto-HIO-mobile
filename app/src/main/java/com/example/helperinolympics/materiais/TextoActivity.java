package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterTexto;
import com.example.helperinolympics.databinding.ActivityTextoBinding;
import com.example.helperinolympics.model.DadosAluno;
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.model.DadosTexto;
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

public class TextoActivity extends AppCompatActivity {
    private AdapterTexto adapter;
    private List<DadosTexto> listaTexto = new ArrayList<>();

    private ActivityTextoBinding binding;

    private DadosAluno alunoCadastrado;
    private DadosConteudo conteudo;
    private String siglaOlimpiada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recebendo os dados da outra tela
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");


        //iniciando configurações
        configurarDetalhesTela(siglaOlimpiada);
        configurarRecyclerTexto();


        findViewById(R.id.imgButtonVoltarAOlimpDoTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, InicioOlimpiadaActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("siglaOlimpiada", siglaOlimpiada);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        findViewById(R.id.btnQuestionarioPeloTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, QuestionarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideoPeloTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, VideoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, FlashcardActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarDetalhesTela(String siglaOlimpiada) {

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

    public void configurarRecyclerTexto() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerviewTexto.setLayoutManager(layoutManager);
        binding.recyclerviewTexto.setHasFixedSize(true);

        Integer idConteudo = conteudo.getId();

        if (idConteudo != null) {
            new TextosDownload().execute(idConteudo);
        } else {
            Log.d("ERRO_ID_CONTEUDO", "O id do conteúdo está nulo");
        }

        adapter = new AdapterTexto(listaTexto, alunoCadastrado, conteudo);
        binding.recyclerviewTexto.setAdapter(adapter);
    }

    private class TextosDownload extends AsyncTask<Integer, Void, List<DadosTexto>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<DadosTexto> doInBackground(Integer... params) {
            int idConteudo = params[0];
            Log.d("ID_CONTEUDO_RECEBIDO", "Id conteúdo Recebido: " + idConteudo);

            List<DadosTexto> textos = new ArrayList<>();
            try {
                String urlString = "http://192.168.1.9:8086/phpHio/carregaTextoPorConteudo.php?idConteudoPertencente=" +
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

                InputStream in = conexao.getInputStream();

                try{
                    if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        String jsonString = converterParaJSONString(in);
                        Log.d("DADOS", jsonString);
                        listaTexto.addAll(converterParaList(jsonString));
                        textos.addAll(converterParaList(jsonString));
                    }

                }finally {
                    if (in != null) {
                        in.close();
                    }
                    conexao.disconnect();
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return textos;
        }

        @Override
        protected void onPostExecute(List<DadosTexto> textos) {
            super.onPostExecute(textos);
            if (textos != null) {
                adapter.atualizarOpcoes(textos);
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

        private List<DadosTexto> converterParaList(String jsonString) {
            List<DadosTexto> textos = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("textos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject textoJSON = jsonArray.getJSONObject(i);
                    DadosTexto texto = new DadosTexto();

                    texto.setId(textoJSON.getInt("id"));
                    texto.setTitulo(textoJSON.getString("titulo"));
                    texto.setTexto(textoJSON.getString("texto"));
                    texto.setProfessorCadastrou(textoJSON.getString("profQuePostou"));
                    texto.setConteudoPertencente(conteudo.getId());

                    Log.d("texto", texto.toString());
                    textos.add(texto);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return textos;
        }
    }
}
