package com.example.helperinolympics.telas_iniciais;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterConteudos;
import com.example.helperinolympics.adapter.AdapterLivros;
import com.example.helperinolympics.adapter.AdapterProvasAnteriores;
import com.example.helperinolympics.databinding.ActivityTelaOlimpiadaBinding;
import com.example.helperinolympics.model.DadosAluno;
import com.example.helperinolympics.model.DadosConteudo;
import com.example.helperinolympics.model.DadosLivros;
import com.example.helperinolympics.model.DadosOlimpiada;
import com.example.helperinolympics.model.DadosProvasAnteriores;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InicioOlimpiadaActivity extends AppCompatActivity {

    List<DadosConteudo> conteudos = new ArrayList<>();
    List<DadosLivros> livros = new ArrayList<>();
    List<DadosProvasAnteriores> provas = new ArrayList<>();
    AdapterConteudos adapterConteudos;
    AdapterLivros adapterLivros;
    AdapterProvasAnteriores adapterProvasAnteriores;

    DadosAluno alunoCadastrado;
    String siglaOlimpiada;

    ActivityTelaOlimpiadaBinding binding;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityTelaOlimpiadaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");
        siglaOlimpiada = getIntent().getStringExtra("siglaOlimpiada");
        Log.d("SIGLA_RECEBIDA", "Sigla Recebida: " + siglaOlimpiada);

        configurarDetalhesTela(siglaOlimpiada);
        configurarRecyclerConteudos();
        configurarRecyclerLivros();
        configurarRecyclerProvas();

        findViewById(R.id.btnIniciar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioOlimpiadaActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

    }

    private void configurarDetalhesTela(String siglaOlimpiada) {

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

    public void configurarRecyclerConteudos(){

        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapterConteudos= new AdapterConteudos(conteudos, alunoCadastrado);
        binding.recyclerViewConteudosOlimpiada.setLayoutManager(layoutManager);
        binding.recyclerViewConteudosOlimpiada.setHasFixedSize(true);
        binding.recyclerViewConteudosOlimpiada.setAdapter(adapterConteudos);

        if (siglaOlimpiada != null) {
            new ConteudosDownload().execute(siglaOlimpiada);
        } else {
            Log.d("ERRO_SIGLA", "A sigla da Olimpíada está nula");
        }

    }

    private void configurarRecyclerLivros() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapterLivros= new AdapterLivros(livros);
        binding.recyclerViewLivros.setLayoutManager(layoutManager);
        binding.recyclerViewLivros.setHasFixedSize(true);
        binding.recyclerViewLivros.setAdapter(adapterLivros);

        //DADOS FICTICIOS
        Date dataPublicacao1 = null;
        try {
            // Converta a String para Date
            dataPublicacao1 = sdf.parse("22/02/2022");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosLivros dado1 = new DadosLivros(1, R.drawable.imglivrofisica, "O Livro da Física", "Maria Souza" ,"3", dataPublicacao1);
        livros.add(dado1);

        Date dataPublicacao2 = null;
        try {
            // Converta a String para Date
            dataPublicacao2 = sdf.parse("13/03/2013");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosLivros dado2 = new DadosLivros(2, R.drawable.imglivrofisica, "O Livro 2", "Mariaana" ,"7", dataPublicacao2);
        livros.add(dado2);

        Date dataPublicacao3 = null;
        try {
            // Converta a String para Date
            dataPublicacao3 = sdf.parse("01/01/1991");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DadosLivros dado3 = new DadosLivros(3, R.drawable.imglivrofisica, "OUTRO", "Magali" ,"1", dataPublicacao3);
        livros.add(dado3);

    }

    private void configurarRecyclerProvas() {
        LinearLayoutManager layoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adapterProvasAnteriores= new AdapterProvasAnteriores(provas);
        binding.recyclerViewProvasAnteriores.setLayoutManager(layoutManager);
        binding.recyclerViewProvasAnteriores.setHasFixedSize(true);
        binding.recyclerViewProvasAnteriores.setAdapter(adapterProvasAnteriores);


        //DADOS FICTICIOS
        DadosProvasAnteriores dado1 = new DadosProvasAnteriores(1, 2022, 3, true, "demiLov");
        provas.add(dado1);

        DadosProvasAnteriores dado2 = new DadosProvasAnteriores(2, 2019, 2, false, "doroteia");
        provas.add(dado2);

        DadosProvasAnteriores dado3 = new DadosProvasAnteriores(3, 2006, 1, false, "luanSantana");
        provas.add(dado3);

        DadosProvasAnteriores dado4 = new DadosProvasAnteriores(4, 2020, 5, true, "picasso");
        provas.add(dado4);

        DadosProvasAnteriores dado5 = new DadosProvasAnteriores(5, 2015, 1, false, "marianaContaUm");
        provas.add(dado5);

    }

    private class ConteudosDownload extends AsyncTask<String, Void, List<DadosConteudo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<DadosConteudo> doInBackground(String... params) {
            String siglaOlimpiada = params[0];
            Log.d("SIGLA_RECEBIDA", "Sigla Recebida: " + siglaOlimpiada);

            List<DadosConteudo> conteudos = new ArrayList<>();
            try {
                String urlString = "http://192.168.1.9:8086/phpHio/carregaConteudosPorOlimpiada.php?siglaOlimpiadaPertencente=" +
                        URLEncoder.encode(siglaOlimpiada, "UTF-8");
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
                    conteudos.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return conteudos;
        }

        @Override
        protected void onPostExecute(List<DadosConteudo> conteudos) {
            super.onPostExecute(conteudos);
            if (conteudos != null) {
                adapterConteudos.atualizarOpcoes(conteudos);
                adapterConteudos.notifyDataSetChanged();
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

        private List<DadosConteudo> converterParaList(String jsonString) {
            List<DadosConteudo> conteudos = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("conteudos");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject conteudoJSON = jsonArray.getJSONObject(i);
                    DadosConteudo conteudo = new DadosConteudo();

                    conteudo.setId(conteudoJSON.getInt("id"));
                    conteudo.setTituloConteudo(conteudoJSON.getString("titulo"));
                    conteudo.setSubtituloConteudo(conteudoJSON.getString("subtitulo"));

                    String[] cores = {"Rosa", "Azul", "Laranja", "Ciano"};
                    int colorIndex = i % cores.length; // Alterna as cores ciclicamente
                    String corEscolhida = cores[colorIndex];
                    conteudo.setCorFundo(corEscolhida);

                    Log.d("Conteudo", conteudo.toString());
                    conteudos.add(conteudo);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return conteudos;
        }
    }


}
