package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface ElementoMenuContract {

    interface Model{
        void salvaElementoMenu(ElementoMenu elementoMenu, CallbackResponse<Void> elementoMenuCallback);

        void getAllElementiMenu(CallbackResponse<List<ElementoMenu>> elementoMenuCallback);

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
        void rimuoviElemento(String id);

    }


}
