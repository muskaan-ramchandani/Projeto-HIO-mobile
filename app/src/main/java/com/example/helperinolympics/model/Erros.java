package com.example.helperinolympics.model;

public class Erros {
    private int id, idAlternativaMarcada, idAlternativaCorreta, idQuestionarioPertencente, idQuestaoPertencente;

    public Erros(int idAlternativaMarcada, int idAlternativaCorreta, int idQuestionarioPertencente, int idQuestaoPertencente) {
        setIdAlternativaMarcada(idAlternativaMarcada);
        setIdAlternativaCorreta(idAlternativaCorreta);
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

    public int getIdAlternativaCorreta() {
        return idAlternativaCorreta;
    }

    public void setIdAlternativaCorreta(int idAlternativaCorreta) {
        this.idAlternativaCorreta = idAlternativaCorreta;
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
