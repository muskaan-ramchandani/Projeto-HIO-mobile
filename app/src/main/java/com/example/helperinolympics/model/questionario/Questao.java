package com.example.helperinolympics.model.questionario;

import java.util.ArrayList;

public class Questao {

    //id é o número da pergunta pelo padrão de cadastro auto incremento do banco
    private int id, idQuestionarioPertencente;
    private String txtPergunta, explicacaoResposta;

    public Questao(){}

    public Questao(int id, int idQuestionarioPertencente, String txtPergunta, String explicacaoResposta) {
        setId(id);
        setIdQuestionarioPertencente(idQuestionarioPertencente);
        setTxtPergunta(txtPergunta);
        setExplicacaoResposta(explicacaoResposta);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdQuestionarioPertencente() {
        return idQuestionarioPertencente;
    }

    public void setIdQuestionarioPertencente(int idQuestionarioPertencente) {
        this.idQuestionarioPertencente = idQuestionarioPertencente;
    }

    public String getTxtPergunta() {
        return txtPergunta;
    }

    public void setTxtPergunta(String txtPergunta) {
        this.txtPergunta = txtPergunta;
    }

    public String getExplicacaoResposta() {
        return explicacaoResposta;
    }

    public void setExplicacaoResposta(String explicacaoResposta) {
        this.explicacaoResposta = explicacaoResposta;
    }
}
