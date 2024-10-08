package com.example.helperinolympics.model;

import java.util.Date;

public class Acertos {
    private int id, idAlternativaMarcada, idQuestionarioPertencente, idQuestaoPertencente;
    private Date dataAcerto;
    private String emailAluno;

    //para historico de acertos
    private String siglaOlimpiada;
    private String tituloConteudo;
    private String tituloQuestionario;
    private String usuarioProfessor;
    private String txtPergunta;
    private String alternativaMarcada;

    public Acertos(){}

    public Acertos(int idAlternativaMarcada, int idQuestionarioPertencente, int idQuestaoPertencente, Date dataAcerto, String emailAluno) {
        setIdAlternativaMarcada(idAlternativaMarcada);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setIdQuestaoPertencente(idQuestaoPertencente);
        setDataAcerto(dataAcerto);
        setEmailAluno(emailAluno);
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

    public Date getDataAcerto() {
        return dataAcerto;
    }

    public void setDataAcerto(Date dataAcerto) {
        this.dataAcerto = dataAcerto;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }

    public String getSiglaOlimpiada() {
        return siglaOlimpiada;
    }

    public void setSiglaOlimpiada(String siglaOlimpiada) {
        this.siglaOlimpiada = siglaOlimpiada;
    }

    public String getTituloConteudo() {
        return tituloConteudo;
    }

    public void setTituloConteudo(String tituloConteudo) {
        this.tituloConteudo = tituloConteudo;
    }

    public String getTituloQuestionario() {
        return tituloQuestionario;
    }

    public void setTituloQuestionario(String tituloQuestionario) {
        this.tituloQuestionario = tituloQuestionario;
    }

    public String getUsuarioProfessor() {
        return usuarioProfessor;
    }

    public void setUsuarioProfessor(String usuarioProfessor) {
        this.usuarioProfessor = usuarioProfessor;
    }

    public String getTxtPergunta() {
        return txtPergunta;
    }

    public void setTxtPergunta(String txtPergunta) {
        this.txtPergunta = txtPergunta;
    }

    public String getAlternativaMarcada() {
        return alternativaMarcada;
    }

    public void setAlternativaMarcada(String alternativaMarcada) {
        this.alternativaMarcada = alternativaMarcada;
    }
}
