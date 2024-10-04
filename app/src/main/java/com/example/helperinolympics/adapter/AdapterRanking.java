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

import java.util.ArrayList;
import java.util.List;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.RankingViewHolder> {

    private List<Ranking> listaRanking = new ArrayList<Ranking>();

    public AdapterRanking(List<Ranking> listaRanking){
        this.listaRanking = listaRanking;
    }

    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType){
        View viewItemListaRanking = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_classificacao_ranking, parent, false);
        return new RankingViewHolder(viewItemListaRanking);
    }

    public void onBindViewHolder(@NonNull AdapterRanking.RankingViewHolder holder, int position){
        Ranking itemLista =  listaRanking.get(position);

        holder.posicao.setText(String.valueOf(itemLista.getPosicao()));
        holder.foto.setImageBitmap(itemLista.getFotoPerfil());
        holder.user.setText(itemLista.getUser());
        holder.qntdPontos.setText(String.valueOf(itemLista.getQntdPontos()));
        holder.ponto.setText("pontos");

    }

    public int getItemCount(){return listaRanking.size();}

    public class RankingViewHolder extends RecyclerView.ViewHolder{

        TextView user, posicao, qntdPontos, ponto;
        ImageView foto;
        public RankingViewHolder(@NonNull View itemView){
            super(itemView);
            posicao=itemView.findViewById(R.id.txtPosicaoRanking);
            foto=itemView.findViewById(R.id.imgFotoPerfilModeloRanking);
            user=itemView.findViewById(R.id.txtNomeDeUsuarioRanking);
            qntdPontos = itemView.findViewById(R.id.txtQuantidadePontosRanking);
            ponto=itemView.findViewById(R.id.txtPontosRanking);
        }
    }

    public void atualizarOpcoes(List<Ranking> ranking){
        this.listaRanking.clear();
        this.listaRanking.addAll(ranking);
        this.notifyDataSetChanged();
    }
}
