package com.example.helperinolympics.cadastros;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityCadastroBinding;
import com.example.helperinolympics.model.DadosAluno;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;
import com.example.helperinolympics.telas_iniciais.TelaEscolhaOlimpiadaActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity {

    ActivityCadastroBinding binding;
    String msgErro= "";
    boolean verificaEmail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //pegando dados inseridos
                String nomeCompleto = binding.editTextNomeCompleto.getText().toString();
                String nomeUsuario = binding.editTextNomeUsuario.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String senha = binding.editTextSenha.getText().toString();
                String confirmaSenha = binding.editTextConfirmarSenha.getText().toString();


                boolean dadosCorretos= validarDadosCadastro(nomeCompleto, nomeUsuario, email, senha, confirmaSenha);

                if(dadosCorretos){
                    DadosAluno aluno = new DadosAluno(nomeCompleto, nomeUsuario, email, senha);
                    new CadastrarAluno().execute(aluno);

                    Intent intent = new Intent(CadastroActivity.this, TelaEscolhaOlimpiadaActivity.class);
                    startActivity(intent);
                    finish(); //fechar activity
                }else{
                    Toast.makeText(CadastroActivity.this, msgErro, Toast.LENGTH_LONG).show();
                }

            }
        });

        binding.btnVoltarAEscolhaAlunoProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, CadastroAlunoProfessorActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

    }

    public boolean validarDadosCadastro(String nomeCompleto, String nomeUsuario, String email, String senha, String confirmaSenha){
        boolean retorno;

        //verificando campos vazios
        if(nomeCompleto.isEmpty()||nomeUsuario.isEmpty()||email.isEmpty()||senha.isEmpty()||confirmaSenha.isEmpty()){
            this.msgErro="Preencha todos os campos para prosseguir";
            retorno = false;
        }else if (!senha.equals(confirmaSenha)) {
            this.msgErro = "Senha e confirma senha não coincidem";
            retorno = false;
        } else {
            new VerificarEmail().execute(email);
            if(verificaEmail){
                this.msgErro = "Já existe uma conta com este email.\nTente com outro ou faça o login!";
                retorno = false;
            }else{
                retorno = true;
            }
        }

        return retorno;
    }

    private class VerificarEmail extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... emails) {
            boolean emailExiste = false;
            String email = emails[0];

            try {
                URL url = new URL("http://192.168.1.11/phpHio/verificaExistenciaEmail.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                JSONObject jsonEmail = new JSONObject();
                jsonEmail.put("email", email);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonEmail.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    emailExiste = jsonResponse.getBoolean("emailExiste");
                    verificaEmail = emailExiste;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return emailExiste;
        }
    }


    private class CadastrarAluno extends AsyncTask<DadosAluno, Void, String> {
        @Override
        protected String doInBackground(DadosAluno... alunos) {
            DadosAluno aluno = alunos[0];
            try {
                URL url = new URL("http://192.168.1.11/phpHio/cadastraAluno.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                // Criar JSON para enviar ao servidor
                JSONObject jsonAluno = new JSONObject();
                jsonAluno.put("nomeCompleto", aluno.getNomeCompleto());
                jsonAluno.put("nomeUsuario", aluno.getNomeUsuario());
                jsonAluno.put("email", aluno.getEmail());
                jsonAluno.put("senha", aluno.getSenha());

                // Enviar o JSON para o servidor
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonAluno.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Capturar a resposta do servidor
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    // Converter a resposta em JSON
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    String status = jsonResponse.getString("status");

                    if (status.equals("success")) {
                        return "Cadastro realizado com sucesso!";
                    } else {
                        String message = jsonResponse.getString("message");
                        return "Erro no cadastro: " + message;
                    }

                } else {
                    return "Erro no cadastro: " + responseCode;
                }

            } catch (Exception e) {
                return "Erro: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(CadastroActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}