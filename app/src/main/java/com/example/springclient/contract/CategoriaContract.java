package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;

import java.util.List;

import okhttp3.ResponseBody;

public interface CategoriaContract {
    interface Model{
        void saveCategoria(Categoria categoria, CallbackResponse<Categoria> callbackResponse);

        void getAllCategorie(CallbackResponse<List<Categoria>> categoriaCallback);

        void getFotoCategoriaById(String id, CallbackResponse<ResponseBody> categoriaCallback);


    }

    interface View{
        void initializeComponents();
        void setNomiCategorie(List<String> nomiCategorie);
        void setCategorie(List<Categoria> categorie);
        default void notifyAdapter(int posizione){};

    }

    interface Presenter{
        void getAllCategorie();
        void saveCategoria(Categoria categoria);
        void getFotoCategoriaById(Categoria categoria, int posizione);
        void getNomiCategorie();

    }
}
