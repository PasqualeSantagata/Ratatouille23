package com.example.springclient.view.gestioneMenu;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class NuovoElementoNuovaLinguaActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputEditText textInputEditTextLingua;
    private ElementoMenu elemento;
    private String lingua;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVO ELEMENTO DEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_aggiungi_elem_in_nuova_lingua_gestione_menu);
        elemento = (ElementoMenu) getIntent().getSerializableExtra("elemento");
        lingua = getIntent().getStringExtra("lingua");

        initializeComponents();
    }

    private void initializeComponents() {
        textInputLayoutNome = findViewById(R.id.textInputLayout8);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneNuovaLinguaGestioneMenu);
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoNovaLingua);
        textInputEditTextLingua = findViewById(R.id.textInputEditTextLinguaCorrenteInserisciElemSecondaLingua);

        textInputLayoutPrezzo.getEditText().setText(elemento.getPrezzo().toString());
        textInputEditTextLingua.setText(lingua);


    }




}
