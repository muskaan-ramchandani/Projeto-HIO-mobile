package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityLoginBinding;
import com.example.helperinolympics.model.Aluno;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelaLoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    Aluno alunoLogado = null;
    String email;
    String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnFinalizarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailDigitado = binding.editTextEmail.getText().toString();
                String senhaDigitada = binding.editTextSenha.getText().toString();

                if(emailDigitado.isEmpty() && senhaDigitada.isEmpty()){
                    Toast.makeText(TelaLoginActivity.this, "Insira seus dados para prosseguir ou faça o cadastro!", Toast.LENGTH_LONG).show();

                }else if(emailDigitado.isEmpty()){
                    Toast.makeText(TelaLoginActivity.this, "Insira seu email para prosseguir!", Toast.LENGTH_SHORT).show();

                }else if(senhaDigitada.isEmpty()){
                    Toast.makeText(TelaLoginActivity.this, "Insira sua senha para prosseguir!", Toast.LENGTH_SHORT).show();

                }else{
                    new LoginAlunoTask().execute(emailDigitado, senhaDigitada);
                }
            }
        });

        binding.btnCadastreSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLoginActivity.this, TelaBemVindoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private class LoginAlunoTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            email = params[0];
            senha = params[1];
            String result = null;
            Log.d("CONEXAO", "Tentando fazer login");

            try {
                URL url = new URL("http://10.100.51.3:8086/phpHio/validaLoginAluno.php?email=" + email + "&senha=" + senha);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    result = converterParaJSONString(in);
                } else {
                    Log.d("ERRO", "Código de resposta HTTP não OK: " + conexao.getResponseCode());
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null || result.isEmpty()) {
                Log.d("ERRO", "A resposta do servidor é nula ou vazia");
                Toast.makeText(TelaLoginActivity.this, "Erro ao conectar com o servidor", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                JSONObject jsonResponse = new JSONObject(result);
                String msg = jsonResponse.getString("message");
                Toast.makeText(TelaLoginActivity.this, msg, Toast.LENGTH_LONG).show();

                if (jsonResponse.getString("status").equals("success")) {
                    new AlunoDownload().execute(email);
                }

            } catch (JSONException e) {
                Log.d("ERRO", e.toString());
            }
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


    private class AlunoDownload extends AsyncTask<String, Void, Aluno> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Aluno doInBackground(String... params) {
            String email = params[0];
            Log.d("CONEXAO", "Tentando retornar dados do aluno");

            try {
                URL url = new URL("http://10.100.51.3:8086/phpHio/retornaAlunoPorEmail.php?email=" + email);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    if (jsonString == null || jsonString.isEmpty()) {
                        Log.d("ERRO", "A resposta do servidor é nula ou vazia");
                        return null;
                    }
                    Log.d("DADOS", jsonString);
                    alunoLogado = converterParaAluno(jsonString);
                    return converterParaAluno(jsonString);
                } else {
                    Log.d("ERRO", "Código de resposta HTTP não OK: " + conexao.getResponseCode());
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Aluno aluno) {
            super.onPostExecute(aluno);
            if (aluno == null) {
                Toast.makeText(TelaLoginActivity.this, "Erro ao buscar dados do aluno", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(TelaLoginActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoLogado);
                startActivity(intent);
                finish();
            }
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

        private Aluno converterParaAluno(String jsonString) {
            Aluno aluno = null;
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                String status = jsonObject.getString("status");

                if (status.equals("success")) {
                    JSONObject alunoJSON = jsonObject.getJSONObject("aluno");
                    aluno = new Aluno();
                    aluno.setNomeCompleto(alunoJSON.getString("nomeCompleto"));
                    aluno.setNomeUsuario(alunoJSON.getString("nomeUsuario"));
                    aluno.setEmail(alunoJSON.getString("email"));
                    aluno.setSenha(alunoJSON.getString("senha"));

                } else {
                    Log.d("ERRO", jsonObject.getString("message"));
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return aluno;
        }
    }
}
