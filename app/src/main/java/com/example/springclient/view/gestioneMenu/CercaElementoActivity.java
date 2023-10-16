package com.example.springclient.view.gestioneMenu;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterGestioneElementoMenuInfoBtn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class CercaElementoActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

    private Button buttonInditero;
    private Button buttonCerca;
    private RecyclerView recyclerViewElementi;
    private ImageView imageViewButtonOnItem;
    private RecycleViewAdapterGestioneElementoMenuInfoBtn adapter;
    private TextInputLayout textInputLayoutRicercaNome;
    private List<ElementoMenu> elementoMenuList;
    private ElementoMenuPresenter presenter;
    private AutoCompleteTextView autoTextView;
    private List<ElementoMenu> risultatiCerca;
    private List<ElementoMenu> elementoMenuListApp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_elemento_gestione_menu);
        getSupportActionBar().setTitle("CERCA ELEMENTO MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        presenter = new ElementoMenuPresenter(this);
        presenter.getAllElementiMenu();

    }

    @SuppressLint("CheckResult")
    private void initializeComponents() {
        buttonInditero = findViewById(R.id.buttonIndietroCercaElemento);
        buttonCerca = findViewById(R.id.buttonCercaElemento);
        textInputLayoutRicercaNome = findViewById(R.id.textInputLayoutCerca);

        recyclerViewElementi = findViewById(R.id.recyclerViewElementiRicercati);
        adapter = new RecycleViewAdapterGestioneElementoMenuInfoBtn(this, elementoMenuList, this);
        recyclerViewElementi.setAdapter(adapter);

        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewElementi.setLayoutManager(horizontal);

        autoTextView = (AutoCompleteTextView) textInputLayoutRicercaNome.getEditText();
        RxTextView.textChanges(autoTextView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        charSequence -> {
                            if (charSequence != null && !charSequence.toString().isEmpty()) {
                                for(ElementoMenu e: elementoMenuListApp){
                                    if(!e.getNome().toLowerCase().contains(charSequence.toString().toLowerCase()) && elementoMenuList.contains(e)){
                                        elementoMenuList.remove(e);
                                    } else if (e.getNome().toLowerCase().contains(charSequence.toString().toLowerCase()) && !elementoMenuList.contains(e)) {
                                        elementoMenuList.add(e);
                                    }
                                }

                            }
                            else{
                                for(ElementoMenu e: elementoMenuListApp){
                                    if(!elementoMenuList.contains(e)){
                                        elementoMenuList.add(e);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        });


    }

    public void setElementi(List<ElementoMenu> elementoMenuList){
        this.elementoMenuList = elementoMenuList;
        this.elementoMenuListApp = new ArrayList<>();
        elementoMenuListApp.addAll(elementoMenuList);
        initializeComponents();
    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    private String getTextInputLayoutText (TextInputLayout textInputLayout){
        EditText editText = textInputLayout.getEditText();
        return editText.getText().toString();
    }

    private void startDialogDettagliElemento(ElementoMenu elementoMenu){
        Dialog dialogDettagli = new Dialog(this);
        dialogDettagli.setContentView(R.layout.dialog_dettagli_cerca_elemento_gestione_menu);

        //setto le text view e i buttons
        FloatingActionButton fabModifica = dialogDettagli.findViewById(R.id.fabDialogDettagli);
        Button buttonIndietro = dialogDettagli.findViewById(R.id.buttonIndietroDialogDettagli);
        TextInputLayout textInputLayoutPrezzo = dialogDettagli.findViewById(R.id.textInputLayoutPrezzoDialogDettagli);
        TextInputLayout textInputLayoutAllergeni = dialogDettagli.findViewById(R.id.textInputLayoutAllergeniDialogDettagli);
        TextInputLayout textInputLayoutDescrizione = dialogDettagli.findViewById(R.id.textInputLayoutDescrizioneDialogDettagli);

        //Setto l'elemento menu di cui voglio vedere i dettagli
        setTextInputLayoutText(textInputLayoutPrezzo,elementoMenu.getPrezzo().toString());
        //setTextInputLayoutText(textInputLayoutAllergeni, elementoMenu.getElencoAllergeni().toString());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());

        dialogDettagli.show();

        fabModifica.setOnClickListener(view -> {
            Intent intent = new Intent(this, ModificaElementoActivity.class);
            intent.putExtra("elementoMenu", elementoMenu);
            startActivity(intent);
        });

        buttonIndietro.setOnClickListener(view -> {
            dialogDettagli.dismiss();
        });

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onButtonDeleted(int position){
        startDialogDettagliElemento(elementoMenuList.get(position));
    }
}
