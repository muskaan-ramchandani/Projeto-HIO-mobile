package com.example.helperinolympics.menu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityForumBinding;
import com.example.helperinolympics.menu.forum_fragments.FragmentSuasPerguntas;
import com.example.helperinolympics.menu.forum_fragments.FragmentTudo;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

public class ForumActivity extends AppCompatActivity {
    ActivityForumBinding binding;
    Aluno alunoCadastrado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personalizarSearchHint();
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

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


        textoBarraPesquisa.setHintTextColor(getResources().getColor(R.color.cinza));
        textoBarraPesquisa.setTextColor(getResources().getColor(R.color.black));

        Typeface customFont = ResourcesCompat.getFont(this, R.font.open_sans);
        textoBarraPesquisa.setTypeface(customFont);
        textoBarraPesquisa.setHint("Encontre respostas para sua pergunta");
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForum, fragment);
        fragmentTransaction.commit();
    }

}
