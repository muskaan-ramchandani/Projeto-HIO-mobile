package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;


public class SenhaVerificarAlteracaoActivity extends DialogFragment {

    private Aluno alunoCadastrado;
    private Context contexto;

    public SenhaVerificarAlteracaoActivity(Aluno alunoCadastrado, Context contexto) {
        this.alunoCadastrado = alunoCadastrado;
        this.contexto = contexto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_confirmar_senha_para_permissao, container, false);
        TextView txt = view.findViewById(R.id.txtDigiteSenha);
        txt.setText("Digite sua senha atual para  que você possa ter permissão para alterá-la");

        // Configurar o botão de fechar
        view.findViewById(R.id.btnFecharConfirmarSenha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnConfirmar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edit = view.findViewById(R.id.editTextVerificarSenha);
                String digitado = edit.getText().toString();
                String senhaAluno = alunoCadastrado.getSenha();

                if(digitado.equals(senhaAluno)){
                    SenhaAlterarActivity alterarSenha = new SenhaAlterarActivity(contexto, alunoCadastrado);
                    alterarSenha.show(getParentFragmentManager(), "alterar senha");
                    dismiss();
                }else{
                    Toast.makeText(contexto, "O que foi digitado não condiz com a senha. Tente novamente.", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }


    //CODIGO DE VERIFICAÇÃO DE EMAIL QUE FALHOU
    /*
    String codigoVerificacaoAleatorio = RandomCodeGenerator.generateRandomCode();

    private void enviarCodigoPorEmail() {

        Intent intent = new Intent(Intent.ACTION_);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, alunoCadastrado.getEmail());
        intent.putExtra(Intent.EXTRA_SUBJECT, "Assunto");
        intent.putExtra(Intent.EXTRA_TEXT, "A seguir está o seu código de verificação para alterar senha no app HIO: " + codigoVerificacaoAleatorio);
        intent.setPackage("com.google.android.gm"); // Gmail
        startActivity(intent);
    }


    package com.example.helperinolympics.verificacao;

import java.util.Random;

public class RandomCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 8;

    public static String generateRandomCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }
        return code.toString();
    }
}


    */
}
