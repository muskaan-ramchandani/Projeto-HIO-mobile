package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.modelos_sobrepostos.ConfirmaSenhaAlterarDadosActivity;
import com.example.helperinolympics.modelos_sobrepostos.SenhaVerificarAlteracaoActivity;

public class AlterarDadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_dados);

        findViewById(R.id.btnVoltarAsConfiguracoes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlterarDadosActivity.this, ConfiguracoesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFinalizarAlterarDados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationConfirmarAlterarDados();
            }
        });
    }

    public void showNotificationConfirmarAlterarDados(){
        ConfirmaSenhaAlterarDadosActivity notificationDialogFragment = new ConfirmaSenhaAlterarDadosActivity();
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }
}