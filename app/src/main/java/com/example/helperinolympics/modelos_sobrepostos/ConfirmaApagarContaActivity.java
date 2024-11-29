package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.telas_iniciais.TelaLoginActivity;

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

public class ConfirmaApagarContaActivity extends DialogFragment {
    private Aluno alunoCadastrado;
    private Context contexto;

    public ConfirmaApagarContaActivity(Aluno alunoCadastrado, Context contexto) {
        this.alunoCadastrado = alunoCadastrado;
        this.contexto = contexto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_confirma_apagar_conta, container, false);


        // Configurar o botão de fechar
        view.findViewById(R.id.btnFecharConfirmarDeletar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnConfirmarDeletar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DeletarConta(alunoCadastrado.getEmail()).execute();
            }
        });

        view.findViewById(R.id.btnNegarDeletar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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

    private class DeletarConta extends AsyncTask<Void, Void, String> {

        private String email;

        public DeletarConta(String email) {
            this.email = email;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                // URL do arquivo PHP
                URL url = new URL("http://10.100.51.3:8086/phpHio/deletarConta.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "email=" + URLEncoder.encode(email, "UTF-8");

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

            Log.d("HTTP_RESPONSE", result != null ? result : "Nenhuma resposta recebida");

            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");
                    Toast.makeText(contexto, message, Toast.LENGTH_LONG).show();

                    if ("success".equals(jsonResponse.getString("status"))) {
                        Log.d("SUCESSO_DELETAR", "Conta " + alunoCadastrado.getEmail() + " deletada com sucesso");
                        dismiss();
                    }
                } catch (JSONException e) {
                    Log.e("JSON_ERROR", "Erro ao processar JSON: " + e.getMessage());
                    dismiss();

                    ConfiguracoesActivity activity = (ConfiguracoesActivity) getActivity();
                    if (activity != null) {
                        activity.voltarAoLogin();
                        Toast.makeText(contexto, "Sua conta foi deletada", Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("JSON_ERROR", "Activity is null");
                    }
                }
            } else {
                Toast.makeText(contexto, "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
                dismiss();
            }
        }

    }
}