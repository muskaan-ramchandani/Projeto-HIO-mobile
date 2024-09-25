package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Olimpiada;

import java.util.ArrayList;
import java.util.List;


public class AdapterEscolhaOlimpiadas extends RecyclerView.Adapter<AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder>{
    List<Olimpiada> listaOlimpiadasOpcoes = new ArrayList<>();
    List<Olimpiada> listaEscolhidas= new ArrayList<>();


    public AdapterEscolhaOlimpiadas(List<Olimpiada> olimpiadas){
        this.listaOlimpiadasOpcoes=olimpiadas;
    }

    public AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaOlimpiadasEscolha= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_escolha_olimpiada, parent, false);
        return new AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder(viewItemListaOlimpiadasEscolha);
    }

    public void onBindViewHolder(@NonNull AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder holder, int position) {
        Olimpiada olimp = listaOlimpiadasOpcoes.get(position);

        holder.icone.setImageResource(olimp.getIconeOlimp());
        holder.nomeESigla.setText(olimp.getNome() + " - " + olimp.getSigla());

        String valorCor = olimp.getCor();
        if (valorCor != null) {

            switch (valorCor){
                case "Azul":
                    holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
                    holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
                    break;
                case "Ciano":
                    holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
                    holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
                    break;
                case "Laranja":
                    holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
                    holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
                    break;
                case "Rosa":
                    holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
                    holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
                    break;
            }

        } else {
            holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    if (!listaEscolhidas.contains(olimp)) {
                        listaEscolhidas.add(olimp);
                    }
                } else {
                    listaEscolhidas.remove(olimp);
                }
            }
        });

    }

    public List<Olimpiada> getListaEscolhidas() {
        return listaEscolhidas;
    }

    public class OlimpiadasEscolhaViewHolder extends RecyclerView.ViewHolder{
        ImageView icone;
        TextView nomeESigla;
        CardView cardFundo;
        ConstraintLayout constraintFundo;
        CheckBox checkBox;


        public OlimpiadasEscolhaViewHolder(@NonNull View itemView){
            super(itemView);
            icone=itemView.findViewById(R.id.iconeOlimpiadaEscolha);
            nomeESigla=itemView.findViewById(R.id.txtOlimpiadaEscolha);
            cardFundo=itemView.findViewById(R.id.cardFundoOlimpiada);
            constraintFundo=itemView.findViewById(R.id.constraintFundoCardOlimpiada);
            checkBox = itemView.findViewById(R.id.caixaSelecaoOlimpiada);
        }
    }

    public int getItemCount(){return listaOlimpiadasOpcoes.size();}

    public void atualizarOpcoes(List<Olimpiada> olimpiadas){
        this.listaOlimpiadasOpcoes.clear();
        this.listaOlimpiadasOpcoes.addAll(olimpiadas);
        this.notifyDataSetChanged();
    }

}