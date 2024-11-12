package com.example.helperinolympics.menu.forum_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.forum.AdapterPerguntasForum;
import com.example.helperinolympics.databinding.FragmentForumPerguntasPorOlimpiadaBinding;
import com.example.helperinolympics.model.PerguntasForum;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentPerguntasPorOlimpiada  extends Fragment {

    private FragmentForumPerguntasPorOlimpiadaBinding binding;
    private AdapterPerguntasForum adapter;
    private ArrayList<PerguntasForum> perguntasF = new ArrayList<>();
    private String siglaOlimpiada;

    public FragmentPerguntasPorOlimpiada(String siglaOlimpiada){
        this.siglaOlimpiada = siglaOlimpiada;

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentForumPerguntasPorOlimpiadaBinding.inflate(inflater, container, false);
        binding.txtPerguntasOlimp.setText("Perguntas relacionadas a "+ siglaOlimpiada+":");
        configurarRecyclerPerguntasForum();
        return binding.getRoot();
    }

    public void configurarRecyclerPerguntasForum(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter= new AdapterPerguntasForum(perguntasF);
        binding.recyclerPerguntasOlimpiadas.setLayoutManager(layoutManager);
        binding.recyclerPerguntasOlimpiadas.setHasFixedSize(true);
        binding.recyclerPerguntasOlimpiadas.setAdapter(adapter);

        //new InicialAlunoMenuDeslizanteActivity.OlimpiadasSelecionadasDownload().execute(alunoCadastrado.getEmail());

        //DADOS PARA SIMULAÇÃO
//        Date dataPublicacao1 = null;
//        try {
//            dataPublicacao1 = sdf.parse("22/02/2022");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        perguntasF.add(new PerguntasForum(1, R.drawable.iconeperfilvazioredonda, 12, "Equação geral e reduzida",
//                "user466", "Quais seriam os métodos para achar uma equação geral a partir de uma matriz? Existem outras formas de fazer isso? E como chego na reduzida?",
//                "OBA", null, dataPublicacao1));
//
//        Date dataPublicacao2 = null;
//        try {
//            dataPublicacao2 = sdf.parse("17/06/2024");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        perguntasF.add(new PerguntasForum(2, R.drawable.iconeperfilvazioredonda, 50, "HTML com JavaScript",
//                "noeminoeme", "Uma questão solicitava a criação de um formulário e o tratamento de eventos de 2 botões utilizando o JavaScript, porém não estou conseguindo conectar o arquivo html ao js. Quais seriam as maneiras de fazer isso? Como posso resolver?",
//                "OBA", null, dataPublicacao2));
//
//        Date dataPublicacao3 = null;
//        try {
//            dataPublicacao3 = sdf.parse("25/12/2023");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        perguntasF.add(new PerguntasForum(3, R.drawable.iconeperfilvazioredonda, 500, "Dúvida",
//                "naosei", "Dúvida",
//                "OBA", null, dataPublicacao3));


        //FAZER PROCURA POR OLIMPIADA usando siglaOlimpiada

        adapter.notifyDataSetChanged(); //atualizar o recycler
    }
}
