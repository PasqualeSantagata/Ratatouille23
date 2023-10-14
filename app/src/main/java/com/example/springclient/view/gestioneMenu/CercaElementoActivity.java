package com.example.springclient.view.gestioneMenu;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import java.util.List;

public class CercaElementoActivity extends AppCompatActivity implements IRecycleViewElementoMenu {

    private Button buttonInditero;
    private Button buttonCerca;
    private RecyclerView recyclerViewElementi;
    private ImageView imageViewButtonOnItem;
    private RecycleViewAdapterGestioneElementoMenuInfoBtn adapter;
    private TextInputLayout textInputLayoutRicercaNome;
    private List<ElementoMenu> elementoMenuList;
    private ElementoMenuPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_elemento_gestione_menu);
        getSupportActionBar().setTitle("CERCA ELEMENTO MENU");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        presenter = new ElementoMenuPresenter(this);
        presenter.getAllElementiMenu();

    }

    private void initializeComponents() {
        buttonInditero = findViewById(R.id.buttonIndietroCercaElemento);
        buttonCerca = findViewById(R.id.buttonCercaElemento);
        textInputLayoutRicercaNome = findViewById(R.id.textInputLayoutCerca);

        recyclerViewElementi = findViewById(R.id.recyclerViewElementiRicercati);
        adapter = new RecycleViewAdapterGestioneElementoMenuInfoBtn(this, elementoMenuList, this);
        recyclerViewElementi.setAdapter(adapter);

        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewElementi.setLayoutManager(horizontal);




    }

    public void setElementi(List<ElementoMenu> elementoMenuList){
        this.elementoMenuList = elementoMenuList;
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
        FloatingActionButton fabModifica = findViewById(R.id.fabDialogDettagli);
        Button buttonIndietro = findViewById(R.id.buttonIndietroDialogDettagli);
        TextInputLayout textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoDialogDettagli);
        TextInputLayout textInputLayoutAllergeni = findViewById(R.id.textInputLayoutAllergeniDialogDettagli);
        TextInputLayout textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneDialogDettagli);

        //Setto l'elemento menu di cui voglio vedere i dettagli
        setTextInputLayoutText(textInputLayoutPrezzo,elementoMenu.getPrezzo().toString());
        setTextInputLayoutText(textInputLayoutAllergeni, elementoMenu.getElencoAllergeni().toString());
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
