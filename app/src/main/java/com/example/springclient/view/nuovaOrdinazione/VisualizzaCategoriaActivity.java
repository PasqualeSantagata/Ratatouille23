package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

public class VisualizzaCategoriaActivity extends AppCompatActivity implements IRecycleViewElementoMenu, ElementoMenuContract.View {
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
    //Dialog allergeni
    private CheckBox checkBoxArachidi, checkBoxAnidrideSolforosa, checkBoxCrostacei, checkBoxFruttaGuscio,
            checkBoxGlutine, checkBoxLatte, checkBoxLupini, checkBoxMolluschi, checkBoxPesce,
            checkBoxSedano, checkBoxSenape, checkBoxSesamo, checkBoxSoia, checkBoxUova;
    private Button buttonOkDialog;
    private List<String> allergeni;
    private List<CheckBox> checkBoxes;
    private ElementoMenuPresenter elementoMenuPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elementiMenu = (List<Portata>) getIntent().getSerializableExtra("elementi");
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        String nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_visualizza_categoria_nuova_ordinazione);

        elementoMenuPresenter = new ElementoMenuPresenter(this);
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
                ordinazione.aggiungiPiatto(new Portata(getElementoMenu(),false));
            }
        });

        buttonIndietro.setOnClickListener(view -> {
            onBackPressed();
        });

        buttonRiepilogo.setOnClickListener(view -> {
            Intent intentRiepilogo = new Intent(this, RiepilogoOrdinazioneActivity.class);
            intentRiepilogo.putExtra("ordinazione",ordinazione);
            startActivity(intentRiepilogo);
        });

        textViewAllergeni.setOnClickListener(view -> {
            dialogAllergeni();
        });
    }

    public void listenerAllergeni(){
        allergeni = elementiMenu.get(elementoSelezionato).getElementoMenu().getElencoAllergeni();
        if(allergeni == null)
            allergeni = new ArrayList<>();
        checkBoxes = new ArrayList<>();
        checkBoxes.add(checkBoxArachidi);
        checkBoxes.add(checkBoxAnidrideSolforosa);
        checkBoxes.add(checkBoxCrostacei);
        checkBoxes.add(checkBoxFruttaGuscio);
        checkBoxes.add(checkBoxGlutine);
        checkBoxes.add(checkBoxLatte);
        checkBoxes.add(checkBoxLupini);
        checkBoxes.add(checkBoxMolluschi);
        checkBoxes.add(checkBoxPesce);
        checkBoxes.add(checkBoxSedano);
        checkBoxes.add(checkBoxSenape);
        checkBoxes.add(checkBoxSesamo);
        checkBoxes.add(checkBoxSoia);
        checkBoxes.add(checkBoxUova);
        for(CheckBox c: checkBoxes){
            String valore = (String)c.getTag();
            c.setClickable(false);
            if(allergeni.contains(valore)){
                c.setChecked(true);
            }
            c.setOnCheckedChangeListener((compoundButton, b) -> {
                if(b){
                    if(!allergeni.contains(valore)){
                        allergeni.add(valore);
                    }
                }
                else{
                    allergeni.remove(valore);
                }
            });

        }

    }

    public void dialogAllergeni(){
        Dialog dialogAllergeni = new Dialog(this);
        dialogAllergeni.setContentView(R.layout.dialog_tabella_allergeni);

        buttonOkDialog = dialogAllergeni.findViewById(R.id.buttonOkTabellaAllergeniDialog);
        checkBoxArachidi = dialogAllergeni.findViewById(R.id.checkBoxFiltroTabellaAllergene);
        checkBoxAnidrideSolforosa = dialogAllergeni.findViewById(R.id.checkBoxAnidrideSolforosa);
        checkBoxCrostacei = dialogAllergeni.findViewById(R.id.checkBoxCrostacei);
        checkBoxFruttaGuscio = dialogAllergeni.findViewById(R.id.checkBoxFruttaGuscio);
        checkBoxGlutine = dialogAllergeni.findViewById(R.id.checkBoxGlutine);
        checkBoxLatte = dialogAllergeni.findViewById(R.id.checkBoxLatte);
        checkBoxLupini = dialogAllergeni.findViewById(R.id.checkBoxLupini);
        checkBoxMolluschi = dialogAllergeni.findViewById(R.id.checkBoxMolluschi);
        checkBoxPesce = dialogAllergeni.findViewById(R.id.checkBoxPesce);
        checkBoxSedano = dialogAllergeni.findViewById(R.id.checkBoxSedano);
        checkBoxSenape = dialogAllergeni.findViewById(R.id.checkBoxSenape);
        checkBoxSesamo = dialogAllergeni.findViewById(R.id.checkBoxSesamo);
        checkBoxSoia = dialogAllergeni.findViewById(R.id.checkBoxSoia);
        checkBoxUova = dialogAllergeni.findViewById(R.id.checkBoxUova);
        listenerAllergeni();
        dialogAllergeni.show();

        buttonOkDialog.setOnClickListener(view -> {
            dialogAllergeni.dismiss();
        });
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
                    elementoMenuPresenter.restituisciTraduzione(elementiMenu.get(elementoSelezionato).getElementoMenu().getId().toString());
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu) {
        setParameters(elementoMenu);
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
}
