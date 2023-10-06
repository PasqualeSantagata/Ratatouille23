package com.example.springclient.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.view.inserimentoNelMenu.HomeNuovoElemento;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;
import com.example.springclient.view.inserimentoNelMenu.GestioneMenuActivity;
import com.example.springclient.view.inserimentoNelMenu.VisualizzaCategoriaMenuActivity;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;
import com.example.springclient.view.nuovaOrdinazione.RiepilogoOrdinazioneActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ElementoMenuPresenter implements ElementoMenuContract.Presenter {

    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private RetrofitService retrofitService;
    private InserisciElementoActivity inserisciElementoActivity;
    private GestioneMenuActivity gestioneMenuActivity;
    private HomeNuovoElemento homeNuovoElemento;

    private EsploraCategorieActivity esploraCategorieActivity;
    private RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity;
    private VisualizzaCategoriaMenuActivity visualizzaCategoriaMenuActivity;

    public ElementoMenuPresenter(InserisciElementoActivity inserisciElementoActivity){
        this.inserisciElementoActivity = inserisciElementoActivity;
    }

    public ElementoMenuPresenter(GestioneMenuActivity gestioneMenuActivity){
        this.gestioneMenuActivity = gestioneMenuActivity;
    }

    public ElementoMenuPresenter(HomeNuovoElemento homeNuovoElemento){
        this.homeNuovoElemento = homeNuovoElemento;
    }

    public ElementoMenuPresenter(EsploraCategorieActivity esploraCategorieActivity){
        this.esploraCategorieActivity = esploraCategorieActivity;
    }

    public ElementoMenuPresenter(RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity) {
        this.riepilogoOrdinazioneActivity = riepilogoOrdinazioneActivity;
    }

    public ElementoMenuPresenter(VisualizzaCategoriaMenuActivity visualizzaCategoriaMenuActivity) {
        this.visualizzaCategoriaMenuActivity = visualizzaCategoriaMenuActivity;
    }

    @Override
    public void saveElementoMenu(ElementoMenu elementoMenu) {
        elementoMenuModel.getAllElementiMenu(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.getMessage());
                Toast.makeText(inserisciElementoActivity, t.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onSuccess(Response<Void> response) {
                if(response.isSuccessful()){
                    inserisciElementoActivity.cleanFields();
                    inserisciElementoActivity.elementoSalvatoCorrettamente();
                }
                else{
                    if(response.code() == 412) {
                        ApiResponse[] apiResponse = new Gson().fromJson(response.errorBody().charStream(), ApiResponse[].class);
                        List<String> listOfError = new ArrayList<>();
                        for (ApiResponse a : apiResponse)
                            listOfError.add(a.getMessage());
                        inserisciElementoActivity.showErrors(listOfError);
                    }
                }
            }
        });

    }
    @Override
    public void getAllElementiMenu() {
        elementoMenuModel.getAllElementiMenu(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.getMessage());
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){


                }
            }
        });
    }

    @Override
    public List<ElementoMenu> getElementiPerCategoria(String categoria) {
        //interrogazione per categoria riceve dal model list<ElementoMenu> di quella categoria
        //e starta visualizza categoria
        return  null;
    }

    public void rimuoviElemento(String id){
        elementoMenuModel.rimuoviElemento(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    visualizzaCategoriaMenuActivity.cancellaElemento();
                }
            }
        }, id);

    }

    public void aggiungiLingua(String id, ElementoMenu elementoMenu){
        elementoMenuModel.aggiungiLingua(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {

            }
        }, id, elementoMenu);
    }

    public void restituisciTraduzione(String id){
        elementoMenuModel.restituisciTraduzione(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                if(retData.isSuccessful()) {
                    visualizzaCategoriaMenuActivity.mostraTraduzione(retData.body());
                }
            }
        }, id);
    }

    public void restituisciLinguaBase(String id){
        elementoMenuModel.restituisciLinguaBase(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {

            }
        }, id);
    }




}
