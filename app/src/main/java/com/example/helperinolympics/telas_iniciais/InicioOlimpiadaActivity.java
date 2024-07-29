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
import com.example.helperinolympics.adapter.AdapterLivros;
import com.example.helperinolympics.adapter.AdapterProvasAnteriores;
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.model.DadosLivros;
import com.example.helperinolympics.model.DadosProvasAnteriores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InicioOlimpiadaActivity extends AppCompatActivity {

    List<DadosConteudo> conteudos = new ArrayList<>();
    List<DadosLivros> livros = new ArrayList<>();
    List<DadosProvasAnteriores> provas = new ArrayList<>();
    RecyclerView rvConteudos, rvLivros, rvProvasAnteriores;
    AdapterConteudos adapterConteudos;
    AdapterLivros adapterLivros;
    AdapterProvasAnteriores adapterProvasAnteriores;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_olimpiada);

        findViewById(R.id.btnBemVindoVoltarAoLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioOlimpiadaActivity.this, TelaLoginActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnIniciar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioOlimpiadaActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        configurarRecyclerConteudos();
        configurarRecyclerLivros();
        configurarRecyclerProvas();
    }

    public void configurarRecyclerConteudos(){

        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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

        DadosConteudo dado4 = new DadosConteudo(4, "Termologia", "Termometria, Calorimetria, Termodinâmica",
                "OBF", "Ciano");
        conteudos.add(dado4);

        DadosConteudo dado5 = new DadosConteudo(5, "Campo Elétrico", "Energia e trabalho",
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

    private void configurarRecyclerLivros() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapterLivros= new AdapterLivros(livros);
        rvLivros=findViewById(R.id.recyclerViewLivros);
        rvLivros.setLayoutManager(layoutManager);
        rvLivros.setHasFixedSize(true);
        rvLivros.setAdapter(adapterLivros);

        //DADOS FICTICIOS
        Date dataPublicacao1 = null;
        try {
            // Converta a String para Date
            dataPublicacao1 = sdf.parse("22/02/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosLivros dado1 = new DadosLivros(1, R.drawable.imglivrofisica, "O Livro da Física", "Maria Souza" ,"3", dataPublicacao1);
        livros.add(dado1);

        Date dataPublicacao2 = null;
        try {
            // Converta a String para Date
            dataPublicacao2 = sdf.parse("13/03/2013");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosLivros dado2 = new DadosLivros(2, R.drawable.imglivrofisica, "O Livro 2", "Mariaana" ,"7", dataPublicacao2);
        livros.add(dado2);

        Date dataPublicacao3 = null;
        try {
            // Converta a String para Date
            dataPublicacao3 = sdf.parse("01/01/1991");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosLivros dado3 = new DadosLivros(3, R.drawable.imglivrofisica, "OUTRO", "Magali" ,"1", dataPublicacao3);
        livros.add(dado3);

    }

    private void configurarRecyclerProvas() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapterProvasAnteriores= new AdapterProvasAnteriores(provas);
        rvProvasAnteriores=findViewById(R.id.recyclerViewProvasAnteriores);
        rvProvasAnteriores.setLayoutManager(layoutManager);
        rvProvasAnteriores.setHasFixedSize(true);
        rvProvasAnteriores.setAdapter(adapterProvasAnteriores);


        //DADOS FICTICIOS
        DadosProvasAnteriores dado1 = new DadosProvasAnteriores(1, 2022, 3, true, "demiLov");
        provas.add(dado1);

        DadosProvasAnteriores dado2 = new DadosProvasAnteriores(2, 2019, 2, false, "doroteia");
        provas.add(dado2);

        DadosProvasAnteriores dado3 = new DadosProvasAnteriores(3, 2006, 1, false, "luanSantana");
        provas.add(dado3);

        DadosProvasAnteriores dado4 = new DadosProvasAnteriores(4, 2020, 5, true, "picasso");
        provas.add(dado4);

        DadosProvasAnteriores dado5 = new DadosProvasAnteriores(5, 2015, 1, false, "marianaContaUm");
        provas.add(dado5);

    }

}
