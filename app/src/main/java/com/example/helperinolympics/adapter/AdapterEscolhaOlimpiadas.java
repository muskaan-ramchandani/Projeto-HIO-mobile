package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosOlimpiada;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.ArrayList;
import java.util.List;


public class AdapterEscolhaOlimpiadas extends RecyclerView.Adapter<AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder>{
    List<DadosOlimpiada> listaOlimpiadasOpcoes = new ArrayList<>();
    List<DadosOlimpiada> listaEscolhidas= new ArrayList<>();


    public AdapterEscolhaOlimpiadas(List<DadosOlimpiada> olimpiadas){
        this.listaOlimpiadasOpcoes=olimpiadas;
    }

    public AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaOlimpiadasEscolha= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_escolha_olimpiada, parent, false);
        return new AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder(viewItemListaOlimpiadasEscolha);
    }

    public void onBindViewHolder(@NonNull AdapterEscolhaOlimpiadas.OlimpiadasEscolhaViewHolder holder, int position) {
        DadosOlimpiada olimp = listaOlimpiadasOpcoes.get(position);

        holder.icone.setImageResource(olimp.getIconeOlimp());
        holder.nomeESigla.setText(olimp.getNome() + " - " + olimp.getSigla());

        String valorCor = olimp.getCor();
        if (valorCor.equals("Azul")) {
            holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
        } else if (valorCor.equals("Ciano")) {
            holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
        } else if (valorCor.equals("Laranja")) {
            holder.constraintFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
            holder.cardFundo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
        } else if (valorCor.equals("Rosa")) {
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

    public List<DadosOlimpiada> getListaEscolhidas() {
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

    public void atualizarOpcoes(List<DadosOlimpiada> olimpiadas){
        this.listaOlimpiadasOpcoes.clear();
        this.listaOlimpiadasOpcoes.addAll(olimpiadas);
        this.notifyDataSetChanged();
    }


    public void atualizaFoto(int sigla, Bitmap icone){
        for (DadosOlimpiada olimp : listaOlimpiadasOpcoes){
            if(olimp.getSigla().equals(sigla)){
                int ic = bitmapToInt(icone);
                olimp.setIconeOlimp(ic);
            }
        }
        notifyDataSetChanged();
    }


    public void atualizaFoto(String[] siglaOlimp, Bitmap[] foto){
        for(int i = 0; i < siglaOlimp.length; i++){
            for (DadosOlimpiada olimp : listaOlimpiadasOpcoes){
                if(olimp.getSigla().equals(siglaOlimp[i])){
                    int ic = bitmapToInt(foto[i]);
                    olimp.setIconeOlimp(ic);
                }
            }
        }
        notifyDataSetChanged();
    }

    public int bitmapToInt(Bitmap bitmap) {
        return bitmap.hashCode();
    }
}