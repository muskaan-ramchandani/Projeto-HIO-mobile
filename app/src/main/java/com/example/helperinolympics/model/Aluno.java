package com.example.helperinolympics.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class Aluno implements Parcelable {
    private String nomeCompleto, nomeUsuario, email, senha;
    private Bitmap fotoPerfil;

    public Aluno(){}

    public Aluno(String nomeCompleto, String nomeUsuario, String email, String senha){
        setNomeCompleto(nomeCompleto);
        setNomeUsuario(nomeUsuario);
        setEmail(email);
        setSenha(senha);
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Bitmap getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Bitmap fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    protected Aluno(Parcel in) {
        nomeCompleto = in.readString();
        nomeUsuario = in.readString();
        email = in.readString();
        senha = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomeCompleto);
        dest.writeString(nomeUsuario);
        dest.writeString(email);
        dest.writeString(senha);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Aluno> CREATOR = new Parcelable.Creator<Aluno>() {
        @Override
        public Aluno createFromParcel(Parcel in) {
            return new Aluno(in);
        }

        @Override
        public Aluno[] newArray(int size) {
            return new Aluno[size];
        }
    };
}