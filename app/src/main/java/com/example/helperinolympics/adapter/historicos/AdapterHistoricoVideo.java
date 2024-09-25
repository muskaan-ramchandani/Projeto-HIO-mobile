package com.example.helperinolympics.adapter.historicos;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Video;

import java.util.List;

public class AdapterHistoricoVideo extends RecyclerView.Adapter<AdapterHistoricoVideo.VideoHistoricoViewHolder> {
    private List<Video> listaVideo;

    // Constructor to initialize the list
    public AdapterHistoricoVideo(List<Video> listaVideo) {
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
        Video video = listaVideo.get(position);

        String valorTema=video.getTitulo();
        holder.conteudo.setText(valorTema);

        String valorUser=video.getProfessorRecomendou();
        holder.userProf.setText(valorUser);

        Bitmap valorCapa=video.getCapaVideo();
        holder.capa.setImageBitmap(valorCapa);


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
