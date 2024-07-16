package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaBemVindo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_bem_vindo);

        // Recuperar o nome do usuário passado do Intent
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("name");

            TextView textViewUsername = findViewById(R.id.ediTextNomeCompleto);
            textViewUsername.setText("Bem-vindo, " +username);
        }

    }
}