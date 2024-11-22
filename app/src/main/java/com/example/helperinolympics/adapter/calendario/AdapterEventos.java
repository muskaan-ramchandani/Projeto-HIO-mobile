package com.example.helperinolympics.adapter.calendario;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Eventos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<AdapterEventos.EventosViewHolder>{

    private List<Eventos> listaDadosEventos;

    public AdapterEventos(List<Eventos> listaEventos) {
        this.listaDadosEventos = listaEventos;
    }
    public AdapterEventos.EventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaEventos = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_eventos, parent, false);
        return new AdapterEventos.EventosViewHolder(viewItemListaEventos);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEventos.EventosViewHolder holder, int position) {
        holder.olimpiada.setText(listaDadosEventos.get(position).getOlimpiadaRelacionada());

        Date dataEvento = listaDadosEventos.get(position).getDataOcorrencia();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM, yyyy");
        String dataFormatada = dateFormat.format(dataEvento);
        holder.data.setText(dataFormatada);

        holder.tipo.setText(listaDadosEventos.get(position).getTipo());

        String valorHorario = "<b>Horário: </b>" + listaDadosEventos.get(position).getHorarioComeco() + " - " + listaDadosEventos.get(position).getHorarioFim();
        holder.horario.setText(Html.fromHtml(valorHorario, Html.FROM_HTML_MODE_COMPACT));

        if(!listaDadosEventos.get(position).getLink().isEmpty()){
            String valorLink = "<b>Link: </b>" + listaDadosEventos.get(position).getLink();
            holder.link.setText(Html.fromHtml(valorLink, Html.FROM_HTML_MODE_COMPACT));
        }else{
            holder.link.setVisibility(View.GONE);
        }


        if (listaDadosEventos.get(position).getCor().equals("Rosa")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);

        } else if (listaDadosEventos.get(position).getCor().equals("Azul")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);

        } else if (listaDadosEventos.get(position).getCor().equals("Laranja")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
            holder.olimpiada.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);

        } else if (listaDadosEventos.get(position).getCor().equals("Ciano")) {
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
