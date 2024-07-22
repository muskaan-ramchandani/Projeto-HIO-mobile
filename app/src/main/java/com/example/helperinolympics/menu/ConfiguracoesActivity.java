package com.example.helperinolympics.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.R;
import com.example.helperinolympics.modelos_sobrepostos.FlashcardModelo;
import com.example.helperinolympics.modelos_sobrepostos.SenhaAlterarActivity;

public class ConfiguracoesActivity extends Activity {

    CardView alteraDados, alteraSenha, historicosAcesso, deletarConta;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        alteraDados=findViewById(R.id.cardAlterarDados);
        alteraSenha=findViewById(R.id.cardAlterarSenha);
        historicosAcesso=findViewById(R.id.cardHistoricosAcesso);
        deletarConta=findViewById(R.id.cardDeletarConta);

        //Funções dos botôes
        alteraDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        alteraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configurarDialogsSenha();
            }
        });

        historicosAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        deletarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
