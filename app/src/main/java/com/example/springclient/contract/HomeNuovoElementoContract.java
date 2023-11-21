package com.example.springclient.contract;

import java.util.List;


public interface HomeNuovoElementoContract {

    interface View extends BaseView {
        void setNomiCategorie(List<String> nomiCategorie);
    }

    interface Presenter{

        void getNomiCategorie();

    }
}
