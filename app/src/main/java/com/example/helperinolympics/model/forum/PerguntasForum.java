package com.example.helperinolympics.model.forum;

import android.graphics.Bitmap;

import java.util.Date;

public class PerguntasForum {
    private int id, qntdRespostas;
    private String titulo, nomeDeUsuario, pergunta, olimpiada;
    private Date dataPublicacao;
    private Bitmap fotoPerfil;

    //dados para cadastro da pergunta
    public PerguntasForum(String titulo, String nomeDeUsuario, String pergunta, String olimpiada, Date dataPublicacao) {
        setTitulo(titulo);
        setNomeDeUsuario(nomeDeUsuario);
        setPergunta(pergunta);
        setOlimpiada(olimpiada);
        setDataPublicacao(dataPublicacao);
    }

    //dados para montar recycler view retornado do banco
    public PerguntasForum(int id,int qntdRespostas, String titulo, String nomeDeUsuario, String pergunta, String olimpiada, Date dataPublicacao) {
        setId(id);
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

    public int getQntdRespostas() {
        return qntdRespostas;
    }

    public void setQntdRespostas(int qntdRespostas) {
        this.qntdRespostas = qntdRespostas;
    }

    public Bitmap getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Bitmap fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
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

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
