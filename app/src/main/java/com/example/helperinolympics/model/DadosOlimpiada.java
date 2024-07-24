package com.example.helperinolympics.model;

public class DadosOlimpiada {
    int iconeOlimp;
    String nome, sigla, cor;

    public DadosOlimpiada(int iconeOlimp, String nome, String sigla, String cor) {
        setIconeOlimp(iconeOlimp);
        setNome(nome);
        setSigla(sigla);
        setCor(cor);
    }

    public int getIconeOlimp() {
        return this.iconeOlimp;
    }

    public void setIconeOlimp(int iconeOlimp) {
        this.iconeOlimp = iconeOlimp;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        //se cor é diferente da condição "cor diferente de qualquer opção"
        if(!(cor!="Azul"&&cor!="Ciano"&&cor!="Laranja"&&cor!="Rosa")){
            this.cor = cor;
        }
    }
}
