package com.example.springclient.view.gestioneMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewCategoria;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;

import java.io.Serializable;
import java.util.List;

public class EsploraCategorieMenuActivity extends AppCompatActivity implements CategoriaContract.View, IRecycleViewCategoria {
    private List<Categoria> categorie;
    private CategoriaContract.Presenter categoriaMenuPresenter;
    private RecycleViewAdapterCategoria adapterCategoria;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);
        categoriaMenuPresenter = new CategoriaMenuPresenter(this);
        categoriaMenuPresenter.getAllCategorie();

    }

    public void setCategorie(List<Categoria> categorie){
        //controllare che la lista abbia almeno un elemento
        this.categorie = categorie;
        if(!categorie.isEmpty()){
            for(int i = 0; i<categorie.size(); i++){
                categoriaMenuPresenter.getFotoCategoriaById(categorie.get(i), i);
            }
        } else{
            //TODO
        }
        initializeComponents();
    }

    public void initializeComponents() {

        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);


    }

    public void notifyAdapter(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }


    @Override
    public void onItemClick(int position) {
        Intent intentVisualizzaCategoria = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
        //Setta la lista degli elementi menu in base alla categoria selezionata, caricandola da db
        List<ElementoMenu> elementi = categorie.get(position).getElementi();
        intentVisualizzaCategoria.putExtra("elementi", (Serializable) elementi);
        startActivity(intentVisualizzaCategoria);

    }
}
