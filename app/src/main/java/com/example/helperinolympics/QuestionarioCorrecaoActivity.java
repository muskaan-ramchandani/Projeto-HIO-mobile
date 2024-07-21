package com.example.helperinolympics;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterCorrecao;
import com.example.helperinolympics.model.DadosCorrecao;

import java.util.ArrayList;
import java.util.List;


public class QuestionarioCorrecaoActivity extends Activity {

    RecyclerView rVListaCorrecao;
    AdapterCorrecao correcaoAdapter;
    ImageView hipoTristeOUFeliz;
    TextView txtViewNumeroCertas, txtViewQuestoesTotais;
    int qntdTotal, qntdAcertos, metadeValor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_correcao);


        hipoTristeOUFeliz = findViewById(R.id.imgHipoTristeOuFeliz);
        txtViewNumeroCertas= findViewById(R.id.txtNumeroQuestaoCertas);
        txtViewQuestoesTotais= findViewById(R.id.txtQuestoesTotais);


        qntdTotal= 20;
        metadeValor= qntdTotal/2;
        qntdAcertos=Integer.parseInt(txtViewNumeroCertas.getText().toString());

        if(qntdAcertos>metadeValor){
            hipoTristeOUFeliz.setImageResource(R.drawable.hipoalegredeolhosabertos);
        }else if(qntdAcertos<metadeValor){
            hipoTristeOUFeliz.setImageResource(R.drawable.hipoemo);
        }

        listaCorrecao();
    }

    public void listaCorrecao(){
        rVListaCorrecao = findViewById(R.id.recyclerViewListaCorrecao);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rVListaCorrecao.setLayoutManager(layoutManager);
        rVListaCorrecao.setHasFixedSize(true);

        List<DadosCorrecao> listaCorrecao = new ArrayList<>();
        correcaoAdapter = new AdapterCorrecao(listaCorrecao);
        rVListaCorrecao.setAdapter(correcaoAdapter);


        //DADOS PARA TESTE
        DadosCorrecao dados1 = new DadosCorrecao("Pergunta: Para quê serve o uso da estrutura if/else?",
                "Alternativa correta: Serve para avaliar uma expressão como sendo verdadeira ou falsa e, de acordo com o resultado dessa verificação, executar uma ou outra ação.",
                "Explicação: A estrutura if/else é uma construção de controle de fluxo fundamental em muitas linguagens de programação. Quando um programa chega a um bloco if/else, ele avalia a condição dentro do parêntese após o if. Se a condição for verdadeira, o programa executa o bloco de código imediatamente seguinte ao if. Se a condição for falsa, e houver uma cláusula else, o programa executa o bloco de código imediatamente seguinte ao else.\n" +
                        "Isso permite que o programa tome decisões dinâmicas e execute diferentes caminhos de código com base nas condições em tempo de execução.");

        listaCorrecao.add(dados1);

        DadosCorrecao dados2 = new DadosCorrecao("Qual é a finalidade da instrução switch em Java, e como ela difere da instrução if-else?",
                "A instrução switch é usada para executar diferentes partes de código com base no valor de uma expressão, enquanto if-else é usado apenas para verificar se uma condição é verdadeira ou falsa.",
                "A instrução switch é usada para executar diferentes partes de código com base no valor de uma expressão específica. Ela permite que um programa execute diferentes blocos de código de acordo com o valor de uma variável, utilizando a estrutura case para definir os diferentes caminhos.\n" +
                        "\n" +
                        "A diferença principal entre switch e if-else é que o switch é mais adequado para situações em que você precisa comparar uma única variável contra várias constantes, tornando o código mais legível e organizado. A instrução if-else é mais flexível, pois pode avaliar expressões booleanas complexas, mas pode se tornar menos legível quando há muitas condições a serem verificadas. A resposta B está incorreta porque if-else não está limitado a duas opções; você pode encadear vários else if. A resposta C está incorreta porque switch pode ser usado com tipos de dados int, char, byte, short, String, e enumeradores. A resposta D está incorreta porque a eficiência depende do caso de uso específico e da implementação subjacente da JVM.");

        listaCorrecao.add(dados2);
    }
}
