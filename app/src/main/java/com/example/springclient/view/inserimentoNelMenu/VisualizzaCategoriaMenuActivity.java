package com.example.springclient.view.inserimentoNelMenu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterGestioneElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class VisualizzaCategoriaMenuActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

    //impostare categoria dall'intent
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutAllergeni;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutNota;
    private FloatingActionButton fabAggiungiAdOrdinazione;
    private List<ElementoMenu> elementiMenu;
    private RecycleViewAdapterGestioneElementoMenu adapterElementoMenu;
    private int elementoSelezionato = -1;
    private ElementoMenuPresenter elementoMenuPresenter;

    //boolean categoriaInclusa; serve a sapere quali categorie ci sono per sapere quali mostrare nel riepilogo, non so se sarà utile quindi lo mantengo commentato per ora
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementi");
        String nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);
        elementoMenuPresenter = new ElementoMenuPresenter(this);

        initializeComponents();

    }

    private void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroVisuaCatNuovaOrdinazione);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoVisualCatNuovaOrdinazione);

        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoVisuaCatNuovaOrdinazione);
        textInputLayoutAllergeni = findViewById(R.id.textInputLayoutAllergeniVisuaCatNuovaOrdinazione);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneVisuaCatNuovaOrdinazione);

        fabAggiungiAdOrdinazione = findViewById(R.id.fabAggiungiAdOrdinazione);

        //Setto la recycle view
        setElementiMenuRecycleView(elementiMenu);

    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    //usato dopo per settare l'elem della recycle view
    public void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        //Attenzione controllare il to string default di List
//        setTextInputLayoutText(textInputLayoutAllergeni, elementoMenu.getElencoAllergeni().toString());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    /*
    TODO metodo che prende la lista degli elm, menu
     e li "sistema" nella recycle view,
     */
    public void setElementiMenuRecycleView(List<ElementoMenu> listaElementiMenu){
        RecyclerView recyclerViewPiatti = findViewById(R.id.RecyclerViewPiattiNuovaOrdinazione);
        adapterElementoMenu = new RecycleViewAdapterGestioneElementoMenu(this, listaElementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setItemAnimator(null);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
    }


    private ElementoMenu getElementoMenu(){
        return elementiMenu.get(elementoSelezionato);
    }

    public void mostraTraduzione(ElementoMenu elementoMenu){
        Log.d("Traduzione: ", elementoMenu.getNome() + " " + elementoMenu.getDescrizione());
    }



    @Override
    public void onItemClick(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position));
        elementoMenuPresenter.restituisciTraduzione(elementiMenu.get(position).getId().toString());

    }

    @Override
    public void onButtonDeleted(int position) {
        elementoSelezionato = position;
        elementoMenuPresenter.rimuoviElemento(elementiMenu.get(position).getId().toString());
    }

    public void cancellaElemento(){
        elementiMenu.remove(elementoSelezionato);
        adapterElementoMenu.notifyItemChanged(elementoSelezionato);
    }
}
