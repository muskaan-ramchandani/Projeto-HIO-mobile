package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ModeloPerguntasForumBinding;
import com.example.helperinolympics.model.DadosOlimpiada;
import com.example.helperinolympics.model.DadosOlimpiadaForum;
import com.example.helperinolympics.model.DadosPerguntasForum;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.List;

public class AdapterPerguntasForum extends RecyclerView.Adapter<AdapterPerguntasForum.PerguntasForumViewHolder>{ List<DadosPerguntasForum> listaPerguntasForum;

    public AdapterPerguntasForum(List<DadosPerguntasForum> perguntas){
        this.listaPerguntasForum=perguntas;
    }

    public AdapterPerguntasForum.PerguntasForumViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaPerguntas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_perguntas_forum, parent, false);
        return new AdapterPerguntasForum.PerguntasForumViewHolder(viewItemListaPerguntas, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterPerguntasForum.PerguntasForumViewHolder holder, int position) {
        DadosPerguntasForum pergunta = listaPerguntasForum.get(position);

        holder.fotoPerfil.setImageResource(pergunta.getFotoPerfil());
        holder.titulo.setText(pergunta.getTitulo());
        holder.nomeDeUsuario.setText(pergunta.getNomeDeUsuario());

        String data = pergunta.getDataPublicacao().toString();
        holder.dataEOlimp.setText(data + " • "+pergunta.getOlimpiada());

        holder.pergunta.setText(pergunta.getPergunta());
        int qntdRespostasInt = pergunta.getQntdRespostas();
        String qntdRespostasString = String.valueOf(qntdRespostasInt);

        if(qntdRespostasInt>0){
            holder.respostas.setText(qntdRespostasString + " respostas • Clique aqui para exibir");
        }else{
            holder.respostas.setText("0 respostas • A pergunta ainda não foi respondida");
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

    public void atualizarOpcoes(List<DadosPerguntasForum> perguntas){
        this.listaPerguntasForum.clear();
        this.listaPerguntasForum.addAll(perguntas);
        this.notifyDataSetChanged();
    }
}
