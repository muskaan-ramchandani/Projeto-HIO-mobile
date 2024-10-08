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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ErrosSemanaisActivity extends Activity {
    private Aluno alunoCadastrado;
    private ActivityErrosSemanaisBinding binding;
    private Date dataAtual, dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2,
            dataInicialSemana3, dataFinalSemana3;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

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

        configurarDatas();
        configurarBarra();
        configurarRecyclerErros();
    }

    private void configurarRecyclerErros() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewListaErros.setLayoutManager(layoutManager);
        binding.recyclerViewListaErros.setHasFixedSize(true);

        List<Erros> listaErros = new ArrayList<>();
        errosAdapter = new AdapterDadosErros(listaErros);
        binding.recyclerViewListaErros.setAdapter(errosAdapter);

        errosAdapter.notifyDataSetChanged();
    }

    private void configurarDatas() {
        //CONFIGURANDO DATAS (SEMANA ATUAL, PASSADA E RETRASADA)
        Calendar calendar = Calendar.getInstance();
        dataAtual = calendar.getTime();

        // Semana 3 (semana atual)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dataInicialSemana3 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dataFinalSemana3 = calendar.getTime();

        // Semana 2 (semana passada)
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dataInicialSemana2 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dataFinalSemana2 = calendar.getTime();

        // Semana 1 (semana retrasada)
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dataInicialSemana1 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dataFinalSemana1 = calendar.getTime();
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

        //LEGENDAS DO GRÁFICO
        LegendEntry entradaLegenda1 = new LegendEntry();
        entradaLegenda1.label = dateFormat.format(dataInicialSemana1) +  dateFormat.format(dataFinalSemana1);
        entradaLegenda1.formColor = corAzul;

        LegendEntry entradaLegenda2 = new LegendEntry();
        entradaLegenda2.label = dateFormat.format(dataInicialSemana2) +  dateFormat.format(dataFinalSemana2);
        entradaLegenda2.formColor = corRosa;

        LegendEntry entradaLegenda3 = new LegendEntry();
        entradaLegenda3.label = dateFormat.format(dataInicialSemana3) +  dateFormat.format(dataFinalSemana3);
        entradaLegenda3.formColor = corRoxa;

        legend.setCustom(new LegendEntry[]{entradaLegenda1, entradaLegenda2, entradaLegenda3});

        //Adicionando configurações
        BarData barData = new BarData(barDataSet);
        binding.graficoBarraErrosSemanais.setData(barData);
        binding.graficoBarraErrosSemanais.animateY(2000);
    }




}
