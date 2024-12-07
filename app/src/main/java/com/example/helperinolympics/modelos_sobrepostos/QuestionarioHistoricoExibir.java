package com.example.helperinolympics.modelos_sobrepostos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.questionario.Questao;
import com.example.helperinolympics.model.questionario.Questionario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class QuestionarioHistoricoExibir extends DialogFragment {
    private Questionario questionario;
    private List<Questao> listaDeQuestoes = new ArrayList<>();
    private int qntdQuestoes, numeroAtual=1;

    public QuestionarioHistoricoExibir(Questionario questionario, ArrayList<Questao> listaDeQuestoes){
        this.questionario = questionario;
        this.listaDeQuestoes.addAll(listaDeQuestoes);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questionario_historico, container, false);

        qntdQuestoes = listaDeQuestoes.size();

        TextView titulo = view.findViewById(R.id.txtTituloQuestHistorico);
        titulo.setText(questionario.getTitulo());

        TextView autor = view.findViewById(R.id.txtProfAutorQuestHistorico);
        autor.setText("Por: " + questionario.getProfessorCadastrou());

        TextView pergunta = view.findViewById(R.id.txtPerguntaHistoricoAcesso);
        String perguntaFormatada = "<b>Pergunta </b>"+ "<b>"+ numeroAtual +": </b>" + listaDeQuestoes.get(0).getTxtPergunta();
        pergunta.setText(Html.fromHtml(perguntaFormatada, Html.FROM_HTML_MODE_COMPACT));

        view.findViewById(R.id.btnFecharQuestHistorico).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.findViewById(R.id.imgButtonVoltarQuestao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numeroAtual > 1) {
                    numeroAtual--;
                    String perguntaFormatada = "<b>Pergunta </b>" + "<b>" + numeroAtual + ": </b>" + listaDeQuestoes.get(numeroAtual - 1).getTxtPergunta();
                    pergunta.setText(Html.fromHtml(perguntaFormatada, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    Toast.makeText(getContext(), "Não é possível voltar mais, pois esta é a primeira pergunta!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.imgButtonAvancarDireita).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numeroAtual < qntdQuestoes) {
                    numeroAtual++;
                    String perguntaFormatada = "<b>Pergunta </b>" + "<b>" + numeroAtual + ": </b>" + listaDeQuestoes.get(numeroAtual - 1).getTxtPergunta();
                    pergunta.setText(Html.fromHtml(perguntaFormatada, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    Toast.makeText(getContext(), "Não é possível avançar mais, pois esta é a última pergunta!", Toast.LENGTH_SHORT).show();
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