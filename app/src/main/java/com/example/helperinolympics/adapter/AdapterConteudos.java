package com.example.helperinolympics.adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.materiais.TextoActivity;
import com.example.helperinolympics.model.DadosAluno;
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.model.DadosOlimpiada;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import java.util.List;

public class AdapterConteudos extends RecyclerView.Adapter<AdapterConteudos.ConteudosViewHolder>{
    List<DadosConteudo> listaConteudos;
    DadosAluno alunoCadastrado;

    public AdapterConteudos(List<DadosConteudo> conteudos, DadosAluno alunoCadastrado){
        this.listaConteudos=conteudos;
        this.alunoCadastrado = alunoCadastrado;
    }

    public AdapterConteudos.ConteudosViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaConteudos= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_conteudo_olimpiada, parent, false);
        return new AdapterConteudos.ConteudosViewHolder(viewItemListaConteudos, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterConteudos.ConteudosViewHolder holder, int position) {
        DadosConteudo conteudo = listaConteudos.get(position);

        String valorTitulo = conteudo.getTituloConteudo();
        String valorSubtitulo = conteudo.getSubtituloConteudo();
        String valorTituloSubTitulo = "<b>" + valorTitulo + ": </b> " + valorSubtitulo;

        holder.btnConteudo.setText(Html.fromHtml(valorTituloSubTitulo, Html.FROM_HTML_MODE_COMPACT)); //manter parte do texto em negrito


        String valorCor = conteudo.getCorFundo();
        if (valorCor.equals("Azul")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_azul);
        } else if (valorCor.equals("Ciano")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_ciano);
        } else if (valorCor.equals("Laranja")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_laranja);
        } else if (valorCor.equals("Rosa")) {
            holder.btnConteudo.setBackgroundResource(R.drawable.fundo_btn_olimp_rosa);
        }

        holder.btnConteudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, TextoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                context.startActivity(intent);

                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });


    }

    public class ConteudosViewHolder extends RecyclerView.ViewHolder{
        AppCompatButton btnConteudo;

        public ConteudosViewHolder(@NonNull View itemView, final Context context){
            super(itemView);
            btnConteudo = itemView.findViewById(R.id.btnConteudoOlimpiada);


        }
    }


    public int getItemCount(){return listaConteudos.size();}

    public void atualizarOpcoes(List<DadosConteudo> conteudos){
        this.listaConteudos.clear();
        this.listaConteudos.addAll(conteudos);
        this.notifyDataSetChanged();
    }
}