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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.example.helperinolympics.adapter.AdapterAcertos;
import com.example.helperinolympics.databinding.ActivityAcertosBinding;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.model.Acertos;
import com.example.helperinolympics.model.Aluno;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class AcertosActivity extends Activity {

    private ActivityAcertosBinding binding;
    private Aluno alunoCadastrado;
    AdapterAcertos acertosAdapter;
    private ArrayList<Acertos> listaAcertos = new ArrayList<>();

    private Date dataAtual, dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2,
            dataInicialSemana3, dataFinalSemana3;

    private int totalAcertosSemana1, totalAcertosSemana2, totalAcertosSemana3;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityAcertosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarDatas();
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        new CarregaAcertos(alunoCadastrado.getEmail(), dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2, dataInicialSemana3, dataFinalSemana3).execute();

        binding.btnVoltarAoPerfilDosAcertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcertosActivity.this, PerfilAlunoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarRecyclerAcertos() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewListaAcertos.setLayoutManager(layoutManager);
        binding.recyclerViewListaAcertos.setHasFixedSize(true);

        acertosAdapter = new AdapterAcertos(listaAcertos);
        binding.recyclerViewListaAcertos.setAdapter(acertosAdapter);


        acertosAdapter.notifyDataSetChanged();
    }

    private void configurarBarra() {
        // Entradas de dados
        ArrayList<BarEntry> entradaDados2 = new ArrayList<>();

        // Adicionando os valores reais
        entradaDados2.add(new BarEntry(1f, totalAcertosSemana1));
        entradaDados2.add(new BarEntry(2f, totalAcertosSemana2));
        entradaDados2.add(new BarEntry(3f, totalAcertosSemana3));

        // Cores
        int corAzul = ContextCompat.getColor(this, R.color.btnOlimpiadaAzul);
        int corRosa = ContextCompat.getColor(this, R.color.btnOlimpiadaRosa);
        int corRoxa = ContextCompat.getColor(this, R.color.corIcones);
        int[] cores = new int[] {corAzul, corRosa, corRoxa};

        // Criando um conjunto de dados de barras
        BarDataSet barDataSet = new BarDataSet(entradaDados2, "Gráfico de comparação");
        barDataSet.setColors(cores);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(10f);


        //Largura barra
        XAxis xAxis = binding.graficoBarraAcertosSemanais.getXAxis();
        xAxis.setGranularity(1f);

        // LEGENDAS DO GRÁFICO
        binding.datasLegendaSemana1.setText(String.format("%s - %s", dateFormat.format(dataInicialSemana1), dateFormat.format(dataFinalSemana1)));

        binding.datasLegendaSemana2.setText(String.format("%s - %s", dateFormat.format(dataInicialSemana2), dateFormat.format(dataFinalSemana2)));

        binding.datasLegendaSemana3.setText(String.format("%s - %s", dateFormat.format(dataInicialSemana3), dateFormat.format(dataFinalSemana3)));


        // Adicionando configurações
        BarData barData = new BarData(barDataSet);
        binding.graficoBarraAcertosSemanais.setData(barData);
        binding.graficoBarraAcertosSemanais.getDescription().setEnabled(false);
        binding.graficoBarraAcertosSemanais.getLegend().setEnabled(false);
        binding.graficoBarraAcertosSemanais.setExtraOffsets(0, 5, 0, 0); // Adiciona um espaço extra no topo
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

    private class CarregaAcertos extends AsyncTask<String, Void, String> {
        String emailAluno;
        String inicioSemana1, fimSemana1, inicioSemana2, fimSemana2, inicioSemana3, fimSemana3;

        public CarregaAcertos(String emailAluno, Date inicioSemana1, Date fimSemana1, Date inicioSemana2, Date fimSemana2, Date inicioSemana3, Date fimSemana3){
            SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");

            this.emailAluno = emailAluno;
            this.inicioSemana1= formatoBanco.format(inicioSemana1);
            this.fimSemana1= formatoBanco.format(fimSemana1);
            this.inicioSemana2= formatoBanco.format(inicioSemana2);
            this.fimSemana2= formatoBanco.format(fimSemana2);
            this.inicioSemana3= formatoBanco.format(inicioSemana3);
            this.fimSemana3= formatoBanco.format(fimSemana3);
        }
        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            try {
                String urlString = "http://10.0.0.64:8086/phpHio/carregaAcertosAluno.php?emailAluno=" + emailAluno +
                        "&dataInicialSemana1=" + inicioSemana1 +
                        "&dataFinalSemana1=" + fimSemana1 +
                        "&dataInicialSemana2=" + inicioSemana2 +
                        "&dataFinalSemana2=" + fimSemana2 +
                        "&dataInicialSemana3=" + inicioSemana3 +
                        "&dataFinalSemana3=" + fimSemana3;

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
                Log.e("CarregaAcertos", "Erro na requisição HTTP", e);
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

                JSONArray listaAcertosJSON = jsonObject.getJSONArray("listaAcertos");
                ArrayList<Acertos> acertosLista = new ArrayList<>();

                listaAcertos.clear();

                for (int i = 0; i < listaAcertosJSON.length(); i++) {
                    JSONObject acertoJson = listaAcertosJSON.getJSONObject(i);

                    Acertos acerto = new Acertos();
                    acerto.setSiglaOlimpiada(acertoJson.getString("siglaOlimpiada"));
                    acerto.setTituloConteudo(acertoJson.getString("tituloConteudo"));
                    acerto.setTituloQuestionario(acertoJson.getString("tituloQuestionario"));
                    acerto.setUsuarioProfessor(acertoJson.getString("usuarioProfessor"));
                    acerto.setTxtPergunta(acertoJson.getString("txtPergunta"));
                    acerto.setAlternativaMarcada(acertoJson.getString("alternativaMarcada"));

                    acertosLista.add(acerto);
                    listaAcertos.add(acerto);
                }

                configurarBarra();
                configurarRecyclerAcertos();

            } catch (JSONException e) {
                Log.e("CarregaAcertos", "Erro ao fazer o parse do JSON", e);
            }
        }
    }

}
