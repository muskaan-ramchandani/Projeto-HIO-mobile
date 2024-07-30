package com.example.helperinolympics.model;

public class DadosVideo {
    private String titulo, linkVideo, professorRecomendou, olimpiadaPertencente, temaPertencente;
    private int capaVideo, id;

    public DadosVideo(int id, String titulo, String linkVideo, String professorRecomendou, String olimpiadaPertencente, String temaPertencente, int capaVideo) {
        setId(id);
        setTitulo(titulo);
        setLinkVideo(linkVideo);
        setProfessorRecomendou(professorRecomendou);
        setOlimpiadaPertencente(olimpiadaPertencente);
        setTemaPertencente(temaPertencente);
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

    public String getOlimpiadaPertencente() {
        return olimpiadaPertencente;
    }

    public void setOlimpiadaPertencente(String olimpiadaPertencente) {
        this.olimpiadaPertencente = olimpiadaPertencente;
    }

    public String getTemaPertencente() {
        return temaPertencente;
    }

    public void setTemaPertencente(String temaPertencente) {
        this.temaPertencente = temaPertencente;
    }

    public int getCapaVideo() {
        return capaVideo;
    }

    public void setCapaVideo(int capaVideo) {
        this.capaVideo = capaVideo;
    }
}
