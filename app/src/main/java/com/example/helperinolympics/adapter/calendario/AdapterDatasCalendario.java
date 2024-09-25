package com.example.helperinolympics.adapter.calendario;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;

import java.util.Calendar;
import java.util.List;

public class AdapterDatasCalendario extends RecyclerView.Adapter<AdapterDatasCalendario.DatasViewHolder>{

    private List<String> diasDoMes;
    private Calendar dataAtual;

    public AdapterDatasCalendario(List<String> diasDoMes, Calendar dataAtual) {
        this.diasDoMes = diasDoMes;
        this.dataAtual = dataAtual;
    }
    public AdapterDatasCalendario.DatasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewData = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_datas_calendario, parent, false);
        return new AdapterDatasCalendario.DatasViewHolder(viewData);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatasCalendario.DatasViewHolder holder, int position) {
        int diaAtual = dataAtual.get(Calendar.DAY_OF_MONTH);

        holder.data.setText(diasDoMes.get(position));

        if (diasDoMes.get(position).equals(String.valueOf(diaAtual))) {
            holder.data.setBackgroundResource(R.drawable.fundo_arredondado_roxo_btn_selecionado);
            holder.data.setTextColor(Color.parseColor("#FFFFFFFF"));

        } else {
            holder.data.setBackgroundResource(0);
            holder.data.setTextColor(Color.parseColor("#FF000000"));

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
