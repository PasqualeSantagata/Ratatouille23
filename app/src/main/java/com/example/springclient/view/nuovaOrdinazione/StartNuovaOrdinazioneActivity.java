package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.contract.AutenticazioneContract;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.model.entity.Ordinazione;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.example.springclient.presenter.ILogout;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.view.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class StartNuovaOrdinazioneActivity extends AppCompatActivity implements OrdinazioneContract.StartNuovaOrdinazioneViewContract, ILogout {
    private TextInputLayout textInputLayoutNumeroPersone;
    private TextInputLayout textInputLayoutSala;
    private TextInputLayout textInputLayoutTavolo;
    private Button buttonOk;
    private Button buttonIndietro;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private FloatingActionButton fab4;
    private FloatingActionButton fab8;
    private FloatingActionButton fab10;
    private FloatingActionButton fab20;
    private FloatingActionButton fab30;
    private FloatingActionButton fab50;
    private FloatingActionButton fabAdd;
    private FloatingActionButton fabMinus;
    private int numPersoneTavolo = 0;
    private Ordinazione ordinazione;
    private OrdinazioneContract.Presenter ordinazionePresenter;
    private AutenticazioneContract.Presenter autenticazionePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVA ORDINAZIONE");
        setContentView(R.layout.activity_start_nuova_ordinazione);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ordinazionePresenter = new OrdinazionePresenter(this);
        autenticazionePresenter = new AutenticazionePresenter(this);
        inizializzaComponenti();
    }
    @Override
    public void inizializzaComponenti(){
        textInputLayoutNumeroPersone = findViewById(R.id.textInputLayoutNumPersoneStartNuovaOrdinazione);
        EditText etNumPersone = textInputLayoutNumeroPersone.getEditText();
        textInputLayoutSala = findViewById(R.id.textInputLayoutSalaStartNuovaOrdinazione);
        EditText etSala = textInputLayoutSala.getEditText();
        textInputLayoutTavolo = findViewById(R.id.textInputLayoutTavoloStartNuovaOrdinazione);
        EditText etTavolo = textInputLayoutTavolo.getEditText();
        buttonOk = findViewById(R.id.buttonOkStartNuovaOrdinazione);
        buttonIndietro = findViewById(R.id.buttonIndietroStartNuovaOrdinazione);
        fab1 = findViewById(R.id.fab1);
        fab2 = findViewById(R.id.fab2);
        fab4 = findViewById(R.id.fab4);
        fab8 = findViewById(R.id.fab8);
        fab10 = findViewById(R.id.fab10);
        fab20 = findViewById(R.id.fab20);
        fab30 = findViewById(R.id.fab30);
        fab50 = findViewById(R.id.fab50);
        fabAdd = findViewById(R.id.fabPlus);
        fabMinus = findViewById(R.id.fabMinus);

        buttonIndietro.setOnClickListener(view -> tornaIndietro());

        fab1.setOnClickListener(view -> etNumPersone.setText("1"));

        fab2.setOnClickListener(view -> etNumPersone.setText("2"));

        fab4.setOnClickListener(view -> etNumPersone.setText("4"));

        fab8.setOnClickListener(view -> etNumPersone.setText("8"));

        fab10.setOnClickListener(view -> etNumPersone.setText("10"));

        fab20.setOnClickListener(view -> etNumPersone.setText("20"));

        fab30.setOnClickListener(view -> etNumPersone.setText("30"));

        fab50.setOnClickListener(view -> etNumPersone.setText("50"));

        fabAdd.setOnClickListener(view -> {
            Editable numPersone = etNumPersone.getText();
            String nPersone = numPersone.toString();
            if(!nPersone.equals("")) {
                this.numPersoneTavolo = Integer.parseInt(nPersone);
            }
            etNumPersone.setText(Integer.valueOf(this.numPersoneTavolo + 1).toString());
        });

        fabMinus.setOnClickListener(view -> {
            Editable numPersone = etNumPersone.getText();
            String nPersone = numPersone.toString();
            int n = Integer.parseInt(nPersone);
            if(n > 1) {
                etNumPersone.setText(Integer.valueOf(n - 1).toString());
            }
        });

        buttonOk.setOnClickListener(view -> {
            Editable numPersone = etNumPersone.getText();
            String persone = numPersone.toString();


            Editable numTavolo = etTavolo.getText();
            String tavolo = numTavolo.toString();

            Editable numSala = etSala.getText();
            String sala = numSala.toString();

            if(checkFields(persone, tavolo, sala)) {
                Integer n = Integer.valueOf(persone);
                Integer n2 = Integer.valueOf(tavolo);
                Integer n3 = Integer.valueOf(sala);

                Integer nPersone = n;
                Integer ntavolo = n2;
                Integer nsala = n3;

                ordinazione = new Ordinazione(nPersone, ntavolo, nsala);
                ordinazionePresenter.mostraEsploraCategorie(ordinazione);
            }
        });
        buttonIndietro.setOnClickListener(view -> avviaLogout());
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

    private boolean checkFields(String persone, String tavolo, String sala){
        boolean checked = true;

        if(persone.equals("")){
            textInputLayoutNumeroPersone.setError("Inserisci il numero di persone");
            checked = false;
        }
        else{
            textInputLayoutNumeroPersone.setErrorEnabled(false);
        }
        if(tavolo.equals("")){
            textInputLayoutTavolo.setError("Inserisci il numero di tavolo");
            checked = false;
        }
        else{
            textInputLayoutTavolo.setErrorEnabled(false);
        }
        if(sala.equals("")){
            textInputLayoutSala.setError("Inserisci la sala");
            checked = false;
        }
        else{
            textInputLayoutSala.setErrorEnabled(false);
        }

        return checked;
    }

    @Override
    public void onBackPressed() {
        avviaLogout();
    }

    @Override
    public void mostraEsploraCategorie(Ordinazione ordinazione) {
        Intent intentCategorie = new Intent(this, EsploraCategorieActivity.class);
        intentCategorie.putExtra("ordinazione", ordinazione);
        startActivity(intentCategorie);

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
}
