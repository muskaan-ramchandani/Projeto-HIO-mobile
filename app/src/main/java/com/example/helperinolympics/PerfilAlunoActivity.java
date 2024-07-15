package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.example.helperinolympics.databinding.ActivityPerfilAlunoBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class PerfilAlunoActivity extends Activity {

    private ActivityPerfilAlunoBinding binding;
    Button btnVoltarInicio, btnAcertos;
    TextView txtAcertos, txtErros;
    PieChart graficoPizzaEstatisticas;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_aluno);

        binding = ActivityPerfilAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnVoltarInicio= binding.btnIniciar;
        btnVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, MenuDeslizanteAlunoActivity.class);
                startActivity(intent);
            }
        });

        btnAcertos= binding.btnHistoricoAcertos;
        btnAcertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, AcertosSemanaisActivity.class);
                startActivity(intent);
            }
        });

        //dados gráfico
        txtAcertos= findViewById(R.id.txtLegendaAcertos);
        txtErros= findViewById(R.id.txtLegendaErros);
        graficoPizzaEstatisticas= findViewById(R.id.graficoPizzaErrosAcertos);
        setData();
    }

    private void setData(){
        int numeroAcertos = 25;
        int numeroErros = 30;
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