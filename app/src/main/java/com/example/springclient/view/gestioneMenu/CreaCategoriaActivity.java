package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.google.android.material.textfield.TextInputLayout;

public class CreaCategoriaActivity extends AppCompatActivity {
    private ImageView imageViewAggiungiImmagine;
    private TextView textViewAggiungiImmagine;
    private TextInputLayout textInputLayoutNomeCategoria;
    private Button buttonOk;
    private Button buttonIndietro;
    private CategoriaContract.Presenter categoriaPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea_categoria_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setTitle("CREA CATEGORIA");
        categoriaPresenter = new CategoriaMenuPresenter(this);

        initializeComponents();
    }

    private void initializeComponents() {
        imageViewAggiungiImmagine = findViewById(R.id.imageViewCategoriaCreaCategoriaGestioneMenu);
        textInputLayoutNomeCategoria = findViewById(R.id.TextInputLayoutNomeCategoriaCreaCategoriaGestioneMenu);
        textViewAggiungiImmagine = findViewById(R.id.textViewAggiungiImgCreaCategoriaGestioneMenu);
        buttonIndietro = findViewById(R.id.buttonIndietroCreaCategoriaGestioneMenu);
        buttonOk = findViewById(R.id.buttonOkCreaCategoriaGestioneMenu);

        buttonOk.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
            //TODO salva la categoria
            startActivity(intent);
        });

        buttonIndietro.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
            startActivity(intent);
        });
    }
}
