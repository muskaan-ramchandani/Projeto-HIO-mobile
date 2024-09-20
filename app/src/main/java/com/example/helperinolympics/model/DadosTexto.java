package com.example.helperinolympics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DadosTexto  implements Parcelable{
    private String titulo, professorCadastrou, texto;
    private int id, conteudoPertencente;

    public DadosTexto(){}

    public DadosTexto(int id, String titulo, String professorCadastrou, int conteudoPertencente, String texto) {
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

    protected DadosTexto(Parcel in) {
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

    public static final Parcelable.Creator<DadosTexto> CREATOR = new Parcelable.Creator<DadosTexto>() {
        @Override
        public DadosTexto createFromParcel(Parcel in) {
            return new DadosTexto(in);
        }

        @Override
        public DadosTexto[] newArray(int size) {
            return new DadosTexto[size];
        }
    };
}
