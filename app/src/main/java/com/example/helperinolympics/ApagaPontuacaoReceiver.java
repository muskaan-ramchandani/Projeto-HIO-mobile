package com.example.helperinolympics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.net.HttpURLConnection;
import java.net.URL;

public class ApagaPontuacaoReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        new ApagaPontuacaoAsyncTask().execute();
    }

    public static class ApagaPontuacaoAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("http://10.100.51.3:8086/phpHio/atualizaPontuacaoAcertosErrosSemana.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
