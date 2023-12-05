package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.RecuperoCredenzialiContract;
import com.example.springclient.model.RecuperoCredenzialiModel;

import com.example.springclient.view.MainActivity;

import retrofit2.Response;

public class RecuperoCredenzialiPresenter implements RecuperoCredenzialiContract.Presenter {

    private RecuperoCredenzialiContract.ViewContract recuperoCredenzialiView;
    private RecuperoCredenzialiModel recuperoCredenzialiModel = new RecuperoCredenzialiModel(RetrofitService.getIstance());
    private MainActivity loginActivity;
    public RecuperoCredenzialiPresenter(RecuperoCredenzialiContract.ViewContract recuperoCredenzialiView){
        this.recuperoCredenzialiView = recuperoCredenzialiView;
    }
    public RecuperoCredenzialiPresenter(MainActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    @Override
    public void avviaRecuperoPassword(String email){
        recuperoCredenzialiView.mostraPorgressBar();
        recuperoCredenzialiModel.recuperaPassword(email, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                recuperoCredenzialiView.nascondiProgressBar();
                if(recuperoCredenzialiView.isVisibile()) {
                    recuperoCredenzialiView.erroreConnessione("Errore di connessione: impossiblie comunicare con il server");
                }
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                recuperoCredenzialiView.nascondiProgressBar();
                if(retData.isSuccessful() && recuperoCredenzialiView.isVisibile()){
                    if(recuperoCredenzialiView!= null)
                        recuperoCredenzialiView.emailInviataCorrettamente();
                }
                else if(retData.code() == 401 && recuperoCredenzialiView.isVisibile()){
                    recuperoCredenzialiView.emailErrata();
                }
            }
        });
    }
    @Override
    public void avviaAggiornaPassword(String email){
        loginActivity.mostraPorgressBar();
        recuperoCredenzialiModel.recuperaPassword(email, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                loginActivity.nascondiProgressBar();
                if(loginActivity.isVisibile()) {
                    loginActivity.impossibileContattareIlServer("Errore di connessione durante l'aggiornamento della password");
                }
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                loginActivity.nascondiProgressBar();
            }
        });
    }
}
