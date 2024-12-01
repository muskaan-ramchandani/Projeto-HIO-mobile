package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.CadastraHistoricoAsynTask;
import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Video;

import java.util.List;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.VideoViewHolder> {
    private List<Video> listaVideos;
    private Aluno alunoCadastrado;

    public AdapterVideo(List<Video>listaVideos, Aluno alunoCadastrado){
        this.listaVideos=listaVideos;
        this.alunoCadastrado = alunoCadastrado;
    }

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
                    new CadastraHistoricoAsynTask().execute(alunoCadastrado.getEmail(), "Video", String.valueOf(id));
                    context.startActivity(intent);
                }
            });
        }
    }

    public void atualizarOpcoes(List<Video> videos){
        this.listaVideos.clear();
        this.listaVideos.addAll(videos);
        this.notifyDataSetChanged();
    }


}
