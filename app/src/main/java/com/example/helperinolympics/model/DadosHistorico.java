package com.example.helperinolympics.model;

public class DadosHistorico {
    private int idItem;
    private String tipoItem;

    public DadosHistorico(int idItem, String tipoItem) {
        setIdItem(idItem);
        setTipoItem(tipoItem);
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int id) {
        this.idItem = id;
    }

    public String getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(String tipoItem) {
        this.tipoItem = tipoItem;
    }
}
