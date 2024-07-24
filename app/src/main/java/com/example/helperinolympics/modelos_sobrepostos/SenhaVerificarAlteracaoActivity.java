package com.example.helperinolympics.modelos_sobrepostos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;

public class SenhaVerificarAlteracaoActivity extends DialogFragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_senha_verificar_alteracao, container, false);


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
                SenhaAlterarActivity alterarSenha = new SenhaAlterarActivity();
                alterarSenha.show(getParentFragmentManager(), "alterar senha");
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
