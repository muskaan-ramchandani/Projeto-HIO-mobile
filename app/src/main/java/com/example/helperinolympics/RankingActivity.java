package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterRanking;
import com.example.helperinolympics.databinding.ActivityRankingBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Ranking;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends Activity {
    AdapterRanking adapter;

    ActivityRankingBinding binding;

    Aluno alunoCadastrado;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        binding.btnRetornarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, CalendarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        dadosPodio();
        configurarRecyclerRanking();
    }

    public void configurarRecyclerRanking(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewRanking.setLayoutManager(layoutManager);
        binding.recyclerViewRanking.setHasFixedSize(true);

        //configurando divisória
        Drawable divisorItens = ContextCompat.getDrawable(this, R.drawable.linha_cinza);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerViewRanking.getContext(),
                new LinearLayoutManager(this).getOrientation());

        if (divisorItens != null) {
            dividerItemDecoration.setDrawable(divisorItens);
        }

        binding.recyclerViewRanking.addItemDecoration(dividerItemDecoration);

        List<Ranking> listaRanking = new ArrayList<>();
        adapter = new AdapterRanking(listaRanking);
        binding.recyclerViewRanking.setAdapter(adapter);



    }

    public void dadosPodio(){

    }
}
