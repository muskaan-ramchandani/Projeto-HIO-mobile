package com.example.helperinolympics.telas_iniciais;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.app.AlarmManager;

import com.example.helperinolympics.ApagaPontuacaoReceiver;
import com.example.helperinolympics.databinding.ActivitySplashScreenBinding;

import java.util.Calendar;


public class MainActivity extends Activity {

    ActivitySplashScreenBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarAlarmManager();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, TelaLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500); // 3,5 segundos de splash
    }


    //apagando dados de acertos e erros a cada 7 dias
    private void configurarAlarmManager() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ApagaPontuacaoReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        );
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);

        // Se a hora agendada já passou, execute imediatamente
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            new ApagaPontuacaoReceiver.ApagaPontuacaoAsyncTask().execute();
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
    }


}