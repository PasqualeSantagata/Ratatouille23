package com.example.springclient.view.gestioneElementiMenu;

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
import com.example.springclient.view.StartGestioneMenuActivity;
import com.example.springclient.view.gestioneCategorie.EsploraCategorieMenuActivity;

public class HomeModificaElemMenuActivity extends AppCompatActivity implements GestioneElementiContract.HomeModificaElemMenu {

    private Button buttonCerca;
    private Button buttonCategorie;
    private Button buttonIndietro;
    private GestioneElementiContract.Presenter gestioneElementiPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Modifica Elemento del Menù");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_home_modifica_elem_gestione_menu);
        gestioneElementiPresenter = new GestioneElementiPresenter(this);
        inizializzaComponenti();
    }

    @Override
    public void inizializzaComponenti() {
        buttonCategorie = findViewById(R.id.espoloraCategoriaHomeModificaButton);
        buttonCerca = findViewById(R.id.cercaHomeModificaButton);
        buttonIndietro = findViewById(R.id.buttonIndietroHomeModElemGestioneMenu);

        buttonCategorie.setOnClickListener(view -> gestioneElementiPresenter.mostraEsploraCategorie());
        buttonCerca.setOnClickListener(view -> gestioneElementiPresenter.mostraCercaElemento());
        buttonIndietro.setOnClickListener(view -> gestioneElementiPresenter.mostraStartGestioneMenu());
    }

    @Override
    public void mostraEsploraCategorie(){
        Intent categorie = new Intent(this, EsploraCategorieMenuActivity.class);
        startActivity(categorie);
    }

    @Override
    public void mostraCercaElemento(){
        Intent cerca = new Intent(this, CercaElementoActivity.class);
        startActivity(cerca);
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
    public void onBackPressed() {
        Intent indietro = new Intent(this, StartGestioneMenuActivity.class);
        startActivity(indietro);
        super.onBackPressed();
    }
}
