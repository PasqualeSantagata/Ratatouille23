package com.example.springclient.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.presenter.AdminPresenter;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenzaActivity;
import com.example.springclient.view.inserimentoNelMenu.GestioneMenuActivity;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;

public class DashboardAdmin extends AppCompatActivity {
    private ImageView imageViewMenu;
    private ImageView imageViewAnalytics;
    private ImageView imageViewNuovaUtenza;
    private AdminPresenter adminPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("DASHBOARD");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        adminPresenter = new AdminPresenter(this);
        initializeComponents();




    }


    private void initializeComponents(){
        imageViewMenu = findViewById(R.id.imageViewMenuDash);
        imageViewAnalytics = findViewById(R.id.imageViewAnalyticsDash);
        imageViewNuovaUtenza = findViewById(R.id.imageViewNewUserDash);

        imageViewMenu.setOnClickListener(view -> {
            Intent intentMenu = new Intent(this, GestioneMenuActivity.class);
            startActivity(intentMenu);
           /* Intent intentMenu = new Intent(this, InserisciElementoActivity.class);// aggiungere navigazione
            */
        });

        imageViewAnalytics.setOnClickListener(view -> {
            //TODO

        });

        imageViewNuovaUtenza.setOnClickListener(view -> {
            Intent intentUtenza = new Intent(this, StartNuovaUtenzaActivity.class);
            startActivity(intentUtenza);
        });
    }

}

