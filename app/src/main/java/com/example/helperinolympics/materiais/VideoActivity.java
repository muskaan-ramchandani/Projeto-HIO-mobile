package com.example.helperinolympics.materiais;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterVideo;
import com.example.helperinolympics.model.DadosVideo;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
    RecyclerView rvVideo;
    AdapterVideo adapter;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        configurarRecyclerVideo();


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
