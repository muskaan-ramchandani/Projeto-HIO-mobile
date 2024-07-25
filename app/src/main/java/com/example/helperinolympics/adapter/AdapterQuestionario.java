package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosQuestionario;

import java.util.List;

public class AdapterQuestionario extends RecyclerView.Adapter<AdapterQuestionario.QuestionarioViewHolder> {
    private List<DadosQuestionario> listaQuestionario;

    public AdapterQuestionario(List<DadosQuestionario> listaQuestionario) {
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
        DadosQuestionario questionario = listaQuestionario.get(position);
        holder.conteudo.setText(questionario.getTemaPertencente());
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
        }
    }
}
