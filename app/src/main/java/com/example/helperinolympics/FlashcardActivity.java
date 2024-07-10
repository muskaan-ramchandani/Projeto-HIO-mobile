package com.example.helperinolympics;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;


public class FlashcardActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);


        findViewById(R.id.acessoFlashcard3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }

    private void showNotification() {
        FlashcardModelo notificationDialogFragment = new FlashcardModelo();
        notificationDialogFragment.show(getSupportFragmentManager(), "notificationDialog");
    }

}
