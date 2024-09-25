package com.example.helperinolympics.materiais;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterAlternativasQuestionario;
import com.example.helperinolympics.databinding.FragmentQuestionarioBinding;
import com.example.helperinolympics.model.questionario.Alternativas;
import com.example.helperinolympics.model.questionario.Questao;

import java.util.ArrayList;

public class FragmentPerguntaRespostasQuestionario  extends Fragment {
    private AdapterAlternativasQuestionario adapter;

    private ArrayList<Alternativas> listaAlternativas;
    private FragmentQuestionarioBinding binding;
    private Questao questao;

    private int idQuestionarioPertencente, idQuestaoPertencente;
    private Context context;

    public FragmentPerguntaRespostasQuestionario(Questao questao, int idQuestionarioPertencente, int idQuestaoPertencente, Context context){
        this.questao = questao;
        this.idQuestionarioPertencente = idQuestionarioPertencente;
        this.idQuestaoPertencente= idQuestaoPertencente;
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQuestionarioBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView pergunta = binding.txtPerguntaQuestionario;
        String valorPergunta= "<b>Pergunta " +questao.getId()+"</b><br>"+questao.getTxtPergunta();
        pergunta.setText(Html.fromHtml(valorPergunta, Html.FROM_HTML_MODE_COMPACT));



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Libera o binding quando a view é destruída
    }


    public void configurarRecyclerAlternativas(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerAlternativas.setLayoutManager(layoutManager);
        binding.recyclerAlternativas.setHasFixedSize(true);

//        Integer idConteudo = conteudo.getId();
//
//        if (idConteudo != null) {
//            new FlashcardsDownload().execute(idConteudo);
//        } else {
//            Log.d("ERRO_ID_CONTEUDO", "O id do conteúdo está nulo");
//        }

        adapter=new AdapterAlternativasQuestionario(listaAlternativas);
        binding.recyclerAlternativas.setAdapter(adapter);
    }

}
