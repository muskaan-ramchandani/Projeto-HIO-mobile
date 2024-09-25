package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.questionario.Alternativas;

import java.util.List;

public class AdapterAlternativasQuestionario extends RecyclerView.Adapter<AdapterAlternativasQuestionario.AlternativasQuestionarioViewHolder> {
    private List<Alternativas> listaAlternativas;

    public AdapterAlternativasQuestionario(List<Alternativas> listaAlternativas) {
        this.listaAlternativas = listaAlternativas;
    }

    @NonNull
    @Override
    public AlternativasQuestionarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaAlternativas = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_alternativas_questionario, parent, false);
        return new AlternativasQuestionarioViewHolder(viewItemListaAlternativas);
    }

    @Override
    public void onBindViewHolder(@NonNull AlternativasQuestionarioViewHolder holder, int position) {
          Alternativas alternativa = listaAlternativas.get(position);

          holder.textoAlternativa.setText(alternativa.getTextoAlternativa());
          holder.id = alternativa.getId();
          holder.idQuestionarioPertencente= alternativa.getIdQuestionarioPertencente();
          holder.corretaOuErrada = alternativa.isCorretaOuErrada();
    }

    @Override
    public int getItemCount() {
        return listaAlternativas.size();
    }

    public static class AlternativasQuestionarioViewHolder extends RecyclerView.ViewHolder {
        TextView textoAlternativa;
        int id, idQuestionarioPertencente;
        boolean corretaOuErrada;

        public AlternativasQuestionarioViewHolder(@NonNull View itemView) {
            super(itemView);

            textoAlternativa = itemView.findViewById(R.id.textoAlternativa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(corretaOuErrada ==true){

                    }else if(corretaOuErrada == false){

                    }
                }
            });
        }
    }



    public void atualizarOpcoes(List<Alternativas> alternativas){
        this.listaAlternativas.clear();
        this.listaAlternativas.addAll(alternativas);
        this.notifyDataSetChanged();
    }
}
