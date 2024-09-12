package com.example.helperinolympics.menu.forum_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterPerguntasForum;
import com.example.helperinolympics.model.DadosPerguntasForum;


import java.util.ArrayList;

public class FragmentPerguntasRecentes  extends Fragment {

    private AdapterPerguntasForum adapter;
    private ArrayList<DadosPerguntasForum> perguntasF = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forum_tudo, container, false);
    }

    public void configurarRecyclerOlimpiadasForum(){
//        LinearLayoutManager layoutManager= new LinearLayoutManager(FragmentForumTudoBinding.this);
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAl);
//        adapter= new AdapterOlimpiadasForum(olimpiadasF);
//        binding.recyclerPerguntasPorOlimpiada.setLayoutManager(layoutManager);
//        binding.recyclerPerguntasPorOlimpiada.setHasFixedSize(true);
//        binding.recyclerPerguntasPorOlimpiada.setAdapter(adapter);

//        new InicialAlunoMenuDeslizanteActivity.OlimpiadasSelecionadasDownload().execute(alunoCadastrado.getEmail());
//
//        adapter.notifyDataSetChanged(); //atualizar o recycler
    }
}
