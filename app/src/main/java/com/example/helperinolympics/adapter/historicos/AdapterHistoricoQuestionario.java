package com.example.helperinolympics.adapter.historicos;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.model.questionario.Questao;
import com.example.helperinolympics.model.questionario.Questionario;
import com.example.helperinolympics.modelos_sobrepostos.QuestionarioHistoricoExibir;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AdapterHistoricoQuestionario extends RecyclerView.Adapter<AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder> {
    private List<Questionario> listaQuestionario;
    private Questionario quest;
    private FragmentManager fragmentManager;

    public AdapterHistoricoQuestionario(List<Questionario> listaQuestionario, FragmentManager fragmentManager) {
        this.listaQuestionario = listaQuestionario;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewItemListaHistoricoQuestionario = LayoutInflater.from(parent.getContext()).inflate(R.layout.modelo_lista_materiais, parent, false);
        return new AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder(viewItemListaHistoricoQuestionario);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistoricoQuestionario.QuestionarioHistoricoViewHolder holder, int position) {
        Questionario quest = listaQuestionario.get(position);
        holder.conteudo.setText(quest.getTitulo());
        holder.userProf.setText(quest.getProfessorCadastrou());
    }

    @Override
    public int getItemCount() {
        return listaQuestionario.size();
    }

    public class QuestionarioHistoricoViewHolder extends RecyclerView.ViewHolder {
        TextView conteudo, userProf;
        ArrayList<Questao> listaQuestoes = new ArrayList<>();

        public QuestionarioHistoricoViewHolder(@NonNull View itemView) {
            super(itemView);
            conteudo = itemView.findViewById(R.id.txtConteudo);
            userProf = itemView.findViewById(R.id.txtUserProf);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listaQuestoes.isEmpty()) {
                        Questionario quest = listaQuestionario.get(getAdapterPosition());
                        new QuestoesDownload(quest, listaQuestoes).execute(quest.getId());
                    }
                }
            });
            
        }
    }


    private class QuestoesDownload extends AsyncTask<Integer, Void, List<Questao>> {
        private Questionario quest;
        private ArrayList<Questao> listaQuestoes;

        public QuestoesDownload(Questionario quest, ArrayList<Questao> listaQuestoes) {
            this.quest = quest;
            this.listaQuestoes = listaQuestoes;
        }

        @Override
        protected List<Questao> doInBackground(Integer... params) {
            int idQuestionario = params[0];
            Log.d("ID_QUESTIONARIO_RECEBIDO", "Id questionário Recebido: " + idQuestionario);

            List<Questao> questoes = new ArrayList<>();
            try {
                String urlString = "http://10.0.0.64:8086/phpHio/carregaQuestoesPorQuestionario.php?idQuestionarioPertencente=" +
                        URLEncoder.encode(String.valueOf(idQuestionario), "UTF-8");
                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(5000);
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
                    questoes.addAll(converterParaList(jsonString));
                }

            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questoes;
        }

        @Override
        protected void onPostExecute(List<Questao> questoes) {
            super.onPostExecute(questoes);
            if (questoes != null && !questoes.isEmpty()) {
                listaQuestoes.clear();
                listaQuestoes.addAll(questoes);
                QuestionarioHistoricoExibir notificationDialogFragment = new QuestionarioHistoricoExibir(quest, listaQuestoes);
                notificationDialogFragment.show(fragmentManager, "notificationDialog");
            } else {
                Log.e("ERRO_QUESTOES", "Nenhuma questão carregada");
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

        private List<Questao> converterParaList(String jsonString) {
            List<Questao> questoes = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("questoesDoQuestionario");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject questaoJSON = jsonArray.getJSONObject(i);
                    Questao questao = new Questao();

                    questao.setId(questaoJSON.getInt("id"));
                    questao.setTxtPergunta(questaoJSON.getString("txtPergunta"));
                    questao.setExplicacaoResposta(questaoJSON.getString("explicacaoResposta"));

                    Log.d("Questão  ", questao.toString());
                    questoes.add(questao);
                }
            } catch (Exception e) {
                Log.d("ERRO", e.toString());
            }
            return questoes;
        }
    }
}
