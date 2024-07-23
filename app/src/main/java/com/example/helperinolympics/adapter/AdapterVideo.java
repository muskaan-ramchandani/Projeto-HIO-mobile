package com.example.helperinolympics.adapter;

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

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.VideoViewHolder> {
    private List<DadosVideo> listaVideos;

    public AdapterVideo(List<DadosVideo>listaVideos){this.listaVideos=listaVideos; }

    public VideoViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaVideo= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_video, parent, false);
        return new VideoViewHolder(viewItemListaVideo);
    }

    public void onBindViewHolder(@NonNull AdapterVideo.VideoViewHolder holder, int position){
        String valorTema=listaVideos.get(position).getTemaPertencente();
        holder.conteudo.setText(valorTema);

        String valorUser=listaVideos.get(position).getProfessorRecomendou();
        holder.userProf.setText(valorUser);

        int valorCapa=listaVideos.get(position).getCapaVideo();
        holder.capa.setImageResource(valorCapa);



    }

    public int getItemCount(){return listaVideos.size();}

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView conteudo, userProf;
        ImageView capa;
        public VideoViewHolder(@NonNull View itemView){
            super(itemView);
            conteudo=itemView.findViewById(R.id.txtConteudo);
            userProf=itemView.findViewById(R.id.txtUserProf);
            capa=itemView.findViewById(R.id.imgVideo);

        }
    }
}
