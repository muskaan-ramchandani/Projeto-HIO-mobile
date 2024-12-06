package com.example.helperinolympics.adapter.forum;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.forum.PerguntasForum;
import com.example.helperinolympics.model.forum.RespostasForum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdapterPerguntasForum extends RecyclerView.Adapter<AdapterPerguntasForum.PerguntasForumViewHolder>{
    List<PerguntasForum> listaPerguntasForum;
    List<RespostasForum> listaRespostas= new ArrayList<>();
    Context contexto;
    AdapterRespostasForum adapterRespostas;

    public AdapterPerguntasForum(List<PerguntasForum> perguntas,Context contexto){
        this.listaPerguntasForum=perguntas;
        this.contexto = contexto;
    }

    public AdapterPerguntasForum.PerguntasForumViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
        View viewItemListaPerguntas= LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_perguntas_forum, parent, false);
        return new AdapterPerguntasForum.PerguntasForumViewHolder(viewItemListaPerguntas, parent.getContext());
    }

    public void onBindViewHolder(@NonNull AdapterPerguntasForum.PerguntasForumViewHolder holder, int position) {
        PerguntasForum pergunta = listaPerguntasForum.get(position);

        Bitmap foto = pergunta.getFotoPerfil();
        if(foto==null){
            holder.fotoPerfil.setImageResource(R.drawable.iconeperfilsemfoto);
        }else{
            holder.fotoPerfil.setImageBitmap(foto);
        }

        holder.id= pergunta.getId();
        holder.titulo.setText(pergunta.getTitulo());
        holder.nomeDeUsuario.setText(pergunta.getNomeDeUsuario());

        Date dataPublicacao = pergunta.getDataPublicacao();
        String dataFormatada = "00/00/0000";

        if (dataPublicacao != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            dataFormatada = dateFormat.format(dataPublicacao);
        }

        holder.dataEOlimp.setText(dataFormatada + " • " + pergunta.getOlimpiada());

        holder.pergunta.setText(pergunta.getPergunta());
        holder.qntdRespostas = pergunta.getQntdRespostas();
        String qntdRespostasString = String.valueOf(holder.qntdRespostas);

        if(holder.qntdRespostas>0){
            holder.respostas.setText(qntdRespostasString + " respostas • Clique aqui para exibir");
            holder.respostas.setEnabled(true);
        }else{
            holder.respostas.setText("0 respostas • A pergunta não foi respondida");
            holder.respostas.setEnabled(false);
        }

        configurarRecyclerRespostas(holder.recyclerRespostas, pergunta.getId());

        holder.respostas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isExpanded) {
                    collapse(holder.linearExpansivel, holder.recyclerRespostas);
                    holder.respostas.setText(qntdRespostasString + " respostas • Clique aqui para exibir");
                } else {
                    expand(holder.linearExpansivel, holder.recyclerRespostas);
                    holder.respostas.setText("Respostas para esta pergunta:");
                }
                holder.isExpanded = !holder.isExpanded;
            }
        });

    }

    public class PerguntasForumViewHolder extends RecyclerView.ViewHolder{
        CircleImageView fotoPerfil;
        TextView titulo, nomeDeUsuario, dataEOlimp, pergunta, respostas;
        int id, qntdRespostas;

        boolean isExpanded = false;
        LinearLayout linearExpansivel;
        RecyclerView recyclerRespostas;

        public PerguntasForumViewHolder(@NonNull View itemView, final Context context){
            super(itemView);

            //normal
            fotoPerfil=itemView.findViewById(R.id.fotoPerfilPergunta);
            titulo=itemView.findViewById(R.id.txtTituloPergunta);
            nomeDeUsuario=itemView.findViewById(R.id.txtNomeUser);
            dataEOlimp=itemView.findViewById(R.id.txtDataEOlimpiada);
            pergunta=itemView.findViewById(R.id.txtPergunta);
            respostas=itemView.findViewById(R.id.txtQntdRespostas);

            //escondido
            linearExpansivel = itemView.findViewById(R.id.linearExpansivel);
            recyclerRespostas = itemView.findViewById(R.id.recyclerRespostas);
        }
    }


    public int getItemCount(){return listaPerguntasForum.size();}

    public void atualizarOpcoes(List<PerguntasForum> perguntas){
        this.listaPerguntasForum.clear();
        this.listaPerguntasForum.addAll(perguntas);
        this.notifyDataSetChanged();
    }

    //para exibir RESPOSTAS
    private void expand(View view, RecyclerView recyclerRespostas) {

        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
            recyclerRespostas.setVisibility(View.VISIBLE);
            view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
            view.requestLayout();
        }

        view.post(new Runnable() {
            @Override
            public void run() {
                int widthSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(view.getHeight(), View.MeasureSpec.EXACTLY);
                view.measure(widthSpec, heightSpec);

                int targetHeight = view.getMeasuredHeight();
                view.setVisibility(View.VISIBLE);
                Log.d("Expand", "Target height: " + targetHeight);

                ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
                animator.addUpdateListener(animation -> {
                    int value = (int) animation.getAnimatedValue();
                    view.getLayoutParams().height = value;
                    view.requestLayout();
                });
                animator.setDuration(300);
                animator.start();
            }
        });
    }

    private void collapse(View view, RecyclerView recyclerRespostas) {
        int initialHeight = view.getMeasuredHeight();

        ValueAnimator animator = ValueAnimator.ofInt(initialHeight, 0);
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            view.getLayoutParams().height = value;
            view.requestLayout();

            if (value == 0) {
                view.setVisibility(View.GONE);
                recyclerRespostas.setVisibility(View.GONE);
            }
        });
        animator.setDuration(300);
        animator.start();
    }


    public void configurarRecyclerRespostas(RecyclerView recycler, int idPergunta){
        LinearLayoutManager layoutManager = new LinearLayoutManager(contexto);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapterRespostas= new AdapterRespostasForum(listaRespostas);
        recycler.setLayoutManager(layoutManager);
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapterRespostas);

        new CarregaRespostas(idPergunta).execute();
    }

    private class CarregaRespostas extends AsyncTask<String, Void, String> {
        int idPergunta;

        public CarregaRespostas(int idPergunta){
            this.idPergunta = idPergunta;
        }

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder result = new StringBuilder();

            try {
                String urlString = "https://hio.lat/carregaRespostasDaPergunta.php?idPergunta=" + idPergunta;

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    reader.close();
                }
            } catch (Exception e) {
                Log.e("CarregaRespostas", "Erro na requisição HTTP", e);
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String jsonString) {
            super.onPostExecute(jsonString);
            try {
                Log.d("RespostasJSON", jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);

                JSONArray listaRespostasOlimpiadaJSON = jsonObject.getJSONArray("listaRespostas");

                listaRespostas.clear();

                for (int i = 0; i < listaRespostasOlimpiadaJSON.length(); i++) {
                    JSONObject respostasJson = listaRespostasOlimpiadaJSON.getJSONObject(i);

                    RespostasForum resposta = new RespostasForum(respostasJson.getInt("id"), idPergunta, respostasJson.getString("nomeUsuario"),
                            respostasJson.getString("resposta"));

                    String fotoBase64 = respostasJson.optString("fotoPerfil", null);

                    if (fotoBase64 != null && !fotoBase64.isEmpty()) {
                        Bitmap bitmapFoto = decodeBase64ToBitmap(fotoBase64);
                        resposta.setFotoPerfil(bitmapFoto);
                    } else {
                        resposta.setFotoPerfil(null);
                    }

                    listaRespostas.add(resposta);
                }

                adapterRespostas.notifyDataSetChanged();

            } catch (JSONException e) {
                Log.e("CarregaRespostas", "Erro ao fazer o parse do JSON", e);
            }
        }

    }

    public Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

}
