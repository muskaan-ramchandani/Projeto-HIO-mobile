package com.example.helperinolympics.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class DadosAluno implements Parcelable {
    private String nomeCompleto, nomeUsuario, email, senha;
    private File fotoPerfil;

    public DadosAluno(){}

    public DadosAluno(String nomeCompleto, String nomeUsuario, String email, String senha){
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

    public File getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(File fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    protected DadosAluno(Parcel in) {
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

    public static final Parcelable.Creator<DadosAluno> CREATOR = new Parcelable.Creator<DadosAluno>() {
        @Override
        public DadosAluno createFromParcel(Parcel in) {
            return new DadosAluno(in);
        }

        @Override
        public DadosAluno[] newArray(int size) {
            return new DadosAluno[size];
        }
    };
}