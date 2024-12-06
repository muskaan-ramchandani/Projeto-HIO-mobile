package com.example.helperinolympics.materiais;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterFlashcard;
import com.example.helperinolympics.databinding.ActivityFlashcardBinding;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Conteudo;
import com.example.helperinolympics.model.Flashcard;
import com.example.helperinolympics.telas_iniciais.InicioOlimpiadaActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class FlashcardActivity extends AppCompatActivity {
    AdapterFlashcard adapter;
    List<Flashcard> listaFlashcard= new ArrayList<>();

    ActivityFlashcardBinding binding;

    private Aluno alunoCadastrado;
    private Conteudo conteudo;
    private String siglaOlimpiada;

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding=ActivityFlashcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //recebendo os dados da outra tela
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        conteudo = getIntent().getParcelableExtra("conteudo");
        siglaOlimpiada = getIntent().getStringExtra("olimpiada");

        configurarDetalhesTela(siglaOlimpiada, conteudo);

        configurarRecyclerFlashcard();

        findViewById(R.id.btnVoltarDeFlashcardParaInicio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, InicioOlimpiadaActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("siglaOlimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnTexto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, TextoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnQuestionario).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, QuestionarioActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.btnVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, VideoActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                intent.putExtra("conteudo", conteudo);
                intent.putExtra("olimpiada", siglaOlimpiada);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarDetalhesTela(String siglaOlimpiada, Conteudo conteudo) {

        binding.txtTema.setText(conteudo.getTituloConteudo());

        switch (siglaOlimpiada){
            case "OBA":
                binding.txtNomeOlimp.setText("OBA");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgtelescopio);
                break;
            case "OBF":
                binding.txtNomeOlimp.setText("OBF");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgmacacaindo);
                break;
            case "OBI":
                binding.txtNomeOlimp.setText("OBI");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgcomputador);
                break;
            case "OBMEP":
                binding.txtNomeOlimp.setText("OBMEP");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgoperacoesbasicas);
                break;
            case "ONHB":
                binding.txtNomeOlimp.setText("ONHB");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgpapiro);
                break;
            case "OBQ":
                binding.txtNomeOlimp.setText("OBQ");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgtubodeensaio);
                break;
            case "OBB":
                binding.txtNomeOlimp.setText("OBB");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgdna);
                break;
            case "ONC":
                binding.txtNomeOlimp.setText("ONC");
                binding.imgSimboloOlimpiada.setImageResource(R.drawable.imgatomo);
                break;
        }

    }

    public void configurarRecyclerFlashcard(){
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.recyclerviewFlashcard.setLayoutManager(layoutManager);
        binding.recyclerviewFlashcard.setHasFixedSize(true);

        Integer idConteudo = conteudo.getId();

        if (idConteudo != null) {
            new FlashcardsDownload().execute(idConteudo);
        } else {
            Log.d("ERRO_ID_CONTEUDO", "O id do conteúdo está nulo");
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter=new AdapterFlashcard(listaFlashcard, fragmentManager, alunoCadastrado);
        binding.recyclerviewFlashcard.setAdapter(adapter);
    }

    private class FlashcardsDownload extends AsyncTask<Integer, Void, List<Flashcard>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Flashcard> doInBackground(Integer... params) {
            int idConteudo = params[0];
            Log.d("ID_CONTEUDO_RECEBIDO", "Id conteúdo Recebido: " + idConteudo);

            List<Flashcard> flashs = new ArrayList<>();
            try {
                String urlString = "https://hio.lat/carregaFlashcardPorConteudo.php?idConteudoPertencente=" +
                        URLEncoder.encode(String.valueOf(idConteudo), "UTF-8");
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
                    listaFlashcard.addAll(converterParaList(jsonString));
                    flashs.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return flashs;
        }

        @Override
        protected void onPostExecute(List<Flashcard> flashs) {
            super.onPostExecute(flashs);
            if (flashs != null) {
                adapter.atualizarOpcoes(flashs);
                adapter.notifyDataSetChanged();
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

        private List<Flashcard> converterParaList(String jsonString) {
            List<Flashcard> flashs = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("flashcards");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject flashJSON = jsonArray.getJSONObject(i);
                    Flashcard flashcard = new Flashcard();

                    flashcard.setId(flashJSON.getInt("id"));

                    int idConteudo = conteudo.getId();

                    flashcard.setIdConteudoPertencente(idConteudo);
                    flashcard.setTitulo(flashJSON.getString("titulo"));
                    flashcard.setProfQuePostou(flashJSON.getString("profQuePostou"));
                    flashcard.setTexto(flashJSON.getString("texto"));


                    String imgBase64 = flashJSON.getString("imagem");
                    Bitmap bitmapImage = decodeBase64ToBitmap(imgBase64);
                    flashcard.setImagem(bitmapImage);

                    Log.d("Flashcard", flashcard.toString());
                    flashs.add(flashcard);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return flashs;
        }
    }

    public Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }
}
