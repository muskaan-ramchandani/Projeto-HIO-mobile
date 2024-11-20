package com.example.helperinolympics.model.forum;

import android.graphics.Bitmap;

import java.util.Date;

public class RespostasForum {
    private int id, idPergunta;
    private String userProf, resposta;
    private Bitmap fotoPerfil;

    public RespostasForum(int id, int idPergunta, String userProf, String resposta) {
        setId(id);
        setIdPergunta(idPergunta);
        setUserProf(userProf);
        setResposta(resposta);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(int idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getUserProf() {
        return userProf;
    }

    public void setUserProf(String userProf) {
        this.userProf = userProf;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Bitmap getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(Bitmap fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}