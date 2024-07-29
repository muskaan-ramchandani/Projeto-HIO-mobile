package com.example.helperinolympics.model;

public class DadosProvasAnteriores {

    private int id, anoProva, fase;
    private boolean estado;
    private  String userProf;
    //fazer um pro pdf


    public DadosProvasAnteriores(int id, int anoProva, int fase, boolean estado, String userProf) {
        setId(id);
        setAnoProva(anoProva);
        setFase(fase);
        setEstado(estado);
        setUserProf(userProf);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnoProva() {
        return anoProva;
    }

    public void setAnoProva(int anoProva) {
        this.anoProva = anoProva;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getUserProf() {
        return userProf;
    }

    public void setUserProf(String userProf) {
        this.userProf = userProf;
    }
}
