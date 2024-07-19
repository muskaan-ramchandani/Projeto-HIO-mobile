package com.example.helperinolympics;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ImageView;




public class QuestionarioFinalizarActivity extends Activity {


    ImageView hipoTristeOUFeliz;
    TextView txtViewNumeroCertas, txtViewQuestoesTotais;
    int qntdTotal, qntdAcertos, metadeValor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_finalizar);


        hipoTristeOUFeliz = findViewById(R.id.imgHipoTristeOuFeliz);
        txtViewNumeroCertas= findViewById(R.id.txtNumeroQuestaoCertas);
        txtViewQuestoesTotais= findViewById(R.id.txtQuestoesTotais);


        qntdTotal= 20;
        metadeValor= qntdTotal/2;
        qntdAcertos=Integer.parseInt(txtViewNumeroCertas.getText().toString());


        if(qntdAcertos>metadeValor){
            hipoTristeOUFeliz.setImageResource(R.drawable.hipoalegredeolhosabertos);
        }else if(qntdAcertos<metadeValor){
            hipoTristeOUFeliz.setImageResource(R.drawable.hipoemo);
        }


    }
}
