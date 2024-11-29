package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.verificacao.RandomCodeGenerator;
import com.example.helperinolympics.model.Aluno;


public class SenhaVerificarAlteracaoActivity extends DialogFragment {

    private Aluno alunoCadastrado;
    private String codigoVerificacaoAleatorio;
    private Context contexto;

    public SenhaVerificarAlteracaoActivity(Aluno alunoCadastrado, Context contexto) {
        this.alunoCadastrado = alunoCadastrado;
        this.contexto = contexto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_senha_verificar_alteracao, container, false);
        codigoVerificacaoAleatorio = RandomCodeGenerator.generateRandomCode();

        enviarCodigoPorEmail();

        // Configurar o botão de fechar
        view.findViewById(R.id.btnFecharAlterarSenha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnVerificarCodigo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = view.findViewById(R.id.editTextVerificarCodigo);

                if(edit.getText().toString().equals(codigoVerificacaoAleatorio)){
                    SenhaAlterarActivity alterarSenha = new SenhaAlterarActivity(contexto, alunoCadastrado);
                    alterarSenha.show(getParentFragmentManager(), "alterar senha");
                    dismiss();
                }else{
                    Toast.makeText(contexto, "O que foi digitado não condiz com o código de verificção enviado. Tente novamente.", Toast.LENGTH_LONG);
                }
            }
        });

        view.findViewById(R.id.btnEnviarOutro).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String novoCodigo = RandomCodeGenerator.generateRandomCode();
                codigoVerificacaoAleatorio = novoCodigo;
                enviarCodigoPorEmail();
            }
        });

        return view;
    }

    private void enviarCodigoPorEmail() {

        Intent intent = new Intent(Intent.ACTION_);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, alunoCadastrado.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Assunto");
        intent.putExtra(Intent.EXTRA_TEXT, "A seguir está o seu código de verificação para alterar senha no app HIO: " + codigoVerificacaoAleatorio);
        intent.setPackage("com.google.android.gm"); // Gmail
        startActivity(intent);
    }


    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}
