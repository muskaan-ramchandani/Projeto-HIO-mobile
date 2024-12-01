package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.CadastraHistoricoAsynTask;
import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Flashcard;
import com.example.helperinolympics.modelos_sobrepostos.FlashcardModelo;

import java.util.List;

public class AdapterFlashcard extends RecyclerView.Adapter<AdapterFlashcard.FlashcardViewHolder> {
    private List<Flashcard> listaFlashcard;
    private FragmentManager fragmentManager;
    private Aluno alunoCadastrado;

    public AdapterFlashcard(List<Flashcard> listaFlashcard, FragmentManager fragmentManager, Aluno alunoCadastrado) {
        this.listaFlashcard = listaFlashcard;
        this.fragmentManager = fragmentManager;
        this.alunoCadastrado = alunoCadastrado;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaFlashcard = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new FlashcardViewHolder(viewItemListaFlashcard);
    }


    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        holder.flashcard = listaFlashcard.get(position);

        String valorTitulo=listaFlashcard.get(position).getTitulo();
        holder.titulo.setText(valorTitulo);

        String valorUser=listaFlashcard.get(position).getProfQuePostou();
        holder.userProf.setText(valorUser);

        holder.id = listaFlashcard.get(position).getId();
        holder.idConteudoPertencente = listaFlashcard.get(position).getIdConteudoPertencente();

    }


    @Override
    public int getItemCount() {
        return listaFlashcard.size();
    }


    public class FlashcardViewHolder extends RecyclerView.ViewHolder {
        Flashcard flashcard;
        TextView titulo, userProf;
        int id, idConteudoPertencente;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FlashcardModelo notificationDialogFragment = new FlashcardModelo(flashcard);
                    new CadastraHistoricoAsynTask().execute(alunoCadastrado.getEmail(), "Flashcard", String.valueOf(id));
                    notificationDialogFragment.show(fragmentManager, "notificationDialog");
                }
            });

        }

    }

    public void atualizarOpcoes(List<Flashcard> videos){
        this.listaFlashcard.clear();
        this.listaFlashcard.addAll(videos);
        this.notifyDataSetChanged();
    }

}

