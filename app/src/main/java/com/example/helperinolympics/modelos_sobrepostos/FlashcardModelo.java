package com.example.helperinolympics.modelos_sobrepostos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosFlashcard;


public class FlashcardModelo extends DialogFragment {
    DadosFlashcard flashcard;

    public FlashcardModelo(DadosFlashcard flashcard){
        this.flashcard = flashcard;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.modelo_flashcard, container, false);

        configurarExibicaoFlashcard(view, flashcard);

        // Configurar o botão de fechar
        view.findViewById(R.id.btnFechar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void configurarExibicaoFlashcard(View view, DadosFlashcard flashcard) {
        TextView titulo = view.findViewById(R.id.txtTopico);
        titulo.setText(flashcard.getTitulo());

        TextView texto = view.findViewById(R.id.txtResumoTopico);
        texto.setText(flashcard.getTexto());

        ImageView imagem = view.findViewById(R.id.imgDoAssuntoFlashcard);
        imagem.setImageBitmap(flashcard.getImagem());
    }


    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }
}





