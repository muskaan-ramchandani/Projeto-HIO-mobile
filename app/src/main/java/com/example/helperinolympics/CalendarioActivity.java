package com.example.helperinolympics;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.calendario.AdapterDatasCalendario;
import com.example.helperinolympics.adapter.calendario.AdapterEventos;
import com.example.helperinolympics.databinding.ActivityCalendarioBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Eventos;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarioActivity extends AppCompatActivity {
    private ActivityCalendarioBinding binding;
    private AdapterDatasCalendario adapterCalendario;
    private AdapterEventos adapter;
    private TextView txtMesEAno;
    private Calendar dataAtual;

    private Aluno alunoCadastrado;
    private ArrayList<String> diasDoMes;

    private List<Eventos> listaEventos = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        dataAtual = Calendar.getInstance();
        new CarregaEventos(alunoCadastrado.getEmail(), dataAtual).execute();

        diasDoMes = vetorDiasNoMes(dataAtual);

        configurarCalendario();
        configurarRecyclerEventos();

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
    }

    private void configurarCalendario(){
        txtMesEAno = findViewById(R.id.txtMesAno);

        txtMesEAno.setText(mesAnoAtravesData(dataAtual));

        LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        binding.recyclerViewCalendario.setLayoutManager(layoutManager);
        binding.recyclerViewCalendario.setHasFixedSize(true);

        adapterCalendario = new AdapterDatasCalendario(diasDoMes, dataAtual, listaEventos);
        binding.recyclerViewCalendario.setAdapter(adapterCalendario);
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
        adapterCalendario.notifyDataSetChanged();
    }

    public void avancarMes() {
        dataAtual.add(Calendar.MONTH, 1);
        atualizarCalendario(); // Atualiza o calendário
        adapterCalendario.notifyDataSetChanged();
    }

    private void atualizarCalendario() {
        txtMesEAno.setText(mesAnoAtravesData(dataAtual));
        ArrayList<String> diasDoMes = vetorDiasNoMes(dataAtual);
        if (adapterCalendario != null) {
            adapterCalendario.atualizarDatas(diasDoMes, listaEventos);
            adapterCalendario.notifyDataSetChanged();
        }
    }

    private void configurarRecyclerEventos() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewEventosCalendario.setLayoutManager(layoutManager);
        binding.recyclerViewEventosCalendario.setHasFixedSize(true);

        adapter = new AdapterEventos(listaEventos);
        binding.recyclerViewEventosCalendario.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private class CarregaEventos extends AsyncTask<String, Void, String> {
        String emailAluno;
        Calendar dataAConverter;

        public CarregaEventos(String emailAluno, Calendar dataAConverter){
            this.emailAluno = emailAluno;
            this.dataAConverter = dataAConverter;
        }
        @Override
        protected String doInBackground(String... strings) {
            StringBuilder result = new StringBuilder();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatada = dateFormat.format(dataAConverter.getTime());

            try {
                String urlString = "https://hio.lat/carregaEventosOlimpiadasAluno.php?emailAluno=" + emailAluno +
                        "&dataAtual=" + dataFormatada;

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
                } else {
                    Log.e("Erro HTTP", "Código de resposta: " + responseCode);
                }
            } catch (Exception e) {
                Log.e("CarregaEventos", "Erro na requisição HTTP", e);
            }

            Log.d("Resposta da API", result.toString());
            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);

            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray eventosJSON = jsonObject.getJSONArray("eventos");

                listaEventos.clear();
                adapter.notifyDataSetChanged();
                adapterCalendario.notifyDataSetChanged();

                if(eventosJSON.length()!=0){
                    for (int i = 0; i < eventosJSON.length(); i++) {
                        JSONObject eventoItemJson = eventosJSON.getJSONObject(i);

                        // Converter dataOcorrencia
                        String data = eventoItemJson.getString("dataOcorrencia");
                        Date dataOcorrencia = converterParaData(data);

                        // Converter para Time
                        String horarioInicial = eventoItemJson.getString("horarioComeco");
                        String horarioFinal = eventoItemJson.getString("horarioFim");

                        Time horarioComeco = Time.valueOf(horarioInicial);
                        Time horarioFim = Time.valueOf(horarioFinal);

                        Eventos evento = new Eventos(eventoItemJson.getInt("id"), dataOcorrencia,
                                horarioComeco, horarioFim, eventoItemJson.getString("siglaOlimpiadaPertencente"),
                                eventoItemJson.getString("tipo"), eventoItemJson.getString("link"),
                                eventoItemJson.getString("cor"));


                        listaEventos.add(evento);
                    }
                    adapter.notifyDataSetChanged();
                    adapterCalendario.notifyDataSetChanged();

                }else{

                    binding.linearRecycler.removeView(binding.scrollDoRecycler);
                    binding.linearRecycler.removeView(binding.recyclerViewEventosCalendario);

                    LayoutInflater inflater = LayoutInflater.from(CalendarioActivity.this);
                    View newItemView = inflater.inflate(R.layout.msg_sem_eventos, binding.linearRecycler, false);

                    binding.linearRecycler.addView(newItemView);
                }

            } catch (JSONException e) {
                Log.e("CarregaCalendario", "Erro ao fazer o parse do JSON", e);
            }
        }
    }

    private Date converterParaData(String dataString) {
        try {
            Date date = apiDateFormat.parse(dataString);
            String dataFormatada = dateFormat.format(date);
            Log.d("DATA_FORMATADA", dataFormatada);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}