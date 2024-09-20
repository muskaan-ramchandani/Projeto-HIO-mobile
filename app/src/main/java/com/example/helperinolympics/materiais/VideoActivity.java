package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterVideo;
import com.example.helperinolympics.databinding.ActivityVideoBinding;
import com.example.helperinolympics.model.DadosAluno;
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.model.DadosVideo;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    private AdapterVideo adapter;
    private List<DadosVideo> listaVideo= new ArrayList<>();

    private ActivityVideoBinding binding;

    private DadosAluno alunoCadastrado;
    private DadosConteudo conteudo;
    private String siglaOlimpiada;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding= ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recebendo os dados da outra tela
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");

        configurarDetalhesTela(siglaOlimpiada);
        configurarRecyclerVideo();

        findViewById(R.id.imgButtonVoltarAOlimpPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, InicioOlimpiadaActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("siglaOlimpiada", siglaOlimpiada);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        findViewById(R.id.btnQuestionarioPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, QuestionarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnTextoPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, TextoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, FlashcardActivity.class);
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

    public void configurarRecyclerVideo(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerviewVideo.setLayoutManager(layoutManager);
        binding.recyclerviewVideo.setHasFixedSize(true);

        Integer idConteudo = conteudo.getId();

        if (idConteudo != null) {
            new VideoActivity.VideosDownload().execute(idConteudo);
        } else {
            Log.d("ERRO_ID_CONTEUDO", "O id do conteúdo está nulo");
        }

        adapter=new AdapterVideo(listaVideo);
        binding.recyclerviewVideo.setAdapter(adapter);
    }

    private class VideosDownload extends AsyncTask<Integer, Void, List<DadosVideo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<DadosVideo> doInBackground(Integer... params) {
            int idConteudo = params[0];
            Log.d("ID_CONTEUDO_RECEBIDO", "Id conteúdo Recebido: " + idConteudo);

            List<DadosVideo> videos = new ArrayList<>();
            try {
                String urlString = "http://192.168.1.9:8086/phpHio/carregaVideoPorConteudo.php?idConteudoPertencente=" +
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
                    listaVideo.addAll(converterParaList(jsonString));
                    videos.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return videos;
        }

        @Override
        protected void onPostExecute(List<DadosVideo> videos) {
            super.onPostExecute(videos);
            if (videos != null) {
                adapter.atualizarOpcoes(videos);
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

        private List<DadosVideo> converterParaList(String jsonString) {
            List<DadosVideo> videos = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("videos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject videoJSON = jsonArray.getJSONObject(i);
                    DadosVideo video = new DadosVideo();


                    video.setId(videoJSON.getInt("id"));

                    int idConteudo = conteudo.getId();

                    video.setIdConteudoPertencente(idConteudo);
                    video.setTitulo(videoJSON.getString("titulo"));
                    video.setProfessorRecomendou(videoJSON.getString("profQuePostou"));
                    video.setLinkVideo(videoJSON.getString("link"));


                    String capaBase64 = videoJSON.getString("capa");
                    Bitmap bitmapCapa = decodeBase64ToBitmap(capaBase64);
                    video.setCapaVideo(bitmapCapa);

                    Log.d("Video", video.toString());
                    videos.add(video);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return videos;
        }
    }

    public Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
