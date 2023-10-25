package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.view.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class StartNuovaOrdinazioneActivity extends AppCompatActivity {

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
    private ImageView imageViewRectangleNumbers;
    private int n = 0;
    private Ordinazione ordinazione;
    private OrdinazionePresenter ordinazionePresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("NUOVA ORDINAZIONE");
        setContentView(R.layout.activity_start_nuova_ordinazione);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ordinazionePresenter = new OrdinazionePresenter(this);
        initializeComponents();
    }

    private void initializeComponents(){
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

        buttonIndietro.setOnClickListener(view -> {

            Dialog dialog = new Dialog(this);
            TextView errorMessage = findViewById(R.id.textViewDialogeWarnTwoBtn);
            errorMessage.setText(R.string.dialog_sicuro_di_uscire);
            dialog.setContentView(R.layout.dialog_warning_two_button);
            dialog.show();

            Button buttonNo = findViewById(R.id.buttonNoDialogWarnTwoBtn);
            Button buttonSi = findViewById(R.id.buttonSiDialogWarnTwoBtn);

            buttonNo.setOnClickListener(view1 -> {
                dialog.dismiss();
            });

            buttonSi.setOnClickListener(view1 -> {
                Intent intentLogOut = new Intent(this, MainActivity.class);
                dialog.dismiss();
                startActivity(intentLogOut);
            });

        });

        fab1.setOnClickListener(view -> {
            etNumPersone.setText("1");
        });

        fab2.setOnClickListener(view -> {
            etNumPersone.setText("2");
        });

        fab4.setOnClickListener(view -> {
            etNumPersone.setText("4");
        });

        fab8.setOnClickListener(view -> {
            etNumPersone.setText("8");
        });

        fab10.setOnClickListener(view -> {
            etNumPersone.setText("10");
        });

        fab20.setOnClickListener(view -> {
            etNumPersone.setText("20");
        });

        fab30.setOnClickListener(view -> {
            etNumPersone.setText("30");
        });

        fab50.setOnClickListener(view -> {
            etNumPersone.setText("50");
        });

        fabAdd.setOnClickListener(view -> {
            Editable numPersone = etNumPersone.getText();
            String nPersone = numPersone.toString();
            if(!nPersone.equals("")) {
                n = Integer.parseInt(nPersone);
            }
            etNumPersone.setText(Integer.valueOf(n + 1).toString());
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
            //TODO Aggiungere controllo sul numero dei tavoli e sale complessivi
            Editable numPersone = etNumPersone.getText();
            String persone = numPersone.toString();
            Integer n = Integer.valueOf(persone);

            Editable numTavolo = etTavolo.getText();
            String tavolo = numTavolo.toString();
            Integer n2 = Integer.valueOf(tavolo);

            Editable numSala = etSala.getText();
            String sala = numSala.toString();
            Integer n3 = Integer.valueOf(sala);

            Integer nPersone = n;
            Integer ntavolo = n2;
            Integer nsala = n3;

            ordinazione = new Ordinazione(nPersone,ntavolo,nsala);

            Intent intentCategorie = new Intent(this, EsploraCategorieActivity.class);
            intentCategorie.putExtra("ordinazione",ordinazione);
            startActivity(intentCategorie);
        });
    }

}
