package com.example.helperinolympics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosProvasAnteriores;

import java.util.List;

public class AdapterProvasAnteriores extends RecyclerView.Adapter<AdapterProvasAnteriores.ProvasAnterioresViewHolder>{
    List<DadosProvasAnteriores> listaProvasAnteriores;

    public AdapterProvasAnteriores(List<DadosProvasAnteriores> provas){
        this.listaProvasAnteriores=provas;
    }

    public AdapterProvasAnteriores.ProvasAnterioresViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaProvasAnteriores= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acesso_provas_anteriores, parent, false);
        return new AdapterProvasAnteriores.ProvasAnterioresViewHolder(viewItemListaProvasAnteriores, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterProvasAnteriores.ProvasAnterioresViewHolder holder, int position) {
        DadosProvasAnteriores prova = listaProvasAnteriores.get(position);

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


    public class ProvasAnterioresViewHolder extends RecyclerView.ViewHolder{
        TextView ano, estado, fase, prof;

        public ProvasAnterioresViewHolder(@NonNull View itemView, final Context context){
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


    public int getItemCount(){return listaProvasAnteriores.size();}
}
