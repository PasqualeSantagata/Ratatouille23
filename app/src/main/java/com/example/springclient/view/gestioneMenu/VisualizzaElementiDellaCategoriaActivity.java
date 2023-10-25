package com.example.springclient.view.gestioneMenu;

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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.presenter.ElementoMenuPresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterGestioneElementoMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VisualizzaElementiDellaCategoriaActivity extends AppCompatActivity implements IRecycleViewElementoMenu, ElementoMenuContract.View {

    private TextInputLayout textInputLayouNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private FloatingActionButton fabModifica;
    private TextView allergeniButton;
    private List<String> allergeni;
    private List<CheckBox> checkBoxes;

    private CheckBox checkBoxArachidi, checkBoxAnidrideSolforosa, checkBoxCrostacei, checkBoxFruttaGuscio,
            checkBoxGlutine, checkBoxLatte, checkBoxLupini, checkBoxMolluschi, checkBoxPesce,
            checkBoxSedano, checkBoxSenape, checkBoxSesamo, checkBoxSoia, checkBoxUova;
    private List<ElementoMenu> elementiMenu;
    private Categoria categoria;
    private RecyclerView recyclerView;
    private int elementoSelezionato = -1;
    private ElementoMenuPresenter elementoMenuPresenter;
    private  RecycleViewAdapterGestioneElementoMenu adapterElementoMenu;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private String nome;
    private Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //verifica l'acrtivity prececente
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementi");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        nome = getIntent().getStringExtra("nomeCategoria");
        getSupportActionBar().setTitle(nome);
        setContentView(R.layout.activity_visualizza_cagtegoria_gestione_menu);
        elementoMenuPresenter = new ElementoMenuPresenter(this);

        simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN
                | ItemTouchHelper.START | ItemTouchHelper.END, 0) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(elementiMenu, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        };

        initializeComponents();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow_visualizza_elem_menu_inserimento_nel_menu, menu);

        MenuItem itemLingua = findViewById(R.id.item_lingue);
        MenuItem itemLinguaBase = findViewById(R.id.item_lingua_base);
        MenuItem itemRiordina = findViewById(R.id.item_riordinare);
        //itemLinguaBase.setEnabled(false);

        this.menu = menu;
        return true;
    }

    private boolean b;
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(b==true){
            menu.findItem(R.id.item_lingue).setEnabled(false);
            menu.findItem(R.id.item_lingua_base).setEnabled(true);
        }else{
            menu.findItem(R.id.item_lingue).setEnabled(true);
            menu.findItem(R.id.item_lingua_base).setEnabled(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_riordinare:
                Intent intent = new Intent(this, FiltraCategoria.class);
                intent.putExtra("elementiMenu",(Serializable) elementiMenu);
                intent.putExtra("nomeCategoria", nome);
                startActivity(intent);
                break;
            case R.id.item_lingue:
                    if(elementoSelezionato != -1) {
                        elementoMenuPresenter.restituisciTraduzione(elementiMenu.get(elementoSelezionato).getId().toString());
                        invalidateOptionsMenu();
                        b = true;

                    }
                    else{
                        Toast.makeText(this, "Seleziona un elemento per visualizzarne la traduzione", Toast.LENGTH_LONG).show();
                    }
                break;
            case R.id.item_lingua_base:
                if(elementoSelezionato != -1) {
                    setParameters(getElementoMenu());
                    invalidateOptionsMenu();
                    b = false;
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeComponents(){
        textInputLayouNome = findViewById(R.id.textInputLayoutNomeInserimNelMenu);
        textInputLayoutDescrizione  = findViewById(R.id.textInputLayoutDescrizioneInserimNelMenu);
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoInserimNelMenu);
        allergeniButton = findViewById(R.id.textViewAllergeniElementoGestioneMenu);
        fabModifica = findViewById(R.id.fabModificaInserNelMenu);
        setElementiMenuRecycleView();

        fabModifica.setOnClickListener(view -> {
            Intent intentModElemento = new Intent(this, ModificaElementoActivity.class);
            intentModElemento.putExtra("elementoMenu", elementoSelezionato);
            startActivity(intentModElemento);
        });

        allergeniButton.setOnClickListener(view -> {
            dialogAllergeni();
        });


    }

    public void listenerAllergeni(){
        if(allergeni == null) {
            allergeni = new ArrayList<>();
        }
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
        Button buttonOkDialog = dialogAllergeni.findViewById(R.id.buttonOkTabellaAllergeniDialog);
        buttonOkDialog.setOnClickListener(view -> {
            dialogAllergeni.dismiss();
        });

        listenerAllergeni();
        dialogAllergeni.show();
    }


    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }


    public void setElementiMenuRecycleView(){
        RecyclerView recyclerViewPiatti = findViewById(R.id.recycleViewPiattiCategoriaInserimentoNelMenu);
        adapterElementoMenu = new RecycleViewAdapterGestioneElementoMenu(this, elementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
        recyclerViewPiatti.setItemAnimator(null);

        //Dovrebbe implementare la funzione drug and drop
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewPiatti.addItemDecoration(dividerItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewPiatti);

    }

    public void setParameters(ElementoMenu elementoMenu){
        allergeni = elementoMenu.getElencoAllergeni();
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        setTextInputLayoutText(textInputLayouNome, elementoMenu.getNome());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    private ElementoMenu getElementoMenu(){
        return elementiMenu.get(elementoSelezionato);
    }

    @Override
    public void onItemClick(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position));
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

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu){
        setParameters(elementoMenu);
    }

    @Override
    public void traduzioneAssente() {
        mostraDialogWarningOneBtn("Traduzione non presente. Modifica elemento per aggiungere una traduzione");
    }

    private void mostraDialogWarningOneBtn(String messaggio){
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_error_one_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogueErrorOneBt);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });
    }

}
