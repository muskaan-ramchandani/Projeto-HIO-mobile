package com.example.helperinolympics.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.helperinolympics.AcertosSemanaisActivity;
import com.example.helperinolympics.ErrosSemanaisActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;
import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityPerfilAlunoBinding;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class PerfilAlunoActivity extends Activity {

    private Aluno alunoCadastrado;
    private ActivityPerfilAlunoBinding binding;
    private Integer numeroAcertos;
    private Integer numeroErros;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        binding.btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        binding.btnHistoricoAcertos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, AcertosSemanaisActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        binding.btnHistoricoErros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, ErrosSemanaisActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        configuraDadosPerfil(alunoCadastrado);

        //dados gráfico
        new CarregaCorrecao(alunoCadastrado.getEmail()).execute();
        setData(numeroAcertos, numeroErros);
    }

    private void configuraDadosPerfil(Aluno alunoCadastrado) {
        if(alunoCadastrado.getFotoPerfil()==null){
            binding.fotoperfilAluno.setImageResource(R.drawable.iconeperfilsemfoto);
        }else{
            binding.fotoperfilAluno.setImageBitmap(alunoCadastrado.getFotoPerfil());
        }
        binding.txtNomeCompletoAluno.setText(alunoCadastrado.getNomeCompleto());
        binding.txtNomeDeUsuario.setText(alunoCadastrado.getNomeUsuario());
        binding.txtEmail.setText(alunoCadastrado.getEmail());
    }

    private void setData(Integer numeroAcertos, Integer numeroErros){
        //fazer um if para verificar se há dados para comparar
        if((numeroAcertos==null)&&(numeroErros==null)){
            PieChart grafico = findViewById(R.id.graficoPizzaErrosAcertos);
            LinearLayout linearLegendas = findViewById(R.id.linearLayoutLegendaGrafico);

            binding.linearLayoutGraficoAcertosEErros.removeView(grafico);
            binding.linearLayoutGraficoAcertosEErros.removeView(linearLegendas);
            binding.btnHistoricoAcertos.setEnabled(true);
            binding.btnHistoricoErros.setEnabled(true);

            LayoutInflater inflater = LayoutInflater.from(this);
            View newItemView = inflater.inflate(R.layout.msg_sem_dados_grafico, binding.linearLayoutGraficoAcertosEErros, false);

            binding.linearLayoutGraficoAcertosEErros.addView(newItemView);
        }else{

            binding.graficoPizzaErrosAcertos.addPieSlice(new PieModel("Acertos", numeroAcertos,
                    Color.parseColor("#835AD2")));

            binding.graficoPizzaErrosAcertos.addPieSlice(new PieModel("Erros", numeroErros,
                    Color.parseColor("#CB6CE6")));

            binding.graficoPizzaErrosAcertos.startAnimation();
        }
    }

    private class CarregaCorrecao extends AsyncTask<Void, Void, List<String>> {
        String email;

        public CarregaCorrecao(String email) {
            this.email = email;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                String urlString = "http://192.168.1.10:8086/phpHio/retornaQntdAcertosErrosAluno.php?emailAluno=" + URLEncoder.encode(email, "UTF-8");

                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(5000);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    Log.d("DADOS", jsonString);

                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);

                        numeroAcertos = jsonObject.getInt("totalAcertos");
                        numeroErros = jsonObject.getInt("totalErros");

                        String valorAcerto = String.valueOf(numeroAcertos);
                        binding.txtLegendaAcertos.setText("Acertos ("+valorAcerto+")");

                        String valorErro = String.valueOf(numeroErros);
                        binding.txtLegendaAcertos.setText("Erros ("+valorErro+")");

                    } catch (Exception e) {
                        Log.d("ERRO", e.toString());
                    }

                } else {
                    Log.d("ERRO_CONEXAO", "Erro ao conectar, código de resposta: " + conexao.getResponseCode());
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return null;
        }

        private String converterParaJSONString(InputStream in) {
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream dados = new ByteArrayOutputStream();
            try {
                int qtdBytesLido;
                while ((qtdBytesLido = in.read(buffer)) != -1) {
                    dados.write(buffer, 0, qtdBytesLido);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return dados.toString();
        }

    }
}