package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosErros;

import java.util.List;

public class AdapterDadosErros extends RecyclerView.Adapter<AdapterDadosErros.ErrosViewHolder>{

    private List<DadosErros> listaDadosErros;

    public AdapterDadosErros(List<DadosErros> listaDadosAcertos) {
        this.listaDadosErros = listaDadosErros;
    }
    public AdapterDadosErros.ErrosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaErros = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_erros, parent, false);
        return new AdapterDadosErros.ErrosViewHolder(viewItemListaErros);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDadosErros.ErrosViewHolder holder, int position) {
        String valorOlimpiada = listaDadosErros.get(position).getOlimpiadaQuestaoErrada();
        holder.olimpiada.setText(valorOlimpiada);

        String valorAssunto = listaDadosErros.get(position).getAssuntoQuestaoErrada();
        holder.assunto.setText(valorAssunto);

        String valorTopico = listaDadosErros.get(position).getTopicoDaQuestaoErrada();
        holder.topico.setText(valorTopico);

        String valorProf = listaDadosErros.get(position).getProfQuestaoErrada();
        holder.prof.setText(valorProf);

        String valorPergunta = listaDadosErros.get(position).getPerguntaQuestaoErrada();
        holder.pergunta.setText(valorPergunta);

        String valorQuestao = listaDadosErros.get(position).getQuestaoMarcadaErrada();
        holder.questao.setText(valorQuestao);
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

        public ErrosViewHolder(@NonNull View itemView) {
            super(itemView);
            olimpiada=itemView.findViewById(R.id.txtNomeOlimpiadaErros);
            assunto=itemView.findViewById(R.id.txtTemaErrosOlimp);
            topico=itemView.findViewById(R.id.txtTopicoErrosOlimp);
            prof=itemView.findViewById(R.id.txtProfQueFezQuestaoErros);
            pergunta=itemView.findViewById(R.id.txtPerguntaErros);
            questao=itemView.findViewById(R.id.txtRespostaErros);
            correcao=itemView.findViewById(R.id.txtCorrecaoErros);
        }
    }
}
