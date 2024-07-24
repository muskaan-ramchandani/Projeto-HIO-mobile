package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosOlimpiada;

import java.util.List;

public class AdapterOlimpiadas extends RecyclerView.Adapter<AdapterOlimpiadas.OlimpiadasViewHolder>{
    List<DadosOlimpiada> listaOlimpiadas;

    public AdapterOlimpiadas(List<DadosOlimpiada> olimpiadas){this.listaOlimpiadas=olimpiadas;}

    public AdapterOlimpiadas.OlimpiadasViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaOlimpiadas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acesso_olimpiadas, parent, false);
        return new AdapterOlimpiadas.OlimpiadasViewHolder(viewItemListaOlimpiadas);
    }

    public void onBindViewHolder(@NonNull AdapterOlimpiadas.OlimpiadasViewHolder holder, int position){
        int valorIcone = listaOlimpiadas.get(position).getIconeOlimp();
        holder.icone.setImageResource(valorIcone);

        String valorNome = listaOlimpiadas.get(position).getNome();
        String valorSigla = listaOlimpiadas.get(position).getSigla();
        String nomeESigla = valorNome + " - "+ valorSigla;
        holder.nomeESigla.setText(nomeESigla);

        String valorCor = listaOlimpiadas.get(position).getCor();
        if(valorCor=="Azul"){
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);

        } else if (valorCor == "Ciano") {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);

        }else if (valorCor == "Laranja") {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);

        }else if (valorCor == "Rosa") {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
        }

    }

    public class OlimpiadasViewHolder extends RecyclerView.ViewHolder{
        ImageView icone;
        TextView nomeESigla;
        CardView cardFundo;
        LinearLayout linearFundo;


        public OlimpiadasViewHolder(@NonNull View itemView){
            super(itemView);
            icone=itemView.findViewById(R.id.iconeOlimpiada);
            nomeESigla=itemView.findViewById(R.id.txtOlimpiada);
            cardFundo=itemView.findViewById(R.id.cardFundoOlimpiada);
            linearFundo=itemView.findViewById(R.id.linearFundoCardOlimpiada);
        }
    }

    public int getItemCount(){return listaOlimpiadas.size();}
}
