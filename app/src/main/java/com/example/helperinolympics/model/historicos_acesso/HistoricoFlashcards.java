package com.example.helperinolympics.model.historicos_acesso;

public class HistoricoFlashcards {
    private int idItem;

    public HistoricoFlashcards(int idItem) {
        setIdItem(idItem);
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int id) {
        this.idItem = id;
    }
}
