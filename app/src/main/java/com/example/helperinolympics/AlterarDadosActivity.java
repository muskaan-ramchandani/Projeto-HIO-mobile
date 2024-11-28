package com.example.helperinolympics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.databinding.ActivityAlterarDadosBinding;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.modelos_sobrepostos.ConfirmaSenhaAlterarDadosActivity;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AlterarDadosActivity extends AppCompatActivity {

    private ActivityAlterarDadosBinding binding;
    private Aluno alunoCadastrado;

    private boolean fotoAlterada;
    private Bitmap novaFotoPerfil;
    private String novoNomeCompleto, novoNomeUsuario;
    Bitmap foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlterarDadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.fotoAlterada = false;
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        FotoAlunoTask fotoAlunoTask = new FotoAlunoTask();
        fotoAlunoTask.execute(alunoCadastrado.getEmail());

        binding.btnVoltarAsConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlterarDadosActivity.this, ConfiguracoesActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarFoto();
            }
        });

        binding.btnFinalizarAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextNomeCompleto.getText().toString().isEmpty()&&binding.editTextNomeUsuario.getText().toString().isEmpty()
                        && !fotoAlterada){
                    Toast.makeText(AlterarDadosActivity.this, "Nenhuma informação nova foi inserida para que ocorra a alteração", Toast.LENGTH_LONG).show();

                }else{
                    if (fotoAlterada){
                        novaFotoPerfil = foto;
                    }
                    if(!binding.editTextNomeCompleto.getText().toString().isEmpty()){
                        novoNomeCompleto = binding.editTextNomeCompleto.getText().toString();
                    }
                    if(!binding.editTextNomeUsuario.getText().toString().isEmpty()){
                        novoNomeUsuario = binding.editTextNomeUsuario.getText().toString();
                    }

                    showNotificationConfirmarAlterarDados(alunoCadastrado, novaFotoPerfil, novoNomeCompleto, novoNomeUsuario, AlterarDadosActivity.this);
                }
            }
        });
    }

    private void editarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Escolha sua nova foto de perfil"), 1);
    }

    protected void onActivityResult(int RequestCode, int ResultCode, Intent dados) {
        super.onActivityResult(RequestCode, ResultCode, dados);
        if(ResultCode == AlterarDadosActivity.RESULT_OK){
            if(RequestCode == 1){
                try {
                    foto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dados.getData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                binding.fotoPerfil.setImageBitmap(foto);

                fotoAlterada = true;
            }
        }
    }

    public void showNotificationConfirmarAlterarDados(Aluno alunoCadastrado, Bitmap novaFotoPerfil, String novoNomeCompleto, String novoNomeUsuario, Context contexto){
        ConfirmaSenhaAlterarDadosActivity notificationDialogFragment = new ConfirmaSenhaAlterarDadosActivity(alunoCadastrado, novaFotoPerfil, novoNomeCompleto, novoNomeUsuario, contexto);
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
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
                binding.fotoPerfil.setImageBitmap(fotoBitmap);

            } else {
                Log.d("ERRO", "Não foi possível carregar a foto.");
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

}