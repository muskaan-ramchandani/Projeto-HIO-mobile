package com.example.helperinolympics.cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.R;
import com.example.helperinolympics.modelos_sobrepostos.FlashcardModelo;
import com.example.helperinolympics.modelos_sobrepostos.RecadoProfWebActivity;
import com.example.helperinolympics.telas_iniciais.TelaBemVindoActivity;

public class CadastroAlunoProfessorActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_aluno_professor);

        findViewById(R.id.btnVoltarAoBemVindo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroAlunoProfessorActivity.this, TelaBemVindoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnSouAluno).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroAlunoProfessorActivity.this, CadastroActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnSouProfessor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        RecadoProfWebActivity notificationDialogFragment = new RecadoProfWebActivity();
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }
}