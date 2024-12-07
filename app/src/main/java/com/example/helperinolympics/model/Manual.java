package com.example.helperinolympics.model;

public class Manual {
    private int id;
    private byte[] arquivoPdfBytes;

    public Manual(){}

    public Manual(int id, byte[] arquivoPdfBytes) {
        setId(id);
        setArquivoPdfBytes(arquivoPdfBytes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getArquivoPdfBytes() {
        return arquivoPdfBytes;
    }

    public void setArquivoPdfBytes(byte[] arquivoPdfBytes) {
        this.arquivoPdfBytes = arquivoPdfBytes;
    }
}
