package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface CategoriaContract {
    interface Model{
        void saveCategoria(Categoria categoria, CallbackResponse<Void> callbackResponse);

        void getAllCategorie(CallbackResponse<List<Categoria>> categoriaCallback);


    }

    interface View{
        void initializeComponents();
        void cleanFields();
    }

    interface Presenter{
        void getAllCategorie();

    }
}
