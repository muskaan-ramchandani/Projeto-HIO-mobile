package com.example.helperinolympics.cadastros;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

    private class CadastrarAluno extends AsyncTask<DadosAluno, Void, String> {
        @Override
        protected String doInBackground(DadosAluno... alunos) {
            String msg="";
            DadosAluno aluno = alunos[0];
            Log.d("CONEXAO", "tentando cadastro");

            try {
                URL url = new URL("http://192.168.111.214/phpHio/cadastraAluno.php");
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

                int responseCode = conexao.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Log.d("CONEXAO", "Conexão estabelecida");
                    Log.d("ALUNO CADASTRADO:", aluno.toString());

                } else {
                    msg="Erro no cadastro: " + responseCode;
                }

            } catch (Exception e) {
                return "Erro: " + e.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(CadastroActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }
}