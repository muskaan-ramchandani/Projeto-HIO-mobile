package com.example.helperinolympics.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.DadosProvasAnteriores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AdapterProvasAnteriores extends RecyclerView.Adapter<AdapterProvasAnteriores.ProvasAnterioresViewHolder>{
    List<DadosProvasAnteriores> listaProvasAnteriores;

    public AdapterProvasAnteriores(List<DadosProvasAnteriores> provas){
        this.listaProvasAnteriores=provas;
    }

    public AdapterProvasAnteriores.ProvasAnterioresViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaProvasAnteriores= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acesso_provas_anteriores, parent, false);
        return new AdapterProvasAnteriores.ProvasAnterioresViewHolder(viewItemListaProvasAnteriores, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterProvasAnteriores.ProvasAnterioresViewHolder holder, int position) {
        DadosProvasAnteriores prova = listaProvasAnteriores.get(position);

        String valorAno= String.valueOf(prova.getAnoProva());
        holder.ano.setText(valorAno);

        String valorEstado;
        if((prova.isEstado())==true){
            valorEstado ="Estado: Respondida";
        }else{
            valorEstado = "Estado: Não respondida";
        }
        holder.estado.setText(valorEstado);

        String fase= String.valueOf(prova.getFase());
        String valorFase= "Fase: " + fase;
        holder.fase.setText(valorFase);

        String valorProf= prova.getUserProf();
        holder.prof.setText(valorProf);

        holder.pdfBytes= prova.getArquivoPdfBytes();
    }


    public class ProvasAnterioresViewHolder extends RecyclerView.ViewHolder{
        TextView ano, estado, fase, prof;
        byte[] pdfBytes;

        public ProvasAnterioresViewHolder(@NonNull View itemView, final Context context){
            super(itemView);

            ano=itemView.findViewById(R.id.txtAnoProvaAnterior);
            estado=itemView.findViewById(R.id.txtEstadoProvaAnterior);
            fase=itemView.findViewById(R.id.txtFaseProvaAnterior);
            prof=itemView.findViewById(R.id.txtUserProf);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pdfBytes != null && pdfBytes.length > 0) {
                        salvarPdfNoDispositivo(pdfBytes, context);
                    } else {
                        Toast.makeText(context, "PDF não disponível.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public int getItemCount(){return listaProvasAnteriores.size();}

    private void salvarPdfNoDispositivo(byte[] pdfBytes, Context context) {
        try {

            File file = new File(context.getExternalFilesDir(null), "prova_olimpiada.pdf");

            // Escrevendo bytes
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(pdfBytes);
            fos.close();

            abrirPdf(file, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirPdf(File arquivoPDF, Context context) {
        if (arquivoPDF.exists()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri pdfUri = Uri.fromFile(arquivoPDF);

            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "Nenhum visualizador de PDF encontrado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Arquivo PDF não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void atualizarOpcoes(List<DadosProvasAnteriores> provas){
        this.listaProvasAnteriores.clear();
        this.listaProvasAnteriores.addAll(provas);
        this.notifyDataSetChanged();
    }

}
