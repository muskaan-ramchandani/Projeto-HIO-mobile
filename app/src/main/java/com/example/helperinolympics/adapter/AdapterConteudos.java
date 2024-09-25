package com.example.helperinolympics.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.materiais.TextoActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;

import java.util.List;

public class AdapterConteudos extends RecyclerView.Adapter<AdapterConteudos.ConteudosViewHolder>{
    List<Conteudo> listaConteudos;
    Aluno alunoCadastrado;
    String siglaOlimp = null;

    public AdapterConteudos(List<Conteudo> conteudos, Aluno alunoCadastrado, String siglaOlimp){
        this.listaConteudos=conteudos;
        this.alunoCadastrado = alunoCadastrado;
        this.siglaOlimp = siglaOlimp;
    }

    public AdapterConteudos.ConteudosViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaConteudos= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_conteudo_olimpiada, parent, false);
        return new AdapterConteudos.ConteudosViewHolder(viewItemListaConteudos, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterConteudos.ConteudosViewHolder holder, int position) {
        Conteudo conteudo = listaConteudos.get(position);

        String valorTitulo = conteudo.getTituloConteudo();
        String valorSubtitulo = conteudo.getSubtituloConteudo();
        String valorTituloSubTitulo = "<b>" + valorTitulo + ": </b> " + valorSubtitulo;

        holder.btnConteudo.setText(Html.fromHtml(valorTituloSubTitulo, Html.FROM_HTML_MODE_COMPACT)); //manter parte do texto em negrito


        String valorCor = conteudo.getCorFundo();
        if (valorCor.equals("Azul")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
        } else if (valorCor.equals("Ciano")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
        } else if (valorCor.equals("Laranja")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
        } else if (valorCor.equals("Rosa")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
        }

        holder.btnConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, TextoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimp);
                context.startActivity(intent);

                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });


    }

    public class ConteudosViewHolder extends RecyclerView.ViewHolder{
        AppCompatButton btnConteudo;

        public ConteudosViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            btnConteudo = itemView.findViewById(R.id.btnConteudoOlimpiada);


        }
    }


    public int getItemCount(){return listaConteudos.size();}

    public void atualizarOpcoes(List<Conteudo> conteudos){
        this.listaConteudos.clear();
        this.listaConteudos.addAll(conteudos);
        this.notifyDataSetChanged();
    }
}