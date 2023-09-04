package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.presenter.CategoriaPresenter;
import com.example.springclient.presenter.ElementoMenuPresenter;

import java.util.List;

public class EsploraCategorieActivity extends AppCompatActivity {


    private Ordinazione ordinazione;
    private ImageView imageViewPrimi;
    private ImageView imageViewSecondi;
    private ImageView imageViewBevande;
    private ImageView imageViewSushi;
    private ImageView imageViewPizze;
    private ImageView imageViewDessert;

    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private CategoriaContract.Presenter categoriaPresenter;

    private ElementoMenuContract.Presenter presenter = new ElementoMenuPresenter(this);
    private List<Categoria> categorie;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);
        int nPersone = Integer.valueOf(getIntent().getStringExtra("nPersone"));
        int tavolo = Integer.valueOf(getIntent().getStringExtra("nTavolo"));
        int sala = Integer.valueOf(getIntent().getStringExtra("nSala"));
        categoriaPresenter = new CategoriaPresenter(this);
        ordinazione = new Ordinazione(nPersone, tavolo, sala);
        initializeComponents();
    }

    public void startVisualizzaCategoria(){
        Intent intentVisiualizzaCategoria = new Intent(this, VisualizzaCategoria.class);
        //intentVisiualizzaCategoria.putExtra("ordinazione",ordinazione);
        startActivity(intentVisiualizzaCategoria);
    }

    private void initializeComponents() {

        imageViewPrimi = findViewById(R.id.imgViewPrimiCategorieNuovaOrd);
        imageViewSecondi = findViewById(R.id.imgViewSecondiCategorieNuovaOrd);
        imageViewBevande = findViewById(R.id.imgViewBevandeCategorieNuovaOrd);
        imageViewSushi  = findViewById(R.id.imgViewSushiCategorieNuovaOrd);
        imageViewPizze  = findViewById(R.id.imgViewPizzeCategorieNuovaOrd);
        imageViewDessert  = findViewById(R.id.imgViewDessertCategorieNuovaOrd);
        buttonIndietro = findViewById(R.id.buttonIndietroCategorieNuovaOrd);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);

        //magari si fa l'entity categoria cosi si leggono da db
        //e si fa un metodo per associarre imageview alla foto e al nome della categoria
        //cosi da rendere semplice l'aggiunta di categorie da parte dell'admin
        imageViewPrimi.setOnClickListener(view -> {
            String categoria = "primi";
            //Deve ritornare la lista di tutte le pietanze della categoria
            List<ElementoMenu> elementoMenuList = presenter.getElementiPerCategoria(categoria);
        });

        imageViewSecondi.setOnClickListener(view -> {
            String categoria = "secondi";
            //Deve ritornare la lista di tutte le pietanze della categoria
            List<ElementoMenu> elementoMenuList = presenter.getElementiPerCategoria(categoria);
        });

        imageViewBevande.setOnClickListener(view -> {
            String categoria = "bevande";
            //Deve ritornare la lista di tutte le pietanze della categoria
            List<ElementoMenu> elementoMenuList = presenter.getElementiPerCategoria(categoria);
        });

        imageViewSushi.setOnClickListener(view -> {
            String categoria = "sushi";
            //Deve ritornare la lista di tutte le pietanze della categoria
            List<ElementoMenu> elementoMenuList = presenter.getElementiPerCategoria(categoria);
        });

        imageViewPizze.setOnClickListener(view -> {
            String categoria = "pizze";
            //Deve ritornare la lista di tutte le pietanze della categoria
            List<ElementoMenu> elementoMenuList = presenter.getElementiPerCategoria(categoria);
        });

        imageViewDessert.setOnClickListener(view -> {
            String categoria = "dessert";
            //Deve settare la lista di tutte le pietanze della categoria nelle recycle view e
            //passare la categoria , ma anche le info dell'ordinazione dall'intent della pre. activity
            List<ElementoMenu> elementoMenuList = presenter.getElementiPerCategoria(categoria);
        });
//dopo la list avviano visualizza cateogria

        buttonIndietro.setOnClickListener(view -> {
            Intent intentIndietro = new Intent(this, StartNuovaOrdinazioneActivity.class);
            startActivity(intentIndietro);
        });

       /* buttonRiepilogo.setOnClickListener(view -> {
            if (ordinazione.ordinazioneVuota()){
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_error_one_button);
                TextView errorMessage = findViewById(R.id.textViewMessageDialogueErrorOneBt);
                errorMessage.setText(R.string.dialog_ord_vuota);
                dialog.show();
            } else {
                /* starta il riepilogo ordinazione non vuota
                Intent intentRiepilogo = new Intent(this, Riepilogo.class);
                intentCategoria.putExtra("")

            }

        });
*/
        categoriaPresenter.getAllCategorie();
    }

    public void setCategorie(List<Categoria> categorie){
        //controllare che la lista abbia almeno un elemento
        this.categorie = categorie;
        /*creare un metodo che crea bottoni in automatico in base alle categorie create
        * quelle default non conviene tenerle perchè non saprei come popolarle senza db
        * */
    }

}
