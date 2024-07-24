package com.example.helperinolympics.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosFlashcard;
import com.example.helperinolympics.model.DadosVideo;

import java.util.List;

public class AdapterFlashcard extends RecyclerView.Adapter<AdapterFlashcard.FlashcardViewHolder> {
    private List<DadosFlashcard> listaFlashcard;

    // Constructor to initialize the list
    public AdapterFlashcard(List<DadosFlashcard> listaFlashcard) {
        this.listaFlashcard = listaFlashcard;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaFlashcard = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_flashcard, parent, false);
        return new FlashcardViewHolder(viewItemListaFlashcard);
    }


    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        DadosFlashcard flashcard = listaFlashcard.get(position);


        holder.conteudo.setText(flashcard.getTemaPertencente());
        holder.userProf.setText(flashcard.getProfessorCadastrou());
    }


    @Override
    public int getItemCount() {
        return listaFlashcard.size();
    }


    public class FlashcardViewHolder extends RecyclerView.ViewHolder {
        TextView conteudo, userProf;

        public FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            conteudo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);
        }
    }
}