package com.example.springclient.contract;

import com.example.springclient.model.entity.Categoria;

import java.io.File;

public interface CreaCategoriaContract {
    interface CreaCategoriaViewContract extends BaseViewContract {
        void salvaImmagine(Long id);
        void continuaInserimento();
        void erroreSalvataggioCategoria();
        void mostraScegliFoto();

        void impossibileContattareIlServer(String s);

        void categoriaGiaEsistente();
    }

    interface ScegliFotoViewContract extends BaseViewContract {
        void caricaImmagine(byte[] immagine);
    }
    interface Presenter {
        void salvaCategoria(Categoria categoria);
        void aggiungiFotoCategoria(String id, File foto);
        void tornaHomeNuovoElemento();
        void mostraScegliFoto();
        void annullaInserisciImmagine();
        void caricaImmagine(byte[] immagine);

    }
}
