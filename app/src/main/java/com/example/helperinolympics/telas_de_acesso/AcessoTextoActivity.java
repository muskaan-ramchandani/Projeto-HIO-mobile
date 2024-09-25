package com.example.helperinolympics.telas_de_acesso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityAcessoTextoBinding;
import com.example.helperinolympics.materiais.FlashcardActivity;
import com.example.helperinolympics.materiais.QuestionarioActivity;
import com.example.helperinolympics.materiais.TextoActivity;
import com.example.helperinolympics.materiais.VideoActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.Texto;

public class AcessoTextoActivity extends AppCompatActivity {

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private Texto texto;
    private String siglaOlimpiada;

    private ActivityAcessoTextoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAcessoTextoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //recebendo os dados da outra tela
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        texto = getIntent().getParcelableExtra("texto");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");

        //iniciando configurações
        configurarDetalhesTela(siglaOlimpiada);
        configurarVisualizacaoTexto(texto);

        findViewById(R.id.imgButtonVoltarAOlimpDoTxtAcesso).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AcessoTextoActivity.this, TextoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
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
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
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

    private void configurarVisualizacaoTexto(Texto texto) {

        binding.txtTituloTexto.setText(texto.getTitulo());
        binding.txtProfAutorDoTexto.setText("Por: "+ texto.getProfessorCadastrou());
        binding.txtTextoCriado.setText(texto.getTexto());

    }

    private void configurarDetalhesTela(String siglaOlimpiada) {

        switch (siglaOlimpiada){
            case "OBA":
                binding.txtNomeOlimp.setText("OBA");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgtelescopio);
                break;
            case "OBF":
                binding.txtNomeOlimp.setText("OBF");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgmacacaindo);
                break;
            case "OBI":
                binding.txtNomeOlimp.setText("OBI");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgcomputador);
                break;
            case "OBMEP":
                binding.txtNomeOlimp.setText("OBMEP");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgoperacoesbasicas);
                break;
            case "ONHB":
                binding.txtNomeOlimp.setText("ONHB");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgpapiro);
                break;
            case "OBQ":
                binding.txtNomeOlimp.setText("OBQ");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgtubodeensaio);
                break;
            case "OBB":
                binding.txtNomeOlimp.setText("OBB");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgdna);
                break;
            case "ONC":
                binding.txtNomeOlimp.setText("ONC");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgatomo);
                break;
        }

    }
}