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
import com.example.helperinolympics.adapter.forum.AdapterOlimpiadasForum;
import com.example.helperinolympics.databinding.FragmentForumTudoBinding;
import com.example.helperinolympics.model.OlimpiadaForum;

import java.util.ArrayList;

public class FragmentTudo extends Fragment implements AdapterOlimpiadasForum.OnOlimpiadaClickListener {
    private FragmentForumTudoBinding binding;
    private AdapterOlimpiadasForum adapter;
    private ArrayList<OlimpiadaForum> olimpiadasF = new ArrayList<>();
    private int clickCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentForumTudoBinding.inflate(inflater, container, false);
        configurarRecyclerOlimpiadasForum();
        setupFragmentNavigation();
        return binding.getRoot();
    }

    private void setupFragmentNavigation() {
        setChildFragment(new FragmentPerguntasRecentes()); // Fragment inicial
    }

    private void setChildFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForumPerguntas, fragment);
        fragmentTransaction.commit();
    }

    public void configurarRecyclerOlimpiadasForum() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new AdapterOlimpiadasForum(olimpiadasF, this);
        binding.recyclerPerguntasPorOlimpiada.setLayoutManager(layoutManager);
        binding.recyclerPerguntasPorOlimpiada.setHasFixedSize(true);
        binding.recyclerPerguntasPorOlimpiada.setAdapter(adapter);

        //SIMULAÇÃO DE DADOS
        olimpiadasF.add(new OlimpiadaForum("OBA", "Rosa", 25));
        olimpiadasF.add(new OlimpiadaForum("OBF", "Azul", 13));
        olimpiadasF.add(new OlimpiadaForum("OBI", "Laranja", 45));
        olimpiadasF.add(new OlimpiadaForum("OBMEP", "Ciano", 200));
        olimpiadasF.add(new OlimpiadaForum("ONC", "Ciano", 567));

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onOlimpiadaClick(OlimpiadaForum olimp) {
        clickCount++;

        //Alternando fragmentos com base no número de cliques
        if (clickCount % 2 == 1) {
            setChildFragment(new FragmentPerguntasPorOlimpiada(olimp.getSiglaOlimpiada()));
        } else {
            setChildFragment(new FragmentPerguntasRecentes());
        }
    }
}
