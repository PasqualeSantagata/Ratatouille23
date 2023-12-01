package com.example.springclient.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.BaseView;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.example.springclient.presenter.ILogout;
import com.example.springclient.view.analyticsView.StatisticheActivity;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenzaActivity;

public class  DashboardAdminActivity extends AppCompatActivity implements ILogout, BaseView {
    private ImageView imageViewMenu;
    private ImageView imageViewAnalytics;
    private ImageView imageViewNuovaUtenza;
    private AutenticazionePresenter autenticazionePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("DASHBOARD");
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dashboard_admin);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        autenticazionePresenter = new AutenticazionePresenter(this);
        initializeComponents();
    }


    public void initializeComponents(){
        imageViewMenu = findViewById(R.id.imageViewMenuDash);
        imageViewAnalytics = findViewById(R.id.imageViewAnalyticsDash);
        imageViewNuovaUtenza = findViewById(R.id.imageViewNewUserDash);
        Button buttonEsci = findViewById(R.id.buttonEsciDash);

        imageViewMenu.setOnClickListener(view -> {
            Intent intentMenu = new Intent(this, StartGestioneMenuActivity.class);
            startActivity(intentMenu);
        });

        imageViewAnalytics.setOnClickListener(view -> {
            Intent intentStatistiche = new Intent(this, StatisticheActivity.class);
            startActivity(intentStatistiche);
        });

        imageViewNuovaUtenza.setOnClickListener(view -> {
            Intent intentUtenza = new Intent(this, StartNuovaUtenzaActivity.class);
            startActivity(intentUtenza);
        });

        buttonEsci.setOnClickListener(view -> avviaLogout());
    }

    @Override
    public void tornaIndietro() {

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
    public void avviaLogout() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog,"Sicuro di voler uscire?",
                view -> autenticazionePresenter.logOutUtente(),
                view -> dialog.dismiss());
    }

    @Override
    public void logOutAvvenutoConSuccesso() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void logOutFallito() {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Errore di connessione impossibile terminare la tessione", view -> dialog.dismiss());
    }

    @Override
    public void onBackPressed() {
        avviaLogout();
    }
}

