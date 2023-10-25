package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class FiltraCategoria extends AppCompatActivity {
    private Button buttonNome;
    private Button buttonPrezzo;
    private List<ElementoMenu> elementiMenu;
    private String nome;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtra_categoria_gestione_menu);
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementiMenu");
        nome = getIntent().getStringExtra("nomeCategoria");

        initializeComponents();
    }

    private void initializeComponents() {
        buttonNome = findViewById(R.id.buttonNomeFiltraCategoria);
        buttonPrezzo = findViewById(R.id.buttonPrezzoFiltraCategoria);

        buttonNome.setOnClickListener(view -> {
            Collections.sort(elementiMenu, ElementoMenu.compareNomeDecrescente);
            Intent intent = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
            intent.putExtra("elementi",(Serializable) elementiMenu);
            intent.putExtra("nomeCategoria", nome);
            startActivity(intent);
        });
        buttonPrezzo.setOnClickListener(view -> {
            Collections.sort(elementiMenu, ElementoMenu.comparePrezzoCrescente);
            Intent intent = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
            intent.putExtra("elementi",(Serializable) elementiMenu);
            intent.putExtra("nomeCategoria", nome);
            startActivity(intent);
        });

    }
    
}
