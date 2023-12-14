package com.example.springclient.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.GestioneElementiContract;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.example.springclient.presenter.GestioneElementiPresenter;
import com.example.springclient.presenter.ILogout;
import com.example.springclient.view.gestioneElementiMenu.HomeModificaElementoMenuViewActivity;
import com.example.springclient.view.inserisciElemento.HomeNuovoElementoActivity;

public class StartGestioneMenuActivity extends AppCompatActivity implements GestioneElementiContract.StartGestioneMenuViewContract, ILogout {
    private Button buttonIndietro;
    private Button buttonAggiungiElementi;
    private Button buttonModificaElementi;
    private GestioneElementiContract.Presenter gestioneElementiPresenter;
    private SharedPreferences pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("GESTIONE MENÙ");
        setContentView(R.layout.activity_start_gestione_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        pref = getSharedPreferences("ruoloPref", Context.MODE_PRIVATE);
        String ruolo = getIntent().getStringExtra("ruolo");
        if(ruolo != null){
            pref.edit().putString("ruolo", ruolo).apply();
        }
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
        Intent intent = new Intent(this, HomeModificaElementoMenuViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void mostraHomeNuovoElemento(){
        Intent intent = new Intent(this, HomeNuovoElementoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        String ruolo = pref.getString("ruolo", "");
        if(!ruolo.equals("SUPERVISORE")) {
            Intent intent = new Intent(this, DashboardAdminActivity.class);
            startActivity(intent);
        }else{
            pref.edit().putString("ruolo", "").apply();
            avviaLogout();
        }
    }

    @Override
    public void avviaLogout() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog,"Sicuro di voler uscire?",
                view -> {
                    AutenticazionePresenter presenter = new AutenticazionePresenter(this);
                    presenter.logOutUtente();
                },
                view -> dialog.dismiss());
    }

    @Override
    public void logOutAvvenutoConSuccesso() {
        Intent intent = new Intent(this, MainActivity.class);
        pref.edit().putString("ruolo", "").apply();
        startActivity(intent);
    }

    @Override
    public void logOutFallito() {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Errore di connessione impossibile terminare la tessione", view -> dialog.dismiss());
    }
}
