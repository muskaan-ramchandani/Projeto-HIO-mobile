package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SenhaAlterarActivity extends DialogFragment {
    private Context contexto;
    private Aluno alunoCadastrado;

    public SenhaAlterarActivity(Context contexto, Aluno alunoCadastrado) {
        this.contexto=contexto;
        this.alunoCadastrado = alunoCadastrado;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_senha_alterar, container, false);

        // Configurar o botão de fechar
        view.findViewById(R.id.btnAlterar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText senha = view.findViewById(R.id.editTextNovaSenha);
                EditText confirmaSenha = view.findViewById(R.id.editTextConfirmaNovaSenha);

                if(senha.getText().toString().equals(confirmaSenha.getText().toString())){
                    new AlterarSenha(senha.getText().toString(), alunoCadastrado.getEmail()).execute();

                }else{
                    Toast.makeText(contexto, "Confirma senha está diferente de senha. Tente novamente.", Toast.LENGTH_SHORT).show();
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

    private class AlterarSenha extends AsyncTask<Void, Void, String> {

        private String senha, email;

        public AlterarSenha(String senha, String email) {
            this.senha = senha;
            this.email = email;
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = null;
            try {
                // URL do arquivo PHP
                URL url = new URL("http://10.100.51.3:8086/phpHio/alterarSenha.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setRequestMethod("POST");
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(15000);
                conexao.setDoInput(true);
                conexao.setDoOutput(true);

                String parametros = "senha=" + URLEncoder.encode(senha, "UTF-8") +
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
                        Log.d("SUCESSO_Senha", "Senha AGR é:" + senha);

                        if (getActivity() != null) {
                            ConfiguracoesActivity activity = (ConfiguracoesActivity) getActivity();

                            activity.alterarSenha(senha);
                        } else {
                            Log.e("DialogFragment", "A activity associada ao fragmento é null");
                        }

                        dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(contexto, "Erro ao conectar ao servidor.", Toast.LENGTH_LONG).show();
            }
        }
    }
}