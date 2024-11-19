package com.example.helperinolympics.menu.forum_fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.forum.AdapterOlimpiadasForum;
import com.example.helperinolympics.databinding.FragmentForumTudoBinding;
import com.example.helperinolympics.model.OlimpiadaForum;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class FragmentTudo extends Fragment implements AdapterOlimpiadasForum.OnOlimpiadaClickListener {
    private FragmentForumTudoBinding binding;
    private AdapterOlimpiadasForum adapter;
    private ArrayList<OlimpiadaForum> olimpiadasF = new ArrayList<>();
    private int clickCount = 0;

    private Context contexto;

    //contagens para olimpiadas
    int perguntasOBA = 0;
    int perguntasOBF = 0;
    int perguntasOBI = 0;
    int perguntasOBMEP = 0;
    int perguntasONHB = 0;
    int perguntasOBQ = 0;
    int perguntasOBB = 0;
    int perguntasONC = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForumTudoBinding.inflate(inflater, container, false);

        if (getActivity() != null) {
            contexto = getActivity();
        }

        configurarRecyclerOlimpiadasForum();
        setupFragmentNavigation();
        return binding.getRoot();
    }

    private void setupFragmentNavigation() {
        setChildFragment(new FragmentPerguntasRecentes()); // Fragment inicial
    }

    private void setChildFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForumPerguntas, fragment);
        fragmentTransaction.commit();
    }

    public void configurarRecyclerOlimpiadasForum() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new AdapterOlimpiadasForum(olimpiadasF, this);
        binding.recyclerPerguntasPorOlimpiada.setLayoutManager(layoutManager);
        binding.recyclerPerguntasPorOlimpiada.setHasFixedSize(true);
        binding.recyclerPerguntasPorOlimpiada.setAdapter(adapter);

        new CarregarContagemPerguntasTask().execute();

        //SIMULAÇÃO DE DADOS
        olimpiadasF.add(new OlimpiadaForum("OBA", "Rosa", perguntasOBA));
        olimpiadasF.add(new OlimpiadaForum("OBF", "Azul", perguntasOBF));
        olimpiadasF.add(new OlimpiadaForum("OBI", "Laranja", perguntasOBI));
        olimpiadasF.add(new OlimpiadaForum("OBMEP", "Ciano", perguntasOBMEP));
        olimpiadasF.add(new OlimpiadaForum("ONC", "Ciano", perguntasONC));
        olimpiadasF.add(new OlimpiadaForum("ONHB", "Laranja", perguntasONHB));
        olimpiadasF.add(new OlimpiadaForum("OBQ", "Azul", perguntasOBQ));
        olimpiadasF.add(new OlimpiadaForum("OBB", "Ciano", perguntasOBB));


        adapter.notifyDataSetChanged();
    }

    @Override
    public void onOlimpiadaClick(OlimpiadaForum olimp) {
        clickCount++;

        //Alternando fragmentos com base no número de cliques
        if (clickCount % 2 == 1) {
            if(olimp.getQntdPerguntasRelacionadas()<=0){
                Toast.makeText(contexto, "Não existem perguntas relacionadas a esta olimpíada.", Toast.LENGTH_LONG).show();
            }else{
                setChildFragment(new FragmentPerguntasPorOlimpiada(olimp.getSiglaOlimpiada()));
            }
        } else {
            setChildFragment(new FragmentPerguntasRecentes());
        }
    }

    public class CarregarContagemPerguntasTask extends AsyncTask<Void, Void, HashMap<String, Integer>> {

        @Override
        protected HashMap<String, Integer> doInBackground(Void... voids) {
            HashMap<String, Integer> contagemPerguntas = new HashMap<>();
            try {
                String urlString = "http://10.0.0.64:8086/phpHio/contaPerguntasPorOlimpiada.php";
                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(5000);

                InputStreamReader reader = new InputStreamReader(conexao.getInputStream());
                StringBuilder resposta = new StringBuilder();
                int caractere;
                while ((caractere = reader.read()) != -1) {
                    resposta.append((char) caractere);
                }

                JSONObject jsonObject = new JSONObject(resposta.toString());
                JSONObject contagemPerguntasJson = jsonObject.getJSONObject("contagemPerguntas");

                for (Iterator<String> iter = contagemPerguntasJson.keys(); iter.hasNext();) {
                    String sigla = iter.next();
                    int totalPerguntas = contagemPerguntasJson.getInt(sigla);
                    contagemPerguntas.put(sigla, totalPerguntas);
                }

            } catch (Exception e) {
                Log.e("Erro", "Erro ao carregar contagem de perguntas", e);
            }

            return contagemPerguntas;
        }

        @Override
        protected void onPostExecute(HashMap<String, Integer> contagemPerguntas) {

            if (contagemPerguntas != null && !contagemPerguntas.isEmpty()) {
                perguntasOBA = contagemPerguntas.getOrDefault("OBA", 0);
                perguntasOBF = contagemPerguntas.getOrDefault("OBF", 0);
                perguntasOBI = contagemPerguntas.getOrDefault("OBI", 0);
                perguntasOBMEP = contagemPerguntas.getOrDefault("OBMEP", 0);
                perguntasONHB = contagemPerguntas.getOrDefault("ONHB", 0);
                perguntasOBQ = contagemPerguntas.getOrDefault("OBQ", 0);
                perguntasOBB = contagemPerguntas.getOrDefault("OBB", 0);
                perguntasONC = contagemPerguntas.getOrDefault("ONC", 0);
            }
        }


    }
}
