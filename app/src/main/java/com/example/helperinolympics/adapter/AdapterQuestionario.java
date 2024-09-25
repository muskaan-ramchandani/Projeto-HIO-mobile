package com.example.helperinolympics.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.questionario.Questionario;
import com.example.helperinolympics.telas_de_acesso.AcessoQuestionarioActivity;

import java.util.List;

public class AdapterQuestionario extends RecyclerView.Adapter<AdapterQuestionario.QuestionarioViewHolder> {
    private List<Questionario> listaQuestionario;

    public AdapterQuestionario(List<Questionario> listaQuestionario) {
        this.listaQuestionario = listaQuestionario;
    }

    @NonNull
    @Override
    public QuestionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaQuestionario = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new QuestionarioViewHolder(viewItemListaQuestionario);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionarioViewHolder holder, int position) {
        Questionario questionario = listaQuestionario.get(position);
        holder.conteudo.setText(questionario.getTitulo());
        holder.userProf.setText(questionario.getProfessorCadastrou());
    }

    @Override
    public int getItemCount() {
        return listaQuestionario.size();
    }

    public static class QuestionarioViewHolder extends RecyclerView.ViewHolder {
        TextView conteudo, userProf;

        public QuestionarioViewHolder(@NonNull View itemView) {
            super(itemView);
            conteudo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AcessoQuestionarioActivity.class);
                    context.startActivity(intent);
                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }
    }

    public void atualizarOpcoes(List<Questionario> quests){
        this.listaQuestionario.clear();
        this.listaQuestionario.addAll(quests);
        this.notifyDataSetChanged();
    }
}
