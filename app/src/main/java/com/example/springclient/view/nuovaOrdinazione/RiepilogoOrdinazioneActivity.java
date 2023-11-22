package com.example.springclient.view.nuovaOrdinazione;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.view.adapters.IRecycleViewElementoMenu;
import com.example.springclient.view.adapters.RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiepilogoOrdinazioneActivity extends AppCompatActivity implements IRecycleViewElementoMenu, Serializable, OrdinazioneContract.ViewOrdinazione {

    private Button buttonIndietro;
    private Button buttonOk;

    private Ordinazione ordinazione;
    private List<Portata> portate;
    private RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn adapterElementoMenu;
    private OrdinazioneContract.Presenter presenterOrdinazione;
    private int indiceElementoSelezionato = -1;
    private Portata elementoSelezionato;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("RIEPILOGO ORDINAZIONE");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_riepilogo_ordinazione_nuova_ordinazione);
        //Dettagli ordinazione dalla activity precedente
        ordinazione = (Ordinazione) getIntent().getSerializableExtra("ordinazione");
        presenterOrdinazione = new OrdinazionePresenter(this);

        portate = new ArrayList<>();
        for (Portata o : ordinazione.getElementiOrdinati()) {
            portate.add(o);
        }


        initializeComponents();
    }

    @Override
    public void initializeComponents() {
        //Recycler view
        RecyclerView recyclerViewRiepilogo = findViewById(R.id.RecyclerViewRiepilogoOrdinazione);
        adapterElementoMenu = new RecycleViewAdapterRiepilogoOrdinazioneDeleteBtn(this, this, portate);
        recyclerViewRiepilogo.setAdapter(adapterElementoMenu);
        GridLayoutManager horizontal = new GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false);
        recyclerViewRiepilogo.setLayoutManager(horizontal);
        recyclerViewRiepilogo.setItemAnimator(null);

        //Buttons
        buttonOk = findViewById(R.id.buttonOkRiepilogo);
        buttonIndietro = findViewById(R.id.buttonIndietroRiepilogo);
        buttonOk.setOnClickListener(view -> {
            presenterOrdinazione.salvaPortate(portate);
        });
        buttonIndietro.setOnClickListener(view -> onBackPressed());

    }

    public void salvaOrdinazione(List<Portata> portateOrdinazione) {
        ordinazione.setElementiOrdinati(portateOrdinazione);
        presenterOrdinazione.salvaOrdinazione(ordinazione);
    }


    @Override
    public void ordinazioneAvvvenutaConSuccesso() {
        Dialog ordinazioneAvvenuta = new Dialog(this);
        mostraDialogOkOneBtn(ordinazioneAvvenuta, "Ordinazione inviata con successo", view -> {
            ordinazioneAvvenuta.dismiss();
            Intent loginActivity = new Intent(this, StartNuovaOrdinazioneActivity.class);
            startActivity(loginActivity);
        });
    }

    @Override
    public void onItemClickRecyclerViewPortata(int position) {
        indiceElementoSelezionato = position;
        elementoSelezionato = portate.get(position);
        //Dialog mostra info
        startDialogDettagliElemento(elementoSelezionato.getElementoMenu());
    }

    private void startDialogDettagliElemento(ElementoMenu elementoMenu) {
        Dialog dialogDettagli = new Dialog(this);
        dialogDettagli.setContentView(R.layout.dialog_dettagli_cerca_elemento_gestione_menu);

        //setto le text view e i buttons
        FloatingActionButton fabModifica = dialogDettagli.findViewById(R.id.fabDialogDettagli);
        fabModifica.setVisibility(View.GONE);
        Button buttonIndietro = dialogDettagli.findViewById(R.id.buttonIndietroDialogDettagli);
        TextInputLayout textInputLayoutPrezzo = dialogDettagli.findViewById(R.id.textInputLayoutPrezzoDialogDettagli);
        TextInputLayout textInputLayoutDescrizione = dialogDettagli.findViewById(R.id.textInputLayoutDescrizioneDialogDettagli);
        Button buttonTraduzione = dialogDettagli.findViewById(R.id.buttonLingua);
        buttonTraduzione.setVisibility(View.INVISIBLE);

        //Setto l'elemento menu di cui voglio vedere i dettagli
        setTextInputLayoutText(textInputLayoutPrezzo, elementoMenu.getPrezzo().toString());
        setTextInputLayoutText(textInputLayoutDescrizione, elementoMenu.getDescrizione());

        buttonIndietro.setOnClickListener(view -> {
            dialogDettagli.dismiss();
        });

        dialogDettagli.show();
    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }

    @Override
    public void onButtonDeleted(int position) {
        indiceElementoSelezionato = position;
        elementoSelezionato = portate.get(position);
        //Starta dialog warn per eliminare elem
        Dialog dialogAttenzione = new Dialog(this);
        dialogAttenzione.setContentView(R.layout.dialog_warning_two_button);

        TextView messaggiodialog = dialogAttenzione.findViewById(R.id.textViewDialogeWarnTwoBtn);
        messaggiodialog.setText("Sei sicuro di voler eliminare questo elemento dall'ordinazione?");

        Button buttonSi = dialogAttenzione.findViewById(R.id.buttonSiDialogWarnTwoBtn);
        Button buttonNo = dialogAttenzione.findViewById(R.id.buttonNoDialogWarnTwoBtn);
        dialogAttenzione.show();

        buttonSi.setOnClickListener(view -> {
            //elimina elemento
            dialogAttenzione.dismiss();
            portate.remove(position);
            ordinazione.getElementiOrdinati().remove(position);
            notifyAdapter();
            if (portate.size() == 0) {
                onBackPressed();
            }
        });
        buttonNo.setOnClickListener(view -> {
            dialogAttenzione.dismiss();
        });

    }


    @Override
    public void onBackPressed() {
        Intent intentEsploraCategorie = new Intent(this, EsploraCategorieActivity.class);
        intentEsploraCategorie.putExtra("ordinazione", ordinazione);
        startActivity(intentEsploraCategorie);
        super.onBackPressed();
    }

    public void notifyAdapter() {
        adapterElementoMenu.notifyDataSetChanged();
    }
    @Override
    public Context getContext(){
        return getContext();
    }


}

