package com.example.helperinolympics.adapter.forum;

import android.content.Context;
import android.graphics.Bitmap;
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

import de.hdodenhof.circleimageview.CircleImageView;


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

        Bitmap foto = pergunta.getFotoPerfil();
        if(foto==null){
            holder.fotoPerfil.setImageResource(R.drawable.iconeperfilsemfoto);
        }else{
            holder.fotoPerfil.setImageBitmap(foto);
        }

        holder.id= pergunta.getId();
        holder.titulo.setText(pergunta.getTitulo());
        holder.nomeDeUsuario.setText(pergunta.getNomeDeUsuario());

        Date dataPublicacao = pergunta.getDataPublicacao();
        String dataFormatada = "00/00/0000";

        if (dataPublicacao != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = dateFormat.format(dataPublicacao);
        }

        holder.dataEOlimp.setText(dataFormatada + " • " + pergunta.getOlimpiada());

        holder.pergunta.setText(pergunta.getPergunta());
        holder.qntdRespostas = pergunta.getQntdRespostas();
        String qntdRespostasString = String.valueOf(holder.qntdRespostas);

        if(holder.qntdRespostas>0){
            holder.respostas.setText(qntdRespostasString + " respostas • Clique aqui para exibir");
        }else{
            holder.respostas.setText("0 respostas • A pergunta não foi respondida");
        }


    }

    public class PerguntasForumViewHolder extends RecyclerView.ViewHolder{
        CircleImageView fotoPerfil;
        TextView titulo, nomeDeUsuario, dataEOlimp, pergunta, respostas;
        int id, qntdRespostas;

        public PerguntasForumViewHolder(@NonNull View itemView, final Context context){
            super(itemView);

            fotoPerfil=itemView.findViewById(R.id.fotoPerfilPergunta);
            titulo=itemView.findViewById(R.id.txtTituloPergunta);
            nomeDeUsuario=itemView.findViewById(R.id.txtNomeUser);
            dataEOlimp=itemView.findViewById(R.id.txtDataEOlimpiada);
            pergunta=itemView.findViewById(R.id.txtPergunta);
            respostas=itemView.findViewById(R.id.txtQntdRespostas);

            if (qntdRespostas <= 0) {
                respostas.setEnabled(false);
            }else{
                respostas.setEnabled(true);
            }
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
