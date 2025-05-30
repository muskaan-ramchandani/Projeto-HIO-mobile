package com.example.helperinolympics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterProvasAnteriores;
import com.example.helperinolympics.adapter.AdapterVideo;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoFlashcard;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoQuestionario;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoTexto;
import com.example.helperinolympics.databinding.ActivityHistoricoDeAcessosBinding;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Flashcard;
import com.example.helperinolympics.model.questionario.Questionario;
import com.example.helperinolympics.model.Texto;
import com.example.helperinolympics.model.ProvasAnteriores;
import com.example.helperinolympics.model.Video;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricoDeAcessos extends AppCompatActivity {

    private ActivityHistoricoDeAcessosBinding binding;
    private Aluno alunoCadastrado;

    private AdapterHistoricoFlashcard adapterFlash;
    private AdapterHistoricoQuestionario adapterQuest;
    private AdapterHistoricoTexto adapterTxt;
    private AdapterProvasAnteriores adapterProvas;
    private AdapterVideo adapterVideo;

    private List<Flashcard> listaFlashcardHistorico= new ArrayList<>();
    private List<ProvasAnteriores> listaProvasHistorico = new ArrayList<>();
    private List<Questionario> listaQuestHistorico = new ArrayList<>();
    private List<Texto> listaTxtHistorico = new ArrayList<>();
    private List<Video> listaVideoHistorico = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoricoDeAcessosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        new CarregaHistorico().execute(alunoCadastrado.getEmail());

        findViewById(R.id.btnVoltarAConfiguracoesHistoricoAcesso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoricoDeAcessos.this, ConfiguracoesActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

    }

    public class CarregaHistorico extends AsyncTask<String, Void, Map<String, JSONArray>> {

        private Map<String, JSONArray> resultados;

        @Override
        protected Map<String, JSONArray> doInBackground(String... params) {
            String emailAluno = params[0];
            resultados = new HashMap<>();

            try {
                URL url = new URL("https://hio.lat/carregaHistoricoAcessosAluno.php?emailAluno=" + emailAluno);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.connect();

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);

                    if (jsonString != null && !jsonString.isEmpty()) {
                        JSONObject jsonResponse = new JSONObject(jsonString);

                        // Processar os dados para cada tipo de material
                        for (String tipo : new String[]{"provas", "textos", "videos", "flashcards", "questionarios"}) {
                            if (jsonResponse.has(tipo)) {
                                resultados.put(tipo, jsonResponse.getJSONArray(tipo));
                            } else {
                                resultados.put(tipo, null);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("ERRO", "Erro ao buscar o histórico de acesso: " + e.getMessage());
            }
            return resultados;
        }

        @Override
        protected void onPostExecute(Map<String, JSONArray> result) {
            super.onPostExecute(result);

            if (result != null && !result.isEmpty()) {
                Log.d("INFO", "Histórico de acesso recebido com sucesso.");
                processarResultados(result);
            } else {
                binding.linearHistoricoProvas.setVisibility(View.GONE);
                binding.linearSEMHistoricoProvas.setVisibility(View.VISIBLE);
                binding.linearHistoricoTextos.setVisibility(View.GONE);
                binding.linearSEMHistoricoTexto.setVisibility(View.VISIBLE);
                binding.linearHistoricoVideos.setVisibility(View.GONE);
                binding.linearSEMHistoricoVideos.setVisibility(View.VISIBLE);
                binding.linearHistoricoFlashcards.setVisibility(View.GONE);
                binding.linearSEMHistoricoFlashcards.setVisibility(View.VISIBLE);
                binding.linearHistoricoQuestionarios.setVisibility(View.GONE);
                binding.linearSEMHistoricoQuestionario.setVisibility(View.VISIBLE);
                Log.d("ERRO", "Não foi possível carregar o histórico de acesso.");
            }
        }

        private String converterParaJSONString(InputStream in) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                int charRead;
                while ((charRead = in.read()) != -1) {
                    stringBuilder.append((char) charRead);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return stringBuilder.toString();
        }

        private void processarResultados(Map<String, JSONArray> resultados) {
            for (Map.Entry<String, JSONArray> entry : resultados.entrySet()) {
                String tipo = entry.getKey();
                JSONArray dados = entry.getValue();

                if (dados == null) {
                    Log.d("INFO", "Lista do tipo " + tipo + " é nula.");
                    continue;
                }

                for (int i = 0; i < dados.length(); i++) {
                    try {
                        JSONObject item = dados.getJSONObject(i);
                        switch (tipo) {
                            case "provas":
                                byte[] arquivoPdfBytes = null;

                                if (item.isNull("arquivoPdf")) {
                                    Log.e("ErroProva", "O campo arquivoPdf está nulo.");
                                } else {
                                    try {
                                        arquivoPdfBytes = item.getString("arquivoPdf").getBytes("UTF-8");
                                    } catch (Exception e) {
                                        Log.e("ErroProva", "Erro ao converter arquivo PDF", e);
                                    }
                                }

                                boolean respondidaOuNao=false;
                                if(item.getInt("estado")==0){
                                    respondidaOuNao = false;
                                }else{
                                    respondidaOuNao= true;
                                }
                                ProvasAnteriores prova = new ProvasAnteriores(
                                        item.getInt("id"),
                                        item.getInt("anoDaProva"),
                                        item.getInt("fase"),
                                        respondidaOuNao,
                                        item.getString("profQuePostou"),
                                        item.getString("siglaOlimpiadaPertencente"),
                                        arquivoPdfBytes
                                );

                                listaProvasHistorico.add(prova);
                                break;
                            case "textos":
                                Texto texto = new Texto(
                                        item.getInt("id"),
                                        item.getString("titulo"),
                                        item.getString("profQuePostou"),
                                        item.getInt("idConteudoPertencente"),
                                        item.getString("texto")
                                );

                                listaTxtHistorico.add(texto);
                                break;
                            case "videos":
                                String videoBase64 = item.optString("capa", null);
                                Bitmap videoBitmap = null;

                                if (videoBase64 != null && !videoBase64.isEmpty()) {
                                    try {
                                        byte[] decodedString = Base64.decode(videoBase64, Base64.DEFAULT);
                                        videoBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    } catch (Exception e) {
                                        Log.e("ErroImagem", "Erro ao converter a imagem em base64", e);
                                    }
                                }

                                Video video = new Video(
                                        item.getInt("id"),
                                        item.getString("titulo"),
                                        item.getString("link"),
                                        item.getString("profQuePostou"),
                                        item.getInt("idConteudoPertencente"),
                                        videoBitmap
                                );

                                listaVideoHistorico.add(video);
                                break;
                            case "flashcards":
                                String imagemBase64 = item.optString("imagem", null);
                                Bitmap imagemBitmap = null;

                                if (imagemBase64 != null && !imagemBase64.isEmpty()) {
                                    try {
                                        byte[] decodedString = Base64.decode(imagemBase64, Base64.DEFAULT);
                                        imagemBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    } catch (Exception e) {
                                        Log.e("ErroImagem", "Erro ao converter a imagem em base64", e);
                                    }
                                }

                                Flashcard flashcard = new Flashcard(
                                        item.getString("titulo"),
                                        item.getString("texto"),
                                        item.getString("profQuePostou"),
                                        item.getInt("id"),
                                        item.getInt("idConteudoPertencente"),
                                        imagemBitmap
                                );

                                listaFlashcardHistorico.add(flashcard);
                                break;
                            case "questionarios":
                                Questionario questionario = new Questionario(
                                        item.getInt("id"),
                                        item.getString("titulo"),
                                        item.getString("profQuePostou"),
                                        item.getInt("idConteudoPertencente")
                                );
                                listaQuestHistorico.add(questionario);
                                break;
                        }
                    } catch (Exception e) {
                        Log.d("ERRO", "Erro ao processar item: " + e.getMessage());
                    }
                }
            }


            Log.d("RESULTADOS", "Provas: " + listaProvasHistorico.size());
            int tamP = listaProvasHistorico.size();
            if(listaProvasHistorico == null || tamP==0){
                binding.linearHistoricoProvas.setVisibility(View.GONE);
                binding.linearSEMHistoricoProvas.setVisibility(View.VISIBLE);
            }else{
                binding.linearHistoricoProvas.setVisibility(View.VISIBLE);
                binding.linearSEMHistoricoProvas.setVisibility(View.GONE);
                configurarRecyclerHistoricoProvas();
            }


            Log.d("RESULTADOS", "Textos: " + listaTxtHistorico.size());
            int tamT = listaTxtHistorico.size();
            if(listaTxtHistorico == null || tamT==0){
                binding.linearHistoricoTextos.setVisibility(View.GONE);
                binding.linearSEMHistoricoTexto.setVisibility(View.VISIBLE);
            }else{
                binding.linearHistoricoTextos.setVisibility(View.VISIBLE);
                binding.linearSEMHistoricoTexto.setVisibility(View.GONE);
                configurarRecyclerHistoricoTxt();
            }


            Log.d("RESULTADOS", "Vídeos: " + listaVideoHistorico.size());
            int tamV = listaVideoHistorico.size();
            if(listaVideoHistorico == null || tamV==0){
                binding.linearHistoricoVideos.setVisibility(View.GONE);
                binding.linearSEMHistoricoVideos.setVisibility(View.VISIBLE);
            }else{
                binding.linearHistoricoVideos.setVisibility(View.VISIBLE);
                binding.linearSEMHistoricoVideos.setVisibility(View.GONE);
                configurarRecyclerHistoricoVideos();
            }


            Log.d("RESULTADOS", "Flashcards: " + listaFlashcardHistorico.size());
            int tamF = listaFlashcardHistorico.size();
            if(listaFlashcardHistorico == null || tamF==0){
                binding.linearHistoricoFlashcards.setVisibility(View.GONE);
                binding.linearSEMHistoricoFlashcards.setVisibility(View.VISIBLE);

                Log.d("VISIBILIDADE", "Visibilidade hist flash: "+binding.linearHistoricoFlashcards.getVisibility());
                Log.d("VISIBILIDADE", "Visibilidade MSG SEM flash: "+binding.linearSEMHistoricoFlashcards.getVisibility());

            }else{
                binding.linearHistoricoFlashcards.setVisibility(View.VISIBLE);
                binding.linearSEMHistoricoFlashcards.setVisibility(View.GONE);

                configurarRecyclerHistoricoFlashcards();
            }

            Log.d("RESULTADOS", "Questionários: " + listaQuestHistorico.size());
            int tamQ = listaQuestHistorico.size();
            if(listaQuestHistorico == null || tamQ==0){
                binding.linearHistoricoQuestionarios.setVisibility(View.GONE);
                binding.linearSEMHistoricoQuestionario.setVisibility(View.VISIBLE);
            }else{
                binding.linearHistoricoQuestionarios.setVisibility(View.VISIBLE);
                binding.linearSEMHistoricoQuestionario.setVisibility(View.GONE);
                configurarRecyclerHistoricoQuest();
            }

        }
    }

    private void configurarRecyclerHistoricoFlashcards() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewHistoricoFlashcards.setLayoutManager(layoutManager);
        binding.recyclerViewHistoricoFlashcards.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterFlash=new AdapterHistoricoFlashcard(listaFlashcardHistorico, fragmentManager);
        binding.recyclerViewHistoricoFlashcards.setAdapter(adapterFlash);

        adapterFlash.notifyDataSetChanged();
    }

    private void configurarRecyclerHistoricoQuest() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewHistoricoQuestionario.setLayoutManager(layoutManager);
        binding.recyclerViewHistoricoQuestionario.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterQuest=new AdapterHistoricoQuestionario(listaQuestHistorico, fragmentManager);
        binding.recyclerViewHistoricoQuestionario.setAdapter(adapterQuest);

        adapterQuest.notifyDataSetChanged();
    }

    private void configurarRecyclerHistoricoTxt() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewHistoricoTexto.setLayoutManager(layoutManager);
        binding.recyclerViewHistoricoTexto.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterTxt=new AdapterHistoricoTexto(listaTxtHistorico, fragmentManager);
        binding.recyclerViewHistoricoTexto.setAdapter(adapterTxt);

        adapterTxt.notifyDataSetChanged();
    }

    private void configurarRecyclerHistoricoProvas() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewHistoricoProvas.setLayoutManager(layoutManager);
        binding.recyclerViewHistoricoProvas.setHasFixedSize(true);
        adapterProvas=new AdapterProvasAnteriores(listaProvasHistorico, alunoCadastrado);
        binding.recyclerViewHistoricoProvas.setAdapter(adapterProvas);

        adapterProvas.notifyDataSetChanged();
    }

    private void configurarRecyclerHistoricoVideos() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewHistoricoVideo.setLayoutManager(layoutManager);
        binding.recyclerViewHistoricoVideo.setHasFixedSize(true);
        adapterVideo=new AdapterVideo(listaVideoHistorico, alunoCadastrado);
        binding.recyclerViewHistoricoVideo.setAdapter(adapterVideo);

        adapterVideo.notifyDataSetChanged();
    }
}