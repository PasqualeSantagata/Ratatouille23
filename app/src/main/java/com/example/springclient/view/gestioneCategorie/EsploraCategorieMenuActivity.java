package com.example.springclient.view.gestioneCategorie;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.MostraCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.presenter.MostraCategoriaMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewEventi;
import com.example.springclient.view.adapters.RecycleViewAdapterCategoria;
import com.example.springclient.view.gestioneElementiMenu.HomeModificaElemMenuActivity;

import java.io.Serializable;
import java.util.List;

public class EsploraCategorieMenuActivity extends AppCompatActivity implements MostraCategoriaContract.View, IRecycleViewEventi {
    private List<Categoria> categorie;
    private MostraCategoriaContract.Presenter mostracategoriaMenuPresenter;
    private RecycleViewAdapterCategoria adapterCategoria;
    private Button indietroButton;
    private Categoria categoria;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("CATEGORIE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_esplora_categorie_nuova_ordinazione);
        progressBar = findViewById(R.id.progressBarEsploraCategorieMenu);
        progressBar.setVisibility(View.INVISIBLE);
        mostracategoriaMenuPresenter = new MostraCategoriaMenuPresenter(this);
        mostracategoriaMenuPresenter.getAllCategorie();
        Button riepilogoButton = findViewById(R.id.buttonRiepilogoCategorieNuovaOrd);
        riepilogoButton.setVisibility(View.INVISIBLE);

    }
    @Override
    public void inizializzaComponenti() {
        indietroButton = findViewById(R.id.buttonIndietroCategorieNuovaOrd);


        indietroButton.setOnClickListener(view -> mostracategoriaMenuPresenter.tronaHomeModificaElementiMenu());
        RecyclerView recyclerViewCategorie = findViewById(R.id.RecycleViewCategorie);
        adapterCategoria = new RecycleViewAdapterCategoria(this, categorie, this);
        recyclerViewCategorie.setAdapter(adapterCategoria);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewCategorie.setLayoutManager(horizontal);
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraPorgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void nascondiProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isVisibile() {
       return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    @Override
    public void mostraVisualizzaElementiDellaCategoria(){
        if(categoria.getElementi().isEmpty()){
            Toast.makeText(this,"Categoria attualmente vuota",Toast.LENGTH_LONG).show();
        }
        else {
            Intent intentVisualizzaCategoria = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
            intentVisualizzaCategoria.putExtra("elementiCategoria", (Serializable) categoria.getElementi());
            intentVisualizzaCategoria.putExtra("idCategoria", categoria.getId());
            intentVisualizzaCategoria.putExtra("nomeCategoria", categoria.getNome());
            startActivity(intentVisualizzaCategoria);
        }
    }

    @Override
    public void onItemClickRecyclerView(int position) {
        categoria = categorie.get(position);
        categoria.ordinaCategoria();
        mostracategoriaMenuPresenter.mostraElementiDellaCategoria();
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
        inizializzaComponenti();
    }

    @Override
    public void mostraImmagineCategoria(int posizione){
        adapterCategoria.notifyItemChanged(posizione);
    }

    @Override
    public void caricamentoCategorieFallito() {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Nessuna categoria da visualizzare", view -> {
            Intent intent = new Intent(this, HomeModificaElemMenuActivity.class);
            dialog.dismiss();
            startActivity(intent);
        });

    }

    @Override
    public void apriRiepilogo() {

    }

    @Override
    public void onBackPressed() {
        Intent homeModifica = new Intent(this, HomeModificaElemMenuActivity.class);
        startActivity(homeModifica);
        super.onBackPressed();
    }


}
