package com.example.helperinolympics.model;

public class DadosAcertos {
    private String olimpiadaQuestaoCerta;
    private String assuntoQuestaoCerta;
    private String topicoDaQuestaoCerta;
    private String profQuestaoCerta;
    private String perguntaQuestaoCerta;
    private String questaoMarcadaCerta;

    public DadosAcertos(String olimpiadaQuestaoCerta, String assuntoQuestaoCerta, String topicoDaQuestaoCerta, String profQuestaoCerta, String perguntaQuestaoCerta, String questaoMarcadaCerta) {
        setOlimpiadaQuestaoCerta(olimpiadaQuestaoCerta);
        setAssuntoQuestaoCerta(assuntoQuestaoCerta);
        setTopicoDaQuestaoCerta(topicoDaQuestaoCerta);
        setProfQuestaoCerta(profQuestaoCerta);
        setPerguntaQuestaoCerta(perguntaQuestaoCerta);
        setQuestaoMarcadaCerta(questaoMarcadaCerta);
    }

    public String getOlimpiadaQuestaoCerta() {
        return olimpiadaQuestaoCerta;
    }

    public void setOlimpiadaQuestaoCerta(String olimpiadaQuestaoCerta) {
        this.olimpiadaQuestaoCerta = olimpiadaQuestaoCerta;
    }

    public String getAssuntoQuestaoCerta() {
        return assuntoQuestaoCerta;
    }

    public void setAssuntoQuestaoCerta(String assuntoQuestaoCerta) {
        this.assuntoQuestaoCerta = assuntoQuestaoCerta;
    }

    public String getTopicoDaQuestaoCerta() {
        return topicoDaQuestaoCerta;
    }

    public void setTopicoDaQuestaoCerta(String topicoDaQuestaoCerta) {
        this.topicoDaQuestaoCerta = topicoDaQuestaoCerta;
    }

    public String getProfQuestaoCerta() {
        return profQuestaoCerta;
    }

    public void setProfQuestaoCerta(String profQuestaoCerta) {
        this.profQuestaoCerta = profQuestaoCerta;
    }

    public String getPerguntaQuestaoCerta() {
        return perguntaQuestaoCerta;
    }

    public void setPerguntaQuestaoCerta(String perguntaQuestaoCerta) {
        this.perguntaQuestaoCerta = perguntaQuestaoCerta;
    }

    public String getQuestaoMarcadaCerta() {
        return questaoMarcadaCerta;
    }

    public void setQuestaoMarcadaCerta(String questaoMarcadaCerta) {
        this.questaoMarcadaCerta = questaoMarcadaCerta;
    }
}
