package com.example.helperinolympics.telas_iniciais;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.helperinolympics.R;
import com.example.helperinolympics.cadastros.CadastroActivity;

public class MainActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, TelaLoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500); // 3,5 segundos de splash
    }
}