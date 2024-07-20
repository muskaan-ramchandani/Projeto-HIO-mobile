package com.example.helperinolympics.model;

public class DadosCorrecao {
    private String pergunta, alternativaCorreta, explicacao;

    public DadosCorrecao(String pergunta, String alternativaCorreta, String explicacao) {
        setPergunta(pergunta);
        setAlternativaCorreta(alternativaCorreta);
        setExplicacao(explicacao);
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getAlternativaCorreta() {
        return alternativaCorreta;
    }

    public void setAlternativaCorreta(String alternativaCorreta) {
        this.alternativaCorreta = alternativaCorreta;
    }

    public String getExplicacao() {
        return explicacao;
    }

    public void setExplicacao(String explicacao) {
        this.explicacao = explicacao;
    }
}
