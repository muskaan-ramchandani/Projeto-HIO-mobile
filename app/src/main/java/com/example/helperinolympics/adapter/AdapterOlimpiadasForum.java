package com.example.helperinolympics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosOlimpiadaForum;

import java.util.List;

public class AdapterOlimpiadasForum extends RecyclerView.Adapter<AdapterOlimpiadasForum.OlimpiadasForumViewHolder> {
    List<DadosOlimpiadaForum> listaOlimpiadasForum;
    OnOlimpiadaClickListener listener; // Interface para comunicação

    public AdapterOlimpiadasForum(List<DadosOlimpiadaForum> olimpiadasF, OnOlimpiadaClickListener listener) {
        this.listaOlimpiadasForum = olimpiadasF;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OlimpiadasForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaOlimpiadas = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_forum_por_olimp, parent, false);
        return new OlimpiadasForumViewHolder(viewItemListaOlimpiadas);
    }

    @Override
    public void onBindViewHolder(@NonNull OlimpiadasForumViewHolder holder, int position) {
        DadosOlimpiadaForum olimp = listaOlimpiadasForum.get(position);

        holder.txtSiglaOlimpiada.setText(olimp.getSiglaOlimpiada());
        String qntdPerguntas = String.valueOf(olimp.getQntdPerguntasRelacionadas());
        holder.txtPerguntasRelacionadas.setText(qntdPerguntas + " perguntas relacionadas");

        String valorCor = olimp.getCorOlimp();
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

        holder.itemView.setOnClickListener(v -> listener.onOlimpiadaClick(olimp));
    }

    @Override
    public int getItemCount() {
        return listaOlimpiadasForum.size();
    }

    public void atualizarOpcoes(List<DadosOlimpiadaForum> olimpiadas) {
        this.listaOlimpiadasForum.clear();
        this.listaOlimpiadasForum.addAll(olimpiadas);
        this.notifyDataSetChanged();
    }

    public class OlimpiadasForumViewHolder extends RecyclerView.ViewHolder {
        TextView txtSiglaOlimpiada;
        TextView txtPerguntasRelacionadas;
        CardView cardFundo;
        LinearLayout linearFundo;

        public OlimpiadasForumViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSiglaOlimpiada = itemView.findViewById(R.id.txtSiglaOlimpiada);
            txtPerguntasRelacionadas = itemView.findViewById(R.id.txtPerguntasRelacionadas);
            cardFundo = itemView.findViewById(R.id.cardFundoOlimpForum);
            linearFundo = itemView.findViewById(R.id.linearFundoOlimpForum);
        }
    }

    // Interface para o clique
    public interface OnOlimpiadaClickListener {
        void onOlimpiadaClick(DadosOlimpiadaForum olimp);
    }
}
