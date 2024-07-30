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
import com.example.helperinolympics.model.DadosTexto;
import com.example.helperinolympics.telas_de_acesso.AcessoTextoActivity;

import java.util.List;

public class AdapterHistoricoTexto extends RecyclerView.Adapter<AdapterHistoricoTexto.TextoHistoricoViewHolder> {
private List<DadosTexto> listaTxt;

// Constructor to initialize the list
public AdapterHistoricoTexto(List<DadosTexto> listaTxt) {
    this.listaTxt = listaTxt;
}

// Create new views (invoked by the layout manager)
@NonNull
@Override
public AdapterHistoricoTexto.TextoHistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View viewItemListaHistoricoTexto = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
    return new AdapterHistoricoTexto.TextoHistoricoViewHolder(viewItemListaHistoricoTexto);
}


public void onBindViewHolder(@NonNull AdapterHistoricoTexto.TextoHistoricoViewHolder holder, int position) {
    DadosTexto txt = listaTxt.get(position);


    holder.conteudo.setText(txt.getTitulo());
    holder.userProf.setText(txt.getProfessorCadastrou());
}


@Override
public int getItemCount() {
    return listaTxt.size();
}


public class TextoHistoricoViewHolder extends RecyclerView.ViewHolder {
    TextView conteudo, userProf;

    public TextoHistoricoViewHolder(@NonNull View itemView) {
        super(itemView);
        conteudo = itemView.findViewById(R.id.txtConteudo);
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
