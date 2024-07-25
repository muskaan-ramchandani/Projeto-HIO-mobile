package com.example.helperinolympics.model;

public class DadosConteudo {

    int id;
    String tituloConteudo, subtituloConteudo, olimpiadaPertencente, corFundo;

    //id automatico no banco
    public DadosConteudo(int id, String tituloConteudo, String subtituloConteudo, String olimpiadaPertencente, String corFundo) {
        setId(id);
        setTituloConteudo(tituloConteudo);
        setSubtituloConteudo(subtituloConteudo);
        setOlimpiadaPertencente(olimpiadaPertencente);
        setCorFundo(corFundo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTituloConteudo() {
        return tituloConteudo;
    }

    public void setTituloConteudo(String tituloConteudo) {
        this.tituloConteudo = tituloConteudo;
    }

    public String getSubtituloConteudo() {
        return subtituloConteudo;
    }

    public void setSubtituloConteudo(String subtituloConteudo) {
        this.subtituloConteudo = subtituloConteudo;
    }

    public String getOlimpiadaPertencente() {
        return olimpiadaPertencente;
    }

    public void setOlimpiadaPertencente(String olimpiadaPertencente) {
        this.olimpiadaPertencente = olimpiadaPertencente;
    }

    public String getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(String corFundo) {
        this.corFundo = corFundo;
    }
}
