package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.BaseAllergeniDialog;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.contract.VisualizzElementiContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.presenter.VisualizzElementiPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.example.springclient.view.gestioneCategorie.EsploraCategorieActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.List;

public class VisualizzaMenuCategoriaActivity extends AppCompatActivity implements IRecycleViewElementoMenu, VisualizzElementiContract.View, BaseAllergeniDialog,
        OrdinazioneContract.ViewElementiOrdinazione {
    //impostare categoria dall'intent
    private Button buttonIndietro;
    private Button buttonRiepilogo;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutNota;
    private FloatingActionButton fabAggiungiAdOrdinazione;
    private List<Portata> elementiMenu;
    private Ordinazione ordinazione;
    private int elementoSelezionato = -1;
    private TextView textViewAllergeni;
    private boolean b;
    private List<String> allergeni;
    private VisualizzElementiContract.Presenter visualizzaElementiPresenter;
    private OrdinazioneContract.Presenter viewElementiOrdinazionePresenter;
    private String nome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementiMenu = (List<Portata>) getIntent().getSerializableExtra("elementi");
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);
        viewElementiOrdinazionePresenter = new OrdinazionePresenter(this);
        visualizzaElementiPresenter = new VisualizzElementiPresenter(this);
        initializeComponents();
    }

    public void initializeComponents() {
        buttonIndietro = findViewById(R.id.buttonIndietroVisuaCatNuovaOrdinazione);
        buttonRiepilogo = findViewById(R.id.buttonRiepilogoVisualCatNuovaOrdinazione);

        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoVisuaCatNuovaOrdinazione);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneVisuaCatNuovaOrdinazione);
        textInputLayoutNota= findViewById(R.id.textInputLayoutBreveNota);
        textViewAllergeni= findViewById(R.id.textViewAllergeniVisualCatNuovaOrd);

        fabAggiungiAdOrdinazione = findViewById(R.id.fabAggiungiAdOrdinazione);

        //Setto la recycle view
        setElementiMenuRecycleView(elementiMenu);

        fabAggiungiAdOrdinazione.setOnClickListener(view -> {
            if(elementoSelezionato > -1) {
                Portata portata = new Portata(getElementoMenu(),false);
                portata.setBreveNota(textInputLayoutNota.getEditText().getText().toString());
                ordinazione.aggiungiPiatto(portata);
                Toast.makeText(this,"Aggiunto ad ordinazione",Toast.LENGTH_SHORT).show();
            }
        });

        buttonIndietro.setOnClickListener(view -> {
            allergeni = elementiMenu.get(elementoSelezionato).getElementoMenu().getElencoAllergeni();
            viewElementiOrdinazionePresenter.tornaEsploraCategorie();
        });

        buttonRiepilogo.setOnClickListener(view -> {
            if(ordinazione != null && ordinazione.getElementiOrdinati().size() != 0) {
                visualizzaElementiPresenter.mostraRiepilogo();
            }
            else{
                mostraDialogWarningOneBtn("Attenzione l'ordinazione non ha elementi");
            }
        });

        textViewAllergeni.setOnClickListener(view -> {
            if(elementoSelezionato != -1) {
                allergeni = elementiMenu.get(elementoSelezionato).getElementoMenu().getElencoAllergeni();
                dialogAllergeni(this, allergeni, true);
            }else{
                Toast.makeText(this,"Seleziona un elemento",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }


    private void mostraDialogWarningOneBtn(String messaggio){
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_one_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogueErrorOneBt);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> dialogAttenzione.dismiss());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_visualizza_elem_ordinazione, menu);
        return true;
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        if(b){
            menu.findItem(R.id.item_lingue_ordinazione).setEnabled(false);
            menu.findItem(R.id.item_lingua_base_ordinazione).setEnabled(true);
        }else{
            menu.findItem(R.id.item_lingue_ordinazione).setEnabled(true);
            menu.findItem(R.id.item_lingua_base_ordinazione).setEnabled(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_lingue_ordinazione :
                if(elementoSelezionato != -1) {
                    visualizzaElementiPresenter.restituisciTraduzione(elementiMenu.get(elementoSelezionato).getElementoMenu().getId().toString());
                    invalidateOptionsMenu();
                    b = true;
                }
                else{
                    Toast.makeText(this, "Seleziona un elemento per visualizzarne la traduzione", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.item_lingua_base_ordinazione:
                if(elementoSelezionato != -1) {
                    setParameters(getElementoMenu());
                    invalidateOptionsMenu();
                    b = false;
                }
                break;

            case R.id.item_riordina_elem:
                Intent intent = new Intent(this, FiltraCategoriaNuovaOrdinazioneActivity.class);
                intent.putExtra("elementiMenu", (Serializable) elementiMenu);
                intent.putExtra("nomeCategoria", nome);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu) {
        setParameters(elementoMenu);
    }

    @Override
    public void traduzioneAssente() {
        Toast.makeText(this, "Traduzione non disponibile", Toast.LENGTH_LONG).show();
    }



    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    public void setParameters(ElementoMenu elementoMenu){
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    public void setElementiMenuRecycleView(List<Portata> listaElementiMenu){
        RecyclerView recyclerViewPiatti = findViewById(R.id.recycleViewElemMenuNuovaOrdinazione);
        RecycleViewAdapterElementoMenu adapterElementoMenu = new RecycleViewAdapterElementoMenu(this, listaElementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
    }

    private ElementoMenu getElementoMenu(){
        String nota = textInputLayoutNota.getEditText().getText().toString();
        return elementiMenu.get(elementoSelezionato).getElementoMenu();
    }

    @Override
    public void onItemClickRecyclerViewPortata(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position).getElementoMenu());
    }

    @Override
    public void onBackPressed() {
        Intent intentEsploraCategorie = new Intent(this, EsploraCategorieActivity.class);
        intentEsploraCategorie.putExtra("ordinazione",ordinazione);
        startActivity(intentEsploraCategorie);
        super.onBackPressed();
    }



    @Override
    public void mostraRiepilogo() {
        Intent intentRiepilogo = new Intent(this, RiepilogoRiepilogoOrdinazioneActivity.class);
        intentRiepilogo.putExtra("ordinazione", ordinazione);
        startActivity(intentRiepilogo);
    }

    @Override
    public void mostraModifica(ElementoMenu elementoMenu) {

    }

    @Override
    public void setElementi(List<ElementoMenu> elementoMenuList) {

    }

    @Override
    public void rimuoviElemento() {

    }

}
