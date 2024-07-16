package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.regex.Pattern;

public class TelaLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String email = ediTextNomeCompleto.getText().toString().trim();
        String password = editTextNumberPasswordSenha.getText().toString().trim();

        if(email.equals("aluno@ifam.edu.br") && password.equals("123")){
            Intent intent = new Intent(MainActivity.this, TelaBemVido.class);
            intent.putExtra("name", email);
            startActivity(intent);



        }
        ediTextNomeCompleto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override

            public void afterTextChanged(Editable s){
                String email = toString();
                Pattern padraoEmail = Patterns.EMAIL_ADDRESS;
                boolean emailValido = padraoEmail.matcher(email).matches();
                String mensagem = "";
                if(!emailValido){
                    mensagem = "E-mail inválido";

                    if(!email.equals("aluno@Ifam.edu.br")){
                        mensagem += ("\n Usuário incorreto");
                        ediTextNomeCompleto.setError(mensagem);

                    }
                    else {

                        ediTextNomeCompleto.setError(null);
                    }
                }{


                }


            }

        });

    }



});


        };
        }


