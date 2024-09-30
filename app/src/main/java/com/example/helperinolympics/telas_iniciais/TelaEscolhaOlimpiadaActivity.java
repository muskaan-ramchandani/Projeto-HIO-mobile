package com.example.helperinolympics.telas_iniciais;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterEscolhaOlimpiadas;
import com.example.helperinolympics.cadastros.CadastroActivity;
import com.example.helperinolympics.databinding.ActivityTelaEscolhaOlimpiadaBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Olimpiada;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TelaEscolhaOlimpiadaActivity extends AppCompatActivity {

    Aluno alunoCadastrado;
    ActivityTelaEscolhaOlimpiadaBinding binding;
    List<Olimpiada> listaOlimpiadasOpcoes = new ArrayList<>();
    AdapterEscolhaOlimpiadas adapter = new AdapterEscolhaOlimpiadas(listaOlimpiadasOpcoes);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaEscolhaOlimpiadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intentGet = getIntent();

        if(intentGet!=null){
            alunoCadastrado = intentGet.getParcelableExtra("alunoCadastrado");
        }


        binding.btnFinalizarEscolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Olimpiada> listaEscolhidas = new ArrayList<>(adapter.getListaEscolhidas());

                //deve ter pelo menos 1 escolha de olimpíada
                if(!listaEscolhidas.isEmpty()){
                    new CadastrarOlimpiadasSelecionadas().execute(listaEscolhidas);
                }else{
                    Toast.makeText(TelaEscolhaOlimpiadaActivity.this, "Você deve escolher pelo menos uma olimpíada para prosseguir.", Toast.LENGTH_LONG);
                }
            }
        });

        binding.btnVoltarAoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolhaOlimpiadaActivity.this, CadastroActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish(); //fechar activity
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarRecycler();
    }

    private void configurarRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewEscolhaOlimpiadas.setLayoutManager(layoutManager);
        binding.recyclerViewEscolhaOlimpiadas.setHasFixedSize(true);
        binding.recyclerViewEscolhaOlimpiadas.setAdapter(adapter);

        OlimpiadaDownload olimpDownload = new OlimpiadaDownload();
        olimpDownload.execute();

        adapter.notifyDataSetChanged(); //atualizar o recycler
    }


    //PHP E MYSQL
    class OlimpiadaDownload extends AsyncTask<URL, Void, List<Olimpiada>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Olimpiada> olimpiadas) {
            super.onPostExecute(olimpiadas);
            adapter.atualizarOpcoes(olimpiadas);
            adapter.notifyDataSetChanged();
        }


        @Override
        protected List<Olimpiada> doInBackground(URL... urls) {
            List<Olimpiada> olimpiadas = new ArrayList<>();
            Log.d("CONEXAO", "tentando fazer dowload");
            try {
                URL url = new URL("http://192.168.1.11:8086/phpHio/carregaOlimpiadas.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");
                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
                    Log.d("CONEXAO", "Conexão estabelecida");
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    Log.d("DADOS", jsonString);
                    listaOlimpiadasOpcoes.addAll(converterParaList(jsonString));
                    olimpiadas.addAll(converterParaList(jsonString));
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return olimpiadas;
        }


        //Setando valores na lista
        private List<Olimpiada> converterParaList(String jsonString) {
            List<Olimpiada> olimpiadas = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("olimpiadas");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject olimpJSON = jsonArray.getJSONObject(i);
                    Olimpiada olimp= new Olimpiada();
                    olimp.setNome(olimpJSON.getString("nome"));
                    olimp.setSigla(olimpJSON.getString("sigla"));

                    String nomeDrawable = olimpJSON.getString("icone");

                    //Procurar drawable pelo nome no banco
                    Context context = TelaEscolhaOlimpiadaActivity.this;
                    Resources resources = context.getResources();
                    int drawableId = resources.getIdentifier(nomeDrawable, "drawable",
                            context.getPackageName());

                    if(drawableId!=0){
                        olimp.setIconeOlimp(drawableId);
                    }else{
                        olimp.setIconeOlimp(R.drawable.baseline_disabled_by_default_24);
                    }

                    olimp.setCor(olimpJSON.getString("cor"));

                    olimpiadas.add(olimp);
                    Log.d("Olimp", olimpiadas.toString());
                }
            }catch (Exception e){
                Log.d("ERRO", e.toString());
            }

            return olimpiadas;
        }


        private String converterParaJSONString(InputStream in) {
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream dados = new ByteArrayOutputStream();
            try{
                while(true) {
                    int qtdqBytesLido = in.read(buffer, 0, buffer.length);
                    if (qtdqBytesLido == -1)
                        break;
                    dados.write(buffer, 0, qtdqBytesLido);
                }
            }catch (Exception e){
                Log.d("ERRO", e.toString());
            }
            return dados.toString();
        }
    }

    private class CadastrarOlimpiadasSelecionadas extends AsyncTask<List<Olimpiada>, Void, String> {
        @Override
        protected String doInBackground(List<Olimpiada>... olimpSelecao) {
            StringBuilder result = new StringBuilder();
            Log.d("CONEXAO", "Tentando cadastro de olimpíadas selecionadas");

            List<Olimpiada> olimpiadasSelecionadas = olimpSelecao[0];

            try {
                for(Olimpiada olimp : olimpiadasSelecionadas){
                    URL url = new URL("http://192.168.1.11:8086/phpHio/cadastraOlimpiadasSelecionadas.php");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    conexao.setReadTimeout(1500);
                    conexao.setConnectTimeout(500);
                    conexao.setRequestMethod("POST");
                    conexao.setDoInput(true);
                    conexao.setDoOutput(true);
                    conexao.connect();
                    Log.d("CONEXAO", "Conexão estabelecida");

                    String parametros = "sigla=" + olimp.getSigla() +
                            "&emailAluno=" + alunoCadastrado.getEmail();

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
                }

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

                    Toast.makeText(TelaEscolhaOlimpiadaActivity.this, message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        Intent intent = new Intent(TelaEscolhaOlimpiadaActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                        intent.putExtra("alunoCadastrado", alunoCadastrado);
                        startActivity(intent);
                        finish(); //fechar activity
                    }

                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }

}
