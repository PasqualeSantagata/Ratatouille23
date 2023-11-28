package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;

import java.io.File;

public interface CreaCategoriaContract {
    interface CreaCategoriaView extends BaseView{
        void salvaImmagine(Long id);
        void continuaInserimento();
        void erroreSalvataggioCategoria();
        void mostraScegliFoto();

        void impossibileContattareIlServer(String s);
    }

    interface ScegliFotoView extends BaseView{
        void caricaImmagine(byte[] immagine);
    }
    interface Presenter {
        void salavaCategoria(Categoria categoria);
        void aggiungiFotoCategoria(String id, File foto);
        void tornaHomeNuovoElemento();
        void mostraScegliFoto();
        void annullaInserisciImmagine();
        void caricaImmagine(byte[] immagine);

    }
}
