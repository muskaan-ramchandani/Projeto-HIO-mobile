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

import com.example.helperinolympics.adapter.AdapterErros;
import com.example.helperinolympics.databinding.ActivityErrosSemanaisBinding;
import com.example.helperinolympics.menu.PerfilAlunoActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Erros;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ErrosSemanaisActivity extends Activity {
    private Aluno alunoCadastrado;
    private ActivityErrosSemanaisBinding binding;
    private ArrayList<Erros> listaErros = new ArrayList<>();

    private Date dataAtual, dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2,
            dataInicialSemana3, dataFinalSemana3;

    private int totalErrosSemana1, totalErrosSemana2, totalErrosSemana3;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    private AdapterErros errosAdapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityErrosSemanaisBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarDatas();
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        new ErrosSemanaisActivity.CarregaErrosSemanais(alunoCadastrado.getEmail(), dataInicialSemana1, dataFinalSemana1, dataInicialSemana2, dataFinalSemana2, dataInicialSemana3, dataFinalSemana3).execute();

        binding.btnVoltarAoPerfilDosErros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ErrosSemanaisActivity.this, PerfilAlunoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarRecyclerErros() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewListaErros.setLayoutManager(layoutManager);
        binding.recyclerViewListaErros.setHasFixedSize(true);

        errosAdapter = new AdapterErros(listaErros);
        binding.recyclerViewListaErros.setAdapter(errosAdapter);

        errosAdapter.notifyDataSetChanged();
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

    private void configurarBarra() {
        // Entradas de dados
        ArrayList<BarEntry> entradaDados2 = new ArrayList<>();

        // Adicionando os valores reais
        entradaDados2.add(new BarEntry(1f, totalErrosSemana1));
        entradaDados2.add(new BarEntry(2f, totalErrosSemana2));
        entradaDados2.add(new BarEntry(3f, totalErrosSemana3));

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
        XAxis xAxis = binding.graficoBarraErrosSemanais.getXAxis();
        xAxis.setGranularity(1f);

        // LEGENDAS DO GRÁFICO
        binding.datasLegendaSemana1.setText(String.format("%s - %s", dateFormat.format(dataInicialSemana1), dateFormat.format(dataFinalSemana1)));

        binding.datasLegendaSemana2.setText(String.format("%s - %s", dateFormat.format(dataInicialSemana2), dateFormat.format(dataFinalSemana2)));

        binding.datasLegendaSemana3.setText(String.format("%s - %s", dateFormat.format(dataInicialSemana3), dateFormat.format(dataFinalSemana3)));

        // Adicionando configurações
        BarData barData = new BarData(barDataSet);
        binding.graficoBarraErrosSemanais.setData(barData);
        binding.graficoBarraErrosSemanais.getDescription().setEnabled(false);
        binding.graficoBarraErrosSemanais.getLegend().setEnabled(false);
        binding.graficoBarraErrosSemanais.setExtraOffsets(0, 5, 0, 0); // Adiciona um espaço extra no topo
        binding.graficoBarraErrosSemanais.animateY(2000);
    }


    private class CarregaErrosSemanais extends AsyncTask<String, Void, String> {
        String emailAluno;
        String inicioSemana1, fimSemana1, inicioSemana2, fimSemana2, inicioSemana3, fimSemana3;

        public CarregaErrosSemanais(String emailAluno, Date inicioSemana1, Date fimSemana1, Date inicioSemana2, Date fimSemana2, Date inicioSemana3, Date fimSemana3){
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
                String urlString = "http://10.0.0.64:8086/phpHio/carregaErrosAluno.php?emailAluno=" + emailAluno +
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
                Log.e("CarregaErrosSemanais", "Erro na requisição HTTP", e);
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                // Total de erros por semana
                totalErrosSemana1 = jsonObject.getInt("totalErrosSemana1");
                totalErrosSemana2 = jsonObject.getInt("totalErrosSemana2");
                totalErrosSemana3 = jsonObject.getInt("totalErrosSemana3");

                Log.d("Erros", "Semana 1: " + totalErrosSemana1);
                Log.d("Erros", "Semana 2: " + totalErrosSemana2);
                Log.d("Erros", "Semana 3: " + totalErrosSemana3);

                JSONArray listaErrosJSON = jsonObject.getJSONArray("listaErros");
                ArrayList<Erros> errosLista = new ArrayList<>();

                listaErros.clear();

                for (int i = 0; i < listaErrosJSON.length(); i++) {
                    JSONObject erroJson = listaErrosJSON.getJSONObject(i);

                    Erros erro = new Erros();
                    erro.setSiglaOlimpiada(erroJson.getString("siglaOlimpiada"));
                    erro.setTituloConteudo(erroJson.getString("tituloConteudo"));
                    erro.setTituloQuestionario(erroJson.getString("tituloQuestionario"));
                    erro.setUsuarioProfessor(erroJson.getString("usuarioProfessor"));
                    erro.setTxtPergunta(erroJson.getString("txtPergunta"));
                    erro.setAlternativaMarcada(erroJson.getString("alternativaMarcada"));
                    erro.setAlternativaCorreta(erroJson.getString("alternativaCorreta"));

                    errosLista.add(erro);
                    listaErros.add(erro);
                }

                configurarBarra();
                configurarRecyclerErros();

            } catch (JSONException e) {
                Log.e("CarregaErrosSemanais", "Erro ao fazer o parse do JSON", e);
            }
        }
    }

}
