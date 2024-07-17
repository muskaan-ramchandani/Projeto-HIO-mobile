package com.example.helperinolympics.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosAcertos;

import java.util.List;

public class AdapterDadosAcertos extends RecyclerView.Adapter<AdapterDadosAcertos.AcertosViewHolder>{

    private List<DadosAcertos> listaDadosAcertos;

    public AdapterDadosAcertos(List<DadosAcertos> listaDadosAcertos) {
        this.listaDadosAcertos = listaDadosAcertos;
    }
    public AcertosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaAcertos = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acertos, parent, false);
        return new AcertosViewHolder(viewItemListaAcertos);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDadosAcertos.AcertosViewHolder holder, int position) {
        String valorOlimpiada = listaDadosAcertos.get(position).getOlimpiadaQuestaoCerta();
        holder.olimpiada.setText(valorOlimpiada);
    }

    @Override
    public int getItemCount() {
        return listaDadosAcertos.size();
    }


    public class AcertosViewHolder extends RecyclerView.ViewHolder{
        TextView olimpiada;
        TextView assunto;
        TextView topico;
        TextView prof;
        TextView pergunta;
        TextView questao;
        public AcertosViewHolder(@NonNull View itemView) {
            super(itemView);
            olimpiada=itemView.findViewById(R.id.txtNomeOlimpiadaAcertos);
            assunto=itemView.findViewById(R.id.txtTemaAcertoOlimp);
            topico=itemView.findViewById(R.id.txtTopicoAcertoOlimp);
            prof=itemView.findViewById(R.id.txtProfQueFezQuestao);
            pergunta=itemView.findViewById(R.id.txtPerguntaAcertos);
            questao=itemView.findViewById(R.id.txtRespostaAcertos);
        }
    }
}
