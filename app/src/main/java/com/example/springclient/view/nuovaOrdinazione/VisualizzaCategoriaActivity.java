package com.example.springclient.view.nuovaOrdinazione;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class VisualizzaCategoriaActivity extends AppCompatActivity {

    //impostare categoria dall'intent
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutAllergeni;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutNota;
    private FloatingActionButton fabAggiungiAdOrdinazione;

    //boolean categoriaInclusa; serve a sapere quali categorie ci sono per sapere quali mostrare nel riepilogo, non so se sarà utile quindi lo mantengo commentato per ora
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("PRIMI"); nome della cat. deve venire da un intent passato dal presenter
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);


    }

    private void InitializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroVisuaCatNuovaOrdinazione);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoVisuaCatNuovaOrdinazione);

        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoVisuaCatNuovaOrdinazione);
        textInputLayoutAllergeni = findViewById(R.id.textInputLayoutAllergeniVisuaCatNuovaOrdinazione);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneVisuaCatNuovaOrdinazione);
        textInputLayoutNota = findViewById(R.id.textInputLayoutBreveNotaVisuaCatNuovaOrdinazione);

        fabAggiungiAdOrdinazione = findViewById(R.id.fabAggiungiAdOrdinazione);

    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    //usato dopo per settare l'elem della recycle view
    public void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        //Attenzione controllare il to string default di List
        setTextInputLayoutText(textInputLayoutAllergeni, elementoMenu.getElencoAllergeni().toString());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
        setTextInputLayoutText(textInputLayoutNota, elementoMenu.getBreveNota());
    }

    /*
    TODO metodo che prende la lista degli elm, menu
     (passati dal presenter) e li "sistema" nella recycle view,
    e che setta i campi del singolo elmento, probabilmente si fa con set on click sull'adapter ma è da vedere
     */
    public void setElementiMenuRecycleView(List<ElementoMenu> listaElementiMenu){
        RecyclerView recyclerViewPiatti = findViewById(R.id.RecyclerViewPiattiNuovaOrdinazione);
        RecycleViewAdapterElementoMenu adapterElementoMenu = new RecycleViewAdapterElementoMenu(this, listaElementiMenu);

        //adapterElementoMenu.onBindViewHolder();
    }


}
