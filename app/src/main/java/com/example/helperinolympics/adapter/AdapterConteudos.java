package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.materiais.FlashcardActivity;
import com.example.helperinolympics.materiais.TextoActivity;
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.List;

public class AdapterConteudos extends RecyclerView.Adapter<AdapterConteudos.ConteudosViewHolder>{
    List<DadosConteudo> listaConteudos;

    public AdapterConteudos(List<DadosConteudo> conteudos){
        this.listaConteudos=conteudos;
    }

    public AdapterConteudos.ConteudosViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaConteudos= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_conteudo_olimpiada, parent, false);
        return new AdapterConteudos.ConteudosViewHolder(viewItemListaConteudos, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterConteudos.ConteudosViewHolder holder, int position) {
        DadosConteudo olimp = listaConteudos.get(position);

        String valorTitulo = olimp.getTituloConteudo();
        String valorSubtitulo = olimp.getSubtituloConteudo();
        String valorTituloSubTitulo = "<b>" + valorTitulo + ": </b> " + valorSubtitulo;

        holder.btnConteudo.setText(Html.fromHtml(valorTituloSubTitulo)); //manter parte do texto em negrito

        String valorCor = olimp.getCorFundo();
        if (valorCor.equals("Azul")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
        } else if (valorCor.equals("Ciano")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
        } else if (valorCor.equals("Laranja")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
        } else if (valorCor.equals("Rosa")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
        }

    }

    public class ConteudosViewHolder extends RecyclerView.ViewHolder{
        AppCompatButton btnConteudo;

        public ConteudosViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            btnConteudo = itemView.findViewById(R.id.btnConteudoOlimpiada);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TextoActivity.class);
                    ((AppCompatActivity)context).startActivity(intent);

                    if (context instanceof AppCompatActivity) {
                        ((AppCompatActivity) context).finish();
                    }
                }
            });
        }
    }


    public int getItemCount(){return listaConteudos.size();}
}