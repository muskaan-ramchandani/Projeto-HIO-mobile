package com.example.helperinolympics.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.CadastraHistoricoAsynTask;
import com.example.helperinolympics.R;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.ProvasAnteriores;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class AdapterProvasAnteriores extends RecyclerView.Adapter<AdapterProvasAnteriores.ProvasAnterioresViewHolder>{
    private static final int REQUEST_WRITE_PERMISSION = 100; // Código de requisição para a permissão
    List<ProvasAnteriores> listaProvasAnteriores;
    Aluno alunoCadastrado;

    public AdapterProvasAnteriores(List<ProvasAnteriores> provas, Aluno alunoCadastrado){
        this.listaProvasAnteriores=provas;
        this.alunoCadastrado = alunoCadastrado;
    }

    public AdapterProvasAnteriores.ProvasAnterioresViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaProvasAnteriores= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_acesso_provas_anteriores, parent, false);
        return new AdapterProvasAnteriores.ProvasAnterioresViewHolder(viewItemListaProvasAnteriores, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterProvasAnteriores.ProvasAnterioresViewHolder holder, int position) {
        ProvasAnteriores prova = listaProvasAnteriores.get(position);
        holder.id = prova.getId();

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


    public class ProvasAnterioresViewHolder extends RecyclerView.ViewHolder {
        TextView ano, estado, fase, prof;
        byte[] pdfBytes;
        int id;

        public ProvasAnterioresViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);

            ano = itemView.findViewById(R.id.txtAnoProvaAnterior);
            estado = itemView.findViewById(R.id.txtEstadoProvaAnterior);
            fase = itemView.findViewById(R.id.txtFaseProvaAnterior);
            prof = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (pdfBytes != null && pdfBytes.length > 0) {
                        salvarPdfNoDispositivo(pdfBytes, context);
                        new CadastraHistoricoAsynTask().execute(alunoCadastrado.getEmail(), "ProvaAnterior", String.valueOf(id));
                    } else {
                        Toast.makeText(context, "PDF não disponível.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public int getItemCount() {
        return listaProvasAnteriores.size();
    }

    public void salvarPdfNoDispositivo(byte[] pdfBytes, Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_PERMISSION);
            } else {
                salvarPdf(pdfBytes, context);
            }
        } else {
            salvarPdf(pdfBytes, context);
        }
    }

    private void salvarPdf(byte[] pdfBytes, Context context) {
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
            Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", arquivoPDF);

            intent.setDataAndType(pdfUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NO_HISTORY);

            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "Nenhum visualizador de PDF encontrado.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Arquivo PDF não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    public void atualizarOpcoes(List<ProvasAnteriores> provas) {
        this.listaProvasAnteriores.clear();
        this.listaProvasAnteriores.addAll(provas);
        this.notifyDataSetChanged();
    }

}