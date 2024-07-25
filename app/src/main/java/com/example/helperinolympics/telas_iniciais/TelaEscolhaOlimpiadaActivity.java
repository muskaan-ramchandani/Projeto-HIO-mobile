package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterEscolhaOlimpiadas;
import com.example.helperinolympics.cadastros.CadastroActivity;
import com.example.helperinolympics.model.DadosOlimpiada;

import java.util.ArrayList;
import java.util.List;

public class TelaEscolhaOlimpiadaActivity extends AppCompatActivity {

    RecyclerView rvOlimpiadasEscolher;
    List<DadosOlimpiada> listaOlimpiadasOpcoes = new ArrayList<>();
    AdapterEscolhaOlimpiadas adapter = new AdapterEscolhaOlimpiadas(listaOlimpiadasOpcoes);
    AppCompatButton btnFinalizar;
    ImageView btnVoltarAoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_escolha_olimpiada);

        configurarRecycler();

        btnFinalizar = findViewById(R.id.btnFinalizarEscolha);
        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolhaOlimpiadaActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putParcelableArrayListExtra("listaEscolhidas", new ArrayList<>(adapter.getListaEscolhidas()));
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        btnVoltarAoCadastro = findViewById(R.id.btnVoltarAoCadastro);
        btnVoltarAoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaEscolhaOlimpiadaActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });



    }

    private void configurarRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvOlimpiadasEscolher = findViewById(R.id.recyclerViewEscolhaOlimpiadas);
        rvOlimpiadasEscolher.setLayoutManager(layoutManager);
        rvOlimpiadasEscolher.setHasFixedSize(true);
        rvOlimpiadasEscolher.setAdapter(adapter);

        // Adiciona dados à lista
        DadosOlimpiada dado1 = new DadosOlimpiada(R.drawable.imgtelescopio, "Olimpíada Brasileira de Astronomia", "OBA", "Rosa");
        listaOlimpiadasOpcoes.add(dado1);

        DadosOlimpiada dado2 = new DadosOlimpiada(R.drawable.imgmacacaindo, "Olimpíada Brasileira de Física", "OBF", "Azul");
        listaOlimpiadasOpcoes.add(dado2);

        DadosOlimpiada dado3 = new DadosOlimpiada(R.drawable.imgcomputador, "Olimpíada Brasileira de Informática", "OBI", "Laranja");
        listaOlimpiadasOpcoes.add(dado3);

        DadosOlimpiada dado4 = new DadosOlimpiada(R.drawable.imgoperacoesbasicas, "Olimpíada Brasileira de Matemática das Escolas Públicas", "OBMEP", "Ciano");
        listaOlimpiadasOpcoes.add(dado4);

        DadosOlimpiada dado5 = new DadosOlimpiada(R.drawable.imgpapiro, "Olimpíada Nacional da História Brasileira", "ONHB", "Rosa");
        listaOlimpiadasOpcoes.add(dado5);

        DadosOlimpiada dado6 = new DadosOlimpiada(R.drawable.imgtubodeensaio, "Olimpíada Brasileira de Química", "OBQ", "Azul");
        listaOlimpiadasOpcoes.add(dado6);

        DadosOlimpiada dado7 = new DadosOlimpiada(R.drawable.imgdna, "Olimpíada Brasileira de Biologia", "OBB", "Laranja");
        listaOlimpiadasOpcoes.add(dado7);

        DadosOlimpiada dado8 = new DadosOlimpiada(R.drawable.imgatomo, "Olimpíada Nacional de Ciências", "ONC", "Ciano");
        listaOlimpiadasOpcoes.add(dado8);

        adapter.notifyDataSetChanged(); //atualizar o recycler
    }


}
