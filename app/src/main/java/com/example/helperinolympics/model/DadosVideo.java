package com.example.helperinolympics.model;

import android.graphics.Bitmap;

public class DadosVideo {
    private String titulo, linkVideo, professorRecomendou;
    private int id, idConteudoPertencente;
    private Bitmap capaVideo;

    public DadosVideo(){}

    public DadosVideo(int id, String titulo, String linkVideo, String professorRecomendou, int idConteudoPertencente, Bitmap capaVideo) {
        setId(id);
        setTitulo(titulo);
        setLinkVideo(linkVideo);
        setProfessorRecomendou(professorRecomendou);
        setIdConteudoPertencente(idConteudoPertencente);
        setCapaVideo(capaVideo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

    public String getProfessorRecomendou() {
        return professorRecomendou;
    }

    public void setProfessorRecomendou(String professorRecomendou) {
        this.professorRecomendou = professorRecomendou;
    }

    public int getIdConteudoPertencente() {
        return idConteudoPertencente;
    }

    public void setIdConteudoPertencente(int idConteudoPertencente) {
        this.idConteudoPertencente = idConteudoPertencente;
    }

    public Bitmap getCapaVideo() {
        return capaVideo;
    }

    public void setCapaVideo(Bitmap capaVideo) {
        this.capaVideo = capaVideo;
    }
}
