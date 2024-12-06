package com.example.helperinolympics.modelos_sobrepostos;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.helperinolympics.R;
import com.example.helperinolympics.menu.ForumActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.model.forum.PerguntasForum;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CadastrarPergunta extends DialogFragment {
    private Aluno alunoCadastrado;
    private Context contexto;
    private String olimpiadaRelacionada, titulo, pergunta;
    private Date dataAtual;
    private ForumActivity activity;
    private Spinner spinnerOpcoesOlimpiada;

    private boolean spinnerSelecionado = false;

    public CadastrarPergunta(Aluno aluno, Context contexto){
        this.alunoCadastrado = aluno;
        this.contexto = contexto;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_cadastrar_pergunta, container, false);

        activity = (ForumActivity) getActivity();
        spinnerOpcoesOlimpiada = view.findViewById(R.id.spinnerRelacaoComOlimp);

        configuraSpinner(spinnerOpcoesOlimpiada);

        view.findViewById(R.id.btnPublicar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean tudoPreenchido = verificarPreenchimento(view, spinnerSelecionado);

                if(tudoPreenchido){
                    Calendar calendar = Calendar.getInstance();
                    dataAtual = calendar.getTime();

                    PerguntasForum perguntaACadastrar = new PerguntasForum(titulo, alunoCadastrado.getEmail(), pergunta, olimpiadaRelacionada, dataAtual);

                    new CadastraPergunta(perguntaACadastrar).execute();
                    dismiss();
                }else{
                    Toast.makeText(contexto, "Preencha todos os campos para que a pergunta seja cadastrada!", Toast.LENGTH_LONG).show();
                }
            }
        });

        view.findViewById(R.id.btnCancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    private void configuraSpinner(Spinner spinnerOpcoesOlimpiada) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(contexto, R.array.opcoes_Olimpiadas, R.layout.modelo_spinner_opcoes);

        adapter.setDropDownViewResource(R.layout.modelo_spinner_opcoes);
        spinnerOpcoesOlimpiada.setAdapter(adapter);

        // Capturar a opção selecionada
        spinnerOpcoesOlimpiada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Escolha a olimpíada")){
                    spinnerSelecionado = false;
                }else{
                    olimpiadaRelacionada = parent.getItemAtPosition(position).toString();
                    spinnerSelecionado = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinnerSelecionado = false;
            }
        });
    }

    private boolean verificarPreenchimento(View view, boolean spinnerSelecionado) {
        EditText txtTitulo = view.findViewById(R.id.editTextTitulo);
        EditText txtPergunta = view.findViewById(R.id.editTextPergunta);

        titulo = txtTitulo.getText().toString();
        pergunta = txtPergunta.getText().toString();

        if (titulo.isEmpty() || pergunta.isEmpty() || !spinnerSelecionado) {
            return false;
        }else{
            return true;
        }
    }

    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private class CadastraPergunta extends AsyncTask<Void, Void, String> {
        PerguntasForum perguntaForum;
        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada;

        public CadastraPergunta(PerguntasForum perguntaForum) {
            this.perguntaForum = perguntaForum;
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder result = new StringBuilder();
            Log.d("CONEXAO", "Tentando cadastro de pergunta");

            try {
                URL url = new URL("https://hio.lat/cadastraPerguntaAluno.php");
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                conexao.setReadTimeout(1500);
                conexao.setConnectTimeout(500);
                conexao.setRequestMethod("POST");
                conexao.setDoInput(true);
                conexao.setDoOutput(true);
                conexao.connect();
                Log.d("CONEXAO", "Conexão estabelecida");

                dataFormatada = formatoBanco.format(dataAtual);

                String parametros = "emailAluno=" + perguntaForum.getNomeDeUsuario() +
                        "&titulo=" + perguntaForum.getTitulo() +
                        "&pergunta=" + perguntaForum.getPergunta() +
                        "&siglaOlimpiadaRelacionada=" + perguntaForum.getOlimpiada()+
                        "&dataPublicacao=" + dataFormatada;

                OutputStream os = conexao.getOutputStream();
                byte[] input = parametros.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
                os.close();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();

            } catch (Exception e) {
                Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Erro", e.getMessage());
                return null;
            }

            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    JSONObject jsonResponse = new JSONObject(result);
                    String message = jsonResponse.getString("message");

                    Toast.makeText(contexto, message, Toast.LENGTH_LONG).show();

                    if (jsonResponse.getString("status").equals("success")) {
                        if (activity != null) { activity.atualizarDadosPosPublicacao(); }
                        dismiss();
                    }

                } catch (Exception e) {
                    Log.e("Erro JSON", e.getMessage());
                }
            }
        }
    }


}