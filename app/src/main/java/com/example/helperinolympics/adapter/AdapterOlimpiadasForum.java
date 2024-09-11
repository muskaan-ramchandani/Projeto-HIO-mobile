package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.helperinolympics.model.DadosOlimpiada;
import com.example.helperinolympics.model.DadosOlimpiadaForum;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.List;

public class AdapterOlimpiadasForum extends RecyclerView.Adapter<AdapterOlimpiadasForum.OlimpiadasForumViewHolder>{
    List<DadosOlimpiadaForum> listaOlimpiadasForum;

    public AdapterOlimpiadasForum(List<DadosOlimpiadaForum> olimpiadasF){
        this.listaOlimpiadasForum=olimpiadasF;
    }

    public AdapterOlimpiadasForum.OlimpiadasForumViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaOlimpiadas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_forum_por_olimp, parent, false);
        return new AdapterOlimpiadasForum.OlimpiadasForumViewHolder(viewItemListaOlimpiadas, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterOlimpiadasForum.OlimpiadasForumViewHolder holder, int position) {
        DadosOlimpiadaForum olimp = listaOlimpiadasForum.get(position);

        holder.txtSiglaOlimpiada.setText(olimp.getSiglaOlimpiada());
        String qntdPerguntas = String.valueOf(olimp.getQntdPerguntasRelacionadas());
        holder.txtPerguntasRelacionadas.setText( qntdPerguntas + " perguntas relacionadas");

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

    }

    public class OlimpiadasForumViewHolder extends RecyclerView.ViewHolder{
        TextView txtSiglaOlimpiada;
        TextView txtPerguntasRelacionadas;
        CardView cardFundo;
        LinearLayout linearFundo;


        public OlimpiadasForumViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            txtSiglaOlimpiada=itemView.findViewById(R.id.txtSiglaOlimpiada);
            txtPerguntasRelacionadas=itemView.findViewById(R.id.txtPerguntasRelacionadas);

            cardFundo=itemView.findViewById(R.id.cardFundoOlimpForum);
            linearFundo=itemView.findViewById(R.id.linearFundoOlimpForum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mudar perguntas com fragments
                }
            });
        }
    }


    public int getItemCount(){return listaOlimpiadasForum.size();}

    public void atualizarOpcoes(List<DadosOlimpiadaForum> olimpiadas){
        this.listaOlimpiadasForum.clear();
        this.listaOlimpiadasForum.addAll(olimpiadas);
        this.notifyDataSetChanged();
    }
}
