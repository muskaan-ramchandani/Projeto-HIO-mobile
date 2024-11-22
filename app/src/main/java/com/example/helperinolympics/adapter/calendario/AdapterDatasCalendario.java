package com.example.helperinolympics.adapter.calendario;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Eventos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AdapterDatasCalendario extends RecyclerView.Adapter<AdapterDatasCalendario.DatasViewHolder>{

    private List<String> diasDoMes;
    private Calendar dataAtual;
    private List<Eventos> listaEventos;

    public AdapterDatasCalendario(List<String> diasDoMes, Calendar dataAtual, List<Eventos> listaEventos) {
        this.diasDoMes = diasDoMes;
        this.dataAtual = dataAtual;
        this.listaEventos = listaEventos;
    }
    public AdapterDatasCalendario.DatasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewData = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_datas_calendario, parent, false);
        return new AdapterDatasCalendario.DatasViewHolder(viewData);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatasCalendario.DatasViewHolder holder, int position) {
        String diaTexto = diasDoMes.get(position);

        holder.data.setText(diaTexto);

        if (diaTexto == null || diaTexto.isEmpty()) {
            holder.data.setBackgroundResource(0);
            holder.data.setTextColor(Color.parseColor("#FF000000"));
            return;
        }

        int diaDoMes;
        try {
            diaDoMes = Integer.parseInt(diaTexto);
        } catch (NumberFormatException e) {
            holder.data.setBackgroundResource(0);
            holder.data.setTextColor(Color.parseColor("#FF000000"));
            return;
        }

        int diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);
        int mesAtual = dataAtual.get(Calendar.MONTH);
        int anoAtual = dataAtual.get(Calendar.YEAR);

        boolean eventoEncontrado = false;

        // Verifica se a data pertence a algum evento
        for (Eventos evento : listaEventos) {
            Calendar calendarioEvento = Calendar.getInstance();
            calendarioEvento.setTime(evento.getDataOcorrencia());

            int diaEvento = calendarioEvento.get(Calendar.DAY_OF_MONTH);
            int mesEvento = calendarioEvento.get(Calendar.MONTH);
            int anoEvento = calendarioEvento.get(Calendar.YEAR);

            if (diaDoMes == diaEvento && mesAtual == mesEvento && anoAtual == anoEvento) {
                eventoEncontrado = true;
                switch (evento.getCor()) {
                    case "Rosa":
                        holder.data.setBackgroundResource(R.drawable.fundo_arredondado_rosa);
                        break;
                    case "Azul":
                        holder.data.setBackgroundResource(R.drawable.fundo_arredondado_azul);
                        break;
                    case "Laranja":
                        holder.data.setBackgroundResource(R.drawable.fundo_arredondado_laranja);
                        break;
                    case "Ciano":
                        holder.data.setBackgroundResource(R.drawable.fundo_arredondado_ciano);
                        break;
                    default:
                        holder.data.setBackgroundResource(0);
                }
                holder.data.setTextColor(Color.parseColor("#FFFFFFFF"));
                break;
            }
        }

        // Verifica se a data é a data atual e se não encontrou evento
        if (!eventoEncontrado) {
            if (diaDoMes == diaAtual && mesAtual == dataAtual.get(Calendar.MONTH) && anoAtual == dataAtual.get(Calendar.YEAR)) {
                holder.data.setBackgroundResource(R.drawable.fundo_arredondado_roxo_btn_selecionado);
                holder.data.setTextColor(Color.parseColor("#FFFFFFFF"));
            } else {
                holder.data.setBackgroundResource(0);
                holder.data.setTextColor(Color.parseColor("#FF000000"));
            }
        }
    }


    public void atualizarDatas(List<String> novosDiasDoMes) {
        this.diasDoMes = novosDiasDoMes;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return diasDoMes.size();
    }


    public class DatasViewHolder extends RecyclerView.ViewHolder{
        TextView data;

        public DatasViewHolder(@NonNull View itemView) {
            super(itemView);
            data= itemView.findViewById(R.id.celulaDia);
        }
    }

}
