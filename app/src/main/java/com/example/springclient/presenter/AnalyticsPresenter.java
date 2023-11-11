package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.analytics.AnalyticsData;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.model.AnalyticsModel;
import com.example.springclient.model.UtenteModel;
import com.example.springclient.view.analytics.StatisticheActivity;

import java.util.List;

import retrofit2.Response;

public class AnalyticsPresenter {
    private AnalyticsModel analyticsModel= new AnalyticsModel(RetrofitService.getIstance());
    private UtenteModel utenteModel = new UtenteModel(RetrofitService.getIstance());
    private StatisticheActivity statisticheActivity;

    public AnalyticsPresenter(StatisticheActivity statisticheActivity){
        this.statisticheActivity = statisticheActivity;
    }

    public void getAnalytics(){
        analyticsModel.getAnalytics(new CallbackResponse<List<AnalyticsData>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<AnalyticsData>> retData) {
                if(retData.isSuccessful()){
                    statisticheActivity.setAnalyticsDataList(retData.body());
                    getCuochi();
                }
            }
        });
    }

    public void getCuochi(){
        utenteModel.getAllCuochi(new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<String>> retData) {
                if(retData.isSuccessful()){
                    statisticheActivity.setCuochi(retData.body());
                    statisticheActivity.preparaDati();
                }
            }
        });
    }


}
