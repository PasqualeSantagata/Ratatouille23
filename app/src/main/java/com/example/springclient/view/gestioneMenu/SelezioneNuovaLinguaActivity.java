package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.BaseView;
import com.example.springclient.entity.ElementoMenu;

import java.util.Arrays;
import java.util.List;

public class SelezioneNuovaLinguaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, BaseView {

    private Spinner spinnerLingua;
    private Button buttonIndietro;
    private Button buttonOk;
    private List<String> lingue;
    private String linguaSelezionata;
    private ElementoMenu elemento;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("SELEZIONA LINGUA");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_nuova_lingua_gestione_menu);
        elemento = (ElementoMenu) getIntent().getSerializableExtra("elemento");
        initializeComponents();
    }


    @Override
    public void initializeComponents(){
        spinnerLingua = findViewById(R.id.spinnerSelezionaLinguaGestioneMenu);
        buttonIndietro = findViewById(R.id.buttonIndietroSelezionaLinguaGestioneMenu);
        buttonOk = findViewById(R.id.buttonOkSelezionaLinguaGestioneMenu);

        spinnerLingua.setOnItemSelectedListener(this);
        lingue = Arrays.asList(getResources().getStringArray(R.array.array_lingue));
        ArrayAdapter adapterLingue = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lingue);
        adapterLingue.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLingua.setAdapter(adapterLingue);

        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });
        buttonOk.setOnClickListener(view -> {
            Intent nuovaLinguaElemento = new Intent(this, NuovoElementoNuovaLinguaActivity.class);
            nuovaLinguaElemento.putExtra("elemento", elemento);
            nuovaLinguaElemento.putExtra("lingua", linguaSelezionata);
            startActivity(nuovaLinguaElemento);
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        linguaSelezionata = lingue.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Sei sicuro di voler uscire? Annullerai l'inserimento dell'elemento in un'altra lingua",
                view -> super.onBackPressed(),
                view -> dialog.dismiss());
    }

    @Override
    public Context getContext() {
        return getContext();
    }
}
