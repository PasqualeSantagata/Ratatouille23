package com.example.springclient.view.nuovaOrdinazione;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class VisualizzaCategoriaActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

    //impostare categoria dall'intent
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutAllergeni;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutNota;
    private FloatingActionButton fabAggiungiAdOrdinazione;
    private List<Portata> elementiMenu;

    private Ordinazione ordinazione;
    private int elementoSelezionato = -1;

    //boolean categoriaInclusa; serve a sapere quali categorie ci sono per sapere quali mostrare nel riepilogo, non so se sarà utile quindi lo mantengo commentato per ora
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementiMenu = (List<Portata>) getIntent().getSerializableExtra("elementi");
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        String nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);


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

        fabAggiungiAdOrdinazione.setOnClickListener(view -> {
            if(elementoSelezionato > -1) {
                ordinazione.aggiungiPiatto(new Portata(getElementoMenu(),false));
            }
        });

        buttonIndietro.setOnClickListener(view -> {
            Intent intentEsploraCategorie = new Intent(this, EsploraCategorieActivity.class);
            intentEsploraCategorie.putExtra("ordinazione",ordinazione);
            startActivity(intentEsploraCategorie);

        });

        buttonRiepilogo.setOnClickListener(view -> {
            Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazioneActivity.class);
            intentRiepilogo.putExtra("ordinazione",ordinazione);
            startActivity(intentRiepilogo);
        });

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
    public void setElementiMenuRecycleView(List<Portata> listaElementiMenu){
        RecyclerView recyclerViewPiatti = findViewById(R.id.RecyclerViewPiattiNuovaOrdinazione);
        RecycleViewAdapterElementoMenu adapterElementoMenu = new RecycleViewAdapterElementoMenu(this, listaElementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
    }


    private ElementoMenu getElementoMenu(){
        return elementiMenu.get(elementoSelezionato).getElementoMenu();
    }

    @Override
    public void onItemClick(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position).getElementoMenu());
    }
}
