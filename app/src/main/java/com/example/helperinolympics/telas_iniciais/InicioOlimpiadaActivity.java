package com.example.helperinolympics.telas_iniciais;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterConteudos;
import com.example.helperinolympics.model.DadosConteudo;

import java.util.ArrayList;
import java.util.List;

public class InicioOlimpiadaActivity extends AppCompatActivity {

    List<DadosConteudo> conteudos = new ArrayList<>();
    RecyclerView rvConteudos, rvLivros, rvProvasAnteriores;
    AdapterConteudos adapterConteudos, adapterLivros, adapterProvasAnteriores;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_olimpiada);

        findViewById(R.id.btnIniciar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioOlimpiadaActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        configurarRecyclerConteudos();
    }

    public void configurarRecyclerConteudos(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapterConteudos= new AdapterConteudos(conteudos);
        rvConteudos=findViewById(R.id.recyclerViewConteudosOlimpiada);
        rvConteudos.setLayoutManager(layoutManager);
        rvConteudos.setHasFixedSize(true);
        rvConteudos.setAdapter(adapterConteudos);


        //DADOS FICTICIOS ENQUANTO NÃO HÁ BANCO
        DadosConteudo dado1 = new DadosConteudo(1, "Mecânica Clássica", "Fundamentos da cinemática do ponto material",
                "OBF", "Rosa");
        conteudos.add(dado1);

        DadosConteudo dado2 = new DadosConteudo(2, "Dilatação Superficial", "Conceito e fórmulas",
                "OBF", "Azul");
        conteudos.add(dado2);

        DadosConteudo dado3 = new DadosConteudo(3, "Estática e Hidrostática", "Princípios Básicos",
                "OBF", "Laranja");
        conteudos.add(dado3);

        DadosConteudo dado4 = new DadosConteudo(1, "Termologia", "Termometria, Calorimetria, Termodinâmica",
                "OBF", "Ciano");
        conteudos.add(dado4);

        DadosConteudo dado5 = new DadosConteudo(1, "Campo Elétrico", "Energia e trabalho",
                "OBF", "Rosa");
        conteudos.add(dado5);

        adapterConteudos.notifyDataSetChanged();

        /*QUANDO TIVER OS ASSUNTOS PARA CADA OLIMPIADA NO BANCO

        Intent intent = getIntent();
        ArrayList<DadosConteudo> listaRecebida = intent.getParcelableArrayListExtra("listaEscolhidas");
        if (listaRecebida != null) {
            olimpiadas.clear();
            olimpiadas.addAll(listaRecebida);
            adapter.notifyDataSetChanged();
        } else {
            Log.e("InicialAlunoMenuDeslizanteActivity", "Nenhuma lista de olimpiadas foi recebida.");
        }*/
    }

}
