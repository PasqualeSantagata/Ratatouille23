package com.example.springclient.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.AutenticazioneContract;
import com.example.springclient.authentication.AuthenticationResponse;
import com.example.springclient.entity.Ruolo;
import com.example.springclient.model.AutenticazioneModel;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.view.MainActivity;

import retrofit2.Response;

public class AutenticazionePresenter implements AutenticazioneContract.Presenter {
    private final AutenticazioneModel autenticazioneModel;
    private final RetrofitService retrofitService;
    private MainActivity loginActivity;
    private SharedPreferences sharedPreferences;


    private AutenticazionePresenter(){
        retrofitService = RetrofitService.getIstance();
        retrofitService.setUtentePresenter(this);
        autenticazioneModel = AutenticazioneModel.getIstance();
    }
    public AutenticazionePresenter(MainActivity loginActivity){
        this();
        this.loginActivity = loginActivity;
        sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        retrofitService.getTokenRefreshInterceptor().setMainActivityPresenter(this);
        retrofitService.getMyInterceptor().setPreferences(sharedPreferences);
    }

    @Override
    public void logInUtente(AuthRequest authRequest){
        autenticazioneModel.logInUtente(authRequest, new CallbackResponse<AuthenticationResponse>() {
            @Override
            public void onFailure(Throwable t) {
                loginActivity.disabilitaPorogressBar();
                loginActivity.mostraDialogErrore("Errore nella connsessione con il server");
            }
            @Override
            public void onSuccess(Response<AuthenticationResponse> retData) {
                if(retData.isSuccessful()){
                    AuthenticationResponse authenticationResponse = retData.body();
                    sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("accessToken", authenticationResponse.getAccessToken()).apply();
                    sharedPreferences.edit().putString("refreshToken", authenticationResponse.getRefreshToken()).apply();
                    if(authenticationResponse.getRuolo().equals(Ruolo.ADMIN.name())) {
                        loginActivity.avviaDashboardAdmin();
                    }
                    else if(authenticationResponse.getRuolo().equals(Ruolo.ADDETTO_SALA.name())){
                        loginActivity.avviaDashboardAddettoSala();
                    }
                    else if(authenticationResponse.getRuolo().equals(Ruolo.ADDETTO_CUCINA.name())){
                        loginActivity.avviaDashboardAddettoCucina(authenticationResponse.getEmail());

                    }
                }
                else if(retData.code() == 403){
                   avviaAggiornaPassword();
                }
                else{
                    loginActivity.loginErrore();
                    sharedPreferences.edit().putString("email", "").apply();
                }
            }
        });
    }

    public void avviaAggiornaPassword(){
        loginActivity.dialgPrimoAccesso();
    }

    public MainActivity getActivity(){
        return loginActivity;
    }

}
