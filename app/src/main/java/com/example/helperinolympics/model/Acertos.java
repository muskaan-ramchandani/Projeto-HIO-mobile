package com.example.helperinolympics.model;

public class Acertos {
    private int id, idAlternativaMarcada, idQuestionarioPertencente, idQuestaoPertencente;

    public Acertos(int idAlternativaMarcada, int idQuestionarioPertencente, int idQuestaoPertencente) {
        setIdAlternativaMarcada(idAlternativaMarcada);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setIdQuestaoPertencente(idQuestaoPertencente);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlternativaMarcada() {
        return idAlternativaMarcada;
    }

    public void setIdAlternativaMarcada(int idAlternativaMarcada) {
        this.idAlternativaMarcada = idAlternativaMarcada;
    }

    public int getIdQuestionarioPertencente() {
        return idQuestionarioPertencente;
    }

    public void setIdQuestionarioPertencente(int idQuestionarioPertencente) {
        this.idQuestionarioPertencente = idQuestionarioPertencente;
    }

    public int getIdQuestaoPertencente() {
        return idQuestaoPertencente;
    }

    public void setIdQuestaoPertencente(int idQuestaoPertencente) {
        this.idQuestaoPertencente = idQuestaoPertencente;
    }
}
