package com.example.helperinolympics.modelos_sobrepostos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Texto;

public class TextoHistoricoExibir extends DialogFragment {
    Texto texto;

    public TextoHistoricoExibir(Texto texto){
        this.texto = texto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.texto_historico, container, false);


        TextView titulo = view.findViewById(R.id.txtTituloTextoHistorico);
        titulo.setText(texto.getTitulo());

        TextView autor = view.findViewById(R.id.txtProfAutorTextoHistorico);
        autor.setText("Por: " + texto.getProfessorCadastrou());

        TextView textoAcesso = view.findViewById(R.id.txtTextoHistoricoAcesso);
        textoAcesso.setText(texto.getTexto());

        // Configurar o botão de fechar
        view.findViewById(R.id.btnFecharTxtHistorico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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