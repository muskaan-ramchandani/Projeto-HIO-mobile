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
        DadosTexto dado1 = new DadosTexto("Ponto material e corpo extenso", "material", "OBF", "Fundamentos da cinemática do ponto material");
        listaTexto.add(dado1);
        DadosTexto dado2 = new DadosTexto("Referencial e trajetória", "material", "OBF", "Fundamentos da cinemática do ponto material");
        listaTexto.add(dado2);
        DadosTexto dado3 = new DadosTexto("Distância percorrida e deslocamento", "material", "OBF", "Fundamentos da cinemática do ponto material");
        listaTexto.add(dado3);

        // Criando e configurando o adapter após adicionar os dados
        adapter = new AdapterTexto(listaTexto);
        rvTexto.setAdapter(adapter);
    }
}
