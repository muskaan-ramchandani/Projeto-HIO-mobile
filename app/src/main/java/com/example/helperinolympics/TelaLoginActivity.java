package com.example.helperinolympics;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class TelaLoginActivity extends AppCompatActivity {

    EditText editTextUser, editTextInserirSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextUser=findViewById(R.id.editTextNomeUser);
        editTextInserirSenha=findViewById(R.id.editTextSenha);
        String usuario = editTextUser.getText().toString().trim();
        String senha = editTextInserirSenha.getText().toString().trim();

        editTextUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override

            public void afterTextChanged(Editable s) {
                String usuario = toString();
                //if senha e user não coincidirem no banco = erro; else if coincidirem = prosseguir
            }

        });

    }
}
