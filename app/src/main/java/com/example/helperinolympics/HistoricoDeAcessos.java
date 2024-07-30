package com.example.helperinolympics;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterFlashcard;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoFlashcard;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoProvas;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoQuestionario;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoTexto;
import com.example.helperinolympics.adapter.historicos.AdapterHistoricoVideo;
import com.example.helperinolympics.model.DadosFlashcard;
import com.example.helperinolympics.model.DadosHistorico;
import com.example.helperinolympics.model.DadosProvasAnteriores;
import com.example.helperinolympics.model.DadosQuestionario;
import com.example.helperinolympics.model.DadosTexto;
import com.example.helperinolympics.model.DadosVideo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoricoDeAcessos extends AppCompatActivity {

    List<DadosHistorico> listaHistorico = new ArrayList<>();
    RecyclerView rvHistoricoFlashcard, rvHistoricoProvas, rvHistoricoQuestionarios, rvHistoricoTexto, rvHistoricoVideo;
    AdapterHistoricoFlashcard adapterFlash;
    AdapterHistoricoQuestionario adapterQuest;
    AdapterHistoricoTexto adapterTxt;
    AdapterHistoricoProvas adapterProvas;
    AdapterHistoricoVideo adapterVideo;

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

        // DADOS QUE VAO SER PUXADOS DO BANCO QUANDO ELE EXISTIR
        listaFlashcardsBanco.add(new DadosFlashcard(1,"Ponto material e corpo extenso","material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaFlashcardsBanco.add(new DadosFlashcard(2, "Referencial e trajetória","material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaFlashcardsBanco.add(new DadosFlashcard(3,"Distância percorrida e deslocamento","material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaQuestBanco.add(new DadosQuestionario(1, "Ponto material e corpo extenso", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaQuestBanco.add(new DadosQuestionario(2, "Referencial e trajetória", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaQuestBanco.add(new DadosQuestionario(3, "Distância percorrida e deslocamento", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaTxtBanco.add(new DadosTexto(1, "Ponto material e corpo extenso", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaTxtBanco.add(new DadosTexto(2, "Referencial e trajetória", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaTxtBanco.add(new DadosTexto(3, "Distância percorrida e deslocamento", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaProvasBanco.add(new DadosProvasAnteriores(1, 2022, 3, true, "demiLov"));
        listaProvasBanco.add(new DadosProvasAnteriores(2, 2019, 2, false, "doroteia"));
        listaProvasBanco.add(new DadosProvasAnteriores(3, 2006, 1, false, "luanSantana"));
        listaVideoBanco.add(new DadosVideo(1, "Ponto material e corpo extenso","link", "profAnaCastela", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo1));
        listaVideoBanco.add(new DadosVideo(2, "Referencial e trajetória","link", "zezeDiCamargo", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo2));
        listaVideoBanco.add(new DadosVideo(3, "Distância percorrida e deslocamento","link", "demiLovProf", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo1));


        //adicionando dados aleatorios de historico
        listaHistorico.add(new DadosHistorico(1, "Flashcard"));
        listaHistorico.add(new DadosHistorico(2, "Flashcard"));
        listaHistorico.add(new DadosHistorico(3, "Flashcard"));
        listaHistorico.add(new DadosHistorico(1, "Prova"));
        listaHistorico.add(new DadosHistorico(2, "Prova"));
        listaHistorico.add(new DadosHistorico(3, "Prova"));
        listaHistorico.add(new DadosHistorico(1, "Questionario"));
        listaHistorico.add(new DadosHistorico(2, "Questionario"));
        listaHistorico.add(new DadosHistorico(3, "Questionario"));
        listaHistorico.add(new DadosHistorico(1, "Texto"));
        listaHistorico.add(new DadosHistorico(2, "Texto"));
        listaHistorico.add(new DadosHistorico(3, "Texto"));
        listaHistorico.add(new DadosHistorico(1, "Video"));
        listaHistorico.add(new DadosHistorico(2, "Video"));
        listaHistorico.add(new DadosHistorico(3, "Video"));



        for(DadosHistorico dado : listaHistorico){
            if(dado.getTipoItem().equals("Flashcard")){
                DadosFlashcard flash= listaFlashcardsBanco.get(dado.getIdItem());
                listaFlashcardHistorico.add(flash);

            }else if(dado.getTipoItem().equals("Questionario")){
                DadosQuestionario quest = listaQuestBanco.get(dado.getIdItem());
                listaQuestHistorico.add(quest);
            }
            else if(dado.getTipoItem().equals("Texto")){
                DadosTexto txt = listaTxtBanco.get(dado.getIdItem());
                listaTxtHistorico.add(txt);

            }else if(dado.getTipoItem().equals("Prova")){
                DadosProvasAnteriores prova = listaProvasBanco.get(dado.getIdItem());
                listaProvasHistorico.add(prova);

            }else if(dado.getTipoItem().equals("Video")){
                DadosVideo video = listaVideoBanco.get(dado.getIdItem());
                listaVideoHistorico.add(video);
            }
        }


        //recyclers
        configurarRecyclerHistoricoFlashcards();
        configurarRecyclerHistoricoQuest();
        configurarRecyclerHistoricoTxt();
        configurarRecyclerHistoricoProvas();
        configurarRecyclerHistoricoVideos();
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

    private void configurarRecyclerHistoricoProvas() {
        rvHistoricoProvas=findViewById(R.id.recyclerViewHistoricoProvas);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoProvas.setLayoutManager(layoutManager);
        rvHistoricoProvas.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterProvas=new AdapterHistoricoProvas(listaProvasHistorico);
        rvHistoricoProvas.setAdapter(adapterProvas);

    }


    private void configurarRecyclerHistoricoTxt() {
        rvHistoricoTexto=findViewById(R.id.recyclerViewHistoricoTexto);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoTexto.setLayoutManager(layoutManager);
        rvHistoricoTexto.setHasFixedSize(true);
        FragmentManager fragmentManager = getSupportFragmentManager();
        adapterTxt=new AdapterHistoricoTexto(listaTxtHistorico);
        rvHistoricoTexto.setAdapter(adapterTxt);
    }

    private void configurarRecyclerHistoricoQuest() {
        rvHistoricoQuestionarios=findViewById(R.id.recyclerViewHistoricoQuestionario);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHistoricoQuestionarios.setLayoutManager(layoutManager);
        rvHistoricoQuestionarios.setHasFixedSize(true);
        adapterQuest=new AdapterHistoricoQuestionario(listaQuestHistorico);
        rvHistoricoQuestionarios.setAdapter(adapterQuest);
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
}