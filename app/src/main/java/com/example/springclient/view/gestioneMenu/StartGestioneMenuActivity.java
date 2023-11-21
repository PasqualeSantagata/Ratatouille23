package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.view.DashboardAdminActivity;

public class StartGestioneMenuActivity extends AppCompatActivity {
    private Button buttonIndietro;
    private Button buttonAggiungiElementi;
    private Button buttonModificaElementi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Elementi del Menù");
        setContentView(R.layout.activity_start_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeComponents();
    }

    private void initializeComponents(){
        buttonIndietro = findViewById(R.id.buttonIndietroStartInserMenù);
        buttonAggiungiElementi = findViewById(R.id.buttonAggElemMenù);
        buttonModificaElementi = findViewById(R.id.buttonModElemMenù);

        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });

        buttonAggiungiElementi.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
            startActivity(intent);
        });

        buttonModificaElementi.setOnClickListener(view -> {
            Intent intent = new Intent(this, HomeModificaElemMenuActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardAdminActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
