package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosOlimpiada;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.List;

public class AdapterEscolhaOlimpiadas extends RecyclerView.Adapter<AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder>{
    List<DadosOlimpiada> listaOlimpiadasEscolha;

    public AdapterEscolhaOlimpiadas(List<DadosOlimpiada> olimpiadas){
        this.listaOlimpiadasEscolha=olimpiadas;
    }

    public AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaOlimpiadasEscolha= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_escolha_olimpiada, parent, false);
        return new AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder(viewItemListaOlimpiadasEscolha);
    }

    public void onBindViewHolder(@NonNull AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder holder, int position) {
        DadosOlimpiada olimp = listaOlimpiadasEscolha.get(position);

        holder.icone.setImageResource(olimp.getIconeOlimp());
        holder.nomeESigla.setText(olimp.getNome() + " - " + olimp.getSigla());

        String valorCor = olimp.getCor();
        if (valorCor.equals("Azul")) {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
        } else if (valorCor.equals("Ciano")) {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
        } else if (valorCor.equals("Laranja")) {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
        } else if (valorCor.equals("Rosa")) {
            holder.linearFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
        }

    }


    public class OlimpiadasEscolhaViewHolder extends RecyclerView.ViewHolder{
        ImageView icone;
        TextView nomeESigla;
        CardView cardFundo;
        LinearLayout linearFundo;
        CheckBox checkBox;


        public OlimpiadasEscolhaViewHolder(@NonNull View itemView){
            super(itemView);
            icone=itemView.findViewById(R.id.iconeOlimpiada);
            nomeESigla=itemView.findViewById(R.id.txtOlimpiada);
            cardFundo=itemView.findViewById(R.id.cardFundoOlimpiada);
            linearFundo=itemView.findViewById(R.id.linearFundoCardOlimpiada);
            checkBox = itemView.findViewById(R.id.caixaSelecaoOlimpiada);
        }
    }


    public int getItemCount(){return listaOlimpiadasEscolha.size();}
}