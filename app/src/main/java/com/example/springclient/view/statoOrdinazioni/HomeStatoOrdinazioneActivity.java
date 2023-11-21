package com.example.springclient.view.statoOrdinazioni;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;
import com.example.springclient.entity.StatoOrdinazione;
import com.example.springclient.presenter.OrdinazionePresenter;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniCorrenti;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniEvase;
import com.example.springclient.view.adapters.IRecycleViewOrdinazioniPrenotate;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniCorrenti;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniEvase;
import com.example.springclient.view.adapters.RecycleViewAdapterOrdinazioniPrenotate;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class HomeStatoOrdinazioneActivity extends AppCompatActivity implements IRecycleViewOrdinazioniCorrenti, IRecycleViewOrdinazioniPrenotate
        , IRecycleViewOrdinazioniEvase, OrdinazioneContract.ViewPrenotazionePortate {

    private RecyclerView recyclerViewOrdinazioniCorrenti;
    private RecyclerView recyclerViewOrdinazioniPrenotate;
    private RecyclerView recyclerViewOrdinazioniEvase;
    private StompClient stompClient;
    private OrdinazionePresenter ordinazionePresenter;
    private List<Ordinazione> ordinazioni;
    private List<StatoOrdinazione> ordinazioniSospese;
    private List<StatoOrdinazione> ordinazioniPrenotate;
    private List<StatoOrdinazione> ordinazioniEvase;
    private int posizione;
    private RecycleViewAdapterOrdinazioniCorrenti adapterCorrenti;
    private FirebaseAnalytics firebaseAnalytics;
    private String email;
    private RecycleViewAdapterOrdinazioniPrenotate adapterPrenotate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_stato_ordinazioni);
        getSupportActionBar().setTitle("STATO DELLE ORDINAZIONI");

        ordinazionePresenter = new OrdinazionePresenter(this);
        ordinazionePresenter.getOrdinazioniSospese();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        email = getIntent().getStringExtra("email");
        ordinazioniPrenotate = new ArrayList<>();
        stompConnect();
    }

    public void setOrdinazioniSospese(List<Ordinazione> ordinazioni) {
        this.ordinazioni = ordinazioni;
        ordinazioniSospese = new ArrayList<>();
        for (Ordinazione o : ordinazioni) {
            for (Portata p : o.getElementiOrdinati()) {
                if (!p.isPrenotato()) {
                    ordinazioniSospese.add(new StatoOrdinazione(o, p));
                }
            }
        }
        initializeComponents();
    }

    public void aggiornaPrenotazioni() {
        ordinazioniSospese.remove(posizione);
        adapterCorrenti.notifyItemRemoved(posizione);
    }

    public void concludiOrdinazione(Long id) {
        boolean completo = true;
        long idOrdinazione = -1;
        List<Portata> portataList = new ArrayList<>();
        for (Ordinazione o : ordinazioni) {
            for (Portata p : o.getElementiOrdinati()) {
                if (p.getId().equals(id)) {
                    portataList = o.getElementiOrdinati();
                    idOrdinazione = o.getId();
                }
            }
        }
        for (Portata p : portataList) {
            if (!p.isPrenotato()) {
                completo = false;
                break;
            }
        }
        if (completo && idOrdinazione > 0) {
            ordinazionePresenter.concludiOrdinazione(idOrdinazione);
        }
    }

    private void mostraDialogDettagli(StatoOrdinazione ordinazione){
        Dialog dialogDettagli = new Dialog(this);
        dialogDettagli.setContentView(R.layout.dialog_dettagli_stato_ordinazione);

        TextInputLayout textInputLayoutBreveNota = dialogDettagli.findViewById(R.id.textInputLayoutNotaDialogDettagliStatoOrd);
        TextInputLayout textInputLayoutDescrizione = dialogDettagli.findViewById(R.id.textInputLayoutDescrizioneDialogDettagliStatoOrd);
        Button buttonIndietro = dialogDettagli.findViewById(R.id.buttonIndietroDialogDettagliStatoOrd);

        String descrizione = ordinazione.getPortata().getElementoMenu().getDescrizione();
        String breveNota = ordinazione.getPortata().getBreveNota();
        setTextInputLayoutText(textInputLayoutDescrizione, descrizione);
        setTextInputLayoutText(textInputLayoutBreveNota, breveNota);

        buttonIndietro.setOnClickListener(view -> {
            dialogDettagli.dismiss();
        });

        dialogDettagli.show();
    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }



    public void initializeComponents() {
        recyclerViewOrdinazioniCorrenti = findViewById(R.id.recyclerViewOrdiniDaEvadereStatoOrdinazioni);
        recyclerViewOrdinazioniPrenotate = findViewById(R.id.recyclerViewOrdiniPrenotatiStatoOrdinazioni);
        recyclerViewOrdinazioniEvase = findViewById(R.id.recycleViewOrdiniEvasiStatoOrdinazioni);
        setDatiRecycleViewOrdinazioniCorrenti(recyclerViewOrdinazioniCorrenti, ordinazioniSospese);
        setDatiRecycleViewOrdinazioniPrenotate(recyclerViewOrdinazioniPrenotate, ordinazioniPrenotate);


        //Reecuperare i dati e usare setDatiRecycleView per impostarle
    }


    public void setDatiRecycleViewOrdinazioniCorrenti(RecyclerView recyclerView, List<StatoOrdinazione> ordinazione) {
        adapterCorrenti = new RecycleViewAdapterOrdinazioniCorrenti(this, this, ordinazione);
        recyclerView.setAdapter(adapterCorrenti);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void setDatiRecycleViewOrdinazioniPrenotate(RecyclerView recyclerView, List<StatoOrdinazione> ordinazione) {
        adapterPrenotate = new RecycleViewAdapterOrdinazioniPrenotate(this, this, ordinazione);
        recyclerView.setAdapter(adapterPrenotate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setDatiRecycleViewOrdinazioniEvase(RecyclerView recyclerView, List<StatoOrdinazione> ordinazione) {
        RecycleViewAdapterOrdinazioniEvase adapter = new RecycleViewAdapterOrdinazioniEvase(this, this, ordinazione);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClickOrdinazioniCorrenti(int position) {
        mostraDialogDettagli(ordinazioniSospese.get(position));
    }

    @Override
    public void onGreenButtonClickOrdinazioniCorrenti(int position) {
        Portata p = ordinazioniSospese.get(position).getPortata();
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, email);
        Long id = p.getId();
        ordinazioniSospese.get(position).getPortata().setPrenotato(true);
        posizione = position;
        stompClient.send("/app/invia-prenotazione", id.toString()).subscribe();
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


        //Passa elem. alla reccyle view ordinazioni prenotate
        ordinazioniPrenotate.add(new StatoOrdinazione(ordinazioniSospese.get(position).getOrdinazione(), ordinazioniSospese.get(position).getPortata()));
        adapterPrenotate.notifyItemInserted(ordinazioniPrenotate.size()-1);


    }

    @Override
    public void onItemClickOrdinazioniEvase(int position) {

    }

    @Override
    public void onItemClickOrdinazioniPrenotate(int position) {

    }

    @Override
    public void onGreenButtonClickOrdinazioniPrenotate(int position) {

    }


    private void stompConnect() {
        if (stompClient == null) {
            stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ordinazione-endpoint/websocket");
            stompClient.connect();

            stompClient.topic("/topic/ricevi-ordinazione")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ordinazione -> {
                        Log.d("WS2:", "Received " + ordinazione.getPayload());
                    });
            stompClient.topic("/topic/ricevi-prenotazione")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(prenotazione -> {
                        aggiornaPrenotazioni();
                        concludiOrdinazione(Long.valueOf(prenotazione.getPayload()));
                        Log.d("WS2:", "Received " + prenotazione.getPayload());
                    });
        }
    }


    @Override
    protected void onDestroy() {
        stompClient.disconnect();
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        stompClient.disconnect();
        super.onPause();
    }

    @Override
    protected void onStart() {
        stompConnect();
        super.onStart();
    }

    @Override
    protected void onResume() {
        stompConnect();
        super.onResume();
    }

    @Override
    protected void onStop() {
        stompClient.disconnect();
        super.onStop();
    }


}





