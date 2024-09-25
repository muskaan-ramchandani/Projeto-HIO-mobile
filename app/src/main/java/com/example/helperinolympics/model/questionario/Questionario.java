package com.example.helperinolympics.model.questionario;

public class Questionario {
    private int id, idConteudoPertencente;
    private String titulo, professorCadastrou;

    public Questionario(){}

    public Questionario(int id, String titulo, String professorCadastrou, int idConteudoPertencente) {
        setTitulo(titulo);
        setProfessorCadastrou(professorCadastrou);
        setIdConteudoPertencente(idConteudoPertencente);
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

    public int getIdConteudoPertencente() {
        return idConteudoPertencente;
    }

    public void setIdConteudoPertencente(int idConteudoPertencente) {
        this.idConteudoPertencente = idConteudoPertencente;
    }
}
