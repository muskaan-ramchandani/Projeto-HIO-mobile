package com.example.helperinolympics.menu;

import static com.example.helperinolympics.R.*;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityForumBinding;

public class ForumActivity extends Activity {
    ActivityForumBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personalizarSearchHint();

    }

    private void personalizarSearchHint() {
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        SearchView barraPesquisa = binding.searchViewPerguntas;
        EditText textoBarraPesquisa = barraPesquisa.findViewById(androidx.appcompat.R.id.search_src_text);

        textoBarraPesquisa.setHintTextColor(getResources().getColor(R.color.cinza));
        textoBarraPesquisa.setTextColor(getResources().getColor(color.black));

        Typeface customFont = Typeface.createFromAsset(getAssets(), "@font/open_sans");
        textoBarraPesquisa.setTypeface(customFont);
        textoBarraPesquisa.setHint("Encontre respostas para sua pergunta");
    }
}
