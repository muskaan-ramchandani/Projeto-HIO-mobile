package com.example.helperinolympics.telas_iniciais;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.helperinolympics.R;
import com.example.helperinolympics.materiais.VideoActivity;

public class InicioOlimpiadaActivity extends Activity {
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
