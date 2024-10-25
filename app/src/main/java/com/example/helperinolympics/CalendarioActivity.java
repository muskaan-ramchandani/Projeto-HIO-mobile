package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.calendario.AdapterDatasCalendario;
import com.example.helperinolympics.adapter.calendario.AdapterEventos;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Eventos;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarioActivity extends AppCompatActivity {
    private RecyclerView rvListaEventos, rvCalendario;
    private AdapterDatasCalendario adapterCalendario;
    private AdapterEventos adapter;
    private TextView txtMesEAno;
    private Calendar dataAtual, dataNova;

    private Aluno alunoCadastrado;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        findViewById(R.id.btnAcessarHanking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarioActivity.this, RankingActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnRetornarInicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarioActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });


        findViewById(R.id.btnVoltarMes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voltarMes();
            }
        });

        findViewById(R.id.btnAvancarMes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avancarMes();
            }
        });


        configurarCalendario();
        configurarRecyclerEventos();
    }

    private void configurarCalendario(){

        rvCalendario = findViewById(R.id.recyclerViewCalendario);
        txtMesEAno = findViewById(R.id.txtMesAno);

        dataAtual = Calendar.getInstance();
        txtMesEAno.setText(mesAnoAtravesData(dataAtual));

        ArrayList<String> diasDoMes = vetorDiasNoMes(dataAtual);

        rvCalendario = findViewById(R.id.recyclerViewCalendario);
        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        rvCalendario.setLayoutManager(layoutManager);
        rvCalendario.setHasFixedSize(true);

        adapterCalendario = new AdapterDatasCalendario(diasDoMes, dataAtual);
        rvCalendario.setAdapter(adapterCalendario);
        adapterCalendario.notifyDataSetChanged();
    }

    private String mesAnoAtravesData(Calendar calendar){
        SimpleDateFormat formatacao = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        return formatacao.format(calendar.getTime());
    }

    private ArrayList<String> vetorDiasNoMes(Calendar calendar){
        ArrayList<String> diasDoMes = new ArrayList<>();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH); // Janeiro é 0

        Calendar primeiroDiaDoMes = Calendar.getInstance();
        primeiroDiaDoMes.set(ano, mes, 1);
        int diasNoMes = primeiroDiaDoMes.getActualMaximum(Calendar.DAY_OF_MONTH);

        int diaSemana = primeiroDiaDoMes.get(Calendar.DAY_OF_WEEK);

        int primeiroDiaDaGrade = Calendar.SUNDAY; // Definido como domingo
        for (int i = primeiroDiaDaGrade; i < diaSemana; i++) {
            diasDoMes.add(""); // Adiciona dias vazios antes do início do mês
        }

        for (int i = 1; i <= diasNoMes; i++) {
            diasDoMes.add(String.valueOf(i));
        }

        int diasRestantesNaGrade = 42 - diasDoMes.size();
        for (int i = 0; i < diasRestantesNaGrade; i++) {
            diasDoMes.add("");
        }

        return diasDoMes;
    }

    public void voltarMes() {
        dataAtual.add(Calendar.MONTH, -1);
        atualizarCalendario(); // Atualiza o calendário
    }

    public void avancarMes() {
        dataAtual.add(Calendar.MONTH, 1);
        atualizarCalendario(); // Atualiza o calendário
    }

    private void atualizarCalendario() {
        txtMesEAno.setText(mesAnoAtravesData(dataAtual));
        ArrayList<String> diasDoMes = vetorDiasNoMes(dataAtual);
        if (adapterCalendario != null) {
            adapterCalendario.atualizarDatas(diasDoMes);
        }
    }

    private void configurarRecyclerEventos() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        rvListaEventos = findViewById(R.id.recyclerViewEventosCalendario);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListaEventos.setLayoutManager(layoutManager);
        rvListaEventos.setHasFixedSize(true);

        List<Eventos> listaEventos = new ArrayList<>();
        adapter = new AdapterEventos(listaEventos);
        rvListaEventos.setAdapter(adapter);

        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Converte o Calendar para uma String formatada
        String formattedDate = sdf.format(calendar.getTime());*/

        //DADOS PARA TESTE
        Date data1 = null;
        try {
            // Converta a String para Date
            data1 = sdf.parse("08/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Eventos dados1 = new Eventos(1, data1, "OBA", "Inscrição",
                "09h às 19h", "https://link_para_inscricao");
        listaEventos.add(dados1);

        Date data2 = null;
        try {
            // Converta a String para Date
            data2 = sdf.parse("15/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Eventos dados2 = new Eventos(2, data2, "OBMEP", "Prova Fase 2",
                "06h às 12h", "https://link_para_prova");
        listaEventos.add(dados2);

        Date data3 = null;
        try {
            // Converta a String para Date
            data3 = sdf.parse("30/08/2024");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Eventos dados3 = new Eventos(3, data3, "OBI", "Prova Fase 1",
                "00h01 às 23h59", "https://link_para_prova_OBI");
        listaEventos.add(dados3);

        adapter.notifyDataSetChanged();
    }
}
