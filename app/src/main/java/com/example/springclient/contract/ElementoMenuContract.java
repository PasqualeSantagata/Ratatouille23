package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

public interface ElementoMenuContract {

    interface Model{

        void saveElementoMenu(ElementoMenu elementoMenu, ElementoMenuCallback elementoMenuCallback);

        void getAllElementiMenu();

        interface ElementoMenuCallback{

            void onFinished(String errorMessage);

            void onFailure(Throwable t);

            void onSuccess();

        }



    }

    interface View{
     /************** TODO ******************/
     void initializeComponents();

     ElementoMenu getElementoValues();

      void cleanFields();

    }

    interface Presenter{

        void saveElementoMenu(ElementoMenu elementoMenu);
        void getAllElementiMenu();

    }


}
