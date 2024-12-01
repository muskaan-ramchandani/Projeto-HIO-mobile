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

import com.example.helperinolympics.CadastraHistoricoAsynTask;
import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.Texto;
import com.example.helperinolympics.telas_de_acesso.AcessoTextoActivity;

import java.util.List;

public class AdapterTexto extends RecyclerView.Adapter<AdapterTexto.TextoViewHolder> {
    private List<Texto> listaTexto;
    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String olimpiadaPertencente;

    public AdapterTexto(List<Texto> listaTexto, Aluno alunoCadastrado, Conteudo conteudo, String olimpiadaPertencente) {
        this.listaTexto = listaTexto;
        this.alunoCadastrado= alunoCadastrado;
        this.conteudo = conteudo;
        this.olimpiadaPertencente = olimpiadaPertencente;
    }

    @NonNull
    @Override
    public TextoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaTexto = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new TextoViewHolder(viewItemListaTexto);
    }

    @Override
    public void onBindViewHolder(@NonNull TextoViewHolder holder, int position) {
        Texto texto = listaTexto.get(position);
        holder.id= texto.getId();
        holder.txt = texto;

        String valorUserProf = texto.getProfessorCadastrou();
        holder.userProf.setText(valorUserProf);

        String valorTitulo = texto.getTitulo();
        holder.titulo.setText(valorTitulo);

        holder.textoString=texto.getTexto();
    }

    @Override
    public int getItemCount() {
        return listaTexto.size();
    }

    public class TextoViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, userProf;
        String textoString;
        Texto txt;
        int id;

        public TextoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AcessoTextoActivity.class);
                    intent.putExtra("alunoCadastrado", alunoCadastrado);
                    intent.putExtra("conteudo", conteudo);
                    intent.putExtra("olimpiada", olimpiadaPertencente);
                    intent.putExtra("texto", txt);
                    context.startActivity(intent);

                    new CadastraHistoricoAsynTask().execute(alunoCadastrado.getEmail(), "Texto", String.valueOf(id));

                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }
    }

    public void atualizarOpcoes(List<Texto> textos){
        this.listaTexto.clear();
        this.listaTexto.addAll(textos);
        this.notifyDataSetChanged();
    }
}
