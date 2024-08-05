package com.example.helperinolympics.model;

public class DadosErros {
    private String olimpiadaQuestaoErrada;
    private String assuntoQuestaoErrada;
    private String topicoDaQuestaoErrada;
    private String profQuestaoErrada;
    private String perguntaQuestaoErrada;
    private String questaoMarcadaErrada;
    private String questaoCorrecaoDaErrada;


    public DadosErros(String olimpiadaQuestaoErrada, String assuntoQuestaoErrada, String topicoDaQuestaoErrada, String profQuestaoErrada,String perguntaQuestaoErrada, String questaoMarcadaErrada, String questaoCorrecaoDaErrada) {
        setOlimpiadaQuestaoErrada(olimpiadaQuestaoErrada);
        setAssuntoQuestaoErrada(assuntoQuestaoErrada);
        setTopicoDaQuestaoErrada(topicoDaQuestaoErrada);
        setProfQuestaoErrada(profQuestaoErrada);
        setPerguntaQuestaoErrada(perguntaQuestaoErrada);
        setQuestaoMarcadaErrada(questaoMarcadaErrada);
        setQuestaoCorrecaoDaErrada(questaoCorrecaoDaErrada);
    }

    public String getOlimpiadaQuestaoErrada() {
        return olimpiadaQuestaoErrada;
    }

    public void setOlimpiadaQuestaoErrada(String olimpiadaQuestaoErrada) {
        this.olimpiadaQuestaoErrada = olimpiadaQuestaoErrada;
    }

    public String getAssuntoQuestaoErrada() {
        return assuntoQuestaoErrada;
    }

    public void setAssuntoQuestaoErrada(String assuntoQuestaoErrada) {
        this.assuntoQuestaoErrada = assuntoQuestaoErrada;
    }

    public String getTopicoDaQuestaoErrada() {
        return topicoDaQuestaoErrada;
    }

    public void setTopicoDaQuestaoErrada(String topicoDaQuestaoErrada) {
        this.topicoDaQuestaoErrada = topicoDaQuestaoErrada;
    }

    public String getProfQuestaoErrada() {
        return profQuestaoErrada;
    }

    public void setProfQuestaoErrada(String profQuestaoErrada) {
        this.profQuestaoErrada = profQuestaoErrada;
    }

    public String getPerguntaQuestaoErrada() {
        return perguntaQuestaoErrada;
    }

    public void setPerguntaQuestaoErrada(String perguntaQuestaoErrada) {
        this.perguntaQuestaoErrada = perguntaQuestaoErrada;
    }

    public String getQuestaoMarcadaErrada() {
        return questaoMarcadaErrada;
    }

    public void setQuestaoMarcadaErrada(String questaoMarcadaErrada) {
        this.questaoMarcadaErrada = questaoMarcadaErrada;
    }

    public String getQuestaoCorrecaoDaErrada() {
        return questaoCorrecaoDaErrada;
    }

    public void setQuestaoCorrecaoDaErrada(String questaoCorrecaoDaErrada) {
        this.questaoCorrecaoDaErrada = questaoCorrecaoDaErrada;
    }
}
