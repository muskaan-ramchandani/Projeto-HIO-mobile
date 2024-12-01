package com.example.helperinolympics.menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityForumBinding;
import com.example.helperinolympics.menu.forum_fragments.FragmentPerguntasPorOlimpiada;
import com.example.helperinolympics.menu.forum_fragments.FragmentPerguntasRecentes;
import com.example.helperinolympics.menu.forum_fragments.FragmentSuasPerguntas;
import com.example.helperinolympics.menu.forum_fragments.FragmentTudo;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.forum.PerguntasForum;
import com.example.helperinolympics.modelos_sobrepostos.CadastrarPergunta;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {
    ActivityForumBinding binding;
    SearchView barraPesquisa;
    public Aluno alunoCadastrado;
    //fragments
    Fragment fragmentFilho;
    Fragment fragmentPai;

    ArrayList<PerguntasForum> listaRecentesAtual = new ArrayList<>();
    ArrayList<PerguntasForum> listaOlimpiadasAtual = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        barraPesquisa = findViewById(R.id.searchViewPerguntas);
        barraPesquisa.clearFocus();

        FotoAlunoTask fotoAlunoTask = new FotoAlunoTask();
        fotoAlunoTask.execute(alunoCadastrado.getEmail());

        // Fragment inicial + configurações iniciais
        atribuirFundoEBotao(binding.btnTudo, R.drawable.fundo_botao_forum_selecionado, R.color.textoSelecionadoForum);
        atribuirFundoEBotao(binding.btnSuasPerguntas, R.drawable.fundo_botao_forum_nao_selecionado, R.color.cinza);

        binding.linearBtnfazerPergunta.setVisibility(View.GONE);

        setFragment(new FragmentTudo());


        findViewById(R.id.btnVoltarATelaInicial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();

            }
        });

        personalizarSearchHint();

        findViewById(R.id.btnTudo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atribuirFundoEBotao(findViewById(R.id.btnTudo), R.drawable.fundo_botao_forum_selecionado, R.color.textoSelecionadoForum);
                atribuirFundoEBotao(findViewById(R.id.btnSuasPerguntas), R.drawable.fundo_botao_forum_nao_selecionado, R.color.cinza);

                binding.linearLayoutBarraPesquisaForum.setVisibility(View.VISIBLE);
                binding.linearBtnfazerPergunta.setVisibility(View.GONE);
                setFragment(new FragmentTudo());
            }
        });

        findViewById(R.id.btnSuasPerguntas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ForumActivity", "btnSuasPerguntas clicado");
                atribuirFundoEBotao(findViewById(R.id.btnSuasPerguntas), R.drawable.fundo_botao_forum_selecionado, R.color.textoSelecionadoForum);
                atribuirFundoEBotao(findViewById(R.id.btnTudo), R.drawable.fundo_botao_forum_nao_selecionado, R.color.cinza);

                if (barraPesquisa != null) {
                    barraPesquisa.setQuery(null, false); // Limpa o texto da pesquisa
                    barraPesquisa.clearFocus(); // Remove o foco da barra de pesquisa

                    if (fragmentFilho instanceof FragmentPerguntasRecentes) {
                        ((FragmentPerguntasRecentes) fragmentFilho).alterarListaPorPesquisa(((FragmentPerguntasRecentes) fragmentFilho).retornaListaOriginal());
                    } else if (fragmentFilho instanceof FragmentPerguntasPorOlimpiada) {
                        ((FragmentPerguntasPorOlimpiada) fragmentFilho).alterarListaPorPesquisa(((FragmentPerguntasPorOlimpiada) fragmentFilho).retornaListaOriginal());
                    }
                }


                binding.linearLayoutBarraPesquisaForum.setVisibility(View.GONE);
                binding.linearBtnfazerPergunta.setVisibility(View.VISIBLE);
                setFragment(new FragmentSuasPerguntas());
            }
        });

        findViewById(R.id.btnFazerPergunta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarPergunta notificationDialogFragment = new CadastrarPergunta(alunoCadastrado, ForumActivity.this);
                notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
            }
        });

        barraPesquisa.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("Pesquisa", "Texto submetido: " + query);
                filterList(query);
                barraPesquisa.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("Pesquisa", "Texto alterado: " + newText);

                if(newText.isEmpty()){
                    if (fragmentFilho instanceof FragmentPerguntasRecentes){
                        ((FragmentPerguntasRecentes) fragmentFilho).alterarListaPorPesquisa(((FragmentPerguntasRecentes) fragmentFilho).retornaListaOriginal());

                    }else if (fragmentFilho instanceof FragmentPerguntasPorOlimpiada){
                        ((FragmentPerguntasPorOlimpiada) fragmentFilho).alterarListaPorPesquisa(((FragmentPerguntasPorOlimpiada) fragmentFilho).retornaListaOriginal());

                    }
                }else{
                    filterList(newText);
                }
                return true;
            }
        });

        barraPesquisa.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (barraPesquisa.getQuery().toString().isEmpty()) {
                        if (fragmentFilho instanceof FragmentPerguntasRecentes) {
                            ((FragmentPerguntasRecentes) fragmentFilho).alterarListaPorPesquisa(((FragmentPerguntasRecentes) fragmentFilho).retornaListaOriginal());
                        } else if (fragmentFilho instanceof FragmentPerguntasPorOlimpiada) {
                            ((FragmentPerguntasPorOlimpiada) fragmentFilho).alterarListaPorPesquisa(((FragmentPerguntasPorOlimpiada) fragmentFilho).retornaListaOriginal());
                        }
                    } else {
                        filterList(barraPesquisa.getQuery().toString());
                    }
                }
            }
        });

    }

    private void filterList(String newText) {
        ArrayList<PerguntasForum> perguntasFiltradasRecentes = new ArrayList<>();
        ArrayList<PerguntasForum> perguntasFiltradasOlimpiadas = new ArrayList<>();

        if (fragmentFilho instanceof FragmentPerguntasRecentes) {
            listaRecentesAtual.clear();
            listaRecentesAtual.addAll(((FragmentPerguntasRecentes) fragmentFilho).retornaListaOriginal());
            Log.d("LISTA_ORIGINAIS", "Recentes " + listaRecentesAtual.size());
            Log.d("Fragment", "FragmentPerguntasRecentes está ativo.");

            if (listaRecentesAtual != null && !listaRecentesAtual.isEmpty()) {
                for (PerguntasForum pergunta : listaRecentesAtual) {
                    if (pergunta.getTitulo().toLowerCase().contains(newText.toLowerCase())) {
                        perguntasFiltradasRecentes.add(pergunta);
                    }
                }

                if (perguntasFiltradasRecentes.isEmpty()) {
                    Toast.makeText(ForumActivity.this, "Não existem perguntas relacionadas ao digitado.", Toast.LENGTH_SHORT).show();
                } else {
                    ((FragmentPerguntasRecentes) fragmentFilho).alterarListaPorPesquisa(perguntasFiltradasRecentes);
                }
            }

        } else if (fragmentFilho instanceof FragmentPerguntasPorOlimpiada) {
            listaOlimpiadasAtual.clear();
            listaOlimpiadasAtual.addAll(((FragmentPerguntasPorOlimpiada) fragmentFilho).retornaListaOriginal());
            Log.d("LISTA_ORIGINAIS", "Olimpiadas " + listaOlimpiadasAtual.size());
            Log.d("Fragment", "FragmentPerguntasPorOlimpiada está ativo.");

            if (listaOlimpiadasAtual != null && !listaOlimpiadasAtual.isEmpty()) {
                for (PerguntasForum pergunta : listaOlimpiadasAtual) {
                    if (pergunta.getTitulo().toLowerCase().contains(newText.toLowerCase())) {
                        perguntasFiltradasOlimpiadas.add(pergunta);
                    }
                }

                if (perguntasFiltradasOlimpiadas.isEmpty()) {
                    Toast.makeText(ForumActivity.this, "Não existem perguntas relacionadas ao digitado.", Toast.LENGTH_SHORT).show();
                } else {
                    ((FragmentPerguntasPorOlimpiada) fragmentFilho).alterarListaPorPesquisa(perguntasFiltradasOlimpiadas);
                }
            }
        }
    }

    private void atribuirFundoEBotao(View botao, int fundoResId, int corTextoResId) {
        botao.setBackground(getDrawable(fundoResId));
        if (botao instanceof androidx.appcompat.widget.AppCompatButton) {
            ((androidx.appcompat.widget.AppCompatButton) botao).setTextColor(getColor(corTextoResId));
        }
    }


    private void personalizarSearchHint() {
        SearchView barraPesquisa = findViewById(R.id.searchViewPerguntas);

        int searchTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText textoBarraPesquisa = barraPesquisa.findViewById(searchTextId);
        if (textoBarraPesquisa != null) {
            textoBarraPesquisa.setHintTextColor(getResources().getColor(R.color.cinza));
            textoBarraPesquisa.setTextColor(getResources().getColor(R.color.black));

            Typeface customFont = ResourcesCompat.getFont(this, R.font.open_sans);
            if (customFont != null) {
                textoBarraPesquisa.setTypeface(customFont);
            }

            textoBarraPesquisa.setPadding(50, 0, 0, 0);
        }
    }


    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForum, fragment);
        fragmentTransaction.commit();

        binding.getRoot().postDelayed(this::atribuindoFragmentsParaConfigurarPesquisa, 50);
    }

    public void atribuindoFragmentsParaConfigurarPesquisa(){
        fragmentPai = getSupportFragmentManager().findFragmentById(R.id.fragmentForum);

        if (fragmentPai == null) {
            Log.e("FRAGMENTOPAI", "Fragmento pai é null!");
        } else {
            Log.d("FRAGMENTOPAI", "Fragmento pai encontrado: " + fragmentPai.getClass().getSimpleName());
        }

        if (fragmentPai instanceof FragmentTudo) {
            Log.d("FRAGMENTO_PAI_DETECTADO", "Fragmento pai é forum");

            fragmentFilho = ((FragmentTudo) fragmentPai).retornaFragmentAtual();
            if (fragmentFilho != null) {
                Log.d("Fragmento", "Fragmento filho encontrado e inicializado.");
            } else {
                Log.e("Fragmento", "Fragmento filho não encontrado.");
            }
        }

    }


    public void atualizarDadosPosPublicacao() {
        setFragment(new FragmentSuasPerguntas());
    }

    public class FotoAlunoTask extends AsyncTask<String, Void, Bitmap> {

        private Bitmap fotoBitmap;

        @Override
        protected Bitmap doInBackground(String... params) {
            String email = params[0];
            Bitmap resultBitmap = null;

            try {
                URL url = new URL("http://10.0.0.64:8086/phpHio/retornaFotoPorEmail.php?email=" + email);
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
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String status = jsonObject.getString("status");

                        if (status.equals("success")) {
                            String fotoBase64 = jsonObject.getString("fotoPerfil");
                            resultBitmap = decodeBase64ToBitmap(fotoBase64);
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("ERRO", "Erro ao buscar a foto: " + e.getMessage());
            }
            return resultBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            fotoBitmap = result;

            if (fotoBitmap != null) {
                Log.d("INFO", "Foto recebida com sucesso.");
                binding.imgfotoPerfil.setImageBitmap(fotoBitmap);

            } else {
                Log.d("ERRO", "Não foi possível carregar a foto.");
                binding.imgfotoPerfil.setImageResource(R.drawable.iconeperfilsemfoto);
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

        private Bitmap decodeBase64ToBitmap(String base64String) {
            try {
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } catch (Exception e) {
                Log.d("ERRO", "Erro ao decodificar Base64 para Bitmap: " + e.getMessage());
                return null;
            }
        }

    }

}
