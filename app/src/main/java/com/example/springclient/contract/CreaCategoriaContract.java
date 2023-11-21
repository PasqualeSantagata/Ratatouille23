package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;

import java.io.File;

public interface CreaCategoriaContract {
    interface View extends BaseView{
        void salvaImmagine(Long id);
        void continuaInserimento();



    }
    interface Presenter{
        void salavaCategoria(Categoria categoria);
        void aggiungiFotoCategoria(String id, File foto);

    }
}
