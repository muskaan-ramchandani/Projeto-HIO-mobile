package com.example.helperinolympics.model.forum;

import java.util.ArrayList;

public class OlimpiadaForum {

    private String siglaOlimpiada, corOlimp;
    private int qntdPerguntasRelacionadas;
    private ArrayList<Integer> idsPerguntasForum;

    public OlimpiadaForum(String siglaOlimpiada, String corOlimp, int qntdPerguntasRelacionadas){
        setSiglaOlimpiada(siglaOlimpiada);
        setCorOlimp(corOlimp);
        setQntdPerguntasRelacionadas(qntdPerguntasRelacionadas);
    }

    public String getSiglaOlimpiada() {
        return siglaOlimpiada;
    }

    public void setSiglaOlimpiada(String siglaOlimpiada) {
        this.siglaOlimpiada = siglaOlimpiada;
    }

    public String getCorOlimp() {
        return corOlimp;
    }

    public void setCorOlimp(String corOlimp) {
        this.corOlimp = corOlimp;
    }

    public int getQntdPerguntasRelacionadas() {
        return qntdPerguntasRelacionadas;
    }

    public void setQntdPerguntasRelacionadas(int qntdPerguntasRelacionadas) {
        this.qntdPerguntasRelacionadas = qntdPerguntasRelacionadas;
    }

    public ArrayList<Integer> getIdsPerguntasForum() {
        return idsPerguntasForum;
    }

    public void setIdsPerguntasForum(ArrayList<Integer> idsPerguntasForum) {
        this.idsPerguntasForum = idsPerguntasForum;
    }
}
