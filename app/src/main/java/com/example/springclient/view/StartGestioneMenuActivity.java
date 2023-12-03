package com.example.springclient.view;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.GestioneElementiContract;
import com.example.springclient.presenter.GestioneElementiPresenter;
import com.example.springclient.view.gestioneElementiMenu.HomeModificaElemMenuActivity;
import com.example.springclient.view.inserisciElemento.HomeNuovoElementoActivity;

public class StartGestioneMenuActivity extends AppCompatActivity implements GestioneElementiContract.StartGestioneMenuView {
    private Button buttonIndietro;
    private Button buttonAggiungiElementi;
    private Button buttonModificaElementi;
    private GestioneElementiContract.Presenter gestioneElementiPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Elementi del Menù");
        setContentView(R.layout.activity_start_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gestioneElementiPresenter = new GestioneElementiPresenter(this);
        inizializzaComponenti();
    }

    @Override
    public void inizializzaComponenti(){
        buttonIndietro = findViewById(R.id.buttonIndietroStartInserMenù);
        buttonAggiungiElementi = findViewById(R.id.buttonAggElemMenù);
        buttonModificaElementi = findViewById(R.id.buttonModElemMenù);

        buttonIndietro.setOnClickListener(view -> gestioneElementiPresenter.tornaDashboardAdmin());

        buttonAggiungiElementi.setOnClickListener(view -> gestioneElementiPresenter.mostraHomeNuovoElemento());

        buttonModificaElementi.setOnClickListener(view -> gestioneElementiPresenter.mostraHomeModificaElementoMenu());

    }
    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraPorgressBar() {

    }

    @Override
    public void nascondiProgressBar() {

    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    public void mostraHomeModificaElementoMenu() {
        Intent intent = new Intent(this, HomeModificaElemMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void mostraHomeNuovoElemento(){
        Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, DashboardAdminActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}
