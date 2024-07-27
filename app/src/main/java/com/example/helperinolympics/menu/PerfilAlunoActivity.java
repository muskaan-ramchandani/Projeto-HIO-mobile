package com.example.helperinolympics.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.helperinolympics.AcertosSemanaisActivity;
import com.example.helperinolympics.ErrosSemanaisActivity;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;
import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityPerfilAlunoBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class PerfilAlunoActivity extends Activity {

    private ActivityPerfilAlunoBinding binding;
    Button btnVoltarInicio, btnAcertos, btnErros;
    TextView txtAcertos, txtErros;
    PieChart graficoPizzaEstatisticas;

    LinearLayout linearGraficoPerfil;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_aluno);

        binding = ActivityPerfilAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnVoltarInicio= binding.btnIniciar;
        btnVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        btnAcertos= binding.btnHistoricoAcertos;
        btnAcertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, AcertosSemanaisActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        btnErros= binding.btnHistoricoErros;
        btnErros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, ErrosSemanaisActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        //dados gráfico
        txtAcertos= findViewById(R.id.txtLegendaAcertos);
        txtErros= findViewById(R.id.txtLegendaErros);
        graficoPizzaEstatisticas= findViewById(R.id.graficoPizzaErrosAcertos);
        setData();
    }

    private void setData(){
        linearGraficoPerfil = findViewById(R.id.linearLayoutGraficoAcertosEErros);
        Integer numeroAcertos;
        Integer numeroErros;

        numeroAcertos = 25;
        numeroErros = 30;

        //fazer um if para verificar se há dados para comparar
        if((numeroAcertos==null)&&(numeroErros==null)){
            PieChart grafico = findViewById(R.id.graficoPizzaErrosAcertos);
            LinearLayout linearLegendas = findViewById(R.id.linearLayoutLegendaGrafico);

            linearGraficoPerfil.removeView(grafico);
            linearGraficoPerfil.removeView(linearLegendas);

            LayoutInflater inflater = LayoutInflater.from(this);
            View newItemView = inflater.inflate(R.layout.msg_sem_dados_grafico, linearGraficoPerfil, false);

            linearGraficoPerfil.addView(newItemView);
        }else{
            String conteudoAcertos= "Acertos ("+Integer.toString(numeroAcertos)+")";
            String conteudoErros= "Erros ("+Integer.toString(numeroErros)+")";

            txtAcertos.setText(conteudoAcertos);
            txtErros.setText(conteudoErros);

            graficoPizzaEstatisticas.addPieSlice(new PieModel("Acertos", numeroAcertos,
                    Color.parseColor("#835AD2")));

            graficoPizzaEstatisticas.addPieSlice(new PieModel("Erros", numeroErros,
                    Color.parseColor("#CB6CE6")));

            graficoPizzaEstatisticas.startAnimation();
        }
    }
}