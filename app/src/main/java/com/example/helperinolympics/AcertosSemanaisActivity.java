package com.example.helperinolympics;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

public class AcertosSemanaisActivity extends Activity {

    BarChart barChart;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acertos_semanais);

        //Configurações barra
        barChart = findViewById(R.id.graficoBarraAcertosSemanais);

        //Entradas de dados
        ArrayList<BarEntry> entradaDados = new ArrayList<>();
        entradaDados.add(new BarEntry(1f, 3f));
        entradaDados.add(new BarEntry(2f, 4f));
        entradaDados.add(new BarEntry(3f, 2f));

        // Cores
        int corAzul = ContextCompat.getColor(this, R.color.btnOlimpiadaAzul);
        int corRosa = ContextCompat.getColor(this, R.color.btnOlimpiadaRosa);
        int corRoxa = ContextCompat.getColor(this, R.color.corIcones);
        int[] cores = new int[] {corAzul, corRosa, corRoxa};

        // Creating a bar data set
        BarDataSet barDataSet = new BarDataSet(entradaDados, "Data Set 1");
        barDataSet.setColors(cores);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);
        

        // Creating bar data
        BarData barData = new BarData(barDataSet);

        // Setting the data to the bar chart
        barChart.setData(barData);

        barChart.animateY(2000);
    }


}
