package com.example.helperinolympics.model.questionario;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Questao implements Parcelable {

    //id é o número da pergunta pelo padrão de cadastro auto incremento do banco
    private int id, idQuestionarioPertencente;
    private String txtPergunta, explicacaoResposta;

    public Questao(){}

    public Questao(int id, int idQuestionarioPertencente, String txtPergunta, String explicacaoResposta) {
        setId(id);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setTxtPergunta(txtPergunta);
        setExplicacaoResposta(explicacaoResposta);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestionarioPertencente() {
        return idQuestionarioPertencente;
    }

    public void setIdQuestionarioPertencente(int idQuestionarioPertencente) {
        this.idQuestionarioPertencente = idQuestionarioPertencente;
    }

    public String getTxtPergunta() {
        return txtPergunta;
    }

    public void setTxtPergunta(String txtPergunta) {
        this.txtPergunta = txtPergunta;
    }

    public String getExplicacaoResposta() {
        return explicacaoResposta;
    }

    public void setExplicacaoResposta(String explicacaoResposta) {
        this.explicacaoResposta = explicacaoResposta;
    }

    protected Questao(Parcel in) {
        id = in.readInt();
        idQuestionarioPertencente = in.readInt();
        txtPergunta = in.readString();
        explicacaoResposta = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idQuestionarioPertencente);
        dest.writeString(txtPergunta);
        dest.writeString(explicacaoResposta);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Questao> CREATOR = new Parcelable.Creator<Questao>() {
        @Override
        public Questao createFromParcel(Parcel in) {
            return new Questao(in);
        }

        @Override
        public Questao[] newArray(int size) {
            return new Questao[size];
        }
    };
}
