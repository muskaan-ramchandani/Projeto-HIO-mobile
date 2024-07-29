package com.example.helperinolympics.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.helperinolympics.AlterarDadosActivity;
import com.example.helperinolympics.R;
import com.example.helperinolympics.modelos_sobrepostos.SenhaAlterarActivity;
import com.example.helperinolympics.modelos_sobrepostos.SenhaVerificarAlteracaoActivity;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

public class ConfiguracoesActivity extends AppCompatActivity {

    CardView alteraDados, alteraSenha, historicosAcesso, deletarConta;
    TextView nomeCompleto, user, email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        findViewById(R.id.btnVoltarAoInicioDasConfig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfiguracoesActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                startActivity(intent);
                finish();
            }
        });

        inserirDadosUsuario();

        alteraDados=findViewById(R.id.cardAlterarDados);
        alteraSenha=findViewById(R.id.cardAlterarSenha);
        historicosAcesso=findViewById(R.id.cardHistoricosAcesso);
        deletarConta=findViewById(R.id.cardDeletarConta);

        //Funções dos botôes
        alteraDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConfiguracoesActivity.this, AlterarDadosActivity.class);
                startActivity(intent);
                finish();

            }
        });

        alteraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationAlteraSenha();
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

    private void inserirDadosUsuario() {
        nomeCompleto = findViewById(R.id.txtNomeCompletoConfiguracoes);
        user = findViewById(R.id.txtUserConfiguracoes);
        email= findViewById(R.id.txtEmailConfiguracoes);

        nomeCompleto.setText("Bolofofos da Silva");
        user.setText("bolofofos");
        email.setText("bolofofos@gmail.com");
    }

    private void showNotificationAlteraSenha() {
        SenhaVerificarAlteracaoActivity notificationDialogFragment = new SenhaVerificarAlteracaoActivity();
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }

}
