package com.example.helperinolympics.menu.forum_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterOlimpiadas;
import com.example.helperinolympics.adapter.AdapterOlimpiadasForum;
import com.example.helperinolympics.databinding.FragmentForumTudoBinding;
import com.example.helperinolympics.model.DadosOlimpiadaForum;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import java.util.ArrayList;

public class Fragment_Tudo_Activity extends Fragment {
    private FragmentForumTudoBinding binding = FragmentForumTudoBinding.inflate(getLayoutInflater());
    private AdapterOlimpiadasForum adapter;
    private ArrayList<DadosOlimpiadaForum> olimpiadasF = new ArrayList<>();

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
