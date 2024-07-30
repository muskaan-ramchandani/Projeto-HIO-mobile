package com.example.helperinolympics.model;

import java.util.Date;

public class DadosLivros {

    int id, capa;
    String titulo, autor, edicao;
    Date dataPublicacao;

    public DadosLivros(int id, int capa, String titulo, String autor, String edicao, Date dataPublicacao) {
        setId(id);
        setCapa(capa);
        setTitulo(titulo);
        setAutor(autor);
        setEdicao(edicao);
        setDataPublicacao(dataPublicacao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapa() {
        return capa;
    }

    public void setCapa(int capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
