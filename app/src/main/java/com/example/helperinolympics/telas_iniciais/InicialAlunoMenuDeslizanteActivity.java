package com.example.helperinolympics.telas_iniciais;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.CalendarioActivity;
import com.example.helperinolympics.R;
import com.example.helperinolympics.RankingActivity;
import com.example.helperinolympics.adapter.AdapterOlimpiadas;
import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.example.helperinolympics.menu.ForumActivity;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Manual;
import com.example.helperinolympics.model.Olimpiada;
import com.example.helperinolympics.modelos_sobrepostos.CadastrarNovasOlimpiadas;
import com.example.helperinolympics.modelos_sobrepostos.CadastrarPergunta;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
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

        configurarRecyclerOlimpiadas();

        //Função dos botoes inferiores
        binding.btnAcessarRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(InicialAlunoMenuDeslizanteActivity.this, RankingActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialAlunoMenuDeslizanteActivity.this, CalendarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
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
                    Intent intent = new Intent(InicialAlunoMenuDeslizanteActivity.this, PerfilAlunoActivity.class);
                    intent.putExtra("alunoCadastrado", alunoCadastrado);
                    startActivity(intent);
                    finish();

                    return true;
                }else if(itemID == R.id.nav_forum){
                    Intent intentForum = new Intent(InicialAlunoMenuDeslizanteActivity.this, ForumActivity.class);
                    intentForum.putExtra("alunoCadastrado", alunoCadastrado);
                    startActivity(intentForum);
                    finish();

                    return true;
                }else if(itemID == R.id.nav_manual){
                    String url = "https://hio.lat/Manual_do_usu%C3%A1rio_HIO.pdf";

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);

//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse(url));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//
//                    if (intent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(InicialAlunoMenuDeslizanteActivity.this, "Nenhum aplicativo encontrado para abrir o PDF", Toast.LENGTH_SHORT).show();
//                    }

//                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
//                    request.setTitle("Manual do Usuário HIO");
//                    request.setDescription("Baixando o PDF...");
//                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Manual_do_Usuario_HIO.pdf");
//
//                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//                    if (downloadManager != null) {
//                        downloadManager.enqueue(request);
//                    } else {
//                        Toast.makeText(InicialAlunoMenuDeslizanteActivity.this, "Erro ao inicializar o gerenciador de downloads", Toast.LENGTH_SHORT).show();
//                    }

                    return false;
                }else if(itemID == R.id.nav_configuracoes){
                    Intent intent = new Intent(InicialAlunoMenuDeslizanteActivity.this, ConfiguracoesActivity.class);
                    intent.putExtra("alunoCadastrado", alunoCadastrado);
                    startActivity(intent);
                    finish();

                    return true;
                }else if(itemID == R.id.nav_sair){
                    startActivity(new Intent(InicialAlunoMenuDeslizanteActivity.this, TelaLoginActivity.class));
                    finish();
                    return true;
                }else{
                    return false;
                }
            }
        });

        //extras
        binding.adicionarOlimpiada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarNovasOlimpiadas notificationDialogFragment = new CadastrarNovasOlimpiadas(alunoCadastrado, InicialAlunoMenuDeslizanteActivity.this);
                notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
            }
        });
    }

    public void configurarRecyclerOlimpiadas() {
        adapter = new AdapterOlimpiadas(olimpiadas, alunoCadastrado);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewTelaInicialOlimpiadas.setLayoutManager(layoutManager);
        binding.recyclerViewTelaInicialOlimpiadas.setHasFixedSize(true);
        binding.recyclerViewTelaInicialOlimpiadas.setAdapter(adapter);

        new OlimpiadasSelecionadasDownload().execute(alunoCadastrado.getEmail());
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
                URL url = new URL("https://hio.lat/carregaOlimpiadasSelecionadas.php?emailAluno=" + emailAluno);
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
        protected void onPostExecute(List<Olimpiada> novasOlimpiadas) {
            super.onPostExecute(novasOlimpiadas);
            adapter.atualizarOpcoes(novasOlimpiadas);
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

    public void atualizaRecyclerPosAdicionar(ArrayList<Olimpiada> listaEscolhidas){
        olimpiadas.addAll(listaEscolhidas);
        configurarRecyclerOlimpiadas();
    }


}