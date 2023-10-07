package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
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
        setContentView(R.layout.activity_home_modifica_elem_inserimento_nel_menu);
        initializeComponents();

    }


    private void initializeComponents() {
        buttonCategorie = findViewById(R.id.espoloraCategoriaHomeModificaButton);
        buttonCerca = findViewById(R.id.cercaHomeModificaButton);

        buttonCategorie.setOnClickListener(view -> {
            Intent categorie = new Intent(this, EsploraCategorieMenuActivity.class);
            startActivity(categorie);
        });


    }
}
