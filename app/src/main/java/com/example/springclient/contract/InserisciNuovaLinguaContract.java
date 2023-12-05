package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

public interface InserisciNuovaLinguaContract {

    interface SelezionaNuovaLinguaViewContract extends BaseViewContract {

        void avviaNuovoElementoNuovaLingua();
    }

    interface NuovoElementoNuovaLinguaViewContract extends BaseViewContract {
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
