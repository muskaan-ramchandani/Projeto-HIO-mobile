package com.example.helperinolympics.menu.forum_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterOlimpiadasForum;
import com.example.helperinolympics.databinding.FragmentForumTudoBinding;
import com.example.helperinolympics.model.DadosOlimpiadaForum;

import java.util.ArrayList;

public class FragmentTudo extends Fragment {
    private FragmentForumTudoBinding binding = FragmentForumTudoBinding.inflate(getLayoutInflater());
    private AdapterOlimpiadasForum adapter;
    private ArrayList<DadosOlimpiadaForum> olimpiadasF = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForumTudoBinding.inflate(inflater, container, false);
        configurarRecyclerOlimpiadasForum();
        setupFragmentNavigation();
        return binding.getRoot();
    }

    private void setupFragmentNavigation() {
        setChildFragment(new FragmentPerguntasRecentes());

        // Configurar botões para trocar os fragmentos
        binding.btnOlimpiadas.setOnClickListener(v -> setChildFragment(new FragmentOlimpiadas()));
        binding.btnPerguntasRecentes.setOnClickListener(v -> setChildFragment(new FragmentPerguntasRecentes()));
    }

    private void setChildFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForumPerguntas, fragment);  // Certifique-se que você tem um FrameLayout com esse ID no layout
        fragmentTransaction.commit();
    }

    public void configurarRecyclerOlimpiadasForum(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter= new AdapterOlimpiadasForum(olimpiadasF);
        binding.recyclerPerguntasPorOlimpiada.setLayoutManager(layoutManager);
        binding.recyclerPerguntasPorOlimpiada.setHasFixedSize(true);
        binding.recyclerPerguntasPorOlimpiada.setAdapter(adapter);

        //new InicialAlunoMenuDeslizanteActivity.OlimpiadasSelecionadasDownload().execute(alunoCadastrado.getEmail());

        adapter.notifyDataSetChanged(); //atualizar o recycler
    }
}
