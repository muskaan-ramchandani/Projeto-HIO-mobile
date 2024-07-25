package com.example.helperinolympics.cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.R;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;
import com.example.helperinolympics.telas_iniciais.TelaEscolhaOlimpiadaActivity;

public class CadastroActivity extends AppCompatActivity {

    AppCompatButton cadastrar;
    ImageButton btnVoltarAlunoOuProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        cadastrar=findViewById(R.id.btnFinalizarCadastro);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, TelaEscolhaOlimpiadaActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

        btnVoltarAlunoOuProf=findViewById(R.id.btnVoltarAEscolhaAlunoProf);
        btnVoltarAlunoOuProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroActivity.this, CadastroAlunoProfessorActivity.class);
                startActivity(intent);
                finish(); //fechar activity
            }
        });

    }
}