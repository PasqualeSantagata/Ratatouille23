package com.example.springclient.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.view.inserimentoNelMenu.HomeNuovoElemento;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;
import com.example.springclient.view.inserimentoNelMenu.StartInserimentoNelMenu;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;
import com.example.springclient.view.nuovaOrdinazione.RiepilogoOrdinazione;

import java.util.List;

public class ElementoMenuPresenter implements ElementoMenuContract.Presenter {

    private ElementoMenuModel elementoMenuModel;
    private RetrofitService retrofitService;
    private InserisciElementoActivity inserisciElementoActivity;
    private StartInserimentoNelMenu startInserimentoNelMenu;
    private HomeNuovoElemento homeNuovoElemento;

    private EsploraCategorieActivity esploraCategorieActivity;
    private RiepilogoOrdinazione riepilogoOrdinazione;

    public ElementoMenuPresenter(InserisciElementoActivity inserisciElementoActivity){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        //Da tenere presente eventuali possibili problemi con le altre new ElementoMenuModel negli altri costruttori
        elementoMenuModel = new ElementoMenuModel(retrofitService);
        this.inserisciElementoActivity = inserisciElementoActivity;
    }

    public ElementoMenuPresenter(StartInserimentoNelMenu startInserimentoNelMenu){
        this.startInserimentoNelMenu = startInserimentoNelMenu;
    }

    public ElementoMenuPresenter(HomeNuovoElemento homeNuovoElemento){
        this.homeNuovoElemento = homeNuovoElemento;
    }

    public ElementoMenuPresenter(EsploraCategorieActivity esploraCategorieActivity){
        this.esploraCategorieActivity = esploraCategorieActivity;
    }

    public ElementoMenuPresenter(RiepilogoOrdinazione riepilogoOrdinazione) {
        this.riepilogoOrdinazione = riepilogoOrdinazione;
    }

    @Override
    public void saveElementoMenu(ElementoMenu elementoMenu) {
        elementoMenuModel.salvaElementoMenu(elementoMenu, new ElementoMenuContract.ElementoMenuCallback<Void>() {
            @Override
            public void onFinished(List<String> errorMessages) {
                inserisciElementoActivity.showErrors(errorMessages);
               // getAllElementiMenu();
            }
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(inserisciElementoActivity, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Failure: ", t.getMessage());
            }
            @Override
            public void onSuccess(Void elem) {
                inserisciElementoActivity.cleanFields();
                inserisciElementoActivity.elementoSalvatoCorrettamente();
            }
        });
    }
    @Override
    public void getAllElementiMenu() {
        elementoMenuModel.getAllElementiMenu(new ElementoMenuContract.ElementoMenuCallback<List<ElementoMenu>>() {
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
    public List<ElementoMenu> getElementiPerCategoria(String categoria) {
        //interrogazione per categoria riceve dal model list<ElementoMenu> di quella categoria
        //e starta visualizza categoria
        return  null;
    }


}
