package com.example.helperinolympics.telas_iniciais;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.R;
import com.example.helperinolympics.materiais.TextoActivity;

public class TelaLoginActivity extends AppCompatActivity {

    EditText editTextUser, editTextInserirSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnFinalizarLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLoginActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnCadastreSe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaLoginActivity.this, TelaBemVindoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
