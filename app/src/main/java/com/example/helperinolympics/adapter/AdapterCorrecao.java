package com.example.helperinolympics.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Correcao;

import java.util.List;

public class AdapterCorrecao extends RecyclerView.Adapter<AdapterCorrecao.CorrecaoViewHolder>{

    private List<Correcao> listaQuestoesCorrigidas;

    public AdapterCorrecao(List<Correcao> listaQuestoesCorrigidas) {
        this.listaQuestoesCorrigidas = listaQuestoesCorrigidas;
    }
    public AdapterCorrecao.CorrecaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaCorrecao = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_correcao_erros_questionario, parent, false);
        return new AdapterCorrecao.CorrecaoViewHolder(viewItemListaCorrecao);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCorrecao.CorrecaoViewHolder holder, int position) {

        String valorPergunta ="<b>Pergunta: </b>" + listaQuestoesCorrigidas.get(position).getPergunta();
        holder.pergunta.setText(Html.fromHtml(valorPergunta, Html.FROM_HTML_MODE_COMPACT));

        String valorAlternativaCorreta = "<b>Alternativa correta: </b>" + listaQuestoesCorrigidas.get(position).getAlternativaCorreta();
        holder.alternativaCorreta.setText(Html.fromHtml(valorAlternativaCorreta, Html.FROM_HTML_MODE_COMPACT));

        String valorExplicacao = "<b>Explicação: </b>" + listaQuestoesCorrigidas.get(position).getExplicacao();
        holder.explicacao.setText(Html.fromHtml(valorExplicacao, Html.FROM_HTML_MODE_COMPACT));

    }

    @Override
    public int getItemCount() {
        return listaQuestoesCorrigidas.size();
    }


    public class CorrecaoViewHolder extends RecyclerView.ViewHolder{
        TextView pergunta;
        TextView alternativaCorreta;
        TextView explicacao;

        public CorrecaoViewHolder(@NonNull View itemView) {
            super(itemView);
            pergunta=itemView.findViewById(R.id.txtPergunta);
            alternativaCorreta=itemView.findViewById(R.id.txtAlternativaCorreta);
            explicacao=itemView.findViewById(R.id.txtExplicacao);
        }
    }
}
