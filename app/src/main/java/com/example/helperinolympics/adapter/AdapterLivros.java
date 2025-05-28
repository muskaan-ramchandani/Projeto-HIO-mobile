package com.example.helperinolympics.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Livros;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterLivros extends RecyclerView.Adapter<AdapterLivros.LivrosViewHolder>{
    List<Livros> listaLivros;
    private Context context;


    public AdapterLivros(List<Livros> livros, Context context){
        this.context = context;
        this.listaLivros=livros;
    }

    public AdapterLivros.LivrosViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaLivros= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_livros_recomendados, parent, false);
        return new AdapterLivros.LivrosViewHolder(viewItemListaLivros, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterLivros.LivrosViewHolder holder, int position) {
        Livros livro = listaLivros.get(position);

        String valorTitulo = livro.getTitulo();
        holder.titulo.setText(valorTitulo);

        String valorAutor = "Autor: "+ livro.getAutor();
        holder.autor.setText(valorAutor);

        String valorEdicao = "Edição: "+ livro.getEdicao();
        holder.edicao.setText(valorEdicao);

        Date dataPublicacao = livro.getDataPublicacao();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = dateFormat.format(dataPublicacao);

        String valorData = "Data de publicação: "+ dataFormatada;
        holder.dataPub.setText(valorData);

        holder.capa.setImageBitmap(livro.getCapa());
        holder.livroParaPesquisa = livro.getTitulo();
        holder.autorLivro = livro.getAutor();
    }


    public class LivrosViewHolder extends RecyclerView.ViewHolder{
        TextView titulo, autor, edicao, dataPub;
        ImageView capa;
        Button ondeComprar;
        String livroParaPesquisa;
        String autorLivro;

        public LivrosViewHolder(@NonNull View itemView, final Context context){
            super(itemView);

            titulo= itemView.findViewById(R.id.txtTituloLivro);
            autor=itemView.findViewById(R.id.txtAutor);
            edicao=itemView.findViewById(R.id.txtEdicao);
            dataPub=itemView.findViewById(R.id.txtDataPub);
            capa=itemView.findViewById(R.id.capaLivro);
            ondeComprar=itemView.findViewById(R.id.btnOndeComprar);

            ondeComprar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    abrirPesquisaGoogle(livroParaPesquisa, autorLivro);
                }
            });
        }
    }

    private String criarUrlPesquisa(String livro, String autorLivro) {
        String urlBase = "https://www.google.com/search?q=";
        return urlBase + Uri.encode(livro + " " +autorLivro+ " livro comprar");
    }

    private void abrirPesquisaGoogle(String termoDePesquisa, String autorLivro) {
        String urlPesquisa = criarUrlPesquisa(termoDePesquisa, autorLivro);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlPesquisa));
        context.startActivity(intent);
    }

    public int getItemCount(){return listaLivros.size();}

    public void atualizarOpcoes(List<Livros> livros){
        this.listaLivros.clear();
        this.listaLivros.addAll(livros);
        this.notifyDataSetChanged();
    }

}