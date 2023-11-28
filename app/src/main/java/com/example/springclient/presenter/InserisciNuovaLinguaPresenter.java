package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.InserisciNuovaLinguaContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;

import retrofit2.Response;

public class InserisciNuovaLinguaPresenter implements InserisciNuovaLinguaContract.Presenter {
    private InserisciNuovaLinguaContract.SelezionaNuovaLinguaView selezionaNuovaLinguaView;
    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private InserisciNuovaLinguaContract.NuovoElementoNuovaLinguaView nuovoElementoNuovaLinguaView;

    public InserisciNuovaLinguaPresenter(InserisciNuovaLinguaContract.SelezionaNuovaLinguaView selezionaNuovaLinguaView){
        this.selezionaNuovaLinguaView = selezionaNuovaLinguaView;
    }
    public InserisciNuovaLinguaPresenter(InserisciNuovaLinguaContract.NuovoElementoNuovaLinguaView nuovoElementoNuovaLinguaView){
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
        elementoMenuModel.aggiungiLingua(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    nuovoElementoNuovaLinguaView.linguaAggiuntaConSuccesso();
                }
                else{

                }
            }
        }, elementoMenu,elmentoTradotto);
    }
}
