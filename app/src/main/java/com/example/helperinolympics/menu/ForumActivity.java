package com.example.helperinolympics.menu;

import static com.example.helperinolympics.R.*;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
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

public class ForumActivity extends AppCompatActivity {
    ActivityForumBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personalizarSearchHint();

        //Fragment inicial + configurações iniciais
        binding.imgfotoPerfil.setImageResource(drawable.iconeperfilvazioredonda);
        setFragment(new FragmentTudo());
        binding.btnTudo.setBackgroundResource(R.drawable.fundo_botao_forum_selecionado);
        binding.btnTudo.setTextColor(getColor(R.color.textoSelecionadoForum));
        binding.btnSuasPerguntas.setBackgroundResource(R.drawable.fundo_botao_forum_nao_selecionado);
        binding.btnSuasPerguntas.setTextColor(getColor(color.cinza));


        binding.btnTudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnTudo.setBackgroundResource(R.drawable.fundo_botao_forum_selecionado);
                binding.btnTudo.setTextColor(getColor(R.color.textoSelecionadoForum));

                binding.btnSuasPerguntas.setBackgroundResource(R.drawable.fundo_botao_forum_nao_selecionado);
                binding.btnSuasPerguntas.setTextColor(getColor(color.cinza));

                binding.searchViewPerguntas.setVisibility(View.VISIBLE);

                setFragment(new FragmentTudo());

            }
        });

        binding.btnSuasPerguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnSuasPerguntas.setBackgroundResource(R.drawable.fundo_botao_forum_selecionado);
                binding.btnSuasPerguntas.setTextColor(getColor(R.color.textoSelecionadoForum));

                binding.btnTudo.setBackgroundResource(R.drawable.fundo_botao_forum_nao_selecionado);
                binding.btnTudo.setTextColor(getColor(color.cinza));

                binding.searchViewPerguntas.setVisibility(View.INVISIBLE);


                setFragment(new FragmentSuasPerguntas());

            }
        });

    }

    private void personalizarSearchHint() {
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        SearchView barraPesquisa = binding.searchViewPerguntas;

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
