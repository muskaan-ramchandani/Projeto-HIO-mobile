package com.example.helperinolympics.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosEventos;

import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.EventosViewHolder>{

    private List<DadosEventos> listaDadosEventos;

    public AdapterEventos(List<DadosEventos> listaDadosEventos) {
        this.listaDadosEventos = listaDadosEventos;
    }
    public AdapterEventos.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaEventos = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_eventos, parent, false);
        return new AdapterEventos.EventosViewHolder(viewItemListaEventos);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEventos.EventosViewHolder holder, int position) {
        String valorOlimpiada = listaDadosEventos.get(position).getOlimpiadaRelacionada();
        holder.olimpiada.setText(valorOlimpiada);

        String valorData = String.valueOf(listaDadosEventos.get(position).getData());
        holder.data.setText(valorData);

        String valorTipo = listaDadosEventos.get(position).getTipoEvento();
        holder.tipo.setText(valorTipo);

        String valorHorario = "<b>Horário: </b>" + listaDadosEventos.get(position).getHorario();
        holder.horario.setText(Html.fromHtml(valorHorario, Html.FROM_HTML_MODE_COMPACT));

        String valorLink = "<b>Link: </b>" + listaDadosEventos.get(position).getLink();
        holder.link.setText(Html.fromHtml(valorLink, Html.FROM_HTML_MODE_COMPACT));

        if (valorOlimpiada.equals("OBA")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);

        } else if (valorOlimpiada.equals("OBF")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);

        } else if (valorOlimpiada.equals("OBI")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);

        } else if (valorOlimpiada.equals("OBMEP")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_ciano_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);

        }else if (valorOlimpiada.equals("ONHB")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);

        } else if (valorOlimpiada.equals("OBQ")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);

        } else if (valorOlimpiada.equals("OBB")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);

        } else if (valorOlimpiada.equals("ONC")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_ciano_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);

        }
    }

    @Override
    public int getItemCount() {
        return listaDadosEventos.size();
    }


    public class EventosViewHolder extends RecyclerView.ViewHolder{
        TextView olimpiada;
        TextView data;
        TextView tipo;
        TextView horario;
        TextView link;
        FrameLayout frameBorda;

        public EventosViewHolder(@NonNull View itemView) {
            super(itemView);
            olimpiada=itemView.findViewById(R.id.txtOlimpiadaEvento);
            data=itemView.findViewById(R.id.txtDataEvento);
            tipo = itemView.findViewById(R.id.txtTipoEvento);
            horario=itemView.findViewById(R.id.txtHorarioEvento);
            link=itemView.findViewById(R.id.txtLinkEvento);
            frameBorda=itemView.findViewById(R.id.frameBordaEvento);
        }
    }
}
