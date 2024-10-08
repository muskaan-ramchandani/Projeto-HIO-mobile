package com.example.helperinolympics.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Acertos;

import java.util.List;

public class AdapterDadosAcertos extends RecyclerView.Adapter<AdapterDadosAcertos.AcertosViewHolder>{

    private List<Acertos> listaDadosAcertos;

    public AdapterDadosAcertos(List<Acertos> listaDadosAcertos) {
        this.listaDadosAcertos = listaDadosAcertos;
    }
    public AcertosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaAcertos = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acertos, parent, false);
        return new AcertosViewHolder(viewItemListaAcertos);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDadosAcertos.AcertosViewHolder holder, int position) {

//        String valorOlimpiada = listaDadosAcertos.get(position).getOlimpiadaQuestaoCerta();
//        holder.olimpiada.setText(valorOlimpiada);
//
//        String valorAssunto = listaDadosAcertos.get(position).getAssuntoQuestaoCerta();
//        holder.assunto.setText(valorAssunto);
//
//        String valorTopico = listaDadosAcertos.get(position).getTopicoDaQuestaoCerta();
//        holder.topico.setText(valorTopico);
//
//        String valorProf = "Por: "+ listaDadosAcertos.get(position).getProfQuestaoCerta();
//        holder.prof.setText(valorProf);
//
//        String valorPergunta = "<b>Pergunta: </b>" + listaDadosAcertos.get(position).getPerguntaQuestaoCerta();
//        holder.pergunta.setText(Html.fromHtml(valorPergunta, Html.FROM_HTML_MODE_COMPACT));
//
//        String valorQuestao = "<b>Alternativa marcada: </b>" + listaDadosAcertos.get(position).getQuestaoMarcadaCerta();
//        holder.questao.setText(Html.fromHtml(valorQuestao, Html.FROM_HTML_MODE_COMPACT));
//
//        if (valorOlimpiada.equals("OBA")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
//        } else if (valorOlimpiada.equals("OBF")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
//        } else if (valorOlimpiada.equals("OBI")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
//        } else if (valorOlimpiada.equals("OBMEP")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_ciano_acertos_erros);
//        }else if (valorOlimpiada.equals("ONHB")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
//        } else if (valorOlimpiada.equals("OBQ")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
//        } else if (valorOlimpiada.equals("OBB")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
//        } else if (valorOlimpiada.equals("ONC")) {
//            holder.frameBorda.setBackgroundResource(R.drawable.card_ciano_acertos_erros);
//        }
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
        FrameLayout frameBorda;

        public AcertosViewHolder(@NonNull View itemView) {
            super(itemView);
            olimpiada=itemView.findViewById(R.id.txtNomeOlimpiadaAcertos);
            assunto=itemView.findViewById(R.id.txtTemaAcertoOlimp);
            topico=itemView.findViewById(R.id.txtTopicoAcertoOlimp);
            prof=itemView.findViewById(R.id.txtProfQueFezQuestao);
            pergunta=itemView.findViewById(R.id.txtPerguntaAcertos);
            questao=itemView.findViewById(R.id.txtRespostaAcertos);
            frameBorda=itemView.findViewById(R.id.fundoBordaAcertos);
        }
    }
}
