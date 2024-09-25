package com.example.helperinolympics.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.questionario.Questionario;
import com.example.helperinolympics.telas_de_acesso.AcessoQuestionarioActivity;

import java.util.List;

public class AdapterQuestionario extends RecyclerView.Adapter<AdapterQuestionario.QuestionarioViewHolder> {
    private List<Questionario> listaQuestionario;
    private static Aluno alunoCadastrado;
    private static Conteudo conteudo;
    private static String siglaOlimpiada;

    public AdapterQuestionario(List<Questionario> listaQuestionario, Aluno alunoCadastrado, Conteudo conteudo, String siglaOlimpiada) {
        this.listaQuestionario = listaQuestionario;
        this.alunoCadastrado = alunoCadastrado;
        this.conteudo = conteudo;
        this.siglaOlimpiada = siglaOlimpiada;
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
        holder.conteudoTxt.setText(questionario.getTitulo());
        holder.userProf.setText(questionario.getProfessorCadastrou());
        holder.quest= questionario;
    }

    @Override
    public int getItemCount() {
        return listaQuestionario.size();
    }

    public static class QuestionarioViewHolder extends RecyclerView.ViewHolder {
        TextView conteudoTxt, userProf;
        Questionario quest;

        public QuestionarioViewHolder(@NonNull View itemView) {
            super(itemView);
            conteudoTxt = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AcessoQuestionarioActivity.class);

                    intent.putExtra("questionario", quest);
                    intent.putExtra("alunoCadastrado", alunoCadastrado);
                    intent.putExtra("conteudo", conteudo);
                    intent.putExtra("olimpiada", siglaOlimpiada);

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
