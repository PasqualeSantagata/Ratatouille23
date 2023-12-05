package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.VisualizzElementiContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.model.ElementoMenuModel;

import java.util.List;

import retrofit2.Response;

public class VisualizzElementiPresenter implements VisualizzElementiContract.Presenter {

    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private VisualizzElementiContract.ViewContract visualizzaElementiView;
    public VisualizzElementiPresenter(VisualizzElementiContract.ViewContract visualizzaElementiView){
        this.visualizzaElementiView = visualizzaElementiView;
    }

    @Override
    public void eliminaElementoDallaCategoria(Long idCategoria, ElementoMenu elementoMenu){
        visualizzaElementiView.mostraPorgressBar();
        categoriaModel.eliminaElementoDallaCategoria(idCategoria.toString(), elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                visualizzaElementiView.nascondiProgressBar();
                if(visualizzaElementiView.isVisibile()) {
                    visualizzaElementiView.impossibileRimuovereElemento("Errore di connessione: impossibile rimuovere l'elemento");
                }
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                visualizzaElementiView.nascondiProgressBar();
                if(retData.isSuccessful() && visualizzaElementiView.isVisibile()){
                    visualizzaElementiView.rimuoviElemento();
                }

            }
        });
    }


    @Override
    public void mostraModifica(ElementoMenu elementoMenu) {
        visualizzaElementiView.mostraModifica(elementoMenu);
    }


    @Override
    public void restituisciTraduzione(String idElemento) {
        visualizzaElementiView.mostraPorgressBar();
        elementoMenuModel.restituisciTraduzione(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {
                visualizzaElementiView.nascondiProgressBar();
                if(visualizzaElementiView.isVisibile()) {
                    visualizzaElementiView.impossibileComunicareConServer("Errore di rete con il server, impossibile recuperare la traduzione");
                }
            }
            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                visualizzaElementiView.nascondiProgressBar();
                if(visualizzaElementiView.isVisibile()) {
                    if (retData.isSuccessful()) {
                        visualizzaElementiView.mostraTraduzione(retData.body());
                    } else {
                        visualizzaElementiView.traduzioneAssente();
                    }
                }
            }
        }, idElemento);

    }

    @Override
    public void restituisciLinguaBase(String idElemento) {
        elementoMenuModel.restituisciLinguaBase(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {

            }
        }, idElemento);
    }

    @Override
    public void aggiornaElementiCategoria(String nomeCategoria){

        categoriaModel.getCategoriaByNome(nomeCategoria, new CallbackResponse<Categoria>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<Categoria> retData) {
                if(retData.isSuccessful() && visualizzaElementiView.isVisibile()){
                    Categoria categoria = retData.body();
                    categoria.ordinaCategoria();
                    visualizzaElementiView.setElementi(categoria.getElementi());
                }

            }
        });

    }

    @Override
    public void mostraRiepilogo() {
        visualizzaElementiView.mostraRiepilogo();
    }

    @Override
    public void tornaEsploraCategorieMenu() {
        visualizzaElementiView.tornaIndietro();
    }

    @Override
    public void mostrFiltraCategorie(String nomeCategoria) {
        visualizzaElementiView.mostraPorgressBar();
        elementoMenuModel.trovaElementiPerNomeCategoria(nomeCategoria, new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {
                visualizzaElementiView.nascondiProgressBar();
                if(visualizzaElementiView.isVisibile()) {
                    visualizzaElementiView.impossibileComunicareConServer("Impossibile comunicare con il server");
                }
            }

            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                visualizzaElementiView.nascondiProgressBar();
                if(retData.isSuccessful() && visualizzaElementiView.isVisibile()){
                    visualizzaElementiView.mostraFiltraCategoria(retData.body());
                }
            }
        });
    }


}
