package com.example.helperinolympics.model.questionario;

public class Alternativas {

    private int id, idQuestionarioPertencente, idQuestaoPertencente;
    private String textoAlternativa;
    private boolean corretaOuErrada;

    public Alternativas(){}

    public Alternativas(int id, int idQuestionarioPertencente, int idQuestaoPertencente, String textoAlternativa, boolean corretaOuErrada) {
        setId(id);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setIdQuestaoPertencente(idQuestaoPertencente);
        setTextoAlternativa(textoAlternativa);
        setCorretaOuErrada(corretaOuErrada);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestionarioPertencente() {
        return idQuestionarioPertencente;
    }

    public void setIdQuestionarioPertencente(int idQuestionarioPertencente) {
        this.idQuestionarioPertencente = idQuestionarioPertencente;
    }

    public int getIdQuestaoPertencente() {
        return idQuestaoPertencente;
    }

    public void setIdQuestaoPertencente(int idQuestaoPertencente) {
        this.idQuestaoPertencente = idQuestaoPertencente;
    }

    public String getTextoAlternativa() {
        return textoAlternativa;
    }

    public void setTextoAlternativa(String textoAlternativa) {
        this.textoAlternativa = textoAlternativa;
    }

    public boolean isCorretaOuErrada() {
        return corretaOuErrada;
    }

    //true é correta
    //false é incorreta
    public void setCorretaOuErrada(boolean corretaOuErrada) {
        this.corretaOuErrada = corretaOuErrada;
    }
}
