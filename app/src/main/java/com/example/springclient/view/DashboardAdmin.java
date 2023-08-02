package com.example.springclient.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.Utente;
import com.example.springclient.presenter.UtentePresenter;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenza;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;
import com.example.springclient.view.inserimentoNelMenu.StartInserimentoNelMenu;

public class DashboardAdmin extends AppCompatActivity {
    private ImageView imageViewMenu;
    private ImageView imageViewAnalytics;
    private ImageView imageViewNuovaUtenza;
    private UtentePresenter utentePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("DASHBOARD");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        utentePresenter = new UtentePresenter(this);
        initializeComponents();
    }


    private void initializeComponents(){
        imageViewMenu = findViewById(R.id.imageViewMenuDash);
        imageViewAnalytics = findViewById(R.id.imageViewAnalyticsDash);
        imageViewNuovaUtenza = findViewById(R.id.imageViewNewUserDash);
        imageViewMenu.setOnClickListener(view -> {
            Intent intentMenu = new Intent(this, InserisciElementoActivity.class);// aggiungere navigazione
            startActivity(intentMenu);
        });

        imageViewAnalytics.setOnClickListener(view -> {
            //TODO
        });

        imageViewNuovaUtenza.setOnClickListener(view -> {
            Intent intentUtenza = new Intent(this, StartNuovaUtenza.class);
            startActivity(intentUtenza);
        });
    }


}

