package com.example.helperinolympics.menu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.helperinolympics.R;
import com.example.helperinolympics.databinding.ActivityForumBinding;
import com.example.helperinolympics.menu.forum_fragments.FragmentSuasPerguntas;
import com.example.helperinolympics.menu.forum_fragments.FragmentTudo;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.Correcao;
import com.example.helperinolympics.modelos_sobrepostos.CadastrarPergunta;
import com.example.helperinolympics.telas_iniciais.InicialAlunoMenuDeslizanteActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumActivity extends AppCompatActivity {
    ActivityForumBinding binding;
    Aluno alunoCadastrado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForumBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        personalizarSearchHint();
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        // Fragment inicial + configurações iniciais
        binding.imgfotoPerfil.setImageResource(R.drawable.iconeperfilvazioredonda);
        atribuirFundoEBotao(binding.btnTudo, R.drawable.fundo_botao_forum_selecionado, R.color.textoSelecionadoForum);
        atribuirFundoEBotao(binding.btnSuasPerguntas, R.drawable.fundo_botao_forum_nao_selecionado, R.color.cinza);

        binding.linearBtnfazerPergunta.setVisibility(View.GONE);

        setFragment(new FragmentTudo());

        findViewById(R.id.btnVoltarATelaInicial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this, InicialAlunoMenuDeslizanteActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();

            }
        });
        findViewById(R.id.btnTudo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atribuirFundoEBotao(findViewById(R.id.btnTudo), R.drawable.fundo_botao_forum_selecionado, R.color.textoSelecionadoForum);
                atribuirFundoEBotao(findViewById(R.id.btnSuasPerguntas), R.drawable.fundo_botao_forum_nao_selecionado, R.color.cinza);

                binding.linearLayoutBarraPesquisaForum.setVisibility(View.VISIBLE);
                binding.linearBtnfazerPergunta.setVisibility(View.GONE);
                setFragment(new FragmentTudo());
            }
        });

        findViewById(R.id.btnSuasPerguntas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ForumActivity", "btnSuasPerguntas clicado");
                atribuirFundoEBotao(findViewById(R.id.btnSuasPerguntas), R.drawable.fundo_botao_forum_selecionado, R.color.textoSelecionadoForum);
                atribuirFundoEBotao(findViewById(R.id.btnTudo), R.drawable.fundo_botao_forum_nao_selecionado, R.color.cinza);

                binding.linearLayoutBarraPesquisaForum.setVisibility(View.GONE);
                binding.linearBtnfazerPergunta.setVisibility(View.VISIBLE);
                setFragment(new FragmentSuasPerguntas());
            }
        });

        findViewById(R.id.btnFazerPergunta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastrarPergunta notificationDialogFragment = new CadastrarPergunta(alunoCadastrado, ForumActivity.this);
                notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
            }
        });
    }

    private void atribuirFundoEBotao(View botao, int fundoResId, int corTextoResId) {
        botao.setBackground(getDrawable(fundoResId));
        if (botao instanceof androidx.appcompat.widget.AppCompatButton) {
            ((androidx.appcompat.widget.AppCompatButton) botao).setTextColor(getColor(corTextoResId)); // Alterado para getColor
        }
    }


    private void personalizarSearchHint() {
        SearchView barraPesquisa = findViewById(R.id.searchViewPerguntas);

        int searchTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText textoBarraPesquisa = barraPesquisa.findViewById(searchTextId);


        textoBarraPesquisa.setHintTextColor(getResources().getColor(R.color.cinza));
        textoBarraPesquisa.setTextColor(getResources().getColor(R.color.black));

        Typeface customFont = ResourcesCompat.getFont(this, R.font.open_sans);
        textoBarraPesquisa.setTypeface(customFont);
        textoBarraPesquisa.setHint("Encontre respostas para sua pergunta");
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentForum, fragment);
        fragmentTransaction.commit();
    }

    private class CarregaCorrecao extends AsyncTask<Void, Void, List<Correcao>> {
        String email;
        Date dataErro;
        int idQuestionarioPertencente;

        public CarregaCorrecao(String email, Date dataErro, int idQuestionarioPertencente) {
            this.email = email;
            this.dataErro = dataErro;
            this.idQuestionarioPertencente = idQuestionarioPertencente;
        }

        @Override
        protected List<Correcao> doInBackground(Void... voids) {
            List<Correcao> correcoes = new ArrayList<>();

            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dataErroFormatada = dateFormat.format(dataErro);
                String urlString = "http://10.0.0.64:8086/phpHio/carregaCorrecao.php?emailAluno=" + URLEncoder.encode(email, "UTF-8") +
                        "&dataErro=" + URLEncoder.encode(dataErroFormatada, "UTF-8") +
                        "&idQuestionarioPertencente=" + URLEncoder.encode(String.valueOf(idQuestionarioPertencente), "UTF-8");

                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(15000);
                conexao.setConnectTimeout(5000);
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.setDoOutput(false);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = conexao.getInputStream();
                    String jsonString = converterParaJSONString(in);
                    Log.d("DADOS", jsonString);

                    correcoes.addAll(converterParaCorrecao(jsonString));
                    listaCorrecao.addAll(converterParaCorrecao(jsonString));
                } else {
                    Log.d("ERRO_CONEXAO", "Erro ao conectar, código de resposta: " + conexao.getResponseCode());
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return correcoes;
        }

        @Override
        protected void onPostExecute(List<Correcao> correcoes) {
            metadeValor = (double) qntdTotal / 2;
            binding.txtNumeroQuestaoCertas.setText(String.valueOf(qntdAcertos));
            binding.txtQuestoesTotais.setText("Questões de " + String.valueOf(qntdTotal));

            if (qntdAcertos == 0) {
                binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipocomraiva);
            } else if (qntdAcertos < metadeValor) {
                binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipoemo);
            } else {
                //qntdAcertos > metadeValor
                binding.imgHipoTristeOuFeliz.setImageResource(R.drawable.hipoalegredeolhosabertos);
            }

            correcaoAdapter.notifyDataSetChanged();

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

        private List<Correcao> converterParaCorrecao(String jsonString) {
            List<Correcao> correcoes = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);

                int totalErros = jsonObject.getInt("totalErros");
                qntdTotal = jsonObject.getInt("totalQuestoes");
                qntdAcertos = qntdTotal - totalErros;

                JSONArray jsonArray = jsonObject.getJSONArray("questoesComErros");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject correcaoJSON = jsonArray.getJSONObject(i);

                    Correcao correcao = new Correcao();
                    correcao.setExplicacao(correcaoJSON.getString("explicacaoResposta"));
                    correcao.setPergunta(correcaoJSON.getString("txtPergunta"));
                    correcao.setAlternativaCorreta(correcaoJSON.getString("alternativaCorreta"));

                    Log.d("Correcao", correcao.toString());
                    correcoes.add(correcao);
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return correcoes;
        }
    }

}
