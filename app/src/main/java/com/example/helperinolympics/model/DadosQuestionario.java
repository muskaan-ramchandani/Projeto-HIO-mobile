package com.example.helperinolympics.model;

public class DadosQuestionario {
    private int id;
    private String titulo, professorCadastrou, olimpiadaPertencente, temaPertencente;

    public DadosQuestionario(int id, String titulo, String professorCadastrou, String olimpiadaPertencente, String temaPertencente) {
        setTitulo(titulo);
        setProfessorCadastrou(professorCadastrou);
        setOlimpiadaPertencente(olimpiadaPertencente);
        setTemaPertencente(temaPertencente);
        setId(id);
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

    public String getProfessorCadastrou() {
        return professorCadastrou;
    }

    public void setProfessorCadastrou(String professorCadastrou) {
        this.professorCadastrou = professorCadastrou;
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


}
