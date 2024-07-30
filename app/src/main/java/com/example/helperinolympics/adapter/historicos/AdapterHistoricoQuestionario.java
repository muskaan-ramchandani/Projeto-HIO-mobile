package com.example.helperinolympics.adapter.historicos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosQuestionario;
import com.example.helperinolympics.telas_de_acesso.AcessoQuestionarioActivity;

import java.util.List;

public class AdapterHistoricoQuestionario extends RecyclerView.Adapter<AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder> {
    private List<DadosQuestionario> listaQuestionario;

    // Constructor to initialize the list
    public AdapterHistoricoQuestionario(List<DadosQuestionario> listaQuestionario) {
        this.listaQuestionario = listaQuestionario;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaHistoricoQuestionario = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder(viewItemListaHistoricoQuestionario);
    }


    public void onBindViewHolder(@NonNull AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder holder, int position) {
        DadosQuestionario quest = listaQuestionario.get(position);


        holder.conteudo.setText(quest.getTitulo());
        holder.userProf.setText(quest.getProfessorCadastrou());
    }


    @Override
    public int getItemCount() {
        return listaQuestionario.size();
    }


    public class QuestionarioHistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView conteudo, userProf;

        public QuestionarioHistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            conteudo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AcessoQuestionarioActivity.class);
                    context.startActivity(intent);
                }
            });

        }

    }

}
