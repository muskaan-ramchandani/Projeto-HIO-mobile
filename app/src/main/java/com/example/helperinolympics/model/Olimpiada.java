package com.example.helperinolympics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Olimpiada implements Parcelable {
    int iconeOlimp;
    String nome, sigla, cor;
    private boolean isSelected;

    public Olimpiada(){}

    public Olimpiada(int iconeOlimp, String nome, String sigla, String cor) {
        setIconeOlimp(iconeOlimp);
        setNome(nome);
        setSigla(sigla);
        setCor(cor);
        this.isSelected = false; //inicializando
    }

    public int getIconeOlimp() {
        return this.iconeOlimp;
    }

    public void setIconeOlimp(int iconeOlimp) {
        this.iconeOlimp = iconeOlimp;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected Olimpiada(Parcel in) {
        iconeOlimp = in.readInt();
        nome = in.readString();
        sigla = in.readString();
        cor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(iconeOlimp);
        dest.writeString(nome);
        dest.writeString(sigla);
        dest.writeString(cor);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Olimpiada> CREATOR = new Creator<Olimpiada>() {
        @Override
        public Olimpiada createFromParcel(Parcel in) {
            return new Olimpiada(in);
        }

        @Override
        public Olimpiada[] newArray(int size) {
            return new Olimpiada[size];
        }
    };
}