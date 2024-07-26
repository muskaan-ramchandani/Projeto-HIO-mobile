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
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.model.DadosLivros;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InicioOlimpiadaActivity extends AppCompatActivity {

    List<DadosConteudo> conteudos = new ArrayList<>();
    List<DadosLivros> livros = new ArrayList<>();
    RecyclerView rvConteudos, rvLivros, rvProvasAnteriores;
    AdapterConteudos adapterConteudos, adapterProvasAnteriores;
    AdapterLivros adapterLivros;

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
        configurarRecyclerLivros();
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

}
