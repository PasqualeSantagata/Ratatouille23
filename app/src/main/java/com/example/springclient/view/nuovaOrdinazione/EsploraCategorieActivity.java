package com.example.springclient.view.nuovaOrdinazione;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.presenter.CategoriaPresenter;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.util.ArrayList;
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
        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);

        int [] images = {R.drawable.categoria_primi, R.drawable.categoria_secondi, R.drawable.categoria_bevande, R.drawable.categoria_sushi, R.drawable.categoria_pizza, R.drawable.categoria_dessert};
        List<Categoria> categoriaList = new ArrayList<>();
        categoriaList.add(new Categoria("primi", images[0]));
        categoriaList.add(new Categoria("secondi",images[1]));
        categoriaList.add(new Categoria("bevande",images[2]));
        categoriaList.add(new Categoria("sushi",images[3]));
        categoriaList.add(new Categoria("pizza",images[4]));
        categoriaList.add(new Categoria("dessert",images[5]));

        RecycleViewAdapterCategoria adapterCategoria = new RecycleViewAdapterCategoria(this, categoriaList);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);

        //setta i models della recycle view
        categoriaPresenter.getAllCategorie();


        buttonIndietro = findViewById(R.id.buttonIndietroCategorieNuovaOrd);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);

        //magari si fa l'entity categoria cosi si leggono da db
        //e si fa un metodo per associarre imageview alla foto e al nome della categoria
        //cosi da rendere semplice l'aggiunta di categorie da parte dell'admin


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

    }

    public void setCategorie(List<Categoria> categorie){
        //controllare che la lista abbia almeno un elemento
        this.categorie = categorie;
        /*creare un metodo che crea bottoni in automatico in base alle categorie create
        * quelle default non conviene tenerle perchè non saprei come popolarle senza db
        * */
    }

}
