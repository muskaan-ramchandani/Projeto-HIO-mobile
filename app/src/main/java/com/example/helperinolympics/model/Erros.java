package com.example.helperinolympics.model;

import java.util.Date;

public class Erros {
    private int id, idAlternativaMarcada, idAlternativaCorreta, idQuestionarioPertencente, idQuestaoPertencente;
    private Date dataErro;
    private String emailAluno;

    //para historico de erros
    private String siglaOlimpiada;
    private String tituloConteudo;
    private String tituloQuestionario;
    private String usuarioProfessor;
    private String txtPergunta;
    private String alternativaMarcada;
    private String alternativaCorreta;


    public Erros(){};

    public Erros(int idAlternativaMarcada, int idAlternativaCorreta, int idQuestionarioPertencente, int idQuestaoPertencente, Date dataErro, String emailAluno) {
        setIdAlternativaMarcada(idAlternativaMarcada);
        setIdAlternativaCorreta(idAlternativaCorreta);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setIdQuestaoPertencente(idQuestaoPertencente);
        setDataErro(dataErro);
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

    public Date getDataErro() {
        return dataErro;
    }

    public void setDataErro(Date dataErro) {
        this.dataErro = dataErro;
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

    public String getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(String alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }
}
