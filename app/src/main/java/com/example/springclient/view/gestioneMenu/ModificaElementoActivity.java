package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.springclient.R;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.CategoriaMenuPresenter;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ModificaElementoActivity extends AppCompatActivity implements CategoriaContract.View, AdapterView.OnItemSelectedListener {
    private TextInputLayout textInputLayoutNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private TextInputEditText textInputEditTextLingua;
    private Button buttonOk;
    private Button buttonIndietro;
    private Button buttonModificaAllergeni;
    private Spinner spinnerCategoria;
    private FloatingActionButton fabAggiungiCategoria;
    private FloatingActionButton fabAggiungiLingua;
    private ElementoMenu elementoMenu;
    private List<String> nomiCategoria;
    private CategoriaMenuPresenter categoriaMenuPresenter;
    private String categoriaSelezionata;
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
        getSupportActionBar().setTitle("MODIFICA ELEMENTO NEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_modifica_elemento_gestione_menu);
        elementoMenu = (ElementoMenu) getIntent().getSerializableExtra("elementoMenu");
        categoriaMenuPresenter = new CategoriaMenuPresenter(this);
        elementoMenuPresenter = new ElementoMenuPresenter(this);
        categoriaMenuPresenter.getNomiCategoriaDisponibili(elementoMenu.getId().toString());

    }

    public void initializeComponents() {
        textInputLayoutNome = findViewById(R.id.textInputLayoutModificaNome);
        textInputLayoutPrezzo= findViewById(R.id.textInputLayoutModificaPrezzo);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutModificaDescrizione);
        textInputLayoutNome.getEditText().setText((elementoMenu.getNome()));
        textInputLayoutDescrizione.getEditText().setText((elementoMenu.getDescrizione()));
        textInputLayoutPrezzo.getEditText().setText(elementoMenu.getPrezzo().toString());
        fabAggiungiCategoria = findViewById(R.id.floatingModificaCategoria);
        buttonModificaAllergeni = findViewById(R.id.buttonModificaAllergeni);
        buttonIndietro = findViewById(R.id.buttonIndietroModElemGestioneMenu);
        textInputEditTextLingua = findViewById(R.id.textInputEditTextLinguaCorrenteModificaElemGestioneMenu);
        fabAggiungiLingua = findViewById(R.id.floatingActionButtonAggiungiLingua);
        buttonOk = findViewById(R.id.buttonOkModifica);


        spinnerCategoria = findViewById(R.id.spinnerCategoriaInserimentoNelMenu);
        spinnerCategoria.setOnItemSelectedListener(this);
        ArrayAdapter adapterCategorie = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nomiCategoria);
        adapterCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCategorie);
        textInputEditTextLingua.setText(elementoMenu.getLingua());

        fabAggiungiLingua.setOnClickListener(view -> {
            Intent nuovaLingua = new Intent(this, SelezioneNuovaLinguaActivity.class);
            nuovaLingua.putExtra("elemento", elementoMenu);
            startActivity(nuovaLingua);
        });


        fabAggiungiCategoria.setOnClickListener(view -> {
            if(categoriaSelezionata != null){
                categoriaMenuPresenter.aggiungiElemento(categoriaSelezionata, elementoMenu);
                //se non aggiunge è perchè è già presente
            }
        });

        buttonModificaAllergeni.setOnClickListener(view -> {
            dialogAllergeni();
        });
        buttonOk.setOnClickListener(view -> {
            elementoMenuPresenter.modificaElementoMenu(elementoMenu);

        });
        buttonIndietro.setOnClickListener(view -> {
            /*Intent intentVisualizzaCategorie = new Intent(this, VisualizzaElementiDellaCategoriaActivity.class);
            mostraDialogWarningTwoBtn("Sei sicuro di voler tornare indietro? perderai tutti i dati inseriti fino ad ora!", intentVisualizzaCategorie);*/
            //questa schermata è raggiungibile da due diverse quindi indietro dovrebbe corrispondere ad onBackPressed di default
            onBackPressed();
        });

    }
    public void listenerAllergeni(){
        allergeni = elementoMenu.getElencoAllergeni();
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

    private void mostraDialogWarningTwoBtn(String messaggio, Intent intentSi){
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_two_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewDialogeWarnTwoBtn);
        messaggiodialog.setText(messaggio);

        Button buttonSi = dialogAttenzione.findViewById(R.id.buttonSiDialogWarnTwoBtn);
        Button buttonNo = dialogAttenzione.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        dialogAttenzione.show();

        buttonSi.setOnClickListener(view -> {
            if(intentSi != null)
                onBackPressed();
                //startActivity(intentSi);

            dialogAttenzione.dismiss();
        });
        buttonNo.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }
    @Override
    public void setNomiCategorie(List<String> nomiCategori) {
        this.nomiCategoria = nomiCategori;
        initializeComponents();
    }

    @Override
    public void setCategorie(List<Categoria> categorie) {


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoriaSelezionata = nomiCategoria.get(i);
        fabAggiungiCategoria.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
