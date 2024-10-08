package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterDadosErros;
import com.example.helperinolympics.databinding.ActivityErrosSemanaisBinding;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Erros;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class ErrosSemanaisActivity extends Activity {
    private Aluno alunoCadastrado;
    private ActivityErrosSemanaisBinding binding;

    private AdapterDadosErros errosAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityErrosSemanaisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        binding.btnVoltarAoPerfilDosErros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErrosSemanaisActivity.this, PerfilAlunoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        configurarBarra();

        //Lista de erros
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewListaErros.setLayoutManager(layoutManager);
        binding.recyclerViewListaErros.setHasFixedSize(true);

        List<Erros> listaErros = new ArrayList<>();
        errosAdapter = new AdapterDadosErros(listaErros);
        binding.recyclerViewListaErros.setAdapter(errosAdapter);

        errosAdapter.notifyDataSetChanged();
    }

    private void configurarBarra(){
        //Entradas de dados
        ArrayList<BarEntry> entradaDados2 = new ArrayList<>();
        entradaDados2.add(new BarEntry(1f, 6f));
        entradaDados2.add(new BarEntry(2f, 1f));
        entradaDados2.add(new BarEntry(3f, 3f));

        // Cores
        int corAzul = ContextCompat.getColor(this, R.color.btnOlimpiadaAzul);
        int corRosa = ContextCompat.getColor(this, R.color.btnOlimpiadaRosa);
        int corRoxa = ContextCompat.getColor(this, R.color.corIcones);
        int[] cores = new int[] {corAzul, corRosa, corRoxa};

        // Creating a bar data set
        BarDataSet barDataSet = new BarDataSet(entradaDados2, "Gráfico de comparação");
        barDataSet.setColors(cores);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        //Inserindo legenda
        Legend legend = binding.graficoBarraErrosSemanais.getLegend();
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
        binding.graficoBarraErrosSemanais.setData(barData);
        binding.graficoBarraErrosSemanais.animateY(2000);
    }


}
