package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

public interface InserisciNuovaLinguaContract {

    interface SelezionaNuovaLinguaView extends BaseView {

        void avviaNuovoElementoNuovaLingua();
    }

    interface NuovoElementoNuovaLinguaView extends BaseView{
        void impossibileComunicareServer(String messaggio);

        void linguaAggiuntaConSuccesso();

        void mostraErroreInserimentoElemento(String errore);
    }

    interface Presenter {
        void annullaInserimentoNuovaLingua();

        void avviaNuovoElementoNuovaLingua();
        void aggiungiLingua(String nomeElemento, ElementoMenu elementoTradotto);
    }
}
