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
import com.example.helperinolympics.databinding.FragmentForumPerguntasPorOlimpiadaBinding;
import com.example.helperinolympics.model.forum.PerguntasForum;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentPerguntasPorOlimpiada  extends Fragment {

    private FragmentForumPerguntasPorOlimpiadaBinding binding;
    private AdapterPerguntasForum adapter;
    private ArrayList<PerguntasForum> perguntasF = new ArrayList<>();
    private String siglaOlimpiada;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public FragmentPerguntasPorOlimpiada(String siglaOlimpiada){
        this.siglaOlimpiada = siglaOlimpiada;

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumPerguntasPorOlimpiadaBinding.inflate(inflater, container, false);
        binding.txtPerguntasOlimp.setText("Perguntas relacionadas a "+ siglaOlimpiada+":");

        new CarregaPerguntasPorOlimpiada().execute();
        configurarRecyclerPerguntasForum();
        return binding.getRoot();
    }

    public void configurarRecyclerPerguntasForum(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter= new AdapterPerguntasForum(perguntasF, getContext());
        binding.recyclerPerguntasOlimpiadas.setLayoutManager(layoutManager);
        binding.recyclerPerguntasOlimpiadas.setHasFixedSize(true);
        binding.recyclerPerguntasOlimpiadas.setAdapter(adapter);
    }

    private class CarregaPerguntasPorOlimpiada extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            try {
                String urlString = "http://10.0.0.64:8086/phpHio/carregaPerguntasPorOlimpiada.php?siglaOlimpiada=" + siglaOlimpiada;

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                }
            } catch (Exception e) {
                Log.e("CarregaPerguntasOlimpiada", "Erro na requisição HTTP", e);
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray listaPerguntasOlimpiadaJSON = jsonObject.getJSONArray("listaPerguntasOlimpiada");

                perguntasF.clear();

                for (int i = 0; i < listaPerguntasOlimpiadaJSON.length(); i++) {
                    JSONObject perguntasOlimpiadasJson = listaPerguntasOlimpiadaJSON.getJSONObject(i);

                    String dataPerguntaString = perguntasOlimpiadasJson.getString("dataPublicacao");
                    Date dataPublicacao = converterParaData(dataPerguntaString);

                    PerguntasForum pergunta = new PerguntasForum(
                            perguntasOlimpiadasJson.getInt("id"),
                            perguntasOlimpiadasJson.getInt("totalRespostas"),
                            perguntasOlimpiadasJson.getString("titulo"),
                            perguntasOlimpiadasJson.getString("nomeUsuario"),
                            perguntasOlimpiadasJson.getString("pergunta"),
                            perguntasOlimpiadasJson.getString("siglaOlimpiadaRelacionada"),
                            dataPublicacao
                    );

                    String fotoBase64 = perguntasOlimpiadasJson.getString("fotoPerfil");
                    if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                        Bitmap bitmapFoto = decodeBase64ToBitmap(fotoBase64);
                        pergunta.setFotoPerfil(bitmapFoto);
                    }

                    perguntasF.add(pergunta);
                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.e("CarregaPerguntasOlimpiada", "Erro ao fazer o parse do JSON", e);
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
        this.perguntasF = listaFiltrada;
        adapter.notifyDataSetChanged();
    }
}
