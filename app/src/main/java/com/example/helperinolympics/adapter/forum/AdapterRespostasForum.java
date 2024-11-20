package com.example.helperinolympics.adapter.forum;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.forum.RespostasForum;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterRespostasForum extends RecyclerView.Adapter<AdapterRespostasForum.RespostasForumViewHolder>{
    List<RespostasForum> listaRespostasForum;

    public AdapterRespostasForum(List<RespostasForum> respostas){
        this.listaRespostasForum=respostas;
    }

    public AdapterRespostasForum.RespostasForumViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaRespostas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_respostas_forum, parent, false);
        return new AdapterRespostasForum.RespostasForumViewHolder(viewItemListaRespostas, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterRespostasForum.RespostasForumViewHolder holder, int position) {
        RespostasForum resposta = listaRespostasForum.get(position);

        Bitmap foto = resposta.getFotoPerfil();
        if(foto==null){
            holder.fotoPerfil.setImageResource(R.drawable.iconeperfilsemfoto);
        }else{
            holder.fotoPerfil.setImageBitmap(foto);
        }

        holder.id= resposta.getId();
        holder.nomeDeUsuario.setText(resposta.getUserProf());
        holder.resposta.setText(resposta.getResposta());
    }

    public class RespostasForumViewHolder extends RecyclerView.ViewHolder{
        CircleImageView fotoPerfil;
        TextView nomeDeUsuario, resposta;
        int id;

        public RespostasForumViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            fotoPerfil=itemView.findViewById(R.id.fotoPerfilResposta);
            nomeDeUsuario=itemView.findViewById(R.id.txtNomeUser);
            resposta=itemView.findViewById(R.id.txtResposta);

        }
    }


    @Override
    public int getItemCount() {
        return (listaRespostasForum != null) ? listaRespostasForum.size() : 0;
    }


    public void atualizarOpcoes(List<RespostasForum> respostas){
        this.listaRespostasForum.clear();
        this.listaRespostasForum.addAll(respostas);
        this.notifyDataSetChanged();
    }
}
