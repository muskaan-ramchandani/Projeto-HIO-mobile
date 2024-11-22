package com.example.helperinolympics.model;

import java.sql.Time;
import java.util.Date;

public class Eventos {

    private int id;
    private Date dataOcorrencia;
    private Time horarioComeco, horarioFim;
    private String olimpiadaRelacionada, tipo, link, cor;

    //envolve tudo pois apenas pega do banco
    public Eventos(int id, Date dataOcorrencia, Time horarioComeco, Time horarioFim, String olimpiadaRelacionada, String tipo, String link, String cor) {
        this.id = id;
        this.dataOcorrencia = dataOcorrencia;
        this.horarioComeco = horarioComeco;
        this.horarioFim = horarioFim;
        this.olimpiadaRelacionada = olimpiadaRelacionada;
        this.tipo = tipo;
        this.link = link;
        this.cor = cor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(Date dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public Time getHorarioComeco() {
        return horarioComeco;
    }

    public void setHorarioComeco(Time horarioComeco) {
        this.horarioComeco = horarioComeco;
    }

    public Time getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Time horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getOlimpiadaRelacionada() {
        return olimpiadaRelacionada;
    }

    public void setOlimpiadaRelacionada(String olimpiadaRelacionada) {
        this.olimpiadaRelacionada = olimpiadaRelacionada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
