package com.example.helperinolympics.menu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {
    ActivityForumBinding binding;
    SearchView barraPesquisa;
    public Aluno alunoCadastrado;
    ArrayList<PerguntasForum> listaOriginalRecentes = new ArrayList<>();
    ArrayList<PerguntasForum> listaOriginalOlimpiadas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        barraPesquisa = findViewById(R.id.searchViewPerguntas);
        personalizarSearchHint();

        // Fragment inicial + configurações iniciais
        binding.imgfotoPerfil.setImageResource(R.drawable.iconeperfilvazioredonda);
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
                filterList(query);
                barraPesquisa.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        barraPesquisa.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (barraPesquisa.getQuery().toString().isEmpty()) {
                        Fragment fragmentPai = getSupportFragmentManager().findFragmentById(R.id.fragmentForum);
                        Fragment fragmentFilho = fragmentPai.getChildFragmentManager().findFragmentById(R.id.fragmentForumPerguntas);

                        if (fragmentFilho instanceof FragmentPerguntasRecentes) {
                            ((FragmentPerguntasRecentes) fragmentFilho).alterarListaPorPesquisa(listaOriginalRecentes);
                        } else if (fragmentFilho instanceof FragmentPerguntasPorOlimpiada) {
                            ((FragmentPerguntasPorOlimpiada) fragmentFilho).alterarListaPorPesquisa(listaOriginalOlimpiadas);
                        }
                    }
                }
            }
        });
    }

    private void filterList(String newText) {
        ArrayList<PerguntasForum> perguntasFiltradas = new ArrayList<>();

        Fragment fragmentPai = getSupportFragmentManager().findFragmentById(R.id.fragmentForum);
        Fragment fragmentFilho = fragmentPai.getChildFragmentManager().findFragmentById(R.id.fragmentForumPerguntas);

        if (fragmentFilho instanceof FragmentPerguntasRecentes) {
            Log.d("Fragment", "FragmentPerguntasRecentes está ativo.");
            List<PerguntasForum> listaAtualRecentes= ((FragmentPerguntasRecentes) fragmentFilho).retornaListaAtual();
            listaOriginalRecentes.addAll(listaAtualRecentes);

            for(PerguntasForum pergunta : listaAtualRecentes){
                if(pergunta.getTitulo().toLowerCase().contains(newText.toLowerCase())){
                    perguntasFiltradas.add(pergunta);
                }
            }

            if(perguntasFiltradas.isEmpty()){
                Toast.makeText(ForumActivity.this, "Não existem perguntas relacionadas ao digitado.", Toast.LENGTH_SHORT).show();
            }else{
                ((FragmentPerguntasRecentes) fragmentFilho).alterarListaPorPesquisa(perguntasFiltradas);
            }

        } else if (fragmentFilho instanceof FragmentPerguntasPorOlimpiada) {
            Log.d("Fragment", "FragmentPerguntasPorOlimpiada está ativo.");
            List<PerguntasForum> listaAtualOlimpiada= ((FragmentPerguntasPorOlimpiada) fragmentFilho).retornaListaAtual();
            listaOriginalOlimpiadas.addAll(listaAtualOlimpiada);

            for(PerguntasForum pergunta : listaAtualOlimpiada){
                if(pergunta.getTitulo().toLowerCase().contains(newText.toLowerCase())){
                    perguntasFiltradas.add(pergunta);
                }
            }

            if(perguntasFiltradas.isEmpty()){
                Toast.makeText(ForumActivity.this, "Não existem perguntas relacionadas ao digitado.", Toast.LENGTH_SHORT).show();
            }else{
                ((FragmentPerguntasPorOlimpiada) fragmentFilho).alterarListaPorPesquisa(perguntasFiltradas);
            }
        }
    }


    private void atribuirFundoEBotao(View botao, int fundoResId, int corTextoResId) {
        botao.setBackground(getDrawable(fundoResId));
        if (botao instanceof androidx.appcompat.widget.AppCompatButton) {
            ((androidx.appcompat.widget.AppCompatButton) botao).setTextColor(getColor(corTextoResId)); // Alterado para getColor
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
    }

    public void atualizarDadosPosPublicacao() {
        setFragment(new FragmentSuasPerguntas());
    }

}
