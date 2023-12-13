package com.example.springclient.view.statoOrdinazioni;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.springclient.R;
import com.example.springclient.contract.GestisciComandeContract;
import com.example.springclient.model.entity.Portata;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.example.springclient.presenter.GestisciComandePresenter;
import com.example.springclient.presenter.ILogout;
import com.example.springclient.view.MainActivity;
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
        ,IRecycleViewOrdinazioniEvase, GestisciComandeContract.HomeGestioneComande, ILogout {

    private RecyclerView recyclerViewOrdinazioniCorrenti;
    private RecyclerView recyclerViewOrdinazioniPrenotate;
    private RecyclerView recyclerViewOrdinazioniEvase;
    private StompClient stompClient;
    private GestisciComandeContract.Presenter comandePresenter;
    private List<Portata> portateSospese;
    private List<Portata> portatePrenotate;
    private List<Portata> portateEvase;
   // private int posizione;
    private RecycleViewAdapterOrdinazioniCorrenti adapterCorrenti;
    private RecycleViewAdapterOrdinazioniEvase adapterEvase;
    private RecycleViewAdapterOrdinazioniPrenotate adapterPrenotate;
    private FirebaseAnalytics firebaseAnalytics;
    private String email;
    private AutenticazionePresenter autenticazionePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_stato_ordinazioni);
        getSupportActionBar().setTitle("STATO DELLE ORDINAZIONI");

        comandePresenter = new GestisciComandePresenter(this);
        autenticazionePresenter = new AutenticazionePresenter(this);
        comandePresenter.getOrdinazioniSospese();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        email = getIntent().getStringExtra("email");
        portatePrenotate = new ArrayList<>();
        portateEvase = new ArrayList<>();
        stompConnect();
    }

    public void inizializzaComponenti() {
        recyclerViewOrdinazioniCorrenti = findViewById(R.id.recyclerViewOrdiniDaEvadereStatoOrdinazioni);
        recyclerViewOrdinazioniPrenotate = findViewById(R.id.recyclerViewOrdiniPrenotatiStatoOrdinazioni);
        recyclerViewOrdinazioniEvase = findViewById(R.id.recycleViewOrdiniEvasiStatoOrdinazioni);
        setDatiRecycleViewOrdinazioniCorrenti(recyclerViewOrdinazioniCorrenti, portateSospese);
        setDatiRecycleViewOrdinazioniPrenotate(recyclerViewOrdinazioniPrenotate, portatePrenotate);
        setDatiRecycleViewOrdinazioniEvase(recyclerViewOrdinazioniEvase, portateEvase);
    }

    @Override
    public void setPortateSospese(List<Portata> portateSospese) {
        this.portateSospese = portateSospese;
        inizializzaComponenti();
    }

    @Override
    public void impossibileComunicareConServer(String messaggio) {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, messaggio, view -> dialog.dismiss());
    }

    @Override
    public void ordinazioneConclusa(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public void aggiornaPrenotazioni(long idPortata) {
        int rimuovi = -1;
        for(Portata p: portateSospese){
            if(p.getId() == idPortata){
                rimuovi = portateSospese.indexOf(p);
                break;
            }
        }
        if(rimuovi > -1){
            portateSospese.remove(rimuovi);
            adapterCorrenti.notifyItemRemoved(rimuovi);
        }
    }

    private void mostraDialogDettagli(Portata portata){
        Dialog dialogDettagli = new Dialog(this);
        dialogDettagli.setContentView(R.layout.dialog_dettagli_stato_ordinazione);

        TextInputLayout textInputLayoutTavolo = dialogDettagli.findViewById(R.id.textInputLayoutTavoloDialogStatoOrdinazione);
        TextInputLayout textInputLayoutSala = dialogDettagli.findViewById(R.id.textInputLayoutSalaDialogStatoOrdinazione);
        TextInputLayout textInputLayoutBreveNota = dialogDettagli.findViewById(R.id.textInputLayoutNotaDialogDettagliStatoOrd);
        TextInputLayout textInputLayoutDescrizione = dialogDettagli.findViewById(R.id.textInputLayoutDescrizioneDialogDettagliStatoOrd);
        Button buttonIndietro = dialogDettagli.findViewById(R.id.buttonIndietroDialogDettagliStatoOrd);


        setTextInputLayoutText(textInputLayoutDescrizione, portata.getElementoMenu().getDescrizione());
        setTextInputLayoutText(textInputLayoutBreveNota, portata.getBreveNota());
        setTextInputLayoutText(textInputLayoutTavolo, portata.getOrdinazione().getTavolo().toString());
        setTextInputLayoutText(textInputLayoutSala, portata.getOrdinazione().getSala().toString());

        buttonIndietro.setOnClickListener(view -> dialogDettagli.dismiss());

        dialogDettagli.show();
    }

    private void setTextInputLayoutText(TextInputLayout textInputLayout, String text) {
        EditText editText = textInputLayout.getEditText();
        editText.setText(text);
    }


    @Override
    public void tornaIndietro() {

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


    //TODO potenzial metodo da testare, passando adapter e facendo restituire la recycler view
    public void setDatiRecycleViewOrdinazioniCorrenti(RecyclerView recyclerView, List<Portata> ordinazione) {
        adapterCorrenti = new RecycleViewAdapterOrdinazioniCorrenti(this, this, ordinazione);
        recyclerView.setAdapter(adapterCorrenti);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public void setDatiRecycleViewOrdinazioniPrenotate(RecyclerView recyclerView, List<Portata> ordinazione) {
        adapterPrenotate = new RecycleViewAdapterOrdinazioniPrenotate(this, this, ordinazione);
        recyclerView.setAdapter(adapterPrenotate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void setDatiRecycleViewOrdinazioniEvase(RecyclerView recyclerView, List<Portata> ordinazione) {
        adapterEvase = new RecycleViewAdapterOrdinazioniEvase(this, this, ordinazione);
        recyclerView.setAdapter(adapterEvase);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onItemClickOrdinazioniCorrenti(int position) {
        mostraDialogDettagli(portateSospese.get(position));
    }

    @Override
    public void onGreenButtonClickOrdinazioniCorrenti(int position) {
        Portata p = portateSospese.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, email);
        Long id = p.getId();
        portateSospese.get(position).setPrenotato(true);
        stompClient.send("/app/invia-prenotazione", id.toString()).subscribe();
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //Passa elem. alla reccyle view ordinazioni prenotate
        portatePrenotate.add(p);
        adapterPrenotate.notifyItemInserted(portatePrenotate.size()-1);
    }

    @Override
    public void onItemClickOrdinazioniEvase(int position) {
        mostraDialogDettagli(portateEvase.get(position));
    }

    @Override
    public void onItemClickOrdinazioniPrenotate(int position) {
        mostraDialogDettagli(portatePrenotate.get(position));
    }

    @Override
    public void onGreenButtonClickOrdinazioniPrenotate(int position) {
        Portata p = portatePrenotate.get(position);
        portateEvase.add(p);
        adapterEvase.notifyItemInserted(portateEvase.size() -1);
        portatePrenotate.remove(position);
        adapterPrenotate.notifyItemRemoved(position);
    }


    @SuppressLint("CheckResult")
    private void stompConnect() {
        if (stompClient == null) {
            stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://20.251.220.220:8080/ordinazione-endpoint/websocket");
            stompClient.connect();

            stompClient.topic("/topic/ricevi-ordinazione")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(ordinazione -> {
                        Log.d("WS2:", "Received " + ordinazione.getPayload());
                        comandePresenter.getOrdinazioniSospese();
                    });

            stompClient.topic("/topic/ricevi-prenotazione")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(prenotazione -> {
                        long idPortata = Long.valueOf(prenotazione.getPayload());
                        aggiornaPrenotazioni(idPortata);
                        comandePresenter.evadiOrdinazione(idPortata);
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


    @Override
    public void avviaLogout() {
        Dialog dialog = new Dialog(this);
        mostraDialogWarningTwoBtn(dialog,"Sicuro di voler uscire?",
                view -> autenticazionePresenter.logOutUtente(),
                view -> dialog.dismiss());
    }

    @Override
    public void logOutAvvenutoConSuccesso() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void logOutFallito() {
        Dialog dialog = new Dialog(this);
        mostraDialogErroreOneBtn(dialog, "Errore di connessione impossibile terminare la tessione", view -> dialog.dismiss());
    }

    @Override
    public void onBackPressed() {
        avviaLogout();
    }
}





