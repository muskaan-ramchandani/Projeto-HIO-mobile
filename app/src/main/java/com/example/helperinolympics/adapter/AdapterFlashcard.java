package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosFlashcard;
import com.example.helperinolympics.model.DadosVideo;
import com.example.helperinolympics.modelos_sobrepostos.FlashcardModelo;

import java.util.List;

public class AdapterFlashcard extends RecyclerView.Adapter<AdapterFlashcard.FlashcardViewHolder> {
    private List<DadosFlashcard> listaFlashcard;
    private FragmentManager fragmentManager;

    // Constructor to initialize the list
    public AdapterFlashcard(List<DadosFlashcard> listaFlashcard, FragmentManager fragmentManager) {
        this.listaFlashcard = listaFlashcard;
        this.fragmentManager = fragmentManager;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaFlashcard = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new FlashcardViewHolder(viewItemListaFlashcard);
    }


    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        DadosFlashcard flashcard = listaFlashcard.get(position);


        holder.conteudo.setText(flashcard.getTitulo());
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FlashcardModelo notificationDialogFragment = new FlashcardModelo();
                    notificationDialogFragment.show(fragmentManager, "notificationDialog");
                }
            });

        }

    }

}

