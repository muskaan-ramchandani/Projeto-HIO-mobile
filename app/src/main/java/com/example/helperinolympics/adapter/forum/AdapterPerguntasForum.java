package com.example.helperinolympics.adapter.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.PerguntasForum;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;


public class AdapterPerguntasForum extends RecyclerView.Adapter<AdapterPerguntasForum.PerguntasForumViewHolder>{ List<PerguntasForum> listaPerguntasForum;

    public AdapterPerguntasForum(List<PerguntasForum> perguntas){
        this.listaPerguntasForum=perguntas;
    }

    public AdapterPerguntasForum.PerguntasForumViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaPerguntas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_perguntas_forum, parent, false);
        return new AdapterPerguntasForum.PerguntasForumViewHolder(viewItemListaPerguntas, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterPerguntasForum.PerguntasForumViewHolder holder, int position) {
        PerguntasForum pergunta = listaPerguntasForum.get(position);

        holder.fotoPerfil.setImageResource(pergunta.getFotoPerfil());
        holder.titulo.setText(pergunta.getTitulo());
        holder.nomeDeUsuario.setText(pergunta.getNomeDeUsuario());

        Date dataPublicacao = pergunta.getDataPublicacao();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(dataPublicacao);

        holder.dataEOlimp.setText(dataFormatada + " • " + pergunta.getOlimpiada());

        holder.pergunta.setText(pergunta.getPergunta());
        int qntdRespostasInt = pergunta.getQntdRespostas();
        String qntdRespostasString = String.valueOf(qntdRespostasInt);

        if(qntdRespostasInt>0){
            holder.respostas.setText(qntdRespostasString + " respostas • Clique aqui para exibir");
        }else{
            holder.respostas.setText("0 respostas • A pergunta não foi respondida");
        }


    }

    public class PerguntasForumViewHolder extends RecyclerView.ViewHolder{
        ImageView fotoPerfil;
        TextView titulo, nomeDeUsuario, dataEOlimp, pergunta, respostas;


        public PerguntasForumViewHolder(@NonNull View itemView, final Context context){
            super(itemView);

            fotoPerfil=itemView.findViewById(R.id.fotoPerfilPergunta);
            titulo=itemView.findViewById(R.id.txtTituloPergunta);
            nomeDeUsuario=itemView.findViewById(R.id.txtNomeUser);
            dataEOlimp=itemView.findViewById(R.id.txtDataEOlimpiada);
            pergunta=itemView.findViewById(R.id.txtPergunta);
            respostas=itemView.findViewById(R.id.txtQntdRespostas);

            respostas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //exibir respostas
                }
            });
        }
    }


    public int getItemCount(){return listaPerguntasForum.size();}

    public void atualizarOpcoes(List<PerguntasForum> perguntas){
        this.listaPerguntasForum.clear();
        this.listaPerguntasForum.addAll(perguntas);
        this.notifyDataSetChanged();
    }
}
