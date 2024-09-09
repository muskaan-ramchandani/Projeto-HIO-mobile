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
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

public class AcessoQuestionarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questionario_acesso);

        findViewById(R.id.btnTextoPeloAcessoQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoQuestionarioActivity.this, TextoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideoPeloAcessoQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoQuestionarioActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnFlashcardPeloAcessoQuest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoQuestionarioActivity.this, FlashcardActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}