package com.example.helperinolympics.model.historicos_acesso;

public class HistoricoQuestionario {
    private int idItem;

    public HistoricoQuestionario(int idItem) {
        setIdItem(idItem);
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int id) {
        this.idItem = id;
    }
}
