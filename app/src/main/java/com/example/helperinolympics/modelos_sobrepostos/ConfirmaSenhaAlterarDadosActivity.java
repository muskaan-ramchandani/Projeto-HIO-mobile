package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.model.Aluno;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ConfirmaSenhaAlterarDadosActivity extends DialogFragment {

    private Aluno alunoCadastrado;
    private Bitmap novaFotoPerfil;
    private String novoNomeCompleto, novoNomeUsuario;
    private Context contexto;

    public ConfirmaSenhaAlterarDadosActivity(Aluno alunoCadastrado, Bitmap novaFotoPerfil, String novoNomeCompleto, String novoNomeUsuario, Context contexto){
        this.alunoCadastrado = alunoCadastrado;
        this.novaFotoPerfil = novaFotoPerfil;
        this.novoNomeCompleto = novoNomeCompleto;
        this.novoNomeUsuario = novoNomeUsuario;
        this.contexto = contexto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_confirmar_senha_para_permissao, container, false);

        TextView txt = view.findViewById(R.id.txtDigiteSenha);
        txt.setText("Digite sua senha para confirmar a alteração de dados");

        view.findViewById(R.id.btnFecharConfirmarSenha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText senha = view.findViewById(R.id.editTextVerificarSenha);
                String digitado = senha.getText().toString();
                String senhaAluno = alunoCadastrado.getSenha();


                if(digitado.isEmpty()){
                    Toast.makeText(contexto, "É preciso inserir a senha", Toast.LENGTH_SHORT).show();

                }else if(digitado.equals(senhaAluno)){

                    if(!(novaFotoPerfil==null)){
                        Log.d("FOTO", "NÃO É VAZIA");
                        new AlterarFotoPerfil(alunoCadastrado.getEmail(), novaFotoPerfil).execute(); //fotoPerfil
                    }
                    if(!(novoNomeCompleto==null)){
                        Log.d("NOME COMPLETO", "NÃO É VAZIO");
                        new AlterarNomeCompleto(novoNomeCompleto, alunoCadastrado.getEmail()).execute();
                    }
                    if(!(novoNomeUsuario==null)){
                        Log.d("USER", "NÃO É VAZIO");
                        new AlterarNomeUsuario(novoNomeUsuario, alunoCadastrado.getEmail()).execute();
                    }

                    Toast.makeText(contexto, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        if (isAdded() && getContext() != null) {
                            Intent intent = new Intent(contexto, ConfiguracoesActivity.class);
                            intent.putExtra("alunoCadastrado", alunoCadastrado);
                            startActivity(intent);
                            dismiss();
                        } else {
                            Log.e("FragmentError", "Fragment não está mais anexado. Operação ignorada.");
                        }
                    }, 1015);


                }else{
                    Toast.makeText(contexto, "Senha incorreta, tente novamente se quiser alterar seus dados", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private class AlterarNomeCompleto extends AsyncTask<Void, Void, String> {

        private String nomeCompleto, email;

        public AlterarNomeCompleto(String nomeCompleto, String email) {
            this.nomeCompleto = nomeCompleto;
            this.email = email;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                // URL do arquivo PHP
                URL url = new URL("https://hio.lat/alterarNomeCompletoAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "nomeCompleto=" + URLEncoder.encode(nomeCompleto, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8");

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
                    Toast.makeText(contexto.getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        alunoCadastrado.setNomeCompleto(nomeCompleto);
                        Log.d("SUCESSO_NOME_COMPLETO", "Nome completo AGR é:" + nomeCompleto);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(contexto, "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AlterarNomeUsuario extends AsyncTask<Void, Void, String> {

        private String nomeUsuario, email;

        public AlterarNomeUsuario(String nomeUsuario, String email) {
            this.nomeUsuario = nomeUsuario;
            this.email = email;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                // URL do arquivo PHP
                URL url = new URL("https://hio.lat/alterarNomeUsuarioAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "nomeUsuario=" + URLEncoder.encode(nomeUsuario, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8");

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
                    Toast.makeText(contexto.getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        alunoCadastrado.setNomeUsuario(nomeUsuario);
                        Log.d("SUCESSO_NOME_USUARIO", "Nome user AGR é:" + nomeUsuario);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(contexto, "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AlterarEmail extends AsyncTask<Void, Void, String> {

        private String email, novoEmail;

        public AlterarEmail(String email, String novoEmail) {
            this.email = email;
            this.novoEmail = novoEmail;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                // URL do arquivo PHP
                URL url = new URL("https://hio.lat/alterarEmailAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "email=" + URLEncoder.encode(email, "UTF-8")+ "&novoEmail=" + URLEncoder.encode(this.novoEmail, "UTF-8");

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
                    Toast.makeText(contexto.getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        alunoCadastrado.setEmail(novoEmail);
                        Log.d("SUCESSO_EMAIL", "Email AGR é:" + novoEmail);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(contexto, "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AlterarFotoPerfil extends AsyncTask<Void, Void, String> {

        private String email;
        private String imagemBinario;
        private Bitmap fotoBitmap;

        public AlterarFotoPerfil(String email, Bitmap fotoBitmap) {
            this.email = email;
            this.fotoBitmap = fotoBitmap;
            this.imagemBinario = bitmapToBase64(fotoBitmap);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                URL url = new URL("https://hio.lat/alterarFotoAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "email=" + URLEncoder.encode(email, "UTF-8") +
                        "&fotoPerfil=" + URLEncoder.encode(imagemBinario, "UTF-8");

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
                } else {
                    Log.e("HTTP_ERROR", "Código de resposta: " + responseCode);
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
                    Toast.makeText(contexto.getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        alunoCadastrado.setFotoPerfil(fotoBitmap);
                        Log.d("SUCESSO_FOTO", "Foto de perfil alterada com sucesso.");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(contexto, "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
            }
        }

        private String bitmapToBase64(Bitmap bitmap) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] byteArray = outputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
    }
}