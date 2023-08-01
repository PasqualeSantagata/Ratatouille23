package com.example.springclient.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenza;
import com.example.springclient.view.inserimentoNelMenu.StartInserimentoNelMenu;

public class DashboardAdmin extends AppCompatActivity {
    ImageView imageViewMenu;
    ImageView imageViewAnalytics;
    ImageView imageViewNuovaUtenza;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("DASHBOARD");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        initializeComponents();
    }


    private void initializeComponents(){
        imageViewMenu.setOnClickListener(view -> {
            Intent intentMenu = new Intent(this, StartInserimentoNelMenu.class);
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

