package com.example.helperinolympics.model;

public class Ranking {

    private int posicao, fotoPerfil, qntdPontos;
    private String user, email;

    public Ranking(int posicao, int fotoPerfil, int qntdPontos, String user,String email) {
        setPosicao(posicao);
        setFotoPerfil(fotoPerfil);
        setQntdPontos(qntdPontos);
        setUser(user);
        setEmail(email);
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getQntdPontos() {
        return qntdPontos;
    }

    public void setQntdPontos(int qntdPontos) {
        this.qntdPontos = qntdPontos;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
