package com.example.helperinolympics.modelos_sobrepostos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;

public class SenhaVerificarDeletarActivity extends DialogFragment {

    public SenhaVerificarDeletarActivity(Aluno alunoCadastrado) {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_senha_verificar_deletar_conta, container, false);


        // Configurar o botão de fechar
        view.findViewById(R.id.btnFecharDeletarConta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnVerificarCodigo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmaApagarContaActivity confirmaApagar = new ConfirmaApagarContaActivity();
                confirmaApagar.show(getParentFragmentManager(), "Confirmar a ação de deletar conta");
                dismiss(); //fechar dialog 1
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
