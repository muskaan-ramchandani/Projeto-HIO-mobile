package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterRanking;
import com.example.helperinolympics.model.DadosRanking;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends Activity {

    RecyclerView rvRanking;
    AdapterRanking adapter;
    ImageButton retornaInicio, acessacalendario;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        retornaInicio=findViewById(R.id.btnRetornarInicio);
        retornaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
            }
        });

        acessacalendario= findViewById(R.id.btnCalendario);

        configurarRecyclerRanking();
    }

    public void configurarRecyclerRanking(){
        rvRanking= findViewById(R.id.recyclerViewRanking);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvRanking.setLayoutManager(layoutManager);
        rvRanking.setHasFixedSize(true);

        //configurando divisória
        Drawable divisorItens = ContextCompat.getDrawable(this, R.drawable.linha_cinza);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvRanking.getContext(),
                new LinearLayoutManager(this).getOrientation());

        if (divisorItens != null) {
            dividerItemDecoration.setDrawable(divisorItens);
        }

        rvRanking.addItemDecoration(dividerItemDecoration);

        List<DadosRanking> listaRanking = new ArrayList<>();
        adapter = new AdapterRanking(listaRanking);
        rvRanking.setAdapter(adapter);

        //DADOS FICTICIOS
        DadosRanking dado1 = new DadosRanking(4, R.drawable.iconeseuperfil, 12330, "estevaoferreira");
        listaRanking.add(dado1);

        DadosRanking dado2 = new DadosRanking(5, R.drawable.iconeseuperfil, 12325, "pantera");
        listaRanking.add(dado2);

        DadosRanking dado3= new DadosRanking(6, R.drawable.iconeseuperfil, 12320, "noemiGadelha");
        listaRanking.add(dado3);

        DadosRanking dado4 = new DadosRanking(7, R.drawable.iconeseuperfil, 12315, "hannaMontana");
        listaRanking.add(dado4);

        DadosRanking dado5 = new DadosRanking(8, R.drawable.iconeseuperfil, 12310, "thiagooo");
        listaRanking.add(dado5);

        DadosRanking dado6 = new DadosRanking(9, R.drawable.iconeseuperfil, 12305, "paulista");
        listaRanking.add(dado6);

        DadosRanking dado7 = new DadosRanking(10, R.drawable.iconeseuperfil, 12300, "mahDiva");
        listaRanking.add(dado7);

        DadosRanking dado8 = new DadosRanking(11, R.drawable.iconeseuperfil, 12299, "lunaa");
        listaRanking.add(dado8);

        DadosRanking dado9 = new DadosRanking(12, R.drawable.iconeseuperfil, 1, "besties");
        listaRanking.add(dado9);
    }
}
