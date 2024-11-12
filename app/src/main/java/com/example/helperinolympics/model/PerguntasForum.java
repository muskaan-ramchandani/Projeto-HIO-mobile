package com.example.helperinolympics.model;

import java.util.ArrayList;
import java.util.Date;

public class PerguntasForum {
    private int id, fotoPerfil, qntdRespostas;
    private String titulo, nomeDeUsuario, pergunta, olimpiada, dataPublicacao;


    public PerguntasForum(String titulo, String nomeDeUsuario, String pergunta, String olimpiada, String dataPublicacao) {
        setQntdRespostas(qntdRespostas);
        setTitulo(titulo);
        setNomeDeUsuario(nomeDeUsuario);
        setPergunta(pergunta);
        setOlimpiada(olimpiada);
        setDataPublicacao(dataPublicacao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getQntdRespostas() {
        return qntdRespostas;
    }

    public void setQntdRespostas(int qntdRespostas) {
        this.qntdRespostas = qntdRespostas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public void setNomeDeUsuario(String nomeDeUsuario) {
        this.nomeDeUsuario = nomeDeUsuario;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getOlimpiada() {
        return olimpiada;
    }

    public void setOlimpiada(String olimpiada) {
        this.olimpiada = olimpiada;
    }

    public String getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(String dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
