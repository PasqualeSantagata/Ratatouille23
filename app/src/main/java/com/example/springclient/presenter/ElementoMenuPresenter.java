package com.example.springclient.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.view.gestioneMenu.CercaElementoActivity;
import com.example.springclient.view.gestioneMenu.StartGestioneMenuActivity;
import com.example.springclient.view.gestioneMenu.HomeNuovoElementoActivity;
import com.example.springclient.view.gestioneMenu.InserisciElementoActivity;
import com.example.springclient.view.gestioneMenu.VisualizzaElementiDellaCategoriaActivity;
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
    private StartGestioneMenuActivity gestioneMenuActivity;
    private HomeNuovoElementoActivity homeNuovoElementoActivity;

    private EsploraCategorieActivity esploraCategorieActivity;
    private RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity;
    private VisualizzaElementiDellaCategoriaActivity visualizzaElementiDellaCategoriaActivity;
    private CercaElementoActivity cercaElementoActivity;

    public ElementoMenuPresenter(InserisciElementoActivity inserisciElementoActivity){
        this.inserisciElementoActivity = inserisciElementoActivity;
    }

    public ElementoMenuPresenter(StartGestioneMenuActivity gestioneMenuActivity){
        this.gestioneMenuActivity = gestioneMenuActivity;
    }

    public ElementoMenuPresenter(HomeNuovoElementoActivity homeNuovoElementoActivity){
        this.homeNuovoElementoActivity = homeNuovoElementoActivity;
    }

    public ElementoMenuPresenter(EsploraCategorieActivity esploraCategorieActivity){
        this.esploraCategorieActivity = esploraCategorieActivity;
    }

    public ElementoMenuPresenter(RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity) {
        this.riepilogoOrdinazioneActivity = riepilogoOrdinazioneActivity;
    }

    public ElementoMenuPresenter(VisualizzaElementiDellaCategoriaActivity visualizzaElementiDellaCategoriaActivity) {
        this.visualizzaElementiDellaCategoriaActivity = visualizzaElementiDellaCategoriaActivity;
    }

    public ElementoMenuPresenter(CercaElementoActivity cercaElementoActivity) {
        this.cercaElementoActivity = cercaElementoActivity;
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
                    visualizzaElementiDellaCategoriaActivity.cancellaElemento();
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
                    visualizzaElementiDellaCategoriaActivity.mostraTraduzione(retData.body());
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
