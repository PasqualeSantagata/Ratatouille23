package com.example.springclient.view.gestioneElementiMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.example.springclient.R;
import com.example.springclient.view.BaseAllergeniDialog;
import com.example.springclient.contract.ModificaElementoContract;
import com.example.springclient.model.entity.ElementoMenu;
import com.example.springclient.presenter.ModificaElementoPresenter;
import com.example.springclient.view.inserisciNuovaLingua.SelezioneNuovaLinguaActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ModificaElementoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ModificaElementoContract.ViewContract, BaseAllergeniDialog {
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
    private String categoriaSelezionata;
    private List<String> allergeni;
    private ModificaElementoContract.Presenter modificaElementoMenuPresenter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("MODIFICA ELEMENTO NEL MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_modifica_elemento_gestione_menu);
        progressBar = findViewById(R.id.progressModificaElemento);
        progressBar.setVisibility(View.INVISIBLE);
        elementoMenu = (ElementoMenu) getIntent().getSerializableExtra("elementoMenu");
        modificaElementoMenuPresenter = new ModificaElementoPresenter(this);
        modificaElementoMenuPresenter.getNomiCategoriaDisponibili(elementoMenu.getId().toString());

    }

    @Override
    public void inizializzaComponenti() {
        textInputLayoutNome = findViewById(R.id.textInputLayoutModificaNome);
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutModificaPrezzo);
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

        fabAggiungiLingua.setOnClickListener(view -> modificaElementoMenuPresenter.mostraSelezionaNuovaLingua());

        fabAggiungiCategoria.setOnClickListener(view -> {
            if (categoriaSelezionata != null) {
                modificaElementoMenuPresenter.aggiungiElementoAllaCategoria(categoriaSelezionata, elementoMenu);
            }
        });

        buttonModificaAllergeni.setOnClickListener(view -> {
            allergeni = elementoMenu.getElencoAllergeni();
            dialogAllergeni(this, allergeni, false);
        });
        buttonOk.setOnClickListener(view -> {
            if(controllaCampi()) {
                String nome, descrizione, prezzo;
                nome = textInputLayoutNome.getEditText().getText().toString();
                descrizione = textInputLayoutDescrizione.getEditText().getText().toString();
                prezzo = textInputLayoutPrezzo.getEditText().getText().toString();
                ElementoMenu elementoAggiornato = new ElementoMenu(elementoMenu.getId(),nome, Float.valueOf(prezzo), descrizione, allergeni, elementoMenu.getLingua());
                modificaElementoMenuPresenter.modificaElementoMenu(elementoAggiornato);
            }
        });
        buttonIndietro.setOnClickListener(view -> modificaElementoMenuPresenter.tornaIndietro());

    }

    @Override
    public void mostraSelezionaNuovaLingua(){
        Intent nuovaLingua = new Intent(this, SelezioneNuovaLinguaActivity.class);
        nuovaLingua.putExtra("elemento", elementoMenu);
        startActivity(nuovaLingua);
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

    //TODO pitenzale metodo da testare 2 ma da generalizzare (argomenti generici passati, controllo campi vuoti) 
    private boolean controllaCampi() {
        boolean corretto = true;
        String nome, descrizione, prezzo;
        nome = textInputLayoutNome.getEditText().getText().toString();
        descrizione = textInputLayoutDescrizione.getEditText().getText().toString();
        prezzo = textInputLayoutPrezzo.getEditText().getText().toString();

        if(nome.equals("")){
            textInputLayoutNome.setError("Nome non valido");
            corretto = false;
        }
        if(prezzo.equals("")){
            textInputLayoutPrezzo.setError("Prezzo non valido");
            corretto = false;
        }
        if(descrizione.equals("")){
            textInputLayoutDescrizione.setError("Descrizione non valida");
            corretto = false;
        }
        return corretto;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        categoriaSelezionata = nomiCategoria.get(i);
        fabAggiungiCategoria.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Attenzione se torni indietro tutte le modifiche effettuate fino ad ora andranno perse! Continuare? ",
                view1 -> {
                    super.onBackPressed();
                    dialog.dismiss();
                },
                view12 -> dialog.dismiss());
    }

    @Override
    public void setNomiCategoria(List<String> nomiCategoria) {
        this.nomiCategoria = nomiCategoria;
    }

    @Override
    public void disabilitaFabInserisciTraduzione() {
        fabAggiungiLingua.setVisibility(View.INVISIBLE);
        TextView textViewLingua = findViewById(R.id.textViewLingua);
        textViewLingua.setText("  Lingua corrente  ");
    }
    @Override
    public void elementoGiaPresenteNellaCategoria(){
        Toast.makeText(this, "Elemento già presente nella categoria", Toast.LENGTH_LONG).show();
    }

    @Override
    public void elementoAggiuntoAdUnaCategoria(String nome){
        Toast.makeText(this, "Elemento aggiunto alla categoria " + nome , Toast.LENGTH_LONG).show();
    }

    @Override
    public void elementoModificatoCorrettamente(){
        Dialog dialogOk = new Dialog(this);
        mostraDialogOkOneBtn(dialogOk, "Elemento modificato correttamente", view -> super.onBackPressed());
    }

    @Override
    public void erroreModifica(String messaggio){
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void mostraErrori(String errore){
        if(errore.contains("nome")){
            textInputLayoutDescrizione.setError("Nome elemento non valido");
        }
        else if(errore.contains("prezzo")){
            textInputLayoutDescrizione.setError("Prezzo elemento non valido");
        }
        else{
            textInputLayoutDescrizione.setError("Desrizione non valida");
        }

    }

}
