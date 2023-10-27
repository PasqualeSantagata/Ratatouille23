package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface ElementoMenuContract {

    interface Model{
        void salvaElementoMenu(ElementoMenu elementoMenu, String categoria, CallbackResponse<Void> elementoMenuCallback);

        void getAllElementiMenu(CallbackResponse<List<ElementoMenu>> elementoMenuCallback);

    }


    interface View{
     /************** TODO ******************/
     void initializeComponents();

     default void mostraTraduzione(ElementoMenu elementoMenu){}
     default void traduzioneAssente(){}
     default void setElementi(List<ElementoMenu> elementi){}

    }

    interface Presenter{

        void saveElementoMenu(ElementoMenu elementoMenu, String categoria);
        void getAllElementiMenu();
        List<ElementoMenu> getElementiPerCategoria(String categoria);
        void rimuoviElemento(String id);

    }


}
