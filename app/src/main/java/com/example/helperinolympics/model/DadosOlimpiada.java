package com.example.helperinolympics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DadosOlimpiada implements Parcelable {
    int iconeOlimp;
    String nome, sigla, cor;
    private boolean isSelected;

    public DadosOlimpiada(){}

    public DadosOlimpiada(int iconeOlimp, String nome, String sigla, String cor) {
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
        //se cor é diferente da condição "cor diferente de qualquer opção"
        if(!(cor!="Azul"&&cor!="Ciano"&&cor!="Laranja"&&cor!="Rosa")){
            this.cor = cor;
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected DadosOlimpiada(Parcel in) {
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

    public static final Creator<DadosOlimpiada> CREATOR = new Creator<DadosOlimpiada>() {
        @Override
        public DadosOlimpiada createFromParcel(Parcel in) {
            return new DadosOlimpiada(in);
        }

        @Override
        public DadosOlimpiada[] newArray(int size) {
            return new DadosOlimpiada[size];
        }
    };
}