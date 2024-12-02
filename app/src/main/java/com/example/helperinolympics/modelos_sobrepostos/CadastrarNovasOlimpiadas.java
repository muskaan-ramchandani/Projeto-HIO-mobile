package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterEscolhaOlimpiadas;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Olimpiada;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;
import com.example.helperinolympics.telas_iniciais.TelaEscolhaOlimpiadaActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class CadastrarNovasOlimpiadas extends DialogFragment {
    private Aluno alunoCadastrado;
    private Context contexto;
    private RecyclerView recyclerViewEscolhaMaisOlimpiadas;
    private List<Olimpiada> listaOlimpiadasOpcoes = new ArrayList<>();
    private AdapterEscolhaOlimpiadas adapter = new AdapterEscolhaOlimpiadas(listaOlimpiadasOpcoes);

    public CadastrarNovasOlimpiadas(Aluno alunoCadastrado, Context contexto) {
        this.alunoCadastrado = alunoCadastrado;
        this.contexto = contexto;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_adiciona_mais_olimpiadas, container, false);

        this.recyclerViewEscolhaMaisOlimpiadas = view.findViewById(R.id.recyclerViewEscolhaMaisOlimpiadas);

        view.findViewById(R.id.btnFecharAdicionarMaisOlimpiadas).setOnClickListener(v -> dismiss());

        view.findViewById(R.id.btnFinalizarEscolha).setOnClickListener(v -> {
            ArrayList<Olimpiada> listaEscolhidas = new ArrayList<>(adapter.getListaEscolhidas());
            if (!listaEscolhidas.isEmpty()) {
                new CadastrarOlimpiadasSelecionadas().execute(listaEscolhidas);
            } else {
                Toast.makeText(contexto, "Você deve escolher pelo menos uma olimpíada para prosseguir.", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        configurarRecycler();
    }

    private void configurarRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(contexto);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewEscolhaMaisOlimpiadas.setLayoutManager(layoutManager);
        recyclerViewEscolhaMaisOlimpiadas.setHasFixedSize(true);
        recyclerViewEscolhaMaisOlimpiadas.setAdapter(adapter);

        OlimpiadaNaoSelecionadaDownload olimpDownload = new OlimpiadaNaoSelecionadaDownload();
        olimpDownload.execute(alunoCadastrado.getEmail());

        adapter.notifyDataSetChanged(); // Atualizar o recycler
    }

    private class OlimpiadaNaoSelecionadaDownload extends AsyncTask<String, Void, List<Olimpiada>> {
        @Override
        protected void onPostExecute(List<Olimpiada> olimpiadas) {
            super.onPostExecute(olimpiadas);
            adapter.atualizarOpcoes(olimpiadas);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected List<Olimpiada> doInBackground(String... params) {
            List<Olimpiada> olimpiadas = new ArrayList<>();
            try {
                String emailAluno = params[0];
                URL url = new URL("http://10.0.0.64:8086/phpHio/carregaOlimpiadasParaAdicionar.php?email=" + emailAluno);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    olimpiadas.addAll(converterParaList(jsonString));
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return olimpiadas;
        }

        private List<Olimpiada> converterParaList(String jsonString) {
            List<Olimpiada> olimpiadas = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("olimpiadas");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject olimpJSON = jsonArray.getJSONObject(i);
                    Olimpiada olimp = new Olimpiada();
                    olimp.setNome(olimpJSON.getString("nome"));
                    olimp.setSigla(olimpJSON.getString("sigla"));
                    String nomeDrawable = olimpJSON.getString("icone");
                    Resources resources = contexto.getResources();
                    int drawableId = resources.getIdentifier(nomeDrawable, "drawable", contexto.getPackageName());
                    olimp.setIconeOlimp(drawableId != 0 ? drawableId : R.drawable.baseline_disabled_by_default_24);
                    olimp.setCor(olimpJSON.getString("cor"));
                    olimpiadas.add(olimp);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return olimpiadas;
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
    }

    private class CadastrarOlimpiadasSelecionadas extends AsyncTask<List<Olimpiada>, Void, String> {
        @Override
        protected String doInBackground(List<Olimpiada>... olimpSelecao) {
            StringBuilder result = new StringBuilder();
            List<Olimpiada> olimpiadasSelecionadas = olimpSelecao[0];

            try {
                for (Olimpiada olimp : olimpiadasSelecionadas) {
                    URL url = new URL("http://10.0.0.64:8086/phpHio/cadastraOlimpiadasSelecionadas.php");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    conexao.setReadTimeout(1500);
                    conexao.setConnectTimeout(500);
                    conexao.setRequestMethod("POST");
                    conexao.setDoInput(true);
                    conexao.setDoOutput(true);
                    conexao.connect();

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
                    Toast.makeText(contexto, message, Toast.LENGTH_LONG).show();
                    if (jsonResponse.getString("status").equals("success")) {

                        InicialAlunoMenuDeslizanteActivity activity = (InicialAlunoMenuDeslizanteActivity) getActivity();
                        ArrayList<Olimpiada> listaOlimpiadasNovas = new ArrayList<>();
                        listaOlimpiadasNovas.addAll(adapter.getListaEscolhidas());

                        activity.atualizaRecyclerPosAdicionar(listaOlimpiadasNovas);

                        dismiss();
                        Toast.makeText(contexto, "Novas olimpíadas cadastradas com sucesso!", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }
}

