package com.example.helperinolympics.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Ranking implements Parcelable {

    private int posicao, qntdPontos;
    private Bitmap fotoPerfil;
    private String user, email;

    public Ranking(){}

    public Ranking(int posicao, Bitmap fotoPerfil, int qntdPontos, String user, String email) {
        setPosicao(posicao);
        setFotoPerfil(fotoPerfil);
        setQntdPontos(qntdPontos);
        setUser(user);
        setEmail(email);
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public Bitmap getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Bitmap fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public int getQntdPontos() {
        return qntdPontos;
    }

    public void setQntdPontos(int qntdPontos) {
        this.qntdPontos = qntdPontos;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected Ranking(Parcel in) {
        posicao = in.readInt();
        qntdPontos = in.readInt();
        user = in.readString();
        email = in.readString();

        byte[] bitmapBytes = in.createByteArray();
        if (bitmapBytes != null) {
            fotoPerfil = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        } else {
            fotoPerfil = null;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(posicao);
        dest.writeInt(qntdPontos);
        dest.writeString(user);
        dest.writeString(email);

        if (fotoPerfil != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            fotoPerfil.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bitmapBytes = byteArrayOutputStream.toByteArray();
            dest.writeByteArray(bitmapBytes);
        } else {
            dest.writeByteArray(null);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Ranking> CREATOR = new Parcelable.Creator<Ranking>() {
        @Override
        public Ranking createFromParcel(Parcel in) {
            return new Ranking(in);
        }

        @Override
        public Ranking[] newArray(int size) {
            return new Ranking[size];
        }
    };
}
