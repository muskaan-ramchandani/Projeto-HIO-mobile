package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterTexto;
import com.example.helperinolympics.model.DadosTexto;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.ArrayList;
import java.util.List;

public class TextoActivity extends AppCompatActivity {
    RecyclerView rvTexto;
    AdapterTexto adapter;
    ImageButton botaoVoltarAOlimp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texto);
        configurarRecyclerTexto();

        botaoVoltarAOlimp = findViewById(R.id.imgButtonVoltarAOlimpDoTxt);
        botaoVoltarAOlimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, InicioOlimpiadaActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        findViewById(R.id.btnQuestionarioPeloTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, QuestionarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideoPeloTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TextoActivity.this, FlashcardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void configurarRecyclerTexto() {
        rvTexto = findViewById(R.id.recyclerviewTexto);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTexto.setLayoutManager(layoutManager);
        rvTexto.setHasFixedSize(true);

        List<DadosTexto> listaTexto = new ArrayList<>();
        // Adicionando dados à lista
        DadosTexto dado1 = new DadosTexto(1, "Ponto material e corpo extenso", "material", "OBF", "Fundamentos da cinemática do ponto material", "Cinemática Escalar é um dos principais ramos da Mecânica. Trata-se da área que estuda o movimento dos corpos sem atribuir-lhes uma causa. A palavra escalar refere-se ao fato de lidarmos com movimentos unicamente unidimensionais, ou seja, que se desenvolvem unicamente ao longo de uma direção do espaço, dispensando, dessa forma, o tratamento vetorial das grandezas físicas envolvidas.\n" + "Para o estudo da Cinemática Escalar, alguns conceitos são de grande importância, portanto, trataremos aqui daqueles que são fundamentais para o seu entendimento.\n" + "\n" + "Conceitos fundamentais da Cinemática Escalar\n" + "\n" + "→ Corpo: É uma porção limitada de matéria e é constituído por partículas, mas pode ser tratado macroscopicamente como um único corpo no âmbito da Cinemática Escalar.\n" + "\n" + "→ Ponto material: É todo corpo cujas dimensões podem ser desprezadas em relação às distâncias envolvidas.\n");
        listaTexto.add(dado1);
        DadosTexto dado2 = new DadosTexto(2, "Referencial e trajetória", "material", "OBF", "Fundamentos da cinemática do ponto material", "texto 2");
        listaTexto.add(dado2);
        DadosTexto dado3 = new DadosTexto(3, "Distância percorrida e deslocamento", "material", "OBF", "Fundamentos da cinemática do ponto material", "texto 3");
        listaTexto.add(dado3);

        // Criando e configurando o adapter após adicionar os dados
        adapter = new AdapterTexto(listaTexto);
        rvTexto.setAdapter(adapter);
    }
}
