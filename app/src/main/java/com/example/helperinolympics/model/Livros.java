package com.example.helperinolympics.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class Livros implements Parcelable {

    int id;
    Bitmap capa;
    String titulo, autor, edicao, isbn, siglaOlimpiadaPertencente;
    Date dataPublicacao;

    public Livros(){}

    public Livros(int id, Bitmap capa, String isbn, String titulo, String autor, String edicao, String siglaOlimpiadaPertencente, Date dataPublicacao) {
        setId(id);
        setCapa(capa);
        setIsbn(isbn);
        setTitulo(titulo);
        setAutor(autor);
        setEdicao(edicao);
        setSiglaOlimpiadaPertencente(siglaOlimpiadaPertencente);
        setDataPublicacao(dataPublicacao);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getCapa() {
        return capa;
    }

    public void setCapa(Bitmap capa) {
        this.capa = capa;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getSiglaOlimpiadaPertencente() {
        return siglaOlimpiadaPertencente;
    }

    public void setSiglaOlimpiadaPertencente(String siglaOlimpiadaPertencente) {
        this.siglaOlimpiadaPertencente = siglaOlimpiadaPertencente;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    protected Livros(Parcel in) {
        id = in.readInt();
        isbn = in.readString();
        titulo = in.readString();
        autor = in.readString();
        edicao = in.readString();
        siglaOlimpiadaPertencente = in.readString();

        byte[] bitmapBytes = in.createByteArray();
        if (bitmapBytes != null) {
            capa = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
        } else {
            capa = null;
        }

        long dateMillis = in.readLong();
        if (dateMillis != -1) {
            dataPublicacao = new Date(dateMillis);
        } else {
            dataPublicacao = null;  // Se o valor for -1, a data é nula
        }


    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(isbn);
        dest.writeString(titulo);
        dest.writeString(autor);
        dest.writeString(edicao);
        dest.writeString(siglaOlimpiadaPertencente);

        if (capa != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            capa.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bitmapBytes = byteArrayOutputStream.toByteArray();
            dest.writeByteArray(bitmapBytes);
        } else {
            dest.writeByteArray(null);
        }

        if (dataPublicacao != null) {
            dest.writeLong(dataPublicacao.getTime());
        } else {
            dest.writeLong(-1);
        }
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
