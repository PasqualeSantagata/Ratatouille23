package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.GestisciComandeContract;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.entity.Ordinazione;

import java.util.List;

import retrofit2.Response;

public class GestisciComandePresenter implements GestisciComandeContract.Presenter{
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());
    private GestisciComandeContract.HomeGestioneComande homeGestioneComande;

    public GestisciComandePresenter(GestisciComandeContract.HomeGestioneComande homeGestioneComande) {
        this.homeGestioneComande = homeGestioneComande;
    }

    @Override
    public void getOrdinazioniSospese() {
        ordinazioneModel.getOrdinazioniSospese(new CallbackResponse<List<Ordinazione>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<Ordinazione>> retData) {
                if ((retData.isSuccessful())){
                    homeGestioneComande.setOrdinazioniSospese(retData.body());
                }
            }
        });
    }
    @Override
    public void evadiOrdinazione(long idOrdinazione) {
        ordinazioneModel.concludiOrdinazione(new CallbackResponse<Ordinazione>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<Ordinazione> retData) {
                if(retData.isSuccessful()){


                }
            }
        },idOrdinazione);
    }
}
