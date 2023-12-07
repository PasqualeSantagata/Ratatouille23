package com.example.springclient.view.gestioneElementiMenu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.GestioneElementiContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.GestioneElementiPresenter;
import com.example.springclient.view.StartGestioneMenuActivity;

import com.example.springclient.view.adapters.IRecycleViewEventi;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class CercaElementoActivity extends AppCompatActivity implements IRecycleViewEventi, GestioneElementiContract.CercaElementoViewContract {

    private Button buttonInditero;
    private RecyclerView recyclerViewElementi;
    private RecycleViewAdapterElementoMenu adapter;
    private TextInputLayout textInputLayoutRicercaNome;
    private List<ElementoMenu> elementoMenuList;
    private GestioneElementiContract.Presenter gestioneElementiPresenter;
    private AutoCompleteTextView autoTextView;
    private List<ElementoMenu> elementoMenuListApp;
    private TextInputLayout textInputLayoutDescrizione;
    private Context dialogDettagliContext;
    private ElementoMenu elementoDaEliminare;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_elemento_gestione_menu);
        getSupportActionBar().setTitle("CERCA ELEMENTO MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        progressBar = findViewById(R.id.progressBarCercaElemento);
        progressBar.setVisibility(View.INVISIBLE);
        gestioneElementiPresenter = new GestioneElementiPresenter(this);
        gestioneElementiPresenter.getElementiMenu();

    }
    @Override
    @SuppressLint("CheckResult")
    public void inizializzaComponenti() {
        buttonInditero = findViewById(R.id.buttonIndietroCercaElemento);
        textInputLayoutRicercaNome = findViewById(R.id.textInputLayoutCerca);

        recyclerViewElementi = findViewById(R.id.recyclerViewElementiRicercati);
        adapter = new RecycleViewAdapterElementoMenu(this, elementoMenuList, this);
        recyclerViewElementi.setAdapter(adapter);

        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewElementi.setLayoutManager(horizontal);

        autoTextView = (AutoCompleteTextView) textInputLayoutRicercaNome.getEditText();
        RxTextView.textChanges(autoTextView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        charSequence -> {
                            if(gestioneElementiPresenter.filtraElementi(charSequence.toString(), elementoMenuList, elementoMenuListApp))
                                 adapter.notifyDataSetChanged();
                        });

        buttonInditero.setOnClickListener(view -> gestioneElementiPresenter.tornaStartGestioneMenu());
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


    private void setTextInputLayout(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    //TODO potenziale testing 1
    private void startDialogDettagliElemento(ElementoMenu elementoMenu) {
        Dialog dialogDettagli = new Dialog(this);
        dialogDettagli.setContentView(R.layout.dialog_dettagli_cerca_elemento_gestione_menu);

        //setto le text view e i buttons
        FloatingActionButton fabModifica = dialogDettagli.findViewById(R.id.fabDialogDettagli);
        Button buttonIndietro = dialogDettagli.findViewById(R.id.buttonIndietroDialogDettagli);
        TextInputLayout textInputLayoutPrezzo = dialogDettagli.findViewById(R.id.textInputLayoutPrezzoDialogDettagli);
        TextInputLayout textInputLayoutDescrizione = dialogDettagli.findViewById(R.id.textInputLayoutDescrizioneDialogDettagli);
        Button buttonTraduzione = dialogDettagli.findViewById(R.id.buttonLingua);

        //Setto l'elemento menu di cui voglio vedere i dettagli
        setTextInputLayout(textInputLayoutPrezzo, elementoMenu.getPrezzo().toString());
        setTextInputLayout(textInputLayoutDescrizione, elementoMenu.getDescrizione());

        this.dialogDettagliContext = dialogDettagli.getContext();
        this.textInputLayoutDescrizione = textInputLayoutDescrizione;

        fabModifica.setOnClickListener(view -> {
            gestioneElementiPresenter.mostraModificaElemento(elementoMenu);
            dialogDettagli.dismiss();
        });

        buttonIndietro.setOnClickListener(view -> dialogDettagli.dismiss());

        buttonTraduzione.setOnClickListener(view -> gestioneElementiPresenter.restituisciTraduzione(elementoMenu));

        dialogDettagli.show();
    }

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu) {
        if (elementoMenu != null)
            setTextInputLayout(textInputLayoutDescrizione, elementoMenu.getDescrizione());

    }

    @Override
    public void traduzioneAssente() {
        Toast.makeText(dialogDettagliContext, "Non esiste una lingua alternativa per questo elemento", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onButtonEventRecyclerView(int position){
        elementoDaEliminare = elementoMenuList.get(position);
        gestioneElementiPresenter.avviaRimuoviElemento();
    }

    @Override
    public void onItemClickRecyclerView(int position) {
        startDialogDettagliElemento(elementoMenuList.get(position));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartGestioneMenuActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        gestioneElementiPresenter.getElementiMenu();
        super.onResume();
    }

    @Override
    public void mostraModificaElemento(ElementoMenu elementoMenu) {
        Intent intent = new Intent(this, ModificaElementoActivity.class);
        intent.putExtra("elementoMenu", elementoMenu);
        startActivity(intent);
    }

    @Override
    public void elementoEliminato() {
        int pos = elementoMenuList.indexOf(elementoDaEliminare);
        elementoMenuList.remove(pos);
        adapter.notifyItemRemoved(pos);

    }

    @Override
    public void impossibileContattareIlServer(String messaggio){
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog,  messaggio, view -> dialog.dismiss());
    }

    @Override
    public void rimuoviElementoDalMenu() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Vuoi eliminare " + elementoDaEliminare.getNome() +" dal menù?",
              view -> {
                    gestioneElementiPresenter.rimuoviElementoMenu(elementoDaEliminare);
                    dialog.dismiss();
              },
                view -> dialog.dismiss());
    }

    @Override
    public void caricaElementi(List<ElementoMenu> elementoMenuList) {
        this.elementoMenuList = elementoMenuList;
        this.elementoMenuListApp = new ArrayList<>();
        elementoMenuListApp.addAll(elementoMenuList);
        inizializzaComponenti();
    }


}
