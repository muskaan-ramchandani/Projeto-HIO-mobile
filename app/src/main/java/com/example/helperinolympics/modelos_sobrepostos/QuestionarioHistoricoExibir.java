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
import com.example.helperinolympics.model.questionario.Questionario;

public class QuestionarioHistoricoExibir extends DialogFragment {
    Questionario questionario;

    public QuestionarioHistoricoExibir(Questionario questionario){
        this.questionario = questionario;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionario_historico, container, false);

        TextView olimpiada = view.findViewById(R.id.txtOlimpiadaQuestHistorico);
        //olimpiada.setText(questionario.getOlimpiadaPertencente());

        TextView titulo = view.findViewById(R.id.txtTituloQuestHistorico);
        titulo.setText(questionario.getTitulo());

        TextView autor = view.findViewById(R.id.txtProfAutorQuestHistorico);
        autor.setText("Por: " + questionario.getProfessorCadastrou());

        TextView pergunta = view.findViewById(R.id.txtPerguntaHistoricoAcesso);
        //pergunta.setText(questionario.getPergunta());

        // Configurar o botão de fechar
        view.findViewById(R.id.btnFecharQuestHistorico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.btnAcessarQuestionario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //encaminhar para responder
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