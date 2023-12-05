package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.InserisciNuovaLinguaContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;
import com.google.gson.Gson;

import retrofit2.Response;

public class InserisciNuovaLinguaPresenter implements InserisciNuovaLinguaContract.Presenter {
    private InserisciNuovaLinguaContract.SelezionaNuovaLinguaViewContract selezionaNuovaLinguaView;
    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private InserisciNuovaLinguaContract.NuovoElementoNuovaLinguaViewContract nuovoElementoNuovaLinguaView;

    public InserisciNuovaLinguaPresenter(InserisciNuovaLinguaContract.SelezionaNuovaLinguaViewContract selezionaNuovaLinguaView){
        this.selezionaNuovaLinguaView = selezionaNuovaLinguaView;
    }
    public InserisciNuovaLinguaPresenter(InserisciNuovaLinguaContract.NuovoElementoNuovaLinguaViewContract nuovoElementoNuovaLinguaView){
        this.nuovoElementoNuovaLinguaView = nuovoElementoNuovaLinguaView;
    }


    @Override
    public void annullaInserimentoNuovaLingua() {
        selezionaNuovaLinguaView.tornaIndietro();
    }

    @Override
    public void avviaNuovoElementoNuovaLingua() {
        selezionaNuovaLinguaView.avviaNuovoElementoNuovaLingua();
    }

    @Override
    public void aggiungiLingua(String elementoMenu, ElementoMenu elmentoTradotto){
        nuovoElementoNuovaLinguaView.mostraPorgressBar();
        elementoMenuModel.aggiungiLingua(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                nuovoElementoNuovaLinguaView.nascondiProgressBar();
                if(nuovoElementoNuovaLinguaView.isVisibile()) {
                    nuovoElementoNuovaLinguaView.impossibileComunicareServer("Errore di connessione: impossibile aggiungere una lingua alternativa");
                }
            }

            @Override
            public void onSuccess(Response<Void> response) {
                nuovoElementoNuovaLinguaView.nascondiProgressBar();
                if(nuovoElementoNuovaLinguaView.isVisibile()) {
                    if (response.isSuccessful()) {
                        nuovoElementoNuovaLinguaView.linguaAggiuntaConSuccesso();
                    } else {
                        if (response.code() == 412) {
                            ApiResponse apiResponse = new Gson().fromJson(response.errorBody().charStream(), ApiResponse.class);
                            String errore = apiResponse.getMessage();
                            nuovoElementoNuovaLinguaView.mostraErroreInserimentoElemento(errore);
                        }

                    }
                }
            }
        }, elementoMenu,elmentoTradotto);
    }
}
