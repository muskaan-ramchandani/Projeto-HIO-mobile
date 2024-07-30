package com.example.helperinolympics.adapter.historicos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosProvasAnteriores;
import com.example.helperinolympics.telas_de_acesso.AcessoTextoActivity;

import java.util.List;

public class AdapterHistoricoProvas extends RecyclerView.Adapter<AdapterHistoricoProvas.ProvasHistoricoViewHolder> {
    private List<DadosProvasAnteriores> listaProvas;

    // Constructor to initialize the list
    public AdapterHistoricoProvas(List<DadosProvasAnteriores> listaProvas) {
        this.listaProvas = listaProvas;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AdapterHistoricoProvas.ProvasHistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaHistoricoProvas = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acesso_provas_anteriores, parent, false);
        return new AdapterHistoricoProvas.ProvasHistoricoViewHolder(viewItemListaHistoricoProvas);
    }


    public void onBindViewHolder(@NonNull AdapterHistoricoProvas.ProvasHistoricoViewHolder holder, int position) {
        DadosProvasAnteriores prova = listaProvas.get(position);


        String valorAno= String.valueOf(prova.getAnoProva());
        holder.ano.setText(valorAno);

        String valorEstado;
        if((prova.isEstado())==true){
            valorEstado ="Estado: Respondida";
        }else{
            valorEstado = "Estado: Não respondida";
        }
        holder.estado.setText(valorEstado);

        String fase= String.valueOf(prova.getFase());
        String valorFase= "Fase: " + fase;
        holder.fase.setText(valorFase);

        String valorProf= prova.getUserProf();
        holder.prof.setText(valorProf);
    }


    @Override
    public int getItemCount() {
        return listaProvas.size();
    }


    public class ProvasHistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView ano, estado, fase, prof;

        public ProvasHistoricoViewHolder(@NonNull View itemView){
            super(itemView);

            ano=itemView.findViewById(R.id.txtAnoProvaAnterior);
            estado=itemView.findViewById(R.id.txtEstadoProvaAnterior);
            fase=itemView.findViewById(R.id.txtFaseProvaAnterior);
            prof=itemView.findViewById(R.id.txtUserProf);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //exibir pdf
                }
            });
        }

    }

}
