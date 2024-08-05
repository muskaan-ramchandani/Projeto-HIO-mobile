package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterDadosAcertos;
import com.example.helperinolympics.adapter.AdapterEventos;
import com.example.helperinolympics.model.DadosEventos;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarioActivity extends AppCompatActivity {
    RecyclerView rvListaEventos;
    AdapterEventos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        findViewById(R.id.btnAcessarHanking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarioActivity.this, RankingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnRetornarInicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarioActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        configurarRecyclerEventos();
    }

    private void configurarRecyclerEventos() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        rvListaEventos = findViewById(R.id.recyclerViewEventosCalendario);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListaEventos.setLayoutManager(layoutManager);
        rvListaEventos.setHasFixedSize(true);

        List<DadosEventos> listaEventos = new ArrayList<>();
        adapter = new AdapterEventos(listaEventos);
        rvListaEventos.setAdapter(adapter);

        //DADOS PARA TESTE
        Date data1 = null;
        try {
            // Converta a String para Date
            data1 = sdf.parse("08/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosEventos dados1 = new DadosEventos(1, data1, "OBA", "Inscrição",
                "09h às 19h", "https://link_para_inscricao");
        listaEventos.add(dados1);

        Date data2 = null;
        try {
            // Converta a String para Date
            data2 = sdf.parse("15/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosEventos dados2 = new DadosEventos(2, data2, "OBMEP", "Prova Fase 2",
                "06h às 12h", "https://link_para_prova");
        listaEventos.add(dados2);

        Date data3 = null;
        try {
            // Converta a String para Date
            data3 = sdf.parse("30/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosEventos dados3 = new DadosEventos(3, data3, "OBI", "Prova Fase 1",
                "00h01 às 23h59", "https://link_para_prova_OBI");
        listaEventos.add(dados3);

        adapter.notifyDataSetChanged();
    }
}
