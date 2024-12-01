package com.example.helperinolympics.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;

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
import java.util.Calendar;
import java.util.List;

public class PerfilAlunoActivity extends Activity {

    private Aluno alunoCadastrado;
    private ActivityPerfilAlunoBinding binding;
    private int numeroA, numeroE;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityPerfilAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        PerfilAlunoActivity.FotoAlunoTask fotoAlunoTask = new PerfilAlunoActivity.FotoAlunoTask();
        fotoAlunoTask.execute(alunoCadastrado.getEmail());

        new CarregaCorrecao(alunoCadastrado.getEmail(), new CarregaCorrecaoCallback() {
            @Override public void onCorrecaoCarregada(int numeroAcertos, int numeroErros) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData(numeroAcertos, numeroErros);
                    }
                });
            }
        }).execute();

        binding.btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
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

        //setData(numeroA, numeroE);

    }

    private void configuraDadosPerfil(Aluno alunoCadastrado, Bitmap fotoBitmap) {
        if (fotoBitmap == null) {
            Log.d("FOTO_PERFIL", "Foto de perfil é null, usando imagem padrão.");
            binding.fotoperfilAluno.setImageResource(R.drawable.iconeperfilsemfoto);
        }  else {
            Log.d("FOTO_PERFIL", "Foto de perfil recebida, definindo Bitmap.");
            binding.fotoperfilAluno.setImageBitmap(fotoBitmap);
        }
        binding.txtNomeCompletoAluno.setText(alunoCadastrado.getNomeCompleto());
        binding.txtNomeDeUsuario.setText(alunoCadastrado.getNomeUsuario());
        binding.txtEmail.setText(alunoCadastrado.getEmail());
    }

    private void setData(int numeroAcertos, int numeroErros){
        Log.d("VALORES", "Qntd acertos: "+numeroAcertos+ "   Qntd Erros: "+numeroErros);

        if(numeroAcertos==0 && numeroErros==0){
            Log.d("SET_DATA", "Entrou no bloco if - Nenhum dado para exibir no gráfico");

            PieChart grafico = findViewById(R.id.graficoPizzaErrosAcertos);

            // Verificar se os views não são null antes de remover
            if (grafico != null) {
                binding.linearLayoutGraficoAcertosEErros.removeView(grafico);
            }

            LinearLayout linearLegendas = findViewById(R.id.linearLayoutLegendaGrafico);
            if (linearLegendas != null) {
                binding.linearLayoutGraficoAcertosEErros.removeView(linearLegendas);
            }

            // Adicionar logs para cada remoção
            View txtLegendaAcertos = findViewById(R.id.txtLegendaAcertos);
            if (txtLegendaAcertos != null) {
                binding.linearLayoutGraficoAcertosEErros.removeView(txtLegendaAcertos);
                Log.d("SET_DATA", "Removido: txtLegendaAcertos");
            }

            View viewLegendaAcertos = findViewById(R.id.viewLegendaAcertos);
            if (viewLegendaAcertos != null) {
                binding.linearLayoutGraficoAcertosEErros.removeView(viewLegendaAcertos);
                Log.d("SET_DATA", "Removido: viewLegendaAcertos");
            }

            View txtLegendaErros = findViewById(R.id.txtLegendaErros);
            if (txtLegendaErros != null) {
                binding.linearLayoutGraficoAcertosEErros.removeView(txtLegendaErros);
                Log.d("SET_DATA", "Removido: txtLegendaErros");
            }

            View viewLegendaErros = findViewById(R.id.viewLegendaErros);
            if (viewLegendaErros != null) {
                binding.linearLayoutGraficoAcertosEErros.removeView(viewLegendaErros);
                Log.d("SET_DATA", "Removido: viewLegendaErros");
            }

            LayoutInflater inflater = LayoutInflater.from(this);
            View newItemView = inflater.inflate(R.layout.msg_sem_dados_grafico, binding.linearLayoutGraficoAcertosEErros, false);

            binding.linearLayoutGraficoAcertosEErros.addView(newItemView);
            binding.btnHistoricoAcertos.setEnabled(false); // desativando
            binding.btnHistoricoAcertos.setAlpha(0.5f); // escurecendo
            binding.btnHistoricoErros.setEnabled(false); // desativando
            binding.btnHistoricoErros.setAlpha(0.5f); // escurecendo

            Log.d("SET_DATA", "Views atualizados para o caso sem dados");

        }else{
            Log.d("SET_DATA", "Entrou no bloco else - Dados disponíveis");
            binding.btnHistoricoAcertos.setEnabled(true);
            binding.btnHistoricoErros.setEnabled(true);

            String valorAcerto = String.valueOf(numeroAcertos);
            binding.txtLegendaAcertos.setText("Acertos ("+valorAcerto+")");

            String valorErro = String.valueOf(numeroErros);
            binding.txtLegendaErros.setText("Erros ("+valorErro+")");

            binding.graficoPizzaErrosAcertos.addPieSlice(new PieModel("Acertos", numeroErros,
                    Color.parseColor("#835AD2")));

            binding.graficoPizzaErrosAcertos.addPieSlice(new PieModel("Erros", numeroAcertos,
                    Color.parseColor("#CB6CE6")));

            binding.graficoPizzaErrosAcertos.startAnimation();
        }
    }

    private class CarregaCorrecao extends AsyncTask<Void, Void, List<String>> {
        String email;
        private CarregaCorrecaoCallback callback;

        public CarregaCorrecao(String email, CarregaCorrecaoCallback callback) {
            this.email = email;
            this.callback = callback;

        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            try {
                String urlString = "http://10.0.0.64:8086/phpHio/retornaQntdAcertosErrosAluno.php?emailAluno=" + URLEncoder.encode(email, "UTF-8");

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
                        numeroA = jsonObject.getInt("totalAcertos");
                        numeroE = jsonObject.getInt("totalErros");

                        callback.onCorrecaoCarregada(numeroA, numeroE);

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


    public class FotoAlunoTask extends AsyncTask<String, Void, Bitmap> {

        private Bitmap fotoBitmap;

        @Override
        protected Bitmap doInBackground(String... params) {
            String email = params[0];
            Bitmap resultBitmap = null;

            try {
                URL url = new URL("http://10.0.0.64:8086/phpHio/retornaFotoPorEmail.php?email=" + email);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.connect();

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);

                    if (jsonString != null && !jsonString.isEmpty()) {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        String status = jsonObject.getString("status");

                        if (status.equals("success")) {
                            String fotoBase64 = jsonObject.getString("fotoPerfil");
                            resultBitmap = decodeBase64ToBitmap(fotoBase64);
                        }
                    }
                }
            } catch (Exception e) {
                Log.d("ERRO", "Erro ao buscar a foto: " + e.getMessage());
            }
            return resultBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            fotoBitmap = result;

            if (fotoBitmap != null) {
                Log.d("INFO", "Foto recebida com sucesso.");
                configuraDadosPerfil(alunoCadastrado, fotoBitmap);
            } else {
                Log.d("ERRO", "Não foi possível carregar a foto.");
                configuraDadosPerfil(alunoCadastrado, fotoBitmap);
            }
        }

        private String converterParaJSONString(InputStream in) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                int charRead;
                while ((charRead = in.read()) != -1) {
                    stringBuilder.append((char) charRead);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return stringBuilder.toString();
        }

        private Bitmap decodeBase64ToBitmap(String base64String) {
            try {
                byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } catch (Exception e) {
                Log.d("ERRO", "Erro ao decodificar Base64 para Bitmap: " + e.getMessage());
                return null;
            }
        }
    }

    public interface CarregaCorrecaoCallback {
        void onCorrecaoCarregada(int numeroAcertos, int numeroErros);
    }

    public void onCorrecaoCarregada(final int numeroAcertos, final int numeroErros) {
        runOnUiThread(new Runnable() {
        @Override public void run() {
            setData(numeroAcertos, numeroErros);
        } });
    }

}