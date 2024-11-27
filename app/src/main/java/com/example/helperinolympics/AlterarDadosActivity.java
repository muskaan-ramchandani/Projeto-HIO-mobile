package com.example.helperinolympics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.helperinolympics.databinding.ActivityAlterarDadosBinding;
import com.example.helperinolympics.menu.ConfiguracoesActivity;
import com.example.helperinolympics.model.Aluno;
import com.example.helperinolympics.modelos_sobrepostos.ConfirmaSenhaAlterarDadosActivity;

import java.io.IOException;

public class AlterarDadosActivity extends AppCompatActivity {

    private ActivityAlterarDadosBinding binding;
    private Aluno alunoCadastrado;

    private boolean fotoAlterada;
    private Bitmap novaFotoPerfil;
    private String novoNomeCompleto, novoNomeUsuario, novoEmail;

    Bitmap foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlterarDadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.fotoAlterada = false;
        alunoCadastrado = getIntent().getParcelableExtra("alunoCadastrado");

        if(alunoCadastrado.getFotoPerfil()==null){
            binding.fotoPerfil.setImageResource(R.drawable.iconeperfilsemfoto);
        }else{
            binding.fotoPerfil.setImageBitmap(alunoCadastrado.getFotoPerfil());
        }

        binding.btnVoltarAsConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlterarDadosActivity.this, ConfiguracoesActivity.class);
                intent.putExtra("alunoCadastrado", alunoCadastrado);
                startActivity(intent);
                finish();
            }
        });

        binding.btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarFoto();
            }
        });

        binding.btnFinalizarAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editTextNomeCompleto.getText().toString().isEmpty()&&binding.editTextNomeUsuario.getText().toString().isEmpty()
                        &&binding.editTextEmail.getText().toString().isEmpty()&& !fotoAlterada){
                    Toast.makeText(AlterarDadosActivity.this, "Nenhuma informação nova foi inserida para que ocorra a alteração", Toast.LENGTH_LONG).show();

                }else{
                    if (fotoAlterada){
                        novaFotoPerfil = foto;
                    }
                    if(!binding.editTextNomeCompleto.getText().toString().isEmpty()){
                        novoNomeCompleto = binding.editTextNomeCompleto.getText().toString();
                    }
                    if(!binding.editTextNomeUsuario.getText().toString().isEmpty()){
                        novoNomeUsuario = binding.editTextNomeUsuario.getText().toString();
                    }
                    if(!binding.editTextEmail.getText().toString().isEmpty()){
                        novoEmail = binding.editTextEmail.getText().toString();
                    }

                    showNotificationConfirmarAlterarDados(alunoCadastrado, novaFotoPerfil, novoNomeCompleto, novoNomeUsuario, novoEmail, AlterarDadosActivity.this);
                }
            }
        });
    }

    private void editarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Escolha sua nova foto de perfil"), 1);
    }

    protected void onActivityResult(int RequestCode, int ResultCode, Intent dados) {
        super.onActivityResult(RequestCode, ResultCode, dados);
        if(ResultCode == AlterarDadosActivity.RESULT_OK){
            if(RequestCode == 1){
                try {
                    foto = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dados.getData());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                binding.fotoPerfil.setImageBitmap(foto);

                fotoAlterada = true;
            }
        }
    }

    public void showNotificationConfirmarAlterarDados(Aluno alunoCadastrado, Bitmap novaFotoPerfil, String novoNomeCompleto, String novoNomeUsuario, String novoEmail, Context contexto){
        ConfirmaSenhaAlterarDadosActivity notificationDialogFragment = new ConfirmaSenhaAlterarDadosActivity(alunoCadastrado, novaFotoPerfil, novoNomeCompleto, novoNomeUsuario, novoEmail, contexto);
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }
}