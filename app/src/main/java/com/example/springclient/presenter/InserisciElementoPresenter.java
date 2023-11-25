package com.example.springclient.presenter;

import android.app.Dialog;
import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.InserisciElementoContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;
import com.google.gson.Gson;

import retrofit2.Response;

public class InserisciElementoPresenter implements InserisciElementoContract.Presenter{
    private ElementoMenuModel elementoMenuModel= new ElementoMenuModel(RetrofitService.getIstance());
    private InserisciElementoContract.View inserisciElementoView;

    public InserisciElementoPresenter(InserisciElementoContract.View inserisciElementoView){
        this.inserisciElementoView = inserisciElementoView;
    }

    @Override
    public void inserisciElementoMenu(ElementoMenu elementoMenu, String categoria) {
        elementoMenuModel.salvaElementoMenu(elementoMenu, categoria, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.getMessage());
                Dialog dialog = new Dialog(inserisciElementoView.getContext());
                inserisciElementoView.mostraDialogErroreOneBtn(dialog, "Impossibile comunicare con il server", view -> dialog.dismiss());
            }

            @Override
            public void onSuccess(Response<Void> response) {
                if(response.isSuccessful()){
                    inserisciElementoView.elementoInseritoCorrettamente();
                }
                else{
                    if(response.code() == 412) {
                        ApiResponse apiResponse = new Gson().fromJson(response.errorBody().charStream(), ApiResponse.class);
                        String errore = apiResponse.getMessage();
                        inserisciElementoView.mostraErroreInserimentoElemento(errore);
                    }
                }
            }
        });




    }

}
