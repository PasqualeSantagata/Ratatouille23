package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.presenter.ElementoMenuPresenter;

public class EsploraCategorie extends AppCompatActivity {

    //riceve le info dell'ordinazione dalla preced. activity
    Ordinazione ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
    private ImageView imageViewPrimi;
    private ImageView imageViewSecondi;
    private ImageView imageViewBevande;
    private ImageView imageViewSushi;
    private ImageView imageViewPizze;
    private ImageView imageViewDessert;

    private Button buttonIndietro;
    private Button buttonRiepilogo;

    private ElementoMenuContract.Presenter presenter = new ElementoMenuPresenter(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);

        //Da provare, prende le info dall activity Start nuova ordinazione
        Integer persone = ordinazione.getNumeroPersone();


    }

    private void InitializeComponents() {
        imageViewPrimi = findViewById(R.id.imgViewPrimiCategorieNuovaOrd);
        imageViewSecondi = findViewById(R.id.imgViewSecondiCategorieNuovaOrd);
        imageViewBevande = findViewById(R.id.imgViewBevandeCategorieNuovaOrd);
        imageViewSushi  = findViewById(R.id.imgViewSushiCategorieNuovaOrd);
        imageViewPizze  = findViewById(R.id.imgViewPizzeCategorieNuovaOrd);
        imageViewDessert  = findViewById(R.id.imgViewDessertCategorieNuovaOrd);
        buttonIndietro = findViewById(R.id.buttonIndietroCategorieNuovaOrd);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);

        imageViewPrimi.setOnClickListener(view -> {
            String categoria = "primi";
            //Deve ritornare la lista di tutte le pietanze della categoria
            presenter.setElementiPerCategoriaRecycleView(ordinazione, categoria);
        });

        imageViewSecondi.setOnClickListener(view -> {
            String categoria = "secondi";
            //Deve ritornare la lista di tutte le pietanze della categoria
            presenter.setElementiPerCategoriaRecycleView(ordinazione, categoria);
        });

        imageViewBevande.setOnClickListener(view -> {
            String categoria = "bevande";
            //Deve ritornare la lista di tutte le pietanze della categoria
            presenter.setElementiPerCategoriaRecycleView(ordinazione, categoria);
        });

        imageViewSushi.setOnClickListener(view -> {
            String categoria = "sushi";
            //Deve ritornare la lista di tutte le pietanze della categoria
            presenter.setElementiPerCategoriaRecycleView(ordinazione, categoria);
        });

        imageViewPizze.setOnClickListener(view -> {
            String categoria = "pizze";
            //Deve ritornare la lista di tutte le pietanze della categoria
            presenter.setElementiPerCategoriaRecycleView(ordinazione, categoria);
        });

        imageViewDessert.setOnClickListener(view -> {
            String categoria = "dessert";
            //Deve settare la lista di tutte le pietanze della categoria nelle recycle view e
            //passare la categoria , ma anche le info dell'ordinazione dall'intent della pre. activity
            presenter.setElementiPerCategoriaRecycleView(ordinazione, categoria);
        });

        buttonIndietro.setOnClickListener(view -> {
            Intent intentIndietro = new Intent(this, StartNuovaOrdinazione.class);
            startActivity(intentIndietro);
        });

        buttonRiepilogo.setOnClickListener(view -> {
            if (ordinazione.ordinazioneVuota()){
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_ord_vuota_nuova_ordinazione);
                dialog.show();
            } else {
                /* starta il riepilogo ordinazione non vuota
                Intent intentRiepilogo = new Intent(this, Riepilogo.class);
                intentCategoria.putExtra("")
                 */
            }

        });


    }
}
