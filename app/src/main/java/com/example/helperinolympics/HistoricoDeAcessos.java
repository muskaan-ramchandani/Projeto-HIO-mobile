package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.historicos.AdapterHistoricoFlashcard;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoProvas;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoQuestionario;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoTexto;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoVideo;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.model.DadosFlashcard;
import com.example.helperinolympics.model.historicos_acesso.DadosHistoricoFlashcards;
import com.example.helperinolympics.model.DadosProvasAnteriores;
import com.example.helperinolympics.model.DadosQuestionario;
import com.example.helperinolympics.model.DadosTexto;
import com.example.helperinolympics.model.DadosVideo;
import com.example.helperinolympics.model.historicos_acesso.DadosHistoricoProvasAnteriores;
import com.example.helperinolympics.model.historicos_acesso.DadosHistoricoQuestionario;
import com.example.helperinolympics.model.historicos_acesso.DadosHistoricoTexto;
import com.example.helperinolympics.model.historicos_acesso.DadosHistoricoVideo;

import java.util.ArrayList;
import java.util.List;

public class HistoricoDeAcessos extends AppCompatActivity {

    RecyclerView rvHistoricoFlashcard, rvHistoricoProvas, rvHistoricoQuestionarios, rvHistoricoTexto, rvHistoricoVideo;
    AdapterHistoricoFlashcard adapterFlash;
    AdapterHistoricoQuestionario adapterQuest;
    AdapterHistoricoTexto adapterTxt;
    AdapterHistoricoProvas adapterProvas;
    AdapterHistoricoVideo adapterVideo;

    //Ids guardados nos históricos
    List<DadosHistoricoFlashcards> idsHistoricoFlash = new ArrayList<>();
    List<DadosHistoricoProvasAnteriores> idsHistoricoProvas = new ArrayList<>();
    List<DadosHistoricoQuestionario> idsHistoricoQuest = new ArrayList<>();
    List<DadosHistoricoTexto> idsHistoricoTxt = new ArrayList<>();
    List<DadosHistoricoVideo> idsHistoricoVideo = new ArrayList<>();


    //Listas banco e as que vao para a tela
    List<DadosFlashcard> listaFlashcardHistorico= new ArrayList<>();
    List<DadosFlashcard> listaFlashcardsBanco= new ArrayList<>();

    List<DadosQuestionario> listaQuestHistorico = new ArrayList<>();
    List<DadosQuestionario> listaQuestBanco = new ArrayList<>();

    List<DadosTexto> listaTxtHistorico = new ArrayList<>();
    List<DadosTexto> listaTxtBanco = new ArrayList<>();

    List<DadosProvasAnteriores> listaProvasHistorico = new ArrayList<>();
    List<DadosProvasAnteriores> listaProvasBanco = new ArrayList<>();

    List<DadosVideo> listaVideoHistorico = new ArrayList<>();
    List<DadosVideo> listaVideoBanco = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_de_acessos);

        findViewById(R.id.btnVoltarAConfiguracoesHistoricoAcesso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HistoricoDeAcessos.this, ConfiguracoesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // DADOS QUE VAO SER PUXADOS DO BANCO QUANDO ELE EXISTIR
        listaFlashcardsBanco.add(new DadosFlashcard(1,"Ponto material e corpo extenso","muskaanRamchandani", "OBF", "Fundamentos da cinemática do ponto material"));
        listaFlashcardsBanco.add(new DadosFlashcard(2, "Referencial e trajetória","lanaDelRey", "OBF", "Fundamentos da cinemática do ponto material"));
        listaFlashcardsBanco.add(new DadosFlashcard(3,"Distância percorrida e deslocamento","profGui", "OBF", "Fundamentos da cinemática do ponto material"));
        listaQuestBanco.add(new DadosQuestionario(1, "Ponto material e corpo extenso", "profEdu", "OBF","pergunta 1" , "Fundamentos da cinemática do ponto material"));
        listaQuestBanco.add(new DadosQuestionario(2, "Referencial e trajetória", "daniloo", "OBF", "pergunta 2" ,"Fundamentos da cinemática do ponto material"));
        listaQuestBanco.add(new DadosQuestionario(3, "Distância percorrida e deslocamento", "lanaa", "OBF", "pergunta 3" ,"Fundamentos da cinemática do ponto material"));
//        listaTxtBanco.add(new DadosTexto(1, "Ponto material e corpo extenso", "lilianAlmeida", "OBF", "Fundamentos da cinemática do ponto material", "Cinemática Escalar é um dos principais ramos da Mecânica. Trata-se da área que estuda o movimento dos corpos sem atribuir-lhes uma causa. A palavra escalar refere-se ao fato de lidarmos com movimentos unicamente unidimensionais, ou seja, que se desenvolvem unicamente ao longo de uma direção do espaço, dispensando, dessa forma, o tratamento vetorial das grandezas físicas envolvidas.\n" + "Para o estudo da Cinemática Escalar, alguns conceitos são de grande importância, portanto, trataremos aqui daqueles que são fundamentais para o seu entendimento.\n" + "\n" + "Conceitos fundamentais da Cinemática Escalar\n" + "\n" + "→ Corpo: É uma porção limitada de matéria e é constituído por partículas, mas pode ser tratado macroscopicamente como um único corpo no âmbito da Cinemática Escalar.\n" + "\n" + "→ Ponto material: É todo corpo cujas dimensões podem ser desprezadas em relação às distâncias envolvidas."));
//        listaTxtBanco.add(new DadosTexto(2, "Referencial e trajetória", "profEdu", "OBF", "Fundamentos da cinemática do ponto material", "texto 2"));
//        listaTxtBanco.add(new DadosTexto(3, "Distância percorrida e deslocamento", "profGui", "OBF", "Fundamentos da cinemática do ponto material", "texto 3"));
//        listaProvasBanco.add(new DadosProvasAnteriores(1, 2022, 3, true, "demiLov"));
//        listaProvasBanco.add(new DadosProvasAnteriores(2, 2019, 2, false, "doroteia"));
//        listaProvasBanco.add(new DadosProvasAnteriores(3, 2006, 1, false, "luanSantana"));
        listaVideoBanco.add(new DadosVideo(1, "Ponto material e corpo extenso","link", "profAnaCastela", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo1));
        listaVideoBanco.add(new DadosVideo(2, "Referencial e trajetória","link", "zezeDiCamargo", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo2));
        listaVideoBanco.add(new DadosVideo(3, "Distância percorrida e deslocamento","link", "demiLovProf", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo1));


        //adicionando dados aleatorios de historico
        idsHistoricoFlash.add(new DadosHistoricoFlashcards(1));
        idsHistoricoFlash.add(new DadosHistoricoFlashcards(2));
        idsHistoricoFlash.add(new DadosHistoricoFlashcards(3));
        idsHistoricoProvas.add(new DadosHistoricoProvasAnteriores(1));
        idsHistoricoProvas.add(new DadosHistoricoProvasAnteriores(2));
        idsHistoricoProvas.add(new DadosHistoricoProvasAnteriores(3));
        idsHistoricoQuest.add(new DadosHistoricoQuestionario(1));
        idsHistoricoQuest.add(new DadosHistoricoQuestionario(2));
        idsHistoricoQuest.add(new DadosHistoricoQuestionario(3));
        idsHistoricoTxt.add(new DadosHistoricoTexto(1));
        idsHistoricoTxt.add(new DadosHistoricoTexto(2));
        idsHistoricoTxt.add(new DadosHistoricoTexto(3));
        idsHistoricoVideo.add(new DadosHistoricoVideo(1));
        idsHistoricoVideo.add(new DadosHistoricoVideo(2));
        idsHistoricoVideo.add(new DadosHistoricoVideo(3));


        for(DadosHistoricoFlashcards dado : idsHistoricoFlash){
            int idNoHistorico = dado.getIdItem();
            for(DadosFlashcard flash : listaFlashcardsBanco){
                if(flash.getId()==idNoHistorico){
                    listaFlashcardHistorico.add(flash);
                }
            }
        }

        for(DadosHistoricoProvasAnteriores dado : idsHistoricoProvas){
            int idNoHistorico = dado.getIdItem();
            for(DadosProvasAnteriores prova : listaProvasBanco){
                if(prova.getId()==idNoHistorico){
                    listaProvasHistorico.add(prova);
                }
            }
        }

        for(DadosHistoricoQuestionario dado : idsHistoricoQuest){
            int idNoHistorico = dado.getIdItem();
            for(DadosQuestionario quest : listaQuestBanco){
                if(quest.getId()==idNoHistorico){
                    listaQuestHistorico.add(quest);
                }
            }
        }

        for(DadosHistoricoTexto dado : idsHistoricoTxt){
            int idNoHistorico = dado.getIdItem();
            for(DadosTexto txt : listaTxtBanco){
                if(txt.getId()==idNoHistorico){
                    listaTxtHistorico.add(txt);
                }
            }
        }

        for(DadosHistoricoVideo dado : idsHistoricoVideo){
            int idNoHistorico = dado.getIdItem();
            for(DadosVideo video : listaVideoBanco){
                if(video.getId()==idNoHistorico){
                    listaVideoHistorico.add(video);
                }
            }
        }


        //recyclers
        configurarRecyclerHistoricoFlashcards();
        configurarRecyclerHistoricoQuest();
        configurarRecyclerHistoricoTxt();
        configurarRecyclerHistoricoProvas();
        configurarRecyclerHistoricoVideos();
    }

    private void configurarRecyclerHistoricoFlashcards() {
        rvHistoricoFlashcard=findViewById(R.id.recyclerViewHistoricoFlashcards);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoFlashcard.setLayoutManager(layoutManager);
        rvHistoricoFlashcard.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterFlash=new AdapterHistoricoFlashcard(listaFlashcardHistorico, fragmentManager);
        rvHistoricoFlashcard.setAdapter(adapterFlash);
    }

    private void configurarRecyclerHistoricoQuest() {
        rvHistoricoQuestionarios=findViewById(R.id.recyclerViewHistoricoQuestionario);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoQuestionarios.setLayoutManager(layoutManager);
        rvHistoricoQuestionarios.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterQuest=new AdapterHistoricoQuestionario(listaQuestHistorico, fragmentManager);
        rvHistoricoQuestionarios.setAdapter(adapterQuest);
    }

    private void configurarRecyclerHistoricoTxt() {
        rvHistoricoTexto=findViewById(R.id.recyclerViewHistoricoTexto);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoTexto.setLayoutManager(layoutManager);
        rvHistoricoTexto.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterTxt=new AdapterHistoricoTexto(listaTxtHistorico, fragmentManager);
        rvHistoricoTexto.setAdapter(adapterTxt);
    }

    private void configurarRecyclerHistoricoProvas() {
        rvHistoricoProvas=findViewById(R.id.recyclerViewHistoricoProvas);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoProvas.setLayoutManager(layoutManager);
        rvHistoricoProvas.setHasFixedSize(true);
        adapterProvas=new AdapterHistoricoProvas(listaProvasHistorico);
        rvHistoricoProvas.setAdapter(adapterProvas);

    }

    private void configurarRecyclerHistoricoVideos() {
        rvHistoricoVideo=findViewById(R.id.recyclerViewHistoricoVideo);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoVideo.setLayoutManager(layoutManager);
        rvHistoricoVideo.setHasFixedSize(true);
        adapterVideo=new AdapterHistoricoVideo(listaVideoHistorico);
        rvHistoricoVideo.setAdapter(adapterVideo);

    }
}