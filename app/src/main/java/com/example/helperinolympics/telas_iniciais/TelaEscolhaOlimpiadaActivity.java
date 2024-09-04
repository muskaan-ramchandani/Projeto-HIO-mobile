package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterEscolhaOlimpiadas;
import com.example.helperinolympics.cadastros.CadastroActivity;
import com.example.helperinolympics.model.DadosOlimpiada;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TelaEscolhaOlimpiadaActivity extends AppCompatActivity {

    RecyclerView rvOlimpiadasEscolher;
    List<DadosOlimpiada> listaOlimpiadasOpcoes = new ArrayList<>();
    AdapterEscolhaOlimpiadas adapter = new AdapterEscolhaOlimpiadas(listaOlimpiadasOpcoes);
    AppCompatButton btnFinalizar;
    ImageView btnVoltarAoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_escolha_olimpiada);

//        configurarRecycler();

        btnFinalizar = findViewById(R.id.btnFinalizarEscolha);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolhaOlimpiadaActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putParcelableArrayListExtra("listaEscolhidas", new ArrayList<>(adapter.getListaEscolhidas()));
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        btnVoltarAoCadastro = findViewById(R.id.btnVoltarAoCadastro);
        btnVoltarAoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolhaOlimpiadaActivity.this, CadastroActivity.class);
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
        rvOlimpiadasEscolher = findViewById(R.id.recyclerViewEscolhaOlimpiadas);
        rvOlimpiadasEscolher.setLayoutManager(layoutManager);
        rvOlimpiadasEscolher.setHasFixedSize(true);
        rvOlimpiadasEscolher.setAdapter(adapter);

        OlimpiadaDownload olimpDownload = new OlimpiadaDownload();
        olimpDownload.execute();

        adapter.notifyDataSetChanged(); //atualizar o recycler
    }


    //PHP E MYSQL
    class OlimpiadaDownload extends AsyncTask<URL, Void, List<DadosOlimpiada>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<DadosOlimpiada> olimpiadas) {
            super.onPostExecute(olimpiadas);
            adapter.atualizarOpcoes(olimpiadas);
            adapter.notifyDataSetChanged(); // Atualiza o RecyclerView
//            String[] siglas = new String[olimpiadas.size()];
//            for (int i = 0; i < olimpiadas.size(); i++){
//                DadosOlimpiada olimp = olimpiadas.get(i);
//                siglas[i] = olimp.getSigla();
//            }
//            FotoDownload fotoDownload = new FotoDownload();
//            fotoDownload.execute(siglas);
        }


        @Override
        protected List<DadosOlimpiada> doInBackground(URL... urls) {
            List<DadosOlimpiada> olimpiadas = new ArrayList<>();
            Log.d("CONEXAO", "tentando fazer dowload");
            try {
                URL url = new URL("http://192.168.204.214:8086/phpHio/carregaOlimpiadas.php");
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
                    olimpiadas.addAll(converterParaList(jsonString));
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return olimpiadas;
        }


        private List<DadosOlimpiada> converterParaList(String jsonString) {
            List<DadosOlimpiada> olimpiadas = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("olimpiadas");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject olimpJSON = jsonArray.getJSONObject(i);
                    DadosOlimpiada olimp= new DadosOlimpiada();
                    olimp.setNome(olimpJSON.getString("nome"));
                    olimp.setSigla(olimpJSON.getString("sigla"));

//                    int icone = olimpJSON.getString("icone").hashCode();
//                    olimp.setIconeOlimp(icone);

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


    class FotoDownload extends AsyncTask<String, Void, Bitmap[]> {

        private String[] siglas;

        @Override
        protected void onPostExecute(Bitmap[] foto) {
            super.onPostExecute(foto);
            adapter.atualizaFoto(siglas, foto);
        }


        @Override
        protected Bitmap[] doInBackground(String[] sigla) {
            this.siglas = sigla;
            Bitmap[] fotos = new Bitmap[sigla.length];
            for (int i = 0; i < siglas.length; i++) {
                String siglaOlimp = siglas[i];
                Log.d("Sigla Olimpiada", "" + siglaOlimp);
                try {
                    URL url = new URL("http://192.168.204.214:8086/phpHio/carregaImagem.php?sigla=" + siglaOlimp);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    conexao.setReadTimeout(5000);
                    conexao.setConnectTimeout(5000);
                    conexao.setRequestMethod("GET");
                    conexao.setDoInput(true);
                    conexao.setDoOutput(false);


                    conexao.connect();
                    if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream in = conexao.getInputStream();
                        Bitmap foto = BitmapFactory.decodeStream(in);
                        fotos[i] = foto;
                    } else {
                        fotos[i] = null;
                        Log.d("EntradaDeDados", "Problema para receber os dados!");
                    }
                } catch (Exception e) {
                    fotos[i] = null;
                    Log.d("EntradaDeDados", "Problema para receber os dados!");
                }
            }
            return fotos;
        }
    }


}
