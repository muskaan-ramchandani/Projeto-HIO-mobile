package com.example.helperinolympics.adapter.historicos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Flashcard;
import com.example.helperinolympics.modelos_sobrepostos.FlashcardModelo;

import java.util.List;

public class AdapterHistoricoFlashcard extends RecyclerView.Adapter<AdapterHistoricoFlashcard.FlashcardHistoricoViewHolder> {
    private List<Flashcard> listaFlashcard;
    private FragmentManager fragmentManager;

    // Constructor to initialize the list
    public AdapterHistoricoFlashcard(List<Flashcard> listaFlashcard, FragmentManager fragmentManager) {
        this.listaFlashcard = listaFlashcard;
        this.fragmentManager = fragmentManager;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AdapterHistoricoFlashcard.FlashcardHistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaHistoricoFlashcard = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new AdapterHistoricoFlashcard.FlashcardHistoricoViewHolder(viewItemListaHistoricoFlashcard);
    }


    public void onBindViewHolder(@NonNull AdapterHistoricoFlashcard.FlashcardHistoricoViewHolder holder, int position) {
        Flashcard flashcard = listaFlashcard.get(position);
        holder.flash = flashcard;

        holder.conteudo.setText(flashcard.getTitulo());
        holder.userProf.setText(flashcard.getProfQuePostou());
    }


    @Override
    public int getItemCount() {
        return listaFlashcard.size();
    }


    public class FlashcardHistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView conteudo, userProf;
        Flashcard flash;

        public FlashcardHistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            conteudo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        FlashcardModelo notificationDialogFragment = new FlashcardModelo(flash);
                        notificationDialogFragment.show(fragmentManager, "notificationDialog");
                }
            });

        }

    }

}
