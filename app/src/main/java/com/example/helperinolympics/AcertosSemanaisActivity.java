package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.helperinolympics.adapter.AdapterDadosAcertos;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.model.DadosAcertos;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.BarEntry;


public class AcertosSemanaisActivity extends Activity {

    BarChart barChart;
    RecyclerView rVListaAcertos;
    AdapterDadosAcertos acertosAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acertos_semanais);

        findViewById(R.id.btnVoltarAoPerfilDosAcertos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcertosSemanaisActivity.this, PerfilAlunoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        configurarBarra();


        //Lista de acertos
        rVListaAcertos = findViewById(R.id.recyclerViewListaAcertos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rVListaAcertos.setLayoutManager(layoutManager);
        rVListaAcertos.setHasFixedSize(true);

        List<DadosAcertos> listaAcertos = new ArrayList<>();
        acertosAdapter = new AdapterDadosAcertos(listaAcertos);
        rVListaAcertos.setAdapter(acertosAdapter);

        //DADOS PARA TESTE
        DadosAcertos dados1 = new DadosAcertos("OBMEP", "Matrizes", "Determinante", "Profº Maria João",
                "Qual das seguintes afirmações sobre determinantes está correta?", "Alternativa marcada: O determinante de uma matriz quadrada é sempre um número real.");

        listaAcertos.add(dados1);

        DadosAcertos dados2 = new DadosAcertos("OBI", "Estruturas condicionais", "If e else", "Profº Maria João",
                "Para quê serve o uso da estrutura if/else?", "Alternativa marcada: Serve para avaliar uma expressão como sendo verdadeira ou falsa e, de acordo com o resultado dessa verificação, executar uma ou outra ação.");

        listaAcertos.add(dados2);
        acertosAdapter.notifyDataSetChanged();
    }

    private void configurarBarra(){
        barChart = findViewById(R.id.graficoBarraAcertosSemanais);

        //Entradas de dados
        ArrayList<BarEntry> entradaDados = new ArrayList<>();
        entradaDados.add(new BarEntry(1f, 3f));
        entradaDados.add(new BarEntry(2f, 4f));
        entradaDados.add(new BarEntry(3f, 5f));

        // Cores
        int corAzul = ContextCompat.getColor(this, R.color.btnOlimpiadaAzul);
        int corRosa = ContextCompat.getColor(this, R.color.btnOlimpiadaRosa);
        int corRoxa = ContextCompat.getColor(this, R.color.corIcones);
        int[] cores = new int[] {corAzul, corRosa, corRoxa};

        // Creating a bar data set
        BarDataSet barDataSet = new BarDataSet(entradaDados, "Gráfico de comparação");
        barDataSet.setColors(cores);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        //Inserindo legenda
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f); // Tamanho do ícone na legenda
        legend.setXEntrySpace(25f); // Espaçamento horizontal entre as entradas da legenda
        legend.setYEntrySpace(5f); // Espaçamento vertical entre as entradas da legenda

        LegendEntry entradaLegenda1 = new LegendEntry();
        entradaLegenda1.label = "23/06 - 29/06";
        entradaLegenda1.formColor = corAzul;

        LegendEntry entradaLegenda2 = new LegendEntry();
        entradaLegenda2.label = "30/06 - 06/07";
        entradaLegenda2.formColor = corRosa;

        LegendEntry entradaLegenda3 = new LegendEntry();
        entradaLegenda3.label = "07/07 - 13/07";
        entradaLegenda3.formColor = corRoxa;

        legend.setCustom(new LegendEntry[]{entradaLegenda1, entradaLegenda2, entradaLegenda3});

        //Adicionando configurações
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.animateY(2000);
    }


}
