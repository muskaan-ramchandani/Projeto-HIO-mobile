package com.example.helperinolympics.cadastros;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.databinding.ActivityCadastroBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.telas_iniciais.TelaEscolhaOlimpiadaActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class CadastroActivity extends AppCompatActivity {

    ActivityCadastroBinding binding;
    String msgErro= "";
    Aluno alunoCadastrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intentVoltaEscolha = getIntent();

        //caso tenha voltado da tela de escolha de olimpíadas
        if(intentVoltaEscolha!=null){
            Aluno aluno = intentVoltaEscolha.getParcelableExtra("alunoCadastrado");

            if(aluno!=null){
                binding.editTextNomeCompleto.setText(aluno.getNomeCompleto());
                binding.editTextNomeUsuario.setText(aluno.getNomeUsuario());
                binding.editTextEmail.setText(aluno.getEmail());
                binding.editTextSenha.setText(aluno.getSenha());
                binding.editTextConfirmarSenha.setText(aluno.getSenha());

                //email não pode ser alterado pq o cadastro já foi realizado
                binding.editTextEmail.setEnabled(false);
                binding.editTextEmail.setCursorVisible(false);
                binding.editTextEmail.setFocusable(false);

                //verificar se a pessoa alterou algo ou só deseja prosseguir
                binding.btnFinalizarCadastro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nomeCompletoAtualizado = binding.editTextNomeCompleto.getText().toString();
                        String nomeUsuarioAtualizado = binding.editTextNomeUsuario.getText().toString();
                        String email = binding.editTextEmail.getText().toString();
                        String senhaAtualizada = binding.editTextSenha.getText().toString();
                        String confirmaSenhaAtualizada = binding.editTextConfirmarSenha.getText().toString();


                        boolean dadosCorretos= validarDadosCadastro(nomeCompletoAtualizado, nomeUsuarioAtualizado, email, senhaAtualizada, confirmaSenhaAtualizada);

                        if(dadosCorretos){

                            //editou dados (algum diferente do anterior)
                            if(!aluno.getNomeCompleto().equals(nomeCompletoAtualizado)
                                    || !aluno.getNomeUsuario().equals(nomeUsuarioAtualizado)
                                    ||!aluno.getSenha().equals(senhaAtualizada)){

                                new EditarAlunoTask(nomeCompletoAtualizado, nomeUsuarioAtualizado, email, senhaAtualizada).execute();

                            }else{
                                Intent intent = new Intent(CadastroActivity.this, TelaEscolhaOlimpiadaActivity.class);
                                intent.putExtra("alunoCadastrado", aluno);
                                startActivity(intent);
                                finish();
                            }

                        }else{
                            Toast.makeText(CadastroActivity.this, msgErro, Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }else{
                //caso o intent não seja o do tela escolha olimpiadas
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
                            Aluno aluno = new Aluno(nomeCompleto, nomeUsuario, email, senha);
                            new CadastrarAluno().execute(aluno);
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

        }

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
        }else if (!emailEValido(email)) {
            this.msgErro = "Email inválido";
            retorno = false;
        }else{
            retorno=true;
        }

        return retorno;
    }

    public boolean emailEValido(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private class CadastrarAluno extends AsyncTask<Aluno, Void, String> {
        @Override
        protected String doInBackground(Aluno... alunos) {
            StringBuilder result = new StringBuilder();
            Aluno aluno = alunos[0];

            alunoCadastrado = aluno;

            Log.d("CONEXAO", "tentando cadastro");

            try {
                URL url = new URL("http://192.168.1.11:8086/phpHio/cadastraAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                String parametros = "nomeCompleto=" + aluno.getNomeCompleto() +
                        "&nomeUsuario=" + aluno.getNomeUsuario() +
                        "&email=" + aluno.getEmail() +
                        "&senha=" + aluno.getSenha();

                OutputStream os = conexao.getOutputStream();
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

            } catch (Exception e) {
                Log.e("Erro", e.getMessage());
                return null;
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");

                    Toast.makeText(CadastroActivity.this, message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        Intent intent = new Intent(CadastroActivity.this, TelaEscolhaOlimpiadaActivity.class);
                        intent.putExtra("alunoCadastrado", alunoCadastrado);
                        startActivity(intent);
                        finish();
                    }

                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }

    private class EditarAlunoTask extends AsyncTask<Void, Void, String> {

        private String nomeCompleto, nomeUsuario, email, senha;

        public EditarAlunoTask(String nomeCompleto, String nomeUsuario, String email, String senha) {
            this.nomeCompleto = nomeCompleto;
            this.nomeUsuario = nomeUsuario;
            this.email = email;
            this.senha = senha;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                // URL do arquivo PHP
                URL url = new URL("http://192.168.1.11:8086/phpHio/editarDadosAlunoExcetoEmail.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "nomeCompleto=" + URLEncoder.encode(nomeCompleto, "UTF-8") +
                        "&nomeUsuario=" + URLEncoder.encode(nomeUsuario, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&senha=" + URLEncoder.encode(senha, "UTF-8");

                OutputStream os = conexao.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(parametros);
                writer.flush();
                writer.close();
                os.close();
                conexao.connect();

                int responseCode = conexao.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    result = sb.toString();
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        Intent intent = new Intent(CadastroActivity.this, TelaEscolhaOlimpiadaActivity.class);
                        Aluno alunoAtualizado = new Aluno(this.nomeCompleto, this.nomeUsuario, this.email, this.senha);
                        intent.putExtra("alunoCadastrado", alunoAtualizado);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
            }
        }
    }

}