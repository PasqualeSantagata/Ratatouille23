package com.example.springclient.contract;

public interface RecuperoCredenzialiContract {
    interface Presenter {
        void avviaRecuperoPassword(String email);

    }

    interface Model{
        void recuperaPassword(String email, CallbackResponse<Void> callbackResponse);

    }

    interface View extends BaseView{
        void verificaEmail(String email);
        void emailErrata();

    }

}
