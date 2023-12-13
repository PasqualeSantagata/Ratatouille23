package com.example.springclient.view.gestioneCategorie;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
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
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.view.BaseAllergeniDialog;
import com.example.springclient.contract.VisualizzElementiContract;
import com.example.springclient.model.entity.ElementoMenu;
import com.example.springclient.presenter.VisualizzElementiPresenter;
import com.example.springclient.view.adapters.IRecycleViewEventi;
import com.example.springclient.view.adapters.RecycleViewAdapterElementoMenu;
import com.example.springclient.view.gestioneElementiMenu.ModificaElementoActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class VisualizzaElementiDellaCategoriaActivity extends AppCompatActivity implements IRecycleViewEventi,
        VisualizzElementiContract.ViewContract, BaseAllergeniDialog {

    private TextInputLayout textInputLayouNome;
    private TextInputLayout textInputLayoutDescrizione;
    private TextInputLayout textInputLayoutPrezzo;
    private FloatingActionButton fabModifica;
    private Button modificaAllergeniButton;
    private List<String> allergeni;
    private List<ElementoMenu> elementiMenu;
    private int elementoSelezionato = -1;
    private VisualizzElementiContract.Presenter visualizzaElementiPresenter;
    private RecycleViewAdapterElementoMenu adapterElementoMenu;
    private ItemTouchHelper.SimpleCallback simpleCallback;
    private String nome;
    private Menu menu;
    private Button indietroButton;
    private Long idCategoria;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //verifica l'acrtivity prececente
        elementiMenu = (List<ElementoMenu>) getIntent().getSerializableExtra("elementiCategoria");
        idCategoria = (Long) getIntent().getSerializableExtra("idCategoria");
        nome = getIntent().getStringExtra("nomeCategoria");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().setTitle(nome);
        setContentView(R.layout.activity_visualizza_categoria_gestione_menu);
        visualizzaElementiPresenter = new VisualizzElementiPresenter(this);

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
        inizializzaComponenti();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow_visualizza_elem_menu_inserimento_nel_menu, menu);

        this.menu = menu;
        return true;
    }

    private boolean valBurgerMenu;

    public boolean onPrepareOptionsMenu(Menu menu) {
        if (valBurgerMenu) {
            menu.findItem(R.id.item_lingue).setEnabled(false);
            menu.findItem(R.id.item_lingua_base).setEnabled(true);
        } else {
            menu.findItem(R.id.item_lingue).setEnabled(true);
            menu.findItem(R.id.item_lingua_base).setEnabled(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_riordinare:
                visualizzaElementiPresenter.mostrFiltraCategorie(nome);
                break;
            case R.id.item_lingue:
                if (elementoSelezionato != -1) {
                    visualizzaElementiPresenter.restituisciTraduzione(elementiMenu.get(elementoSelezionato).getId().toString());
                } else {
                    Toast.makeText(this, "Seleziona un elemento per visualizzarne la traduzione", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.item_lingua_base:
                if (elementoSelezionato != -1) {
                    setParameters(getElementoMenu());
                    invalidateOptionsMenu();
                    valBurgerMenu = false;
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void inizializzaComponenti() {
        textInputLayouNome = findViewById(R.id.textInputLayoutNomeInserimNelMenu);
        textInputLayoutDescrizione = findViewById(R.id.textInputLayoutDescrizioneInserimNelMenu);
        textInputLayoutPrezzo = findViewById(R.id.textInputLayoutPrezzoInserimNelMenu);
        modificaAllergeniButton = findViewById(R.id.buttonModificaVisualCatGestioneMenu);
        fabModifica = findViewById(R.id.fabModificaInserNelMenu);
        indietroButton = findViewById(R.id.buttonIndietroElemInserNelMenu);
        setElementiMenuRecycleView();

        fabModifica.setOnClickListener(view -> {
            if (elementoSelezionato != -1) {
                visualizzaElementiPresenter.mostraModifica(elementiMenu.get(elementoSelezionato));
            } else {
                Toast.makeText(this, "Seleziona un elemento", Toast.LENGTH_SHORT).show();
            }
        });

        modificaAllergeniButton.setOnClickListener(view -> {
            //questo può essere chiamato solo dopo set parameters??
            if (allergeni != null) {
                dialogAllergeni(this, allergeni, true);
            }
        });

        indietroButton.setOnClickListener(view -> visualizzaElementiPresenter.tornaEsploraCategorieMenu());
    }

    @Override
    public void tornaIndietro() {
        onBackPressed();
    }

    @Override
    public void mostraPorgressBar() {

    }
    @Override
    public void nascondiProgressBar() {

    }

    @Override
    public boolean isVisibile() {
        return getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }


    public void setElementiMenuRecycleView() {
        RecyclerView recyclerViewPiatti = findViewById(R.id.recycleViewPiattiCategoriaInserimentoNelMenu);
        adapterElementoMenu = new RecycleViewAdapterElementoMenu(this, elementiMenu, this);
        recyclerViewPiatti.setAdapter(adapterElementoMenu);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewPiatti.setLayoutManager(linearLayoutManager);
        recyclerViewPiatti.setItemAnimator(null);
    }

    public void setParameters(ElementoMenu elementoMenu) {
        allergeni = elementoMenu.getElencoAllergeni();
        setTextInputLayoutText(textInputLayoutPrezzo, String.valueOf(elementoMenu.getPrezzo()));
        setTextInputLayoutText(textInputLayouNome, elementoMenu.getNome());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());
    }

    private ElementoMenu getElementoMenu() {
        return elementiMenu.get(elementoSelezionato);
    }

    @Override
    public void onItemClickRecyclerView(int position) {
        elementoSelezionato = position;
        setParameters(elementiMenu.get(position));
    }

    @Override
    public void onButtonEventRecyclerView(int position) {
        elementoSelezionato = position;
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog, "Sei sicuro di voler eliminare questo elemento?",
                view -> {
                    visualizzaElementiPresenter.eliminaElementoDallaCategoria(idCategoria, elementiMenu.get(position));
                    dialog.dismiss();
                },
                view -> dialog.dismiss()
        );

    }
    private void mostraDialogWarningOneBtn(String messaggio) {
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_one_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewMessageDialogueErrorOneBt);
        messaggiodialog.setText(messaggio);

        Button buttonOk = dialogAttenzione.findViewById(R.id.buttonOkDialogueErrorOneBt);
        dialogAttenzione.show();

        buttonOk.setOnClickListener(view -> dialogAttenzione.dismiss());
    }

    @Override
    public void rimuoviElemento() {
        elementiMenu.remove(elementoSelezionato);
        adapterElementoMenu.notifyDataSetChanged();
        if(elementiMenu.isEmpty()){
            Intent intent = new Intent(this, EsploraCategorieMenuActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void mostraRiepilogo() {

    }

    @Override
    public void mostraFiltraCategoria(List<ElementoMenu> elementoMenuList) {
        Intent intent = new Intent(this, FiltraCategoriaGestioneMenuActivity.class);
        intent.putExtra("elementiCategoria", (Serializable) elementoMenuList);
        intent.putExtra("nomeCategoria", nome);
        intent.putExtra("idCategoria", idCategoria);
        startActivity(intent);
    }

    @Override
    public void mostraModifica(ElementoMenu elementoMenu) {
        Intent intentModElemento = new Intent(this, ModificaElementoActivity.class);
        intentModElemento.putExtra("elementoMenu", elementoMenu);
        startActivity(intentModElemento);
    }

    @Override
    public void impossibileComunicareConServer(String messaggio) {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void impossibileRimuovereElemento(String s) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void mostraTraduzione(ElementoMenu elementoMenu) {
        invalidateOptionsMenu();
        valBurgerMenu = true;
        setParameters(elementoMenu);
    }

    @Override
    public void traduzioneAssente() {
        mostraDialogWarningOneBtn("Traduzione non presente. Modifica elemento per aggiungere una traduzione");
    }

    @Override
    public void setElementi(List<ElementoMenu> elementoMenuList) {
        this.elementiMenu = elementoMenuList;
        inizializzaComponenti();
    }

    @Override
    protected void onResume() {
        visualizzaElementiPresenter.aggiornaElementiCategoria(nome);
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent esploraCategorie = new Intent(this, EsploraCategorieMenuActivity.class);
        startActivity(esploraCategorie);
        super.onBackPressed();
    }


}
