package com.example.helperinolympics.menu;

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

import com.example.helperinolympics.AlterarDadosActivity;
import com.example.helperinolympics.HistoricoDeAcessos;
import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityConfiguracoesBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.modelos_sobrepostos.ConfirmaApagarContaActivity;
import com.example.helperinolympics.modelos_sobrepostos.SenhaVerificarAlteracaoActivity;
import com.example.helperinolympics.modelos_sobrepostos.SenhaVerificarDeletarActivity;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;
import com.example.helperinolympics.telas_iniciais.TelaLoginActivity;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfiguracoesActivity extends AppCompatActivity implements ConfirmaApagarContaActivity.OnDialogActionListener {

    private ActivityConfiguracoesBinding binding;
    private Aluno alunoCadastrado;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfiguracoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        FotoAlunoTask fotoAlunoTask = new FotoAlunoTask();
        fotoAlunoTask.execute(alunoCadastrado.getEmail());

        findViewById(R.id.btnVoltarAoInicioDasConfig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfiguracoesActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        //Funções dos botôes
        binding.cardAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConfiguracoesActivity.this, AlterarDadosActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();

            }
        });

        binding.cardAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationAlteraSenha(alunoCadastrado);
            }
        });

        binding.cardHistoricosAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConfiguracoesActivity.this, HistoricoDeAcessos.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.cardDeletarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDeletarConta(alunoCadastrado);
            }
        });

    }

    private void inserirDadosUsuario(Bitmap fotoBitmap) {
        if (fotoBitmap == null) {
            Log.d("FOTO_PERFIL", "Foto de perfil é null, usando imagem padrão.");
            binding.imgFotoPerfilConfiguracoes.setImageResource(R.drawable.iconeperfilsemfoto);
        }  else {
            Log.d("FOTO_PERFIL", "Foto de perfil recebida, definindo Bitmap.");
            binding.imgFotoPerfilConfiguracoes.setImageBitmap(fotoBitmap);
        }

        binding.txtNomeCompletoConfiguracoes.setText(alunoCadastrado.getNomeCompleto());
        binding.txtUserConfiguracoes.setText(alunoCadastrado.getNomeUsuario());
        binding.txtEmailConfiguracoes.setText(alunoCadastrado.getEmail());
    }

    private void showNotificationAlteraSenha(Aluno alunoCadastrado) {
        SenhaVerificarAlteracaoActivity notificationDialogFragment = new SenhaVerificarAlteracaoActivity(alunoCadastrado, ConfiguracoesActivity.this);
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }

    private void showNotificationDeletarConta(Aluno alunoCadastrado) {
        SenhaVerificarDeletarActivity notificationDialogFragment = new SenhaVerificarDeletarActivity(alunoCadastrado, ConfiguracoesActivity.this);
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }

    public class FotoAlunoTask extends AsyncTask<String, Void, Bitmap> {

        private Bitmap fotoBitmap;

        @Override
        protected Bitmap doInBackground(String... params) {
            String email = params[0];
            Bitmap resultBitmap = null;

            try {
                URL url = new URL("https://hio.lat/retornaFotoPorEmail.php?email=" + email);
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
                inserirDadosUsuario(fotoBitmap);
            } else {
                Log.d("ERRO", "Não foi possível carregar a foto.");
                inserirDadosUsuario(fotoBitmap);
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

    public void alterarSenha(String senhaNova){
        this.alunoCadastrado.setSenha(senhaNova);
    }

    @Override
    public void onDialogAction() {
        Log.d("DELETAR_PT_DOIS", "Fechando config");
        Intent intent = new Intent(this, TelaLoginActivity.class);
        startActivity(intent);
        finish();
    }

}
