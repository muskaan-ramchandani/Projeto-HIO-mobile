package com.example.helperinolympics.menu.forum_fragments;

import android.app.Activity;
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
import com.example.helperinolympics.menu.ForumActivity;
import com.example.helperinolympics.model.forum.OlimpiadaForum;

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
    int perguntasOCM = 0;
    int perguntasMOBFOG = 0;
    int perguntasOBG = 0;
    int perguntasOBLE = 0;
    int perguntasOBA = 0;
    int perguntasOBF = 0;
    int perguntasOBI = 0;
    int perguntasOBMEP = 0;
    int perguntasONHB = 0;
    int perguntasOBQ = 0;
    int perguntasOBB = 0;
    int perguntasONC = 0;
    int perguntasOLP = 0;
    int perguntasOBRl = 0;
    int perguntasOBR = 0;
    int perguntasOBSMA = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForumTudoBinding.inflate(inflater, container, false);

        if (getActivity() != null) {
            contexto = getActivity();
        }

        new CarregarContagemPerguntasTask().execute();

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

        binding.getRoot().postDelayed(this::configurandoParaPesquisar, 50);
    }

    public void configurarRecyclerOlimpiadasForum() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new AdapterOlimpiadasForum(olimpiadasF, this);
        binding.recyclerPerguntasPorOlimpiada.setLayoutManager(layoutManager);
        binding.recyclerPerguntasPorOlimpiada.setHasFixedSize(true);
        binding.recyclerPerguntasPorOlimpiada.setAdapter(adapter);
    }

    @Override
    public void onOlimpiadaClick(OlimpiadaForum olimp) {
        clickCount++;

        //Alternando fragmentos com base no número de cliques
        if (clickCount % 2 == 0) {
            setChildFragment(new FragmentPerguntasRecentes());

        } else {
            if(olimp.getQntdPerguntasRelacionadas()<=0){
                Toast.makeText(contexto, "Não existem perguntas relacionadas a esta olimpíada.", Toast.LENGTH_LONG).show();
            }else{
                setChildFragment(new FragmentPerguntasPorOlimpiada(olimp.getSiglaOlimpiada()));
            }
        }
    }

    private void configurandoParaPesquisar() {
        Activity activity = getActivity();

        if (activity != null && activity instanceof ForumActivity) {
            ((ForumActivity) activity).atribuindoFragmentsParaConfigurarPesquisa();
        } else {
            Log.d("ERRO_PESQUISA", "ForumActivity não foi detectado ou é nulo");
        }
    }


    public class CarregarContagemPerguntasTask extends AsyncTask<Void, Void, HashMap<String, Integer>> {

        @Override
        protected HashMap<String, Integer> doInBackground(Void... voids) {
            HashMap<String, Integer> contagemPerguntas = new HashMap<>();
            try {
                String urlString = "https://hio.lat/contaPerguntasPorOlimpiada.php";
                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(5000);

                int responseCode = conexao.getResponseCode();
                Log.d("HTTP", "Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader reader = new InputStreamReader(conexao.getInputStream());
                    StringBuilder resposta = new StringBuilder();
                    int caractere;
                    while ((caractere = reader.read()) != -1) {
                        resposta.append((char) caractere);
                    }

                    String jsonResponse = resposta.toString();
                    Log.d("JSON Response", jsonResponse);

                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONObject contagemPerguntasJson = jsonObject.getJSONObject("contagemPerguntas");

                    for (Iterator<String> iter = contagemPerguntasJson.keys(); iter.hasNext();) {
                        String sigla = iter.next();
                        int totalPerguntas = contagemPerguntasJson.getInt(sigla);
                        contagemPerguntas.put(sigla, totalPerguntas);
                    }
                } else {
                    Log.e("HTTP", "Erro no código de resposta: " + responseCode);
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

                perguntasOCM = contagemPerguntas.getOrDefault("OCM", 0);
                perguntasMOBFOG = contagemPerguntas.getOrDefault("MOBFOG", 0);
                perguntasOBG = contagemPerguntas.getOrDefault("OBG", 0);
                perguntasOBLE = contagemPerguntas.getOrDefault("OBLE", 0);

                perguntasONHB = contagemPerguntas.getOrDefault("ONHB", 0);
                perguntasOBQ = contagemPerguntas.getOrDefault("OBQ", 0);
                perguntasOBB = contagemPerguntas.getOrDefault("OBB", 0);
                perguntasONC = contagemPerguntas.getOrDefault("ONC", 0);

                perguntasOLP = contagemPerguntas.getOrDefault("OLP", 0);
                perguntasOBRl = contagemPerguntas.getOrDefault("OBRL", 0);
                perguntasOBR = contagemPerguntas.getOrDefault("OBR", 0);
                perguntasOBSMA = contagemPerguntas.getOrDefault("OBSMA", 0);


                olimpiadasF.clear();
                olimpiadasF.add(new OlimpiadaForum("OBA", "Rosa", perguntasOBA));
                olimpiadasF.add(new OlimpiadaForum("OBF", "Azul", perguntasOBF));
                olimpiadasF.add(new OlimpiadaForum("OBI", "Laranja", perguntasOBI));
                olimpiadasF.add(new OlimpiadaForum("OBMEP", "Ciano", perguntasOBMEP));

                olimpiadasF.add(new OlimpiadaForum("OCM", "Rosa", perguntasOCM));
                olimpiadasF.add(new OlimpiadaForum("MOBFOG", "Azul", perguntasMOBFOG));
                olimpiadasF.add(new OlimpiadaForum("OBG", "Laranja", perguntasOBG));
                olimpiadasF.add(new OlimpiadaForum("OBLE", "Ciano", perguntasOBLE));

                olimpiadasF.add(new OlimpiadaForum("ONHB", "Rosa", perguntasONHB));
                olimpiadasF.add(new OlimpiadaForum("OBQ", "Azul", perguntasOBQ));
                olimpiadasF.add(new OlimpiadaForum("OBB", "Laranja", perguntasOBB));
                olimpiadasF.add(new OlimpiadaForum("ONC", "Ciano", perguntasONC));

                olimpiadasF.add(new OlimpiadaForum("OLP", "Rosa", perguntasOLP));
                olimpiadasF.add(new OlimpiadaForum("OBRL", "Azul", perguntasOBRl));
                olimpiadasF.add(new OlimpiadaForum("OBR", "Laranja", perguntasOBR));
                olimpiadasF.add(new OlimpiadaForum("OBSMA", "Ciano", perguntasOBSMA));

                adapter.notifyDataSetChanged();
            }
        }
    }

    public Fragment retornaFragmentAtual(){
        return getChildFragmentManager().findFragmentById(R.id.fragmentForumPerguntas);
    }
}
