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
import com.example.helperinolympics.model.Erros;

import java.util.List;

public class AdapterErros extends RecyclerView.Adapter<AdapterErros.ErrosViewHolder>{

    private List<Erros> listaDadosErros;

    public AdapterErros(List<Erros> listaErros) {
        this.listaDadosErros = listaErros;
    }
    public AdapterErros.ErrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaErros = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_erros, parent, false);
        return new AdapterErros.ErrosViewHolder(viewItemListaErros);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterErros.ErrosViewHolder holder, int position) {


        String valorOlimpiada = listaDadosErros.get(position).getSiglaOlimpiada();
        holder.olimpiada.setText(valorOlimpiada);

        String valorAssunto = listaDadosErros.get(position).getTituloConteudo();
        holder.assunto.setText(valorAssunto);

        String valorTopico = listaDadosErros.get(position).getTituloQuestionario();
        holder.topico.setText(valorTopico);

        String valorProf = "Por: "+listaDadosErros.get(position).getUsuarioProfessor();
        holder.prof.setText(valorProf);

        String valorPergunta = "<b>Pergunta: </b>"+listaDadosErros.get(position).getTxtPergunta();
        holder.pergunta.setText(Html.fromHtml(valorPergunta, Html.FROM_HTML_MODE_COMPACT));

        String valorQuestao = "<b>Alternativa marcada: </b>"+listaDadosErros.get(position).getAlternativaMarcada();
        holder.questao.setText(Html.fromHtml(valorQuestao, Html.FROM_HTML_MODE_COMPACT));

        String valorCorrecao = "<b>Alternativa correta: </b>"+listaDadosErros.get(position).getAlternativaCorreta();
        holder.correcao.setText(Html.fromHtml(valorCorrecao, Html.FROM_HTML_MODE_COMPACT));


        if (valorOlimpiada.equals("OBA")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaRosa));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_rosa);
        } else if (valorOlimpiada.equals("OBF")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaAzul));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_azul);
        } else if (valorOlimpiada.equals("OBI")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaLaranja));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_laranja);
        } else if (valorOlimpiada.equals("OBMEP")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_ciano_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaCiano));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_ciano);
        }else if (valorOlimpiada.equals("ONHB")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_rosa_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaRosa));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_rosa);
        } else if (valorOlimpiada.equals("OBQ")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_azul_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaAzul));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_azul);
        } else if (valorOlimpiada.equals("OBB")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_laranja_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaLaranja));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_laranja);
        } else if (valorOlimpiada.equals("ONC")) {
            holder.frameBorda.setBackgroundResource(R.drawable.card_ciano_acertos_erros);
            holder.olimpiada.setTextColor(holder.itemView.getResources().getColor(R.color.btnOlimpiadaCiano));
            holder.linhaDivisoria.setBackgroundResource(R.drawable.linha_ciano);
        }

    }

    @Override
    public int getItemCount() {
        return listaDadosErros.size();
    }


    public class ErrosViewHolder extends RecyclerView.ViewHolder{
        TextView olimpiada;
        TextView assunto;
        TextView topico;
        TextView prof;
        TextView pergunta;
        TextView questao;
        TextView correcao;
        FrameLayout frameBorda;
        View linhaDivisoria;

        public ErrosViewHolder(@NonNull View itemView) {
            super(itemView);
            olimpiada=itemView.findViewById(R.id.txtNomeOlimpiadaErros);
            assunto=itemView.findViewById(R.id.txtTemaErrosOlimp);
            topico=itemView.findViewById(R.id.txtTopicoErrosOlimp);
            prof=itemView.findViewById(R.id.txtProfQueFezQuestaoErros);
            pergunta=itemView.findViewById(R.id.txtPerguntaErros);
            questao=itemView.findViewById(R.id.txtRespostaErros);
            correcao=itemView.findViewById(R.id.txtCorrecaoErros);
            frameBorda=itemView.findViewById(R.id.fundoBordaCardErros);
            linhaDivisoria = itemView.findViewById(R.id.viewLinhaSeparadora);
        }
    }
}
