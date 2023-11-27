package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.MostraCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.MostraCategoriaMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewCategoria;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;
import com.example.springclient.view.nuovaOrdinazione.StartNuovaOrdinazioneActivity;

import java.io.Serializable;
import java.util.List;

public class EsploraCategorieMenuActivity extends AppCompatActivity implements MostraCategoriaContract.View, IRecycleViewCategoria {
    private List<Categoria> categorie;
    private MostraCategoriaContract.Presenter mostracategoriaMenuPresenter;
    private RecycleViewAdapterCategoria adapterCategoria;
    private Button indietroButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);
        mostracategoriaMenuPresenter = new MostraCategoriaMenuPresenter(this);
        mostracategoriaMenuPresenter.getAllCategorie();
        Button riepilogoButton = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);
        riepilogoButton.setVisibility(View.INVISIBLE);
    }

    public void initializeComponents() {
        indietroButton = findViewById(R.id.buttonIndietroCategorieNuovaOrd);

        indietroButton.setOnClickListener(view -> {
            onBackPressed();
        });
        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);
    }


    @Override
    public void onItemClick(int position) {
        Intent intentVisualizzaCategoria = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
        //Setta la lista degli elementi menu in base alla categoria selezionata, caricandola da db
        Categoria categoria = categorie.get(position);
        categoria.ordinaCategoria();
        intentVisualizzaCategoria.putExtra("elementiCategoria", (Serializable) categorie.get(position).getElementi());
        intentVisualizzaCategoria.putExtra("idCategoria", categoria.getId());
        intentVisualizzaCategoria.putExtra("nomeCategoria", categoria.getNome());
        startActivity(intentVisualizzaCategoria);

    }

    @Override
    public void setCategorie(List<Categoria> categorie){
        this.categorie = categorie;
        if(categorie != null && !categorie.isEmpty()){
            for(int i = 0; i<categorie.size(); i++){
                mostracategoriaMenuPresenter.getFotoCategoriaById(categorie.get(i), i);
            }
        } else{
            Dialog dialog = new Dialog(this);
            mostraDialogErroreOneBtn(dialog, "Nessuna categoria da visualizzare", view -> {
                Intent intent = new Intent(this, HomeModificaElemMenuActivity.class);
                dialog.dismiss();
                startActivity(intent);
            });
        }
        initializeComponents();
    }

    @Override
    public void mostraImmagineCategoria(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }

    @Override
    public void caricamentoCategorieFallito() {
        if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            Dialog dialog = new Dialog(this);
            mostraDialogErroreOneBtn(dialog, "Nessuna categoria da visualizzare", view -> {
                Intent intent = new Intent(this, HomeModificaElemMenuActivity.class);
                dialog.dismiss();
                startActivity(intent);
            });
        }
    }

    @Override
    public Context getContext(){
        return getContext();
    }

    @Override
    public void onBackPressed() {
        Intent homeModifica = new Intent(this, HomeModificaElemMenuActivity.class);
        startActivity(homeModifica);
        super.onBackPressed();
    }
}
