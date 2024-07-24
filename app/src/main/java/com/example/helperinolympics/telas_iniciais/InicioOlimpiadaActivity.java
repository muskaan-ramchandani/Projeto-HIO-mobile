package com.example.helperinolympics.telas_iniciais;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterOlimpiadas;
import com.example.helperinolympics.materiais.VideoActivity;
import com.example.helperinolympics.model.DadosOlimpiada;

import java.util.ArrayList;
import java.util.List;

public class InicioOlimpiadaActivity extends Activity {

    RecyclerView rvOlimpiadas;
    AdapterOlimpiadas adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_olimpiada);

        findViewById(R.id.btnConteudo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioOlimpiadaActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

        configurarRecyclerOlimpiadas();
    }

    public void configurarRecyclerOlimpiadas(){
        rvOlimpiadas=findViewById(R.id.recyclerViewTelaInicialOlimpiadas);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOlimpiadas.setLayoutManager(layoutManager);
        rvOlimpiadas.setHasFixedSize(true);

        List<DadosOlimpiada> olimpiadas = new ArrayList<>();
        adapter= new AdapterOlimpiadas(olimpiadas);
        rvOlimpiadas.setAdapter(adapter);

        //DADOS PARA O BANCO
        DadosOlimpiada dado1 = new DadosOlimpiada(R.drawable.imgtelescopio, "Olimpíada Brasileira de Astronomia",
        "OBA", "Rosa");
        olimpiadas.add(dado1);

        DadosOlimpiada dado2 = new DadosOlimpiada(R.drawable.imgmacacaindo, "Olimpíada Brasileira de Física",
                "OBF", "Azul");
        olimpiadas.add(dado2);

        DadosOlimpiada dado3 = new DadosOlimpiada(R.drawable.imgcomputador, "Olimpíada Brasileira de Informática",
                "OBI", "Laranja");
        olimpiadas.add(dado3);

        //CONFIGURAR PARA AUMENTAR SE FOR OBMEP
        DadosOlimpiada dado4 = new DadosOlimpiada(R.drawable.imgoperacoesbasicas, "Olimpíada Brasileira de Matemática das Escolas Públicas",
                "OBMEP", "Ciano");
        olimpiadas.add(dado4);

        DadosOlimpiada dado5 = new DadosOlimpiada(R.drawable.imgpapiro, "Olimpíada Nacional da História Brasileira",
                "ONHB", "Rosa");
        olimpiadas.add(dado5);

        DadosOlimpiada dado6 = new DadosOlimpiada(R.drawable.imgtubodeensaio, "Olimpíada Brasileira de Química",
                "OBQ", "Azul");
        olimpiadas.add(dado6);

        DadosOlimpiada dado7 = new DadosOlimpiada(R.drawable.imgdna, "Olimpíada Brasileira de Biologia",
                "OBB", "Laranja");
        olimpiadas.add(dado7);

        DadosOlimpiada dado8 = new DadosOlimpiada(R.drawable.imgatomo, "Olimpíada Nacional de Ciências",
                "ONC", "Ciano");
        olimpiadas.add(dado8);


    }
}
