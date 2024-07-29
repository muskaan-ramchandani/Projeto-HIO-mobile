package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterVideo;
import com.example.helperinolympics.model.DadosVideo;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    RecyclerView rvVideo;
    AdapterVideo adapter;
    ImageButton botaoVoltarAOlimp;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        configurarRecyclerVideo();

        botaoVoltarAOlimp=findViewById(R.id.imgButtonVoltarAOlimpPeloVideo);
        botaoVoltarAOlimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, InicioOlimpiadaActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        findViewById(R.id.btnQuestionarioPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, QuestionarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnTextoPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, TextoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, FlashcardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void configurarRecyclerVideo(){
        rvVideo=findViewById(R.id.recyclerviewVideo);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvVideo.setLayoutManager(layoutManager);
        rvVideo.setHasFixedSize(true);

        List<DadosVideo> listaVideo= new ArrayList<>();
        adapter=new AdapterVideo(listaVideo);
        rvVideo.setAdapter(adapter);

        DadosVideo dado1=new DadosVideo("Ponto material e corpo extenso","link", "profAnaCastela", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo1);
        listaVideo.add(dado1);
        DadosVideo dado2=new DadosVideo("Referencial e trajetória","link", "zezeDiCamargo", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo2);
        listaVideo.add(dado2);
        DadosVideo dado3=new DadosVideo("Distância percorrida e deslocamento","link", "demiLovProf", "OBF","Fundamentos da cinemática do ponto material", R.drawable.fotovideo1);
        listaVideo.add(dado3);
    }
}
