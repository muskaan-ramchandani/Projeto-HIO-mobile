package com.example.helperinolympics.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.AlterarDadosActivity;
import com.example.helperinolympics.HistoricoDeAcessos;
import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityConfiguracoesBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.modelos_sobrepostos.SenhaVerificarAlteracaoActivity;
import com.example.helperinolympics.modelos_sobrepostos.SenhaVerificarDeletarActivity;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

public class ConfiguracoesActivity extends AppCompatActivity {

    private ActivityConfiguracoesBinding binding;
    private Aluno alunoCadastrado;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfiguracoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        findViewById(R.id.btnVoltarAoInicioDasConfig).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfiguracoesActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        inserirDadosUsuario();


        //Funções dos botôes
        binding.cardAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConfiguracoesActivity.this, AlterarDadosActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();

            }
        });

        binding.cardAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationAlteraSenha(alunoCadastrado);
            }
        });

        binding.cardHistoricosAcesso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConfiguracoesActivity.this, HistoricoDeAcessos.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.cardDeletarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotificationDeletarConta(alunoCadastrado);
            }
        });
    }

    private void inserirDadosUsuario() {
        if(alunoCadastrado.getFotoPerfil()==null){
            binding.imgFotoPerfilConfiguracoes.setImageResource(R.drawable.iconeperfilsemfoto);
        }else{
            binding.imgFotoPerfilConfiguracoes.setImageBitmap(alunoCadastrado.getFotoPerfil());
        }

        binding.txtNomeCompletoConfiguracoes.setText(alunoCadastrado.getNomeCompleto());
        binding.txtUserConfiguracoes.setText(alunoCadastrado.getNomeUsuario());
        binding.txtEmailConfiguracoes.setText(alunoCadastrado.getEmail());
    }

    private void showNotificationAlteraSenha(Aluno alunoCadastrado) {
        SenhaVerificarAlteracaoActivity notificationDialogFragment = new SenhaVerificarAlteracaoActivity(alunoCadastrado);
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }

    private void showNotificationDeletarConta(Aluno alunoCadastrado) {
        SenhaVerificarDeletarActivity notificationDialogFragment = new SenhaVerificarDeletarActivity(alunoCadastrado);
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }

}
