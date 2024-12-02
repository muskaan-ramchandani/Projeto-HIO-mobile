package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Olimpiada;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.List;

public class AdapterOlimpiadas extends RecyclerView.Adapter<AdapterOlimpiadas.OlimpiadasViewHolder>{
    List<Olimpiada> listaOlimpiadas;
    Aluno alunoAtual;

    public AdapterOlimpiadas(List<Olimpiada> olimpiadas, Aluno alunoAtual){
        this.listaOlimpiadas=olimpiadas;
        this.alunoAtual= alunoAtual;
    }

    public AdapterOlimpiadas.OlimpiadasViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaOlimpiadas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acesso_olimpiadas, parent, false);
        return new AdapterOlimpiadas.OlimpiadasViewHolder(viewItemListaOlimpiadas, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterOlimpiadas.OlimpiadasViewHolder holder, int position) {
        Olimpiada olimp = listaOlimpiadas.get(position);

        holder.icone.setImageResource(olimp.getIconeOlimp());
        holder.nomeESigla.setText(olimp.getNome() + " - " + olimp.getSigla());
        holder.siglaDaOlimpiadaSelecionada = olimp.getSigla();

        String valorCor = olimp.getCor();
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

    }



    public class OlimpiadasViewHolder extends RecyclerView.ViewHolder{
        ImageView icone;
        TextView nomeESigla;
        CardView cardFundo;
        LinearLayout linearFundo;
        String siglaDaOlimpiadaSelecionada;

        public OlimpiadasViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            icone=itemView.findViewById(R.id.iconeOlimpiada);
            nomeESigla=itemView.findViewById(R.id.txtOlimpiada);
            cardFundo=itemView.findViewById(R.id.cardFundoOlimpiada);
            linearFundo=itemView.findViewById(R.id.linearFundoCardOlimpiada);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, InicioOlimpiadaActivity.class);
                    intent.putExtra("alunoCadastrado", alunoAtual);
                    intent.putExtra("siglaOlimpiada", siglaDaOlimpiadaSelecionada);
                    Log.d("SIGLA_OLIMPIADA", "Sigla: " + siglaDaOlimpiadaSelecionada);

                    ((AppCompatActivity)context).startActivity(intent);
                }
            });
        }
    }


    public int getItemCount(){return listaOlimpiadas.size();}

    public void atualizarOpcoes(List<Olimpiada> olimpiadas){
        this.listaOlimpiadas.clear();
        this.listaOlimpiadas.addAll(olimpiadas);
        notifyDataSetChanged();
    }

}
