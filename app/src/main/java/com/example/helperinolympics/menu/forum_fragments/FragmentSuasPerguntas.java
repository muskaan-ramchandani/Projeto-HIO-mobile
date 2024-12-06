package com.example.helperinolympics.menu.forum_fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.forum.AdapterPerguntasForum;
import com.example.helperinolympics.databinding.FragmentForumSuasPerguntasBinding;
import com.example.helperinolympics.menu.ForumActivity;
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

public class FragmentSuasPerguntas extends Fragment{
    private FragmentForumSuasPerguntasBinding binding;
    private AdapterPerguntasForum adapterRespondidas;
    private AdapterPerguntasForum adapterNaoRespondidas;
    private ArrayList<PerguntasForum> perguntasRespondidas = new ArrayList<>();
    private ArrayList<PerguntasForum> perguntasNAORespondidas = new ArrayList<>();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Context contexto;

    String emailAluno = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForumSuasPerguntasBinding.inflate(inflater, container, false);

        if (getActivity() != null) {
            contexto = getActivity();
        }

        //pegando dado do email do usuario
        ForumActivity activity = (ForumActivity) getActivity();
        if (activity != null) {
            emailAluno = activity.alunoCadastrado.getEmail();
        }

        new CarregaSuasPerguntas(emailAluno).execute();
        return binding.getRoot();
    }


    public void configurarRecyclerPerguntasForumRespondidas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterRespondidas= new AdapterPerguntasForum(perguntasRespondidas, getContext());
        binding.recyclerSuasPerguntasRespondidas.setLayoutManager(layoutManager);
        binding.recyclerSuasPerguntasRespondidas.setHasFixedSize(true);
        binding.recyclerSuasPerguntasRespondidas.setAdapter(adapterRespondidas);

        adapterRespondidas.notifyDataSetChanged();
    }

    public void configurarRecyclerPerguntasForumNaoRespondidas() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterNaoRespondidas= new AdapterPerguntasForum(perguntasNAORespondidas, getContext());
        binding.recyclerSuasPerguntasSemResposta.setLayoutManager(layoutManager);
        binding.recyclerSuasPerguntasSemResposta.setHasFixedSize(true);
        binding.recyclerSuasPerguntasSemResposta.setAdapter(adapterNaoRespondidas);

        adapterNaoRespondidas.notifyDataSetChanged();
    }

    private class CarregaSuasPerguntas extends AsyncTask<String, Void, String> {
        String emailAluno;

        public CarregaSuasPerguntas(String emailAluno){
            this.emailAluno = emailAluno;
        }
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();

            try {
                String urlString = "https://hio.lat/carregaSuasPerguntas.php?emailAluno=" + emailAluno;

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
                } else {
                    Log.e("Erro HTTP", "Código de resposta: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("CarregaSuasPerguntas", "Erro na requisição HTTP", e);
            }

            Log.d("Resposta da API", result.toString());
            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray listaPerguntasRespondidasJSON = jsonObject.getJSONArray("listaPerguntasRespondidas");
                JSONArray listaPerguntasSemRespostaJSON = jsonObject.getJSONArray("listaPerguntasSemResposta");

                perguntasRespondidas.clear();
                perguntasNAORespondidas.clear();

                //perguntas respondidas
                if(listaPerguntasRespondidasJSON.length()!=0){
                    for (int i = 0; i < listaPerguntasRespondidasJSON.length(); i++) {
                        JSONObject respondidasJson = listaPerguntasRespondidasJSON.getJSONObject(i);

                        String dataPerguntaString = respondidasJson.getString("dataPublicacao");
                        Date dataPublicacao = converterParaData(dataPerguntaString);

                        PerguntasForum pergunta = new PerguntasForum(respondidasJson.getInt("id"), respondidasJson.getInt("totalRespostas"),
                                respondidasJson.getString("titulo"), respondidasJson.getString("nomeUsuario"), respondidasJson.getString("pergunta"),
                                respondidasJson.getString("siglaOlimpiadaRelacionada"), dataPublicacao);


                        String fotoBase64 = respondidasJson.getString("fotoPerfil");

                        if(fotoBase64!=null){
                            Bitmap bitmapFoto= decodeBase64ToBitmap(fotoBase64);
                            pergunta.setFotoPerfil(bitmapFoto);
                        }

                        perguntasRespondidas.add(pergunta);
                    }

                    configurarRecyclerPerguntasForumRespondidas();
                }else{

                    binding.linearPerguntasRespondidas.removeView(binding.recyclerSuasPerguntasRespondidas);
                    LayoutInflater inflater = LayoutInflater.from(contexto);
                    View newItemView = inflater.inflate(R.layout.msg_sem_perguntas, binding.linearPerguntasRespondidas, false);

                    binding.linearPerguntasRespondidas.addView(newItemView);
                }

                //perguntas NAO respondidas
                if(listaPerguntasSemRespostaJSON.length()!=0){
                    for (int i = 0; i < listaPerguntasSemRespostaJSON.length(); i++) {
                        JSONObject semRespostaJson = listaPerguntasSemRespostaJSON.getJSONObject(i);

                        String dataPerguntaString = semRespostaJson.getString("dataPublicacao");
                        Date dataPublicacao = converterParaData(dataPerguntaString);

                        PerguntasForum pergunta = new PerguntasForum(semRespostaJson.getInt("id"), semRespostaJson.getInt("totalRespostas"),
                                semRespostaJson.getString("titulo"), semRespostaJson.getString("nomeUsuario"), semRespostaJson.getString("pergunta"),
                                semRespostaJson.getString("siglaOlimpiadaRelacionada"), dataPublicacao);


                        String fotoBase64 = semRespostaJson.getString("fotoPerfil");

                        if(fotoBase64!=null){
                            Bitmap bitmapFoto= decodeBase64ToBitmap(fotoBase64);
                            pergunta.setFotoPerfil(bitmapFoto);
                        }

                        perguntasNAORespondidas.add(pergunta);
                    }
                    configurarRecyclerPerguntasForumNaoRespondidas();
                }else{

                    binding.linearPerguntasNAORespondidas.removeView(binding.recyclerSuasPerguntasSemResposta);
                    LayoutInflater inflater = LayoutInflater.from(contexto);
                    View newItemView = inflater.inflate(R.layout.msg_sem_perguntas, binding.linearPerguntasNAORespondidas, false);

                    binding.linearPerguntasNAORespondidas.addView(newItemView);
                }

            } catch (JSONException e) {
                Log.e("CarregaSuasPerguntas", "Erro ao fazer o parse do JSON", e);
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

}
