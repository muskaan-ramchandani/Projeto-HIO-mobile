package com.example.helperinolympics.adapter.historicos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosVideo;

import java.util.List;

public class AdapterHistoricoVideo extends RecyclerView.Adapter<AdapterHistoricoVideo.VideoHistoricoViewHolder> {
    private List<DadosVideo> listaVideo;

    // Constructor to initialize the list
    public AdapterHistoricoVideo(List<DadosVideo> listaVideo) {
        this.listaVideo = listaVideo;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public AdapterHistoricoVideo.VideoHistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaHistoricoVideo = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_video, parent, false);
        return new AdapterHistoricoVideo.VideoHistoricoViewHolder(viewItemListaHistoricoVideo);
    }


    public void onBindViewHolder(@NonNull AdapterHistoricoVideo.VideoHistoricoViewHolder holder, int position) {
        DadosVideo video = listaVideo.get(position);



    }


    @Override
    public int getItemCount() {
        return listaVideo.size();
    }


    public class VideoHistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView conteudo, userProf;
        ImageView capa;
        public VideoHistoricoViewHolder(@NonNull View itemView){
            super(itemView);
            conteudo=itemView.findViewById(R.id.txtConteudo);
            userProf=itemView.findViewById(R.id.txtUserProf);
            capa=itemView.findViewById(R.id.imgVideo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //abrir video pelo link no youtube
                }
            });
        }

    }

}
