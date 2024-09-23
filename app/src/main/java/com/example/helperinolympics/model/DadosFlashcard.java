package com.example.helperinolympics.model;

import android.graphics.Bitmap;

public class DadosFlashcard {
    private String titulo, texto, profQuePostou;
    private int id, idConteudoPertencente;
    private Bitmap imagem;

    public DadosFlashcard(){}

    public DadosFlashcard(String titulo, String texto, String profQuePostou, int id, int idConteudoPertencente, Bitmap imagem) {
        setTitulo(titulo);
        setTexto(texto);
        setProfQuePostou(profQuePostou);
        setId(id);
        setIdConteudoPertencente(idConteudoPertencente);
        setImagem(imagem);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getProfQuePostou() {
        return profQuePostou;
    }

    public void setProfQuePostou(String profQuePostou) {
        this.profQuePostou = profQuePostou;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConteudoPertencente() {
        return idConteudoPertencente;
    }

    public void setIdConteudoPertencente(int idConteudoPertencente) {
        this.idConteudoPertencente = idConteudoPertencente;
    }

    public Bitmap getImagem() {
        return imagem;
    }

    public void setImagem(Bitmap imagem) {
        this.imagem = imagem;
    }
}
