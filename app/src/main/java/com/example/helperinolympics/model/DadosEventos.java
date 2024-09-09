package com.example.helperinolympics.model;

import java.util.Date;

public class DadosEventos {

    private int id;
    private Date data;
    private String olimpiadaRelacionada, tipoEvento, horario, link;

    public DadosEventos(int id, Date data, String olimpiadaRelacionada, String tipoEvento, String horario, String link) {
        setId(id);
        setData(data);
        setOlimpiadaRelacionada(olimpiadaRelacionada);
        setTipoEvento(tipoEvento);
        setHorario(horario);
        setLink(link);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getOlimpiadaRelacionada() {
        return olimpiadaRelacionada;
    }

    public void setOlimpiadaRelacionada(String olimpiadaRelacionada) {
        this.olimpiadaRelacionada = olimpiadaRelacionada;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
