package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterQuestionario;
import com.example.helperinolympics.model.DadosQuestionario;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.ArrayList;
import java.util.List;

public class QuestionarioActivity extends AppCompatActivity {
    RecyclerView rvQuestionario;
    AdapterQuestionario adapter;
    ImageButton botaoVoltarAOlimp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

        configurarRecyclerQuestionario();

        botaoVoltarAOlimp = findViewById(R.id.imgButtonVoltarConteudo);
        botaoVoltarAOlimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, InicioOlimpiadaActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnTextoPeloQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, TextoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideoPeloQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuestionarioActivity.this, FlashcardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarRecyclerQuestionario() {
        rvQuestionario = findViewById(R.id.recyclerviewQuestionario);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvQuestionario.setLayoutManager(layoutManager);

        List<DadosQuestionario> listaQuestionario = new ArrayList<>();
        listaQuestionario.add(new DadosQuestionario("Ponto material e corpo extenso", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaQuestionario.add(new DadosQuestionario("Referencial e trajetória", "material", "OBF", "Fundamentos da cinemática do ponto material"));
        listaQuestionario.add(new DadosQuestionario("Distância percorrida e deslocamento", "material", "OBF", "Fundamentos da cinemática do ponto material"));

        adapter = new AdapterQuestionario(listaQuestionario);
        rvQuestionario.setAdapter(adapter);
    }
}
