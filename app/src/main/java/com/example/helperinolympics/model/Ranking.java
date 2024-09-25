package com.example.helperinolympics.model;

public class Ranking {

    private int posicao, fotoPerfil, qntdAcertos;
    private String user;

    public Ranking(int posicao, int fotoPerfil, int qntdAcertos, String user) {
        setPosicao(posicao);
        setFotoPerfil(fotoPerfil);
        setQntdAcertos(qntdAcertos);
        setUser(user);
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

    public int getQntdAcertos() {
        return qntdAcertos;
    }

    public void setQntdAcertos(int qntdAcertos) {
        this.qntdAcertos = qntdAcertos;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
