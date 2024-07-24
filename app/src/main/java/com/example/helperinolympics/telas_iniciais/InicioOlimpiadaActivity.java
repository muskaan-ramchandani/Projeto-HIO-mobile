package com.example.helperinolympics.telas_iniciais;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperinolympics.R;
import com.example.helperinolympics.adapter.AdapterOlimpiadas;
import com.example.helperinolympics.materiais.VideoActivity;
import com.example.helperinolympics.model.DadosOlimpiada;

import java.util.ArrayList;
import java.util.List;

public class InicioOlimpiadaActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_olimpiada);

        findViewById(R.id.btnConteudo1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicioOlimpiadaActivity.this, VideoActivity.class);
                startActivity(intent);
            }
        });

    }

}
