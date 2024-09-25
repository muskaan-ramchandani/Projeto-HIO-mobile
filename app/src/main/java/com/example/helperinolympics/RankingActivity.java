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
import com.example.helperinolympics.model.Ranking;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends Activity {

    RecyclerView rvRanking;
    AdapterRanking adapter;
    ImageButton retornaInicio, acessarCalendario;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        retornaInicio=findViewById(R.id.btnRetornarInicio);
        retornaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        acessarCalendario =findViewById(R.id.btnCalendario);
        acessarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, CalendarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        dadosPodio();
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

        List<Ranking> listaRanking = new ArrayList<>();
        adapter = new AdapterRanking(listaRanking);
        rvRanking.setAdapter(adapter);


        //DADOS FICTICIOS
        Ranking dado4 = new Ranking(4, R.drawable.iconeseuperfil, 12330, "estevaoferreira");
        listaRanking.add(dado4);

        Ranking dado5 = new Ranking(5, R.drawable.iconeseuperfil, 12325, "pantera");
        listaRanking.add(dado5);

        Ranking dado6= new Ranking(6, R.drawable.iconeseuperfil, 12320, "noemiGadelha");
        listaRanking.add(dado6);

        Ranking dado7 = new Ranking(7, R.drawable.iconeseuperfil, 12315, "hannaMontana");
        listaRanking.add(dado7);

        Ranking dado8 = new Ranking(8, R.drawable.iconeseuperfil, 12310, "thiagooo");
        listaRanking.add(dado8);

        Ranking dado9 = new Ranking(9, R.drawable.iconeseuperfil, 12305, "paulista");
        listaRanking.add(dado9);

        Ranking dado10 = new Ranking(10, R.drawable.iconeseuperfil, 12300, "mahDiva");
        listaRanking.add(dado10);

        Ranking dado11 = new Ranking(11, R.drawable.iconeseuperfil, 12299, "lunaa");
        listaRanking.add(dado11);

        Ranking dado12 = new Ranking(12, R.drawable.iconeseuperfil, 1, "besties");
        listaRanking.add(dado12);

        Ranking dado13 = new Ranking(13, R.drawable.iconeseuperfil, 0, "muska");
        listaRanking.add(dado13);
    }

    public void dadosPodio(){

        //DADOS FICTICIOS PODIO
        List<Ranking> listaRankingPodio = new ArrayList<>();
        Ranking dado1 = new Ranking(1, R.drawable.iconeseuperfil, 12400, "User123");
        listaRankingPodio.add(dado1);

        Ranking dado2 = new Ranking(2, R.drawable.iconeseuperfil, 12340, "User321");
        listaRankingPodio.add(dado2);

        Ranking dado3 = new Ranking(3, R.drawable.iconeseuperfil, 12335, "User213");
        listaRankingPodio.add(dado3);

        //Atribuindo aos itens da activity
        ImageView fotoPosicao1, fotoPosicao2, fotoPosicao3;
        TextView userPosicao1, userPosicao2, userPosicao3, acertosPosicao1, acertosPosicao2, acertosPosicao3;

        fotoPosicao1 = findViewById(R.id.fotoPerfilPosicao1);
        fotoPosicao2= findViewById(R.id.fotoPerfilPosicao2);
        fotoPosicao3=findViewById(R.id.fotoPerfilPosicao3);
        userPosicao1=findViewById(R.id.txtUserPosicao1);
        userPosicao2=findViewById(R.id.txtUserPosicao2);
        userPosicao3=findViewById(R.id.txtUserPosicao3);
        acertosPosicao1=findViewById(R.id.txtQntdAcertosPosicao1);
        acertosPosicao2=findViewById(R.id.txtQntdAcertosPosicao2);
        acertosPosicao3=findViewById(R.id.txtQntdAcertosPosicao3);

        fotoPosicao1.setImageResource(listaRankingPodio.get(0).getFotoPerfil());
        userPosicao1.setText(String.valueOf(listaRankingPodio.get(0).getUser()));
        String acertos = String.valueOf(listaRankingPodio.get(0).getQntdAcertos()) + " acertos";
        acertosPosicao1.setText(acertos);

        fotoPosicao2.setImageResource(listaRankingPodio.get(1).getFotoPerfil());
        userPosicao2.setText(String.valueOf(listaRankingPodio.get(1).getUser()));
        String acertos2 = String.valueOf(listaRankingPodio.get(1).getQntdAcertos()) + " acertos";
        acertosPosicao2.setText(acertos2);

        fotoPosicao3.setImageResource(listaRankingPodio.get(2).getFotoPerfil());
        userPosicao3.setText(String.valueOf(listaRankingPodio.get(2).getUser()));
        String acertos3 = String.valueOf(listaRankingPodio.get(2).getQntdAcertos()) + " acertos";
        acertosPosicao3.setText(acertos3);

    }
}
