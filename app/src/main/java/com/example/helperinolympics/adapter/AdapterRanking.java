package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Ranking;

import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.RankingViewHolder> {

    private List<Ranking> listaRanking;

    public AdapterRanking(List<Ranking> listaRanking){
        this.listaRanking = listaRanking;
    }

    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View viewItemListaRanking = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_classificacao_ranking, parent, false);
        return new RankingViewHolder(viewItemListaRanking);
    }

    public void onBindViewHolder(@NonNull AdapterRanking.RankingViewHolder holder, int position){
        int valorPosicao = listaRanking.get(position).getPosicao();
        holder.posicao.setText(String.valueOf(valorPosicao));

        int valorFoto = listaRanking.get(position).getFotoPerfil();
        holder.foto.setImageResource(valorFoto);

        String valorUser = listaRanking.get(position).getUser();
        holder.user.setText(valorUser);

        int valorAcertos = listaRanking.get(position).getQntdAcertos();
        holder.qntdAcertos.setText(String.valueOf(valorAcertos));

    }

    public int getItemCount(){return listaRanking.size();}

    public class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView user, posicao, qntdAcertos;
        ImageView foto;
        public RankingViewHolder(@NonNull View itemView){
            super(itemView);
            posicao=itemView.findViewById(R.id.txtPosicaoRanking);
            foto=itemView.findViewById(R.id.imgFotoPerfilModeloRanking);
            user=itemView.findViewById(R.id.txtNomeDeUsuarioRanking);
            qntdAcertos=itemView.findViewById(R.id.txtQuantidadeAcertosRanking);
        }
    }
}
