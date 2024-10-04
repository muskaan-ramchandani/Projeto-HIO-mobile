package com.example.helperinolympics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.adapter.AdapterRanking;
import com.example.helperinolympics.databinding.ActivityRankingBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Ranking;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends Activity {
    AdapterRanking adapter;

    ActivityRankingBinding binding;

    List<Ranking> rankingCompleto = new ArrayList<>();
    List<Ranking> rankingForaPodio = new ArrayList<>();
    List<Ranking> rankingPodio = new ArrayList<>();

    Aluno alunoCadastrado;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityRankingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        new RankingDownload().execute();

        binding.btnRetornarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.btnCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RankingActivity.this, CalendarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        configurarRecyclerRanking();
    }

    public void configurarRecyclerRanking(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerViewRanking.setLayoutManager(layoutManager);
        binding.recyclerViewRanking.setHasFixedSize(true);

        //configurando divisória
        Drawable divisorItens = ContextCompat.getDrawable(this, R.drawable.linha_cinza);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.recyclerViewRanking.getContext(),
                new LinearLayoutManager(this).getOrientation());

        if (divisorItens != null) {
            dividerItemDecoration.setDrawable(divisorItens);
        }

        binding.recyclerViewRanking.addItemDecoration(dividerItemDecoration);

        adapter = new AdapterRanking(rankingForaPodio);
        binding.recyclerViewRanking.setAdapter(adapter);
    }

    public void dadosPodio(){
        if(rankingPodio.size() > 0){
            binding.txtUserPosicao1.setText(rankingPodio.get(0).getUser());
            binding.txtQntdPontosPosicao1.setText(String.valueOf(rankingPodio.get(0).getQntdPontos()));
        }
        if(rankingPodio.size() > 1){
            binding.txtUserPosicao2.setText(rankingPodio.get(1).getUser());
            binding.txtQntdPontosPosicao2.setText(String.valueOf(rankingPodio.get(1).getQntdPontos()));
        }
        if(rankingPodio.size() > 2){
            binding.txtUserPosicao3.setText(rankingPodio.get(2).getUser());
            binding.txtQntdPontosPosicao3.setText(String.valueOf(rankingPodio.get(2).getQntdPontos()));
        }
    }


    private class RankingDownload extends AsyncTask<Void, Void, List<Ranking>> {
        int posicao =1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Ranking> doInBackground(Void... voids) {

            try {
                String urlString = "http://192.168.1.11:8086/phpHio/montarRanking.php";
                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    Log.d("DADOS", jsonString);
                    rankingCompleto.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return rankingCompleto;
        }

        @Override
        protected void onPostExecute(List<Ranking> ranking) {
            super.onPostExecute(ranking);
            if (ranking != null) {
                adapter.atualizarOpcoes(ranking);
                adapter.notifyDataSetChanged();

                rankingPodio.clear();
                rankingForaPodio.clear();

                for (Ranking rankingItem : ranking) {
                    if (rankingItem.getPosicao() == 1 || rankingItem.getPosicao() == 2 || rankingItem.getPosicao() == 3) {
                        rankingPodio.add(rankingItem);
                    } else {
                        rankingForaPodio.add(rankingItem);
                    }
                }
                dadosPodio();
            }
        }


        private String converterParaJSONString(InputStream in) {
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream dados = new ByteArrayOutputStream();
            try {
                int qtdBytesLido;
                while ((qtdBytesLido = in.read(buffer)) != -1) {
                    dados.write(buffer, 0, qtdBytesLido);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return dados.toString();
        }

        private List<Ranking> converterParaList(String jsonString) {
            List<Ranking> ranking = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("posicoesRanking");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject rankingJSON = jsonArray.getJSONObject(i);
                    Ranking itemRanking = new Ranking();

                    itemRanking.setPosicao(posicao);
                    itemRanking.setEmail(rankingJSON.getString("email"));
                    itemRanking.setUser(rankingJSON.getString("nomeUsuario"));
                    itemRanking.setQntdPontos(rankingJSON.getInt("pontuacao"));

                    String capaBase64 = rankingJSON.optString("fotoPerfil", null);

                    if(capaBase64!=null){
                        Bitmap bitmapCapa = decodeBase64ToBitmap(capaBase64);
                        itemRanking.setFotoPerfil(bitmapCapa);
                    } else {
                        Drawable drawable = ContextCompat.getDrawable(RankingActivity.this, R.drawable.iconeperfilvazioredonda);
                        Bitmap bitmap = drawableToBitmap(drawable);
                        itemRanking.setFotoPerfil(bitmap);
                    }

                    Log.d("Item ranking", itemRanking.toString());
                    ranking.add(itemRanking);
                    posicao++;
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return ranking;
        }
    }

    public Bitmap decodeBase64ToBitmap(String base64Str) {
        try {
            byte[] decodedString = Base64.decode(base64Str, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
