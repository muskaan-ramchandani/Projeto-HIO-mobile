package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.R;
import com.example.helperinolympics.cadastros.CadastroAlunoProfessorActivity;

public class TelaBemVindoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_bem_vindo);

        findViewById(R.id.btnBemVindoVoltarAoLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaBemVindoActivity.this, TelaLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnContinuarCadastro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaBemVindoActivity.this, CadastroAlunoProfessorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}