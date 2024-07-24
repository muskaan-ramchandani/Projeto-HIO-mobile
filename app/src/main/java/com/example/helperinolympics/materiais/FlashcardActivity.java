package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterFlashcard;
import com.example.helperinolympics.adapter.AdapterVideo;
import com.example.helperinolympics.model.DadosFlashcard;
import com.example.helperinolympics.model.DadosVideo;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.ArrayList;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {
    RecyclerView rvFlashcard;
    AdapterFlashcard adapter;
    ImageButton botaoVoltarAOlimp;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);
        configurarRecyclerFlashcard();

        botaoVoltarAOlimp=findViewById(R.id.btnVoltarDeFlashcardParaInicio);
        botaoVoltarAOlimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, InicioOlimpiadaActivity.class);
                startActivity(intent);
            }
        });

    }

    public void configurarRecyclerFlashcard(){
        rvFlashcard=findViewById(R.id.recyclerviewFlashcard);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFlashcard.setLayoutManager(layoutManager);
        rvFlashcard.setHasFixedSize(true);

        List<DadosFlashcard> listaFlashcard= new ArrayList<>();
        adapter=new AdapterFlashcard(listaFlashcard);
        rvFlashcard.setAdapter(adapter);

        DadosFlashcard dado1=new DadosFlashcard("Ponto material e corpo extenso","material", "OBF", "Fundamentos da cinemática do ponto material");
        listaFlashcard.add(dado1);
        DadosFlashcard dado2=new DadosFlashcard("Referencial e trajetória","material", "OBF", "Fundamentos da cinemática do ponto material");
        listaFlashcard.add(dado2);
        DadosFlashcard dado3=new DadosFlashcard("Distância percorrida e deslocamento","material", "OBF", "Fundamentos da cinemática do ponto material");
        listaFlashcard.add(dado3);
    }
}
