package com.example.springclient.view.nuovaOrdinazione;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class OrdinazioneConclusaActivity extends AppCompatActivity {
    private ImageView imageViewFeedbackOrdinazione;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ORDINAZIONE CONCLUSA");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_ordinazione_conclusa_nuova_ordinazione);


    }


    private void InitializeComponents() {
        imageViewFeedbackOrdinazione = findViewById(R.id.imageViewFeedbackConculsaNuovaOrdinazione);

        //controllo dal presenter se è andata bene il caricamento dell'ordinazione e in base a questo setto la X
        /*TODO
        if(!presenter.ordinazioneCaricata()){
            imageViewFeedbackOrdinazione.setImageAlpha(R.drawable.ic_not_done); non so se il metodo è giusto
            imageViewFeedbackOrdinazione.setImageResource(R.drawable.ic_not_done);
        } */
    }

}
