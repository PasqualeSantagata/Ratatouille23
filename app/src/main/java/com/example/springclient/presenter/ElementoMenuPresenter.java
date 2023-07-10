package com.example.springclient.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.view.inserimentoNelMenu.HomeNuovoElemento;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;
import com.example.springclient.view.inserimentoNelMenu.StartInserimentoNelMenu;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorie;

import java.util.List;

public class ElementoMenuPresenter implements ElementoMenuContract.Presenter {

    private ElementoMenuModel elementoMenuModel;
    private RetrofitService retrofitService;
    private InserisciElementoActivity inserisciElementoView;
    private StartInserimentoNelMenu startInserimentoNelMenu;
    private HomeNuovoElemento homeNuovoElemento;

    private EsploraCategorie esploraCategorie;

    public ElementoMenuPresenter(InserisciElementoActivity inserisciElementoView){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        //Da tenere presente eventuali possibili problemi con le altre new ElementoMenuModel negli altri costruttori
        elementoMenuModel = new ElementoMenuModel(retrofitService);
        this.inserisciElementoView = inserisciElementoView;
    }

    public ElementoMenuPresenter(StartInserimentoNelMenu startInserimentoNelMenu){
        this.startInserimentoNelMenu = startInserimentoNelMenu;
    }

    public ElementoMenuPresenter(HomeNuovoElemento homeNuovoElemento){
        this.homeNuovoElemento = homeNuovoElemento;
    }

    public ElementoMenuPresenter(EsploraCategorie esploraCategorie){
        this.esploraCategorie=esploraCategorie;
    }

    @Override
    public void saveElementoMenu(ElementoMenu elementoMenu) {
        elementoMenuModel.saveElementoMenu(elementoMenu, new ElementoMenuContract.Model.ElementoMenuCallback<ElementoMenu>() {
            @Override
            public void onFinished(List<String> errorMessages) {
                inserisciElementoView.showErrors(errorMessages);
                getAllElementiMenu();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(inserisciElementoView, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Failure: ", t.getMessage());
            }
            @Override
            public void onSuccess(ElementoMenu elem) {
                inserisciElementoView.cleanFields();
            }
        });
    }
    @Override
    public void getAllElementiMenu() {
        elementoMenuModel.getAllElementiMenu(new ElementoMenuContract.Model.ElementoMenuCallback<List<ElementoMenu>>() {
            @Override
            public void onFinished(List<String> errorMessage) {}
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.getMessage());
            }
            @Override
            public void onSuccess(List<ElementoMenu> returnedData) {
                for (ElementoMenu e: returnedData) {
                    Log.d("elemento: ", e.getNome());
                }
            }
        });
    }

    @Override
    public void setElementiPerCategoriaRecycleView(Ordinazione ordinazione, String categoria) {
        //interrogazione per categoria
    }


}
