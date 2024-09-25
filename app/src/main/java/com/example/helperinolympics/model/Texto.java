package com.example.helperinolympics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Texto implements Parcelable{
    private String titulo, professorCadastrou, texto;
    private int id, conteudoPertencente;

    public Texto(){}

    public Texto(int id, String titulo, String professorCadastrou, int conteudoPertencente, String texto) {
        setId(id);
        setTitulo(titulo);
        setProfessorCadastrou(professorCadastrou);
        setConteudoPertencente(conteudoPertencente);
        setTexto(texto);
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

    public int getConteudoPertencente() {
        return conteudoPertencente;
    }

    public void setConteudoPertencente(int conteudoPertencente) {
        this.conteudoPertencente = conteudoPertencente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    protected Texto(Parcel in) {
        id = in.readInt();
        conteudoPertencente = in.readInt();
        titulo = in.readString();
        professorCadastrou = in.readString();
        texto = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(conteudoPertencente);
        dest.writeString(titulo);
        dest.writeString(professorCadastrou);
        dest.writeString(texto);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Texto> CREATOR = new Parcelable.Creator<Texto>() {
        @Override
        public Texto createFromParcel(Parcel in) {
            return new Texto(in);
        }

        @Override
        public Texto[] newArray(int size) {
            return new Texto[size];
        }
    };
}
