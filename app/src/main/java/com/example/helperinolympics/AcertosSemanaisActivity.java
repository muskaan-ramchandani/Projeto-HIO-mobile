package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.helperinolympics.adapter.AdapterDadosAcertos;
import com.example.helperinolympics.databinding.ActivityAcertosSemanaisBinding;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.model.Acertos;
import com.example.helperinolympics.model.Aluno;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AcertosSemanaisActivity extends Activity {

    private ActivityAcertosSemanaisBinding binding;
    private Aluno alunoCadastrado;
    AdapterDadosAcertos acertosAdapter;

    private Date dataAtual, dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2,
            dataInicialSemana3, dataFinalSemana3;

    private int totalAcertosSemana1, totalAcertosSemana2, totalAcertosSemana3;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        new CarregaAcertosSemanais(alunoCadastrado.getEmail(), dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2, dataInicialSemana3, dataFinalSemana3).execute();

        binding.btnVoltarAoPerfilDosAcertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcertosSemanaisActivity.this, PerfilAlunoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        configurarDatas();
        configurarBarra();
        configurarRecyclerAcertos();
    }

    private void configurarRecyclerAcertos() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewListaAcertos.setLayoutManager(layoutManager);
        binding.recyclerViewListaAcertos.setHasFixedSize(true);

        List<Acertos> listaAcertos = new ArrayList<>();
        acertosAdapter = new AdapterDadosAcertos(listaAcertos);
        binding.recyclerViewListaAcertos.setAdapter(acertosAdapter);


        acertosAdapter.notifyDataSetChanged();
    }

    private void configurarBarra(){

        //Entradas de
        //Se o maior valor for maior que 10, o fator de escala divide esse valor por 10 para que caiba no gráfico.
        //Cada valor de acerto é dividido pelo fatorEscala para garantir que todos os valores sejam ajustados ao limite do gráfico (10 no eixo Y).
        ArrayList<BarEntry> entradaDados = new ArrayList<>();
        int maiorValor = Math.max(totalAcertosSemana1, Math.max(totalAcertosSemana2, totalAcertosSemana3));
        float fatorEscala = maiorValor > 10 ? maiorValor / 10.0f : 1f;
        entradaDados.add(new BarEntry(1f, totalAcertosSemana1 / fatorEscala));
        entradaDados.add(new BarEntry(2f, totalAcertosSemana2 / fatorEscala));
        entradaDados.add(new BarEntry(3f, totalAcertosSemana3 / fatorEscala));

        // Cores
        int corAzul = ContextCompat.getColor(this, R.color.btnOlimpiadaAzul);
        int corRosa = ContextCompat.getColor(this, R.color.btnOlimpiadaRosa);
        int corRoxa = ContextCompat.getColor(this, R.color.corIcones);
        int[] cores = new int[] {corAzul, corRosa, corRoxa};

        // Creating a bar data set
        BarDataSet barDataSet = new BarDataSet(entradaDados, "Gráfico de comparação");
        barDataSet.setColors(cores);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);

        //Inserindo legenda
        Legend legend = binding.graficoBarraAcertosSemanais.getLegend();
        legend.setEnabled(true);
        legend.setTextSize(12f);
        legend.setTextColor(Color.BLACK);
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setFormSize(10f); // Tamanho do ícone na legenda
        legend.setXEntrySpace(25f); // Espaçamento horizontal entre as entradas da legenda
        legend.setYEntrySpace(5f); // Espaçamento vertical entre as entradas da legenda

        //LEGENDAS DO GRÁFICO
        LegendEntry entradaLegenda1 = new LegendEntry();
        entradaLegenda1.label = dateFormat.format(dataInicialSemana1) +  dateFormat.format(dataFinalSemana1);
        entradaLegenda1.formColor = corAzul;

        LegendEntry entradaLegenda2 = new LegendEntry();
        entradaLegenda2.label = dateFormat.format(dataInicialSemana2) +  dateFormat.format(dataFinalSemana2);
        entradaLegenda2.formColor = corRosa;

        LegendEntry entradaLegenda3 = new LegendEntry();
        entradaLegenda3.label = dateFormat.format(dataInicialSemana3) +  dateFormat.format(dataFinalSemana3);
        entradaLegenda3.formColor = corRoxa;

        legend.setCustom(new LegendEntry[]{entradaLegenda1, entradaLegenda2, entradaLegenda3});

        //Adicionando configurações
        BarData barData = new BarData(barDataSet);
        binding.graficoBarraAcertosSemanais.setData(barData);
        binding.graficoBarraAcertosSemanais.animateY(2000);
    }

    private void configurarDatas() {
        //CONFIGURANDO DATAS (SEMANA ATUAL, PASSADA E RETRASADA)
        Calendar calendar = Calendar.getInstance();
        dataAtual = calendar.getTime();

        // Semana 3 (semana atual)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dataInicialSemana3 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dataFinalSemana3 = calendar.getTime();

        // Semana 2 (semana passada)
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dataInicialSemana2 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dataFinalSemana2 = calendar.getTime();

        // Semana 1 (semana retrasada)
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        dataInicialSemana1 = calendar.getTime();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        dataFinalSemana1 = calendar.getTime();
    }

    private class CarregaAcertosSemanais extends AsyncTask<String, Void, String> {
        String emailAluno;
        String inicioSemana1, fimSemana1, inicioSemana2, fimSemana2, inicioSemana3, fimSemana3;

        public CarregaAcertosSemanais(String emailAluno, Date inicioSemana1, Date fimSemana1, Date inicioSemana2, Date fimSemana2, Date inicioSemana3, Date fimSemana3){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            this.emailAluno = emailAluno;
            this.inicioSemana1= dateFormat.format(inicioSemana1);
            this.fimSemana1= dateFormat.format(fimSemana1);
            this.inicioSemana2= dateFormat.format(inicioSemana2);
            this.fimSemana2= dateFormat.format(fimSemana2);
            this.inicioSemana3= dateFormat.format(inicioSemana3);
            this.fimSemana3= dateFormat.format(fimSemana3);
        }
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            try {
                String urlString = "http://192.168.1.6:8086/phpHio/carregaAcertosAluno.php?emailAluno=" + emailAluno +
                        "&dataInicialSemana1=" + dataInicialSemana1 +
                        "&dataFinalSemana1=" + dataFinalSemana1 +
                        "&dataInicialSemana2=" + dataInicialSemana2 +
                        "&dataFinalSemana2=" + dataFinalSemana2 +
                        "&dataInicialSemana3=" + dataInicialSemana3 +
                        "&dataFinalSemana3=" + dataFinalSemana3;

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                }
            } catch (Exception e) {
                Log.e("CarregaAcertosSemanais", "Erro na requisição HTTP", e);
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                // Total de acertos por semana
                totalAcertosSemana1 = jsonObject.getInt("totalAcertosSemana1");
                totalAcertosSemana2 = jsonObject.getInt("totalAcertosSemana2");
                totalAcertosSemana3 = jsonObject.getInt("totalAcertosSemana3");

                Log.d("Acertos", "Semana 1: " + totalAcertosSemana1);
                Log.d("Acertos", "Semana 2: " + totalAcertosSemana2);
                Log.d("Acertos", "Semana 3: " + totalAcertosSemana3);

                JSONArray listaAcertos = jsonObject.getJSONArray("listaAcertos");
                ArrayList<Acertos> acertosLista = new ArrayList<>();

                for (int i = 0; i < listaAcertos.length(); i++) {
                    JSONObject acertoJson = listaAcertos.getJSONObject(i);

                    Acertos acerto = new Acertos();
                    acerto.setSiglaOlimpiada(acertoJson.getString("siglaOlimpiada"));
                    acerto.setTituloConteudo(acertoJson.getString("tituloConteudo"));
                    acerto.setTituloQuestionario(acertoJson.getString("tituloQuestionario"));
                    acerto.setUsuarioProfessor(acertoJson.getString("usuarioProfessor"));
                    acerto.setTxtPergunta(acertoJson.getString("txtPergunta"));
                    acerto.setAlternativaMarcada(acertoJson.getString("alternativaMarcada"));

                    acertosLista.add(acerto);
                }

            } catch (JSONException e) {
                Log.e("CarregaAcertosSemanais", "Erro ao fazer o parse do JSON", e);
            }
        }
    }

}
