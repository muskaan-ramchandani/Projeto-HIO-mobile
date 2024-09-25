package com.example.helperinolympics.model.questionario;

import java.util.ArrayList;

public class Questao {

    //id é o número da pergunta pelo padrão de cadastro auto incremento do banco
    private int id, idQuestionarioPertencente;
    private String txtPergunta;

    public Questao(){}

    public Questao(int id, int idQuestionarioPertencente, String txtPergunta) {
        setId(id);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setTxtPergunta(txtPergunta);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestionarioPertencente() {
        return idQuestionarioPertencente;
    }

    public void setIdQuestionarioPertencente(int idQuestionarioPertencente) {
        this.idQuestionarioPertencente = idQuestionarioPertencente;
    }

    public String getTxtPergunta() {
        return txtPergunta;
    }

    public void setTxtPergunta(String txtPergunta) {
        this.txtPergunta = txtPergunta;
    }
}
