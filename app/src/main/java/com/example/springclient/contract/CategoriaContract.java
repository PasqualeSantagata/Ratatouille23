package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;

import java.util.List;

public interface CategoriaContract {
    interface Model{
        void saveCategoria(Categoria categoria, CategoriaContract.Model.CategoriaCallback categoriaCallback);

        void getAllCategorie(CategoriaContract.Model.CategoriaCallback categoriaCallback);

        interface CategoriaCallback<T>{

            void onFinished(List<String> errorMessage);

            void onFailure(Throwable t);

            void onSuccess(T returnedData);

        }

    }

    interface View{
        void initializeComponents();
        void cleanFields();
    }

    interface Presenter{
        List<Categoria> getAllCategorie();
    }
}
