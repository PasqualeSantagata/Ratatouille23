package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class SelezioneNuovaLinguaActivity extends AppCompatActivity {

    private Spinner spinnerLingua;
    private Button buttonIndietro;
    private Button buttonOk;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("SELEZIONA LINGUA");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nuova_lingua_gestione_menu);

        initializeComponents();
    }

    private void initializeComponents(){
        spinnerLingua = findViewById(R.id.spinnerSelezionaLinguaGestioneMenu);
        buttonIndietro = findViewById(R.id.buttonIndietroSelezionaLinguaGestioneMenu);
        buttonOk = findViewById(R.id.buttonOkSelezionaLinguaGestioneMenu);

        buttonIndietro.setOnClickListener(view -> {
            dialogAttenzione();
        });
    }


    private void dialogAttenzione (){
        boolean bool;
        Dialog dialgAttenzione = new Dialog(this);
        dialgAttenzione.setContentView(R.layout.dialog_error_two_button);
        TextView textViewMessage = dialgAttenzione.findViewById(R.id.textViewDialogeError);
        textViewMessage.setText("Sei sicuro di voler uscire? Annullerai l'inserimento dell'elemento in un'altra lingua");
        Button buttonSi = dialgAttenzione.findViewById(R.id.buttonSiDialogeError);
        Button buttonNo = dialgAttenzione.findViewById(R.id.buttonNoDialogeError);
        dialgAttenzione.show();

        buttonSi.setOnClickListener(view -> {
            Intent intentEsci = new Intent(this, StartGestioneMenuActivity.class);
            startActivity(intentEsci);
        });
        buttonNo.setOnClickListener(view -> {
            dialgAttenzione.dismiss();
        });

    }

}
