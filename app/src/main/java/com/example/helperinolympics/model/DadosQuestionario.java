package com.example.helperinolympics.model;

public class DadosQuestionario {
    private String titulo, professorCadastrou, olimpiadaPertencente, temaPertencente;

    public DadosQuestionario(String titulo, String professorCadastrou, String olimpiadaPertencente, String temaPertencente) {
        setTitulo(titulo);
        setProfessorCadastrou(professorCadastrou);
        setOlimpiadaPertencente(olimpiadaPertencente);
        setTemaPertencente(temaPertencente);
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
