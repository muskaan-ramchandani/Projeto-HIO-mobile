package com.example.helperinolympics.model.historicos_acesso;

public class DadosHistoricoFlashcards {
    private int idItem;

    public DadosHistoricoFlashcards(int idItem) {
        setIdItem(idItem);
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int id) {
        this.idItem = id;
    }
}
