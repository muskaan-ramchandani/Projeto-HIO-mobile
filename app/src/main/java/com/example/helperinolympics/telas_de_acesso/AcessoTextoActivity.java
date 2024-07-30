package com.example.helperinolympics.telas_de_acesso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.R;
import com.example.helperinolympics.materiais.FlashcardActivity;
import com.example.helperinolympics.materiais.QuestionarioActivity;
import com.example.helperinolympics.materiais.TextoActivity;
import com.example.helperinolympics.materiais.VideoActivity;

public class AcessoTextoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acesso_texto);

        findViewById(R.id.imgButtonVoltarAOlimpDoTxtAcesso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoTextoActivity.this, TextoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnQuestionarioPeloAcessoTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoTextoActivity.this, QuestionarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideoPeloAcessoTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoTextoActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloAcessoTxt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoTextoActivity.this, FlashcardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}