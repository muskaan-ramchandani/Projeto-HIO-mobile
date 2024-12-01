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
                URL url = new URL("http://10.0.0.64:8086/phpHio/carregaHistoricoAcessosAluno.php?emailAluno=" + emailAluno);
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
            ArrayList<ProvasAnteriores> provas = new ArrayList<>();
            ArrayList<Texto> textos = new ArrayList<>();
            ArrayList<Video> videos = new ArrayList<>();
            ArrayList<Flashcard> flashcards = new ArrayList<>();
            ArrayList<Questionario> questionarios = new ArrayList<>();

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

                                ProvasAnteriores prova = new ProvasAnteriores(
                                        item.getInt("id"),
                                        item.getInt("anoDaProva"),
                                        item.getInt("fase"),
                                        item.getBoolean("estado"),
                                        item.getString("profQuePostou"),
                                        item.getString("siglaOlimpiadaPertencente"),
                                        arquivoPdfBytes
                                );

                                provas.add(prova);
                                break;
                            case "textos":
                                Texto texto = new Texto(
                                        item.getInt("id"),
                                        item.getString("titulo"),
                                        item.getString("profQuePostou"),
                                        item.getInt("idConteudoPertencente"),
                                        item.getString("texto")
                                );

                                textos.add(texto);
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

                                videos.add(video);
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

                                flashcards.add(flashcard);
                                break;
                            case "questionarios":
                                Questionario questionario = new Questionario(
                                        item.getInt("id"),
                                        item.getString("titulo"),
                                        item.getString("profQuePostou"),
                                        item.getInt("idConteudoPertencente")
                                );
                                questionarios.add(questionario);
                                break;
                        }
                    } catch (Exception e) {
                        Log.d("ERRO", "Erro ao processar item: " + e.getMessage());
                    }
                }
            }




            Log.d("RESULTADOS", "Provas: " + provas.size());
            if(provas.isEmpty()){
                binding.linearHistoricoProvas.removeView(binding.recyclerViewHistoricoProvas);
                LayoutInflater inflater = LayoutInflater.from(HistoricoDeAcessos.this);
                View newItemView = inflater.inflate(R.layout.msg_sem_historico, binding.linearHistoricoProvas, false);

                binding.linearHistoricoProvas.addView(newItemView);
            }else{
                configurarRecyclerHistoricoProvas();
            }


            Log.d("RESULTADOS", "Textos: " + textos.size());
            if(textos.isEmpty()){
                binding.linearHistoricoTextos.removeView(binding.recyclerViewHistoricoTexto);
                LayoutInflater inflater = LayoutInflater.from(HistoricoDeAcessos.this);
                View newItemView = inflater.inflate(R.layout.msg_sem_historico, binding.linearHistoricoTextos, false);

                binding.linearHistoricoTextos.addView(newItemView);
            }else{
                configurarRecyclerHistoricoTxt();
            }


            Log.d("RESULTADOS", "Vídeos: " + videos.size());
            if(videos.isEmpty()){
                binding.linearHistoricoVideos.removeView(binding.recyclerViewHistoricoVideo);
                LayoutInflater inflater = LayoutInflater.from(HistoricoDeAcessos.this);
                View newItemView = inflater.inflate(R.layout.msg_sem_historico, binding.linearHistoricoVideos, false);

                binding.linearHistoricoVideos.addView(newItemView);
            }else{
                configurarRecyclerHistoricoVideos();
            }


            Log.d("RESULTADOS", "Flashcards: " + flashcards.size());
            if(flashcards.isEmpty()){
                binding.linearHistoricoFlashcards.removeView(binding.recyclerViewHistoricoFlashcards);
                LayoutInflater inflater = LayoutInflater.from(HistoricoDeAcessos.this);
                View newItemView = inflater.inflate(R.layout.msg_sem_historico, binding.linearHistoricoFlashcards, false);

                binding.linearHistoricoFlashcards.addView(newItemView);
            }else{
                configurarRecyclerHistoricoFlashcards();
            }


            Log.d("RESULTADOS", "Questionários: " + questionarios.size());
            if(questionarios.isEmpty()){
                binding.linearHistoricoQuestionarios.removeView(binding.recyclerViewHistoricoQuestionario);
                LayoutInflater inflater = LayoutInflater.from(HistoricoDeAcessos.this);
                View newItemView = inflater.inflate(R.layout.msg_sem_historico, binding.linearHistoricoQuestionarios, false);

                binding.linearHistoricoQuestionarios.addView(newItemView);
            }else{
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
        adapterProvas=new AdapterProvasAnteriores(listaProvasHistorico);
        binding.recyclerViewHistoricoProvas.setAdapter(adapterProvas);

        adapterProvas.notifyDataSetChanged();
    }

    private void configurarRecyclerHistoricoVideos() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        binding.recyclerViewHistoricoVideo.setLayoutManager(layoutManager);
        binding.recyclerViewHistoricoVideo.setHasFixedSize(true);
        adapterVideo=new AdapterVideo(listaVideoHistorico);
        binding.recyclerViewHistoricoVideo.setAdapter(adapterVideo);

        adapterVideo.notifyDataSetChanged();
    }
}