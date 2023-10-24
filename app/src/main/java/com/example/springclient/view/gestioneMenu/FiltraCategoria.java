package com.example.springclient.view.gestioneMenu;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;

public class FiltraCategoria extends AppCompatActivity {
    private Button buttonNome;
    private Button buttonPrezzo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_categoria_gestione_menu);


        initializeComponents();
    }

    private void initializeComponents() {
        buttonNome = findViewById(R.id.buttonNomeFiltraCategoria);
        buttonPrezzo = findViewById(R.id.buttonPrezzoFiltraCategoria);

        buttonNome.setOnClickListener(view -> {

        });
        buttonPrezzo.setOnClickListener(view -> {

        });
    }


}
