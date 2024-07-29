package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosTexto;
import com.example.helperinolympics.telas_de_acesso.AcessoTextoActivity;

import java.util.List;

public class AdapterTexto extends RecyclerView.Adapter<AdapterTexto.TextoViewHolder> {
    private List<DadosTexto> listaTexto;

    public AdapterTexto(List<DadosTexto> listaTexto) {
        this.listaTexto = listaTexto;
    }

    @NonNull
    @Override
    public TextoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaTexto = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new TextoViewHolder(viewItemListaTexto);
    }

    @Override
    public void onBindViewHolder(@NonNull TextoViewHolder holder, int position) {
        DadosTexto texto = listaTexto.get(position);

        String valorUserProf = texto.getProfessorCadastrou();
        holder.userProf.setText(valorUserProf);

        String valorTitulo = texto.getTitulo();
        holder.titulo.setText(valorTitulo);
    }

    @Override
    public int getItemCount() {
        return listaTexto.size();
    }

    public class TextoViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, userProf;

        public TextoViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, AcessoTextoActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
