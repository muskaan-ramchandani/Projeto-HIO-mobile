package com.example.helperinolympics.telas_iniciais;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.CalendarioActivity;
import com.example.helperinolympics.R;
import com.example.helperinolympics.RankingActivity;
import com.example.helperinolympics.adapter.AdapterOlimpiadas;
import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.example.helperinolympics.menu.ForumActivity;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.menu.FavoritosAlunoActivity;
import com.example.helperinolympics.menu.ManualActivity;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.menu.SairActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Olimpiada;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class InicialAlunoMenuDeslizanteActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private ActivityMenuDeslizanteAlunoBinding binding;

    List<Olimpiada> olimpiadas = new ArrayList<>();
    AdapterOlimpiadas adapter;
    Aluno alunoCadastrado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuDeslizanteAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        drawerLayout = binding.drawerLayout;
        navView = binding.navView;

        //Função dos botoes inferiores
        binding.btnAcessarRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(InicialAlunoMenuDeslizanteActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialAlunoMenuDeslizanteActivity.this, CalendarioActivity.class);
                startActivity(intent);
                finish();
            }
        });


        // Configuração do ImageButton para abrir e fechar o DrawerLayout
        binding.btnBarraMenuAluno.setOnClickListener(v -> {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawer(navView);
            } else {
                drawerLayout.openDrawer(navView);
            }
        });

     navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(MenuItem item) {
             // Fecha o drawer quando um item é selecionado
             drawerLayout.closeDrawers();

             int itemID= item.getItemId();

             if(itemID == R.id.nav_perfil_aluno){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, PerfilAlunoActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_favoritos_aluno){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, FavoritosAlunoActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_forum){

                 Intent intentForum = new Intent(InicialAlunoMenuDeslizanteActivity.this, ForumActivity.class);
                 intentForum.putExtra("alunoCadastrado", alunoCadastrado);
                 startActivity(intentForum);
                 finish();

                 return true;
             }else if(itemID == R.id.nav_manual){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ManualActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_configuracoes){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, ConfiguracoesActivity.class));
                 finish();
                 return true;
             }else if(itemID == R.id.nav_sair){
                 startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, SairActivity.class));
                 finish();
                 return true;
             }else{
                 return false;
             }
         }
     });

        configurarRecyclerOlimpiadas();
    }

    public void configurarRecyclerOlimpiadas(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter= new AdapterOlimpiadas(olimpiadas, alunoCadastrado);
        binding.recyclerViewTelaInicialOlimpiadas.setLayoutManager(layoutManager);
        binding.recyclerViewTelaInicialOlimpiadas.setHasFixedSize(true);
        binding.recyclerViewTelaInicialOlimpiadas.setAdapter(adapter);

        new OlimpiadasSelecionadasDownload().execute(alunoCadastrado.getEmail());

        adapter.notifyDataSetChanged(); //atualizar o recycler
    }

    private class OlimpiadasSelecionadasDownload extends AsyncTask<String, Void, List<Olimpiada>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Olimpiada> doInBackground(String... params) {
            List<Olimpiada> olimpiadas = new ArrayList<>();
            String emailAluno = params[0];
            Log.d("CONEXAO", "Tentando fazer download");

            try {
                URL url = new URL("http://192.168.1.9:8086/phpHio/carregaOlimpiadasSelecionadas.php?emailAluno=" + emailAluno);
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
                    olimpiadas.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return olimpiadas;
        }

        @Override
        protected void onPostExecute(List<Olimpiada> olimpiadas) {
            super.onPostExecute(olimpiadas);
            adapter.atualizarOpcoes(olimpiadas);
            adapter.notifyDataSetChanged();
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

                    Context context = InicialAlunoMenuDeslizanteActivity.this;
                    Resources resources = context.getResources();
                    int drawableId = resources.getIdentifier(nomeDrawable, "drawable", context.getPackageName());

                    if (drawableId != 0) {
                        olimp.setIconeOlimp(drawableId);
                    } else {
                        olimp.setIconeOlimp(R.drawable.baseline_disabled_by_default_24);
                    }

                    olimp.setCor(olimpJSON.getString("cor"));
                    olimpiadas.add(olimp);
                    Log.d("Olimp", olimpiadas.toString());
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return olimpiadas;
        }
    }


}
