package com.example.helperinolympics;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Adapter;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.example.helperinolympics.adapter.AdapterDadosAcertos;
import com.example.helperinolympics.model.DadosAcertos;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class AcertosSemanaisActivity extends Activity {

    BarChart barChart;
    RecyclerView rVListaAcertos;

    AdapterDadosAcertos acertosAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acertos_semanais);

        //Configurações barra
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
        

        // Creating bar data
        BarData barData = new BarData(barDataSet);

        // Setting the data to the bar chart
        barChart.setData(barData);

        barChart.animateY(2000);

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
        DadosAcertos dados1 = new DadosAcertos("OBMEP", "Matrizes", "Determinante", "Por: Profº Maria João",
                "Pergunta: Qual das seguintes afirmações sobre determinantes está correta?", "Alternativa marcada: O determinante de uma matriz quadrada é sempre um número real.");

        listaAcertos.add(dados1);

        DadosAcertos dados2 = new DadosAcertos("OBI", "Estruturas condicionais", "If e else", "Por: Profº Maria João",
                "Pergunta: Para quê serve o uso da estrutura if/else?", "Alternativa marcada: Serve para avaliar uma expressão como sendo verdadeira ou falsa e, de acordo com o resultado dessa verificação, executar uma ou outra ação.");

        listaAcertos.add(dados2);
    }


}
