package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface ElementoMenuContract {

    interface Model{
        void salvaElementoMenu(ElementoMenu elementoMenu, ElementoMenuCallback<Void> elementoMenuCallback);

        void getAllElementiMenu(ElementoMenuCallback elementoMenuCallback);

    }

    interface ElementoMenuCallback<T>{

        void onFinished(List<String> errorMessage);

        void onFailure(Throwable t);

        void onSuccess(T returnedData);

    }

    interface View{
     /************** TODO ******************/
     void initializeComponents();
     void cleanFields();
     ElementoMenu getElementoValues();

    }

    interface Presenter{

        void saveElementoMenu(ElementoMenu elementoMenu);
        void getAllElementiMenu();
        List<ElementoMenu> getElementiPerCategoria(String categoria);

    }


}
