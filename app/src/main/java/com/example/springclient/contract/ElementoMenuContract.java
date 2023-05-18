package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface ElementoMenuContract {

    interface Model{
        void saveElementoMenu(ElementoMenu elementoMenu, ElementoMenuCallback elementoMenuCallback);

        void getAllElementiMenu(ElementoMenuCallback elementoMenuCallback);

        interface ElementoMenuCallback<T>{

            void onFinished(List<String> errorMessage);

            void onFailure(Throwable t);

            void onSuccess(T returnedData);

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
