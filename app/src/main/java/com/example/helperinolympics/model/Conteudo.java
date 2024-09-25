package com.example.helperinolympics.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Conteudo implements Parcelable{

    int id;
    String tituloConteudo, subtituloConteudo, olimpiadaPertencente, corFundo;
    private boolean isPressed;

    public Conteudo(){}

    //id automatico no banco
    public Conteudo(int id, String tituloConteudo, String subtituloConteudo, String olimpiadaPertencente, String corFundo) {
        setId(id);
        setTituloConteudo(tituloConteudo);
        setSubtituloConteudo(subtituloConteudo);
        setOlimpiadaPertencente(olimpiadaPertencente);
        setCorFundo(corFundo);
        this.isPressed = false; //inicializando
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloConteudo() {
        return tituloConteudo;
    }

    public void setTituloConteudo(String tituloConteudo) {
        this.tituloConteudo = tituloConteudo;
    }

    public String getSubtituloConteudo() {
        return subtituloConteudo;
    }

    public void setSubtituloConteudo(String subtituloConteudo) {
        this.subtituloConteudo = subtituloConteudo;
    }

    public String getOlimpiadaPertencente() {
        return olimpiadaPertencente;
    }

    public void setOlimpiadaPertencente(String olimpiadaPertencente) {
        this.olimpiadaPertencente = olimpiadaPertencente;
    }

    public String getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(String corFundo) {
        this.corFundo = corFundo;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    protected Conteudo(Parcel in) {
        id = in.readInt();
        tituloConteudo = in.readString();
        subtituloConteudo = in.readString();
        olimpiadaPertencente = in.readString();
        corFundo = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(tituloConteudo);
        dest.writeString(subtituloConteudo);
        dest.writeString(olimpiadaPertencente);
        dest.writeString(corFundo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Conteudo> CREATOR = new Parcelable.Creator<Conteudo>() {
        @Override
        public Conteudo createFromParcel(Parcel in) {
            return new Conteudo(in);
        }

        @Override
        public Conteudo[] newArray(int size) {
            return new Conteudo[size];
        }
    };
}
