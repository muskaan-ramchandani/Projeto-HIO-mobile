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

public class CadastrarPergunta extends DialogFragment {
    private Aluno alunoCadastrado;

    public CadastrarPergunta(Aluno aluno){
        this.alunoCadastrado = aluno;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastrar_pergunta, container, false);

        view.findViewById(R.id.btnPublicar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tudoPreenchido = verificarPreenchimento();

                if(tudoPreenchido == true){
                    
                }else{
                    
                }
                dismiss();
            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private boolean verificarPreenchimento() {
    }

    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}