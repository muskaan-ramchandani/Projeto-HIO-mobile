package com.example.helperinolympics.asyn_tasks_universais;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CadastraHistoricoAsynTask  extends AsyncTask<String, Void, String> {
    private static final String URL_PHP = "http://10.0.0.64:8086/phpHio/cadastraHistoricoAcesso.php";

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        try {
            String emailAluno = params[0];
            String tipoMaterial = params[1];
            String idMaterial = params[2];

            URL url = new URL(URL_PHP);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);

            String postData = "emailAluno=" + emailAluno +
                    "&tipoMaterial=" + tipoMaterial +
                    "&idMaterial=" + idMaterial;

            DataOutputStream writer = new DataOutputStream(urlConnection.getOutputStream());
            writer.writeBytes(postData);
            writer.flush();
            writer.close();

            InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());
            StringBuilder stringBuilder = new StringBuilder();
            int data = reader.read();
            while (data != -1) {
                stringBuilder.append((char) data);
                data = reader.read();
            }

            return stringBuilder.toString();

        } catch (Exception e) {
            Log.e("CadastraHistoricoAsynTask", "Erro ao enviar dados: " + e.getMessage());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if (result != null) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                String message = jsonResponse.getString("message");

                Log.i("SendDataTask", "Resposta do servidor: " + message);
            } catch (Exception e) {
                Log.e("SendDataTask", "Erro ao analisar a resposta: " + e.getMessage());
            }
        } else {
            Log.e("SendDataTask", "Erro ao receber resposta do servidor");
        }
    }
}
