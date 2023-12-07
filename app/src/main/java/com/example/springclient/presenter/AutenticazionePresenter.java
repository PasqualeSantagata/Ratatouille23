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
    private final AutenticazioneModel autenticazioneModel = new AutenticazioneModel(RetrofitService.getIstance());
    private MainActivity loginActivity;
    private ILogout iLogout;
    private SharedPreferences sharedPreferences;

    public AutenticazionePresenter(ILogout iLogout) {
        this.iLogout = iLogout;
    }

    public AutenticazionePresenter(MainActivity loginActivity){
        this.loginActivity = loginActivity;
        sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        RetrofitService.getIstance().getTokenRefreshInterceptor().setMainActivityPresenter(this);
        RetrofitService.getIstance().getMyInterceptor().setPreferences(sharedPreferences);
    }

    @Override
    public void logInUtente(AuthRequest authRequest){
        autenticazioneModel.logInUtente(authRequest, new CallbackResponse<AuthenticationResponse>() {
            @Override
            public void onFailure(Throwable t) {
                loginActivity.disabilitaPorogressBar();
                if(loginActivity.isVisibile()) {
                    loginActivity.impossibileContattareIlServer("Errore nella connsessione con il server");
                }
            }
            @Override
            public void onSuccess(Response<AuthenticationResponse> retData) {
                if(retData.isSuccessful()){
                    AuthenticationResponse authenticationResponse = retData.body();
                    sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("accessToken", authenticationResponse.getAccessToken()).apply();
                    sharedPreferences.edit().putString("refreshToken", authenticationResponse.getRefreshToken()).apply();
                    sharedPreferences.edit().putString("ruolo", authenticationResponse.getRuolo()).apply();
                    if(authenticationResponse.getRuolo().equals(Ruolo.ADMIN.name())) {
                        loginActivity.avviaDashboardAdmin();
                    }
                    else if(authenticationResponse.getRuolo().equals(Ruolo.ADDETTO_SALA.name())){
                        loginActivity.avviaDashboardAddettoSala();
                    }
                    else if(authenticationResponse.getRuolo().equals(Ruolo.ADDETTO_CUCINA.name())){
                        loginActivity.avviaDashboardAddettoCucina(authenticationResponse.getEmail());

                    } else if (authenticationResponse.getRuolo().equals(Ruolo.SUPERVISORE.name())){
                        String ruolo = Ruolo.SUPERVISORE.name();
                        loginActivity.avviaDashboardSupervisore(ruolo);
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
    @Override
    public void logOutUtente(){
        autenticazioneModel.logOutUtente(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                iLogout.logOutFallito();
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    iLogout.logOutAvvenutoConSuccesso();
                }

            }
        });
    }

    @Override
    public void avviaRecuperoPassword() {
        loginActivity.avviaRecuperoPassword();

    }

    public void avviaAggiornaPassword(){
        loginActivity.dialgPrimoAccesso();
    }

    public MainActivity getActivity(){
        return loginActivity;
    }

}
