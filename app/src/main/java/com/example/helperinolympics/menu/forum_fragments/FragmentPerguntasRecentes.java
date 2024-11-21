package com.example.helperinolympics.menu.forum_fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.adapter.forum.AdapterPerguntasForum;
import com.example.helperinolympics.databinding.FragmentForumPerguntasRecentesBinding;
import com.example.helperinolympics.model.forum.PerguntasForum;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentPerguntasRecentes  extends Fragment {

    private FragmentForumPerguntasRecentesBinding binding;
    private AdapterPerguntasForum adapter;
    private ArrayList<PerguntasForum> perguntasF = new ArrayList<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Se a data na API for yyyy-MM-dd


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentForumPerguntasRecentesBinding.inflate(inflater, container, false);
        configurarRecyclerPerguntasForum();
        return binding.getRoot();
    }

    public void configurarRecyclerPerguntasForum(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter= new AdapterPerguntasForum(perguntasF, getContext());
        binding.recyclerPerguntasRecentes.setLayoutManager(layoutManager);
        binding.recyclerPerguntasRecentes.setHasFixedSize(true);
        binding.recyclerPerguntasRecentes.setAdapter(adapter);

       new CarregaPerguntas().execute();
    }

    private class CarregaPerguntas extends AsyncTask<Void, Void, List<PerguntasForum>> {


        @Override
        protected List<PerguntasForum> doInBackground(Void... voids) {
            List<PerguntasForum> perguntasLista = new ArrayList<>();

            try {
                String urlString = "http://10.0.0.64:8086/phpHio/carregaPerguntaAluno.php";

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

                    perguntasLista.addAll(converterParaPergunta(jsonString));
                    perguntasF.addAll(converterParaPergunta(jsonString));
                } else {
                    Log.d("ERRO_CONEXAO", "Erro ao conectar, código de resposta: " + conexao.getResponseCode());
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return perguntasLista;
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

        private List<PerguntasForum> converterParaPergunta(String jsonString) {
            List<PerguntasForum> perguntas = new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray jsonArray = jsonObject.getJSONArray("listaPerguntas");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject perguntasJSON = jsonArray.getJSONObject(i);

                    String dataPerguntaString = perguntasJSON.getString("dataPublicacao");
                    Date dataPublicacao = converterParaData(dataPerguntaString);

                    PerguntasForum pergunta = new PerguntasForum(perguntasJSON.getInt("id"), perguntasJSON.getInt("totalRespostas"),
                            perguntasJSON.getString("titulo"), perguntasJSON.getString("nomeUsuario"), perguntasJSON.getString("pergunta"),
                            perguntasJSON.getString("siglaOlimpiadaRelacionada"), dataPublicacao);


                    String fotoBase64 = perguntasJSON.optString("fotoPerfil", null);

                    if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                        Bitmap bitmapFoto = decodeBase64ToBitmap(fotoBase64);
                        pergunta.setFotoPerfil(bitmapFoto);
                    } else {
                        pergunta.setFotoPerfil(null);
                    }


                    Log.d("Pergunta", pergunta.toString());
                    perguntas.add(pergunta);
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return perguntas;
        }

        @Override
        protected void onPostExecute(List<PerguntasForum> perguntasLista) {
            if (!perguntasLista.isEmpty()) {
                perguntasF.clear();
                perguntasF.addAll(perguntasLista);
                adapter.notifyDataSetChanged();
            } else {
                Log.d("LISTA_VAZIA", "Nenhuma pergunta carregada");
            }
        }

    }

    public Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    private Date converterParaData(String dataString) {
        try {
            Date date = apiDateFormat.parse(dataString);
            String dataFormatada = dateFormat.format(date);
            Log.d("DATA_FORMATADA", dataFormatada);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<PerguntasForum> retornaListaAtual(){
        return this.perguntasF;
    }

    public void alterarListaPorPesquisa(ArrayList<PerguntasForum> listaFiltrada){
        Log.d("ALTERAR_LISTA", "Alterando a lista com " + listaFiltrada.size() + " itens.");

        this.perguntasF.clear();
        this.perguntasF.addAll(listaFiltrada);
        adapter.notifyDataSetChanged();
    }

}
