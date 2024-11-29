package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
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

public class SenhaVerificarDeletarActivity extends DialogFragment {
    private Aluno alunoCadastrado;
    private Context contexto;

    public SenhaVerificarDeletarActivity(Aluno alunoCadastrado, Context contexto) {
        this.alunoCadastrado = alunoCadastrado;
        this.contexto = contexto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_confirmar_senha_para_permissao, container, false);
        TextView txt = view.findViewById(R.id.txtDigiteSenha);
        txt.setText("Digite sua senha atual para  que você possa deletar a conta");

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
                    ConfirmaApagarContaActivity confirmaApagar = new ConfirmaApagarContaActivity(alunoCadastrado, contexto);
                    confirmaApagar.show(getParentFragmentManager(), "Confirmar a ação de deletar conta");
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
}
