package com.example.helperinolympics.model.questionario;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;


public class Questionario implements Parcelable {
    private int id, idConteudoPertencente;
    private String titulo, professorCadastrou;

    public Questionario(){}

    public Questionario(int id,String titulo, String professorCadastrou, int idConteudoPertencente) {
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

    protected Questionario(Parcel in) {
        id = in.readInt();
        titulo = in.readString();
        professorCadastrou = in.readString();
        idConteudoPertencente = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(titulo);
        dest.writeString(professorCadastrou);
        dest.writeInt(idConteudoPertencente);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Questionario> CREATOR = new Parcelable.Creator<Questionario>() {
        @Override
        public Questionario createFromParcel(Parcel in) {
            return new Questionario(in);
        }

        @Override
        public Questionario[] newArray(int size) {
            return new Questionario[size];
        }
    };
}
