package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CreaUtenzaContract;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.model.entity.Utente;
import com.example.springclient.model.AutenticazioneModel;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class AdminPresenter implements CreaUtenzaContract.Presenter {
    private CreaUtenzaContract.ViewContract adminView;
    private AutenticazioneModel autenticazioneModel = new AutenticazioneModel(RetrofitService.getIstance());

    public AdminPresenter(CreaUtenzaContract.ViewContract adminView){
        this.adminView = adminView;
    }

    @Override
    public void registraUtente(Utente utente) {
        adminView.mostraPorgressBar();
        autenticazioneModel.registraUtente(utente,new CallbackResponse<ApiResponse>() {
            @Override
            public void onFailure(Throwable t) {
                adminView.nascondiProgressBar();
                if(adminView.isVisibile()) {
                    adminView.registrazioneFallita();
                }
            }

            @Override
            public void onSuccess(Response<ApiResponse> retData) {
                adminView.nascondiProgressBar();
                if(adminView.isVisibile()) {
                    if (retData.isSuccessful()) {
                        adminView.registrazioneAvvenutaConSuccesso();
                    } else if (retData.code() == 412) {
                        ApiResponse error = new Gson().fromJson(retData.errorBody().charStream(), ApiResponse.class);
                        adminView.mostraErrore(error.getMessage());
                    }
                }
            }
        });
    }

    @Override
    public boolean validaForm(String email, String password){
        Pattern patt = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcherEmail = patt.matcher(email);
        if(!matcherEmail.matches()){
            adminView.mostraErrore("Email non valida");
            return false;
        }
        patt = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}", Pattern.UNICODE_CASE);
        Matcher matcherPass = patt.matcher(password);
        if(!matcherPass.matches()){
            adminView.mostraErrore("Password non sicura");
            return false;
        }
        return true;
    }
}
