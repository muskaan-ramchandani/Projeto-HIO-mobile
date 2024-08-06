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
import com.example.helperinolympics.model.DadosEventos;

import java.util.List;

public class AdapterDatasCalendario extends RecyclerView.Adapter<AdapterDatasCalendario.DatasViewHolder>{

    private final List<String> diasDoMes;

    public AdapterDatasCalendario(List<String> diasDoMes) {
        this.diasDoMes = diasDoMes;
    }
    public AdapterDatasCalendario.DatasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewData = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_datas_calendario, parent, false);
        return new AdapterDatasCalendario.DatasViewHolder(viewData);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatasCalendario.DatasViewHolder holder, int position) {
        holder.data.setText(diasDoMes.get(position));
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
