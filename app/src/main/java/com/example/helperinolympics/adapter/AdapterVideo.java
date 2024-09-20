package com.example.helperinolympics.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosVideo;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.VideoViewHolder> {
    private List<DadosVideo> listaVideos;

    public AdapterVideo(List<DadosVideo>listaVideos){this.listaVideos=listaVideos; }

    public VideoViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaVideo= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_video, parent, false);
        return new VideoViewHolder(viewItemListaVideo, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterVideo.VideoViewHolder holder, int position){
        String valorTitulo=listaVideos.get(position).getTitulo();
        holder.titulo.setText(valorTitulo);

        String valorUser=listaVideos.get(position).getProfessorRecomendou();
        holder.userProf.setText(valorUser);

        Bitmap valorCapa=listaVideos.get(position).getCapaVideo();
        holder.capa.setImageBitmap(valorCapa);

        holder.id = listaVideos.get(position).getId();
        holder.idConteudoPertencente = listaVideos.get(position).getIdConteudoPertencente();
        holder.linkVideo = listaVideos.get(position).getLinkVideo();

    }

    public int getItemCount(){return listaVideos.size();}

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, userProf;
        ImageView capa;
        int id, idConteudoPertencente;
        String linkVideo;


        public VideoViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            titulo=itemView.findViewById(R.id.txtTitulo);
            userProf=itemView.findViewById(R.id.txtUserProf);
            capa=itemView.findViewById(R.id.imgVideo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkVideo));

                    // Verificar se há app pra abrir o video
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Nenhum aplicativo encontrado para abrir o link", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void atualizarOpcoes(List<DadosVideo> videos){
        this.listaVideos.clear();
        this.listaVideos.addAll(videos);
        this.notifyDataSetChanged();
    }


}
