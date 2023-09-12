package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.springclient.view.adapters.IRecycleViewCategoria;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.util.List;

import retrofit2.Response;

public class EsploraCategorieActivity extends AppCompatActivity implements IRecycleViewCategoria {
    private Ordinazione ordinazione;
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private CategoriaContract.Presenter categoriaPresenter;
    private ElementoMenuContract.Presenter presenter = new ElementoMenuPresenter(this);
    private List<Categoria> categorie;
    //provvisorio
    private List<Categoria> categoriaList;
    private RecycleViewAdapterCategoria adapterCategoria;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);


        categoriaPresenter = new CategoriaPresenter(this);
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        categoriaPresenter.getAllCategorie();
        //initializeComponents();
    }

    public void startVisualizzaCategoria(){
        Intent intentVisiualizzaCategoria = new Intent(this, VisualizzaCategoriaActivity.class);
        //intentVisiualizzaCategoria.putExtra("ordinazione",ordinazione);
        startActivity(intentVisiualizzaCategoria);
    }


    public void initializeComponents() {
        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
/*
        int [] images = {R.drawable.categoria_primi, R.drawable.categoria_secondi, R.drawable.categoria_bevande, R.drawable.categoria_sushi, R.drawable.categoria_pizza, R.drawable.categoria_dessert};
        categoriaList = new ArrayList<>();
        List<ElementoMenu> elementoMenuList = new ArrayList<>();
        List<String> allergeni = new ArrayList<>();
        allergeni.add("nessuno (forse) ehehe");
        elementoMenuList.add(new ElementoMenu("carbonata",2.0F, "un buon piatto cos", allergeni));
       */
        //setta i models della recycle view


        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);


        buttonIndietro = findViewById(R.id.buttonIndietroCategorieNuovaOrd);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);

        //magari si fa l'entity categoria cosi si leggono da db
        //e si fa un metodo per associarre imageview alla foto e al nome della categoria
        //cosi da rendere semplice l'aggiunta di categorie da parte dell'admin


       buttonRiepilogo.setOnClickListener(view -> {
            if (ordinazione.ordinazioneVuota()){
                Dialog dialog = new Dialog(this);
                dialog.setContentView(R.layout.dialog_error_one_button);
                TextView errorMessage = findViewById(R.id.textViewMessageDialogueErrorOneBt);
                errorMessage.setText(R.string.dialog_ord_vuota);
                dialog.show();
            } else {
                // starta il riepilogo ordinazione non vuota
                Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazione.class);
                intentRiepilogo.putExtra("ordinazione",ordinazione);
                startActivity(intentRiepilogo);
            }

        });


    }
    /*
    la foto viene recuperata in due passaggi in caso la connessione fosse lenta
     */
    public void setCategorie(List<Categoria> categorie){
        //controllare che la lista abbia almeno un elemento
        this.categorie = categorie;
        if(!categorie.isEmpty()){
            for(int i = 0; i<categorie.size(); i++){
                categoriaPresenter.getFotoCategoriaById(categorie.get(i), i);
            }
        } else{
            //TODO
        }
        initializeComponents();
    }
    public void notifyAdapter(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }

    @Override
    public void onItemClick(int position) {
        Intent intentVisualizzaCategoria = new Intent(this, VisualizzaCategoriaActivity.class);
        intentVisualizzaCategoria.putExtra("categoria",categorie.get(position));
        //provvisorio
        //intentVisualizzaCategoria.putExtra("categoria",categoriaList.get(position));
        intentVisualizzaCategoria.putExtra("ordinazione", ordinazione);
        startActivity(intentVisualizzaCategoria);
    }

}
