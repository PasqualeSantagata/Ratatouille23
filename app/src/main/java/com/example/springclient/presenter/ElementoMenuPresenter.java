package com.example.springclient.presenter;

import android.util.Log;
import android.widget.Toast;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.view.InserisciElementoActivity;

public class ElementoMenuPresenter implements ElementoMenuContract.Presenter {

    private ElementoMenuModel elementoMenuModel;
    private RetrofitService retrofitService;
    private InserisciElementoActivity inserisciElementoView;


    public ElementoMenuPresenter(InserisciElementoActivity inserisciElementoView){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        elementoMenuModel = new ElementoMenuModel(retrofitService);
        this.inserisciElementoView = inserisciElementoView;

    }

    @Override
    public void saveElementoMenu(ElementoMenu elementoMenu) {
        elementoMenuModel.saveElementoMenu(elementoMenu, new ElementoMenuContract.Model.ElementoMenuCallback() {
            @Override
            public void onFinished(String errorMessage) {
                Toast.makeText(inserisciElementoView, errorMessage, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(inserisciElementoView, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("elemento: ", "errore salvataggio elemento");
            }

            @Override
            public void onSuccess() {
                inserisciElementoView.cleanFields();

            }

        });
    }

    @Override
    public void getAllElementiMenu() {

    }
}
