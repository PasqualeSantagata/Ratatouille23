package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class HomeModificaElemMenuActivity extends AppCompatActivity {

    private Button buttonCerca;
    private Button buttonCategorie;
    private Button buttonIndietro;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Modifica Elemento del Menù");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_home_modifica_elem_gestione_menu);

        initializeComponents();
    }
    private void initializeComponents() {
        buttonCategorie = findViewById(R.id.espoloraCategoriaHomeModificaButton);
        buttonCerca = findViewById(R.id.cercaHomeModificaButton);
        buttonIndietro = findViewById(R.id.buttonIndietroHomeModElemGestioneMenu);

        buttonCategorie.setOnClickListener(view -> {
            Intent categorie = new Intent(this, EsploraCategorieMenuActivity.class);
            startActivity(categorie);
        });
        buttonCerca.setOnClickListener(view -> {
            Intent cerca = new Intent(this, CercaElementoActivity.class);
            startActivity(cerca);
        });
        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });
    }
    @Override
    public void onBackPressed() {
        Intent indietro = new Intent(this, StartGestioneMenuActivity.class);
        startActivity(indietro);
        super.onBackPressed();
    }
}
