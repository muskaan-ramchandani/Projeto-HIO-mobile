package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.helperinolympics.databinding.ActivityMenuDeslizanteAlunoBinding;
import com.example.helperinolympics.databinding.ActivityPerfilAlunoBinding;

public class PerfilAlunoActivity extends Activity {

    private ActivityPerfilAlunoBinding binding;
    Button btnVoltarInicio;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_aluno);

        binding = ActivityPerfilAlunoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        btnVoltarInicio= binding.btnIniciar;
        btnVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilAlunoActivity.this, MenuDeslizanteAlunoActivity.class);
                startActivity(intent);
            }
        });
    }
}
