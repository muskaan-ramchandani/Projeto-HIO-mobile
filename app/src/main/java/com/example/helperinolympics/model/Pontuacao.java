package com.example.helperinolympics.model;

public class Pontuacao {

    private int id, pontuacao;
    private String emailAluno;

    public Pontuacao(int pontuacao, String emailAluno) {
        setPontuacao(pontuacao);
        setEmailAluno(emailAluno);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getEmailAluno() {
        return emailAluno;
    }

    public void setEmailAluno(String emailAluno) {
        this.emailAluno = emailAluno;
    }
}
