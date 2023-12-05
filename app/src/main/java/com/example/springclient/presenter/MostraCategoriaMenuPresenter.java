package com.example.springclient.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.MostraCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.model.CategoriaModel;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class MostraCategoriaMenuPresenter implements MostraCategoriaContract.Presenter {
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private MostraCategoriaContract.ViewContract categoriaView;
    public MostraCategoriaMenuPresenter(MostraCategoriaContract.ViewContract categoriaView) {
        this.categoriaView = categoriaView;
    }

    @Override
    public void tronaHomeModificaElementiMenu() {
        categoriaView.tornaIndietro();
    }

    @Override
    public void mostraElementiDellaCategoria() {
        categoriaView.mostraVisualizzaElementiDellaCategoria();
    }

    @Override
    public void getAllCategorie() {
        categoriaView.mostraPorgressBar();
        categoriaModel.getAllCategorie(new CallbackResponse<List<Categoria>>() {
            @Override
            public void onFailure(Throwable t) {
                categoriaView.nascondiProgressBar();
                if(categoriaView.isVisibile()) {
                    categoriaView.caricamentoCategorieFallito();
                }
            }

            @Override
            public void onSuccess(Response<List<Categoria>> retData) {
                categoriaView.nascondiProgressBar();
                if(retData.isSuccessful() && categoriaView.isVisibile()){
                    categoriaView.setCategorie(retData.body());
                }
            }
        });
    }

    @Override
    public void getFotoCategoriaById(Categoria categoria, int posizione) {
        categoriaView.mostraPorgressBar();
        categoriaModel.getFotoCategoriaById(categoria.getId().toString(), new CallbackResponse<ResponseBody>() {

            @Override
            public void onFailure(Throwable t) {
                categoriaView.nascondiProgressBar();
                if(categoriaView.isVisibile()) {
                    categoriaView.caricamentoCategorieFallito();
                }
            }

            @Override
            public void onSuccess(Response<ResponseBody> retData) {
                categoriaView.nascondiProgressBar();
                if(retData.isSuccessful() && categoriaView.isVisibile()){
                    Bitmap bitmap = BitmapFactory.decodeStream(retData.body().byteStream());
                    categoria.setImage(bitmap);
                    categoriaView.mostraImmagineCategoria(posizione);
                }
            }
        });
    }

    @Override
    public void apriRiepilogo() {
        categoriaView.apriRiepilogo();
    }

    @Override
    public void mostraStartNuovaOrdinazione() {
        categoriaView.tornaIndietro();
    }


}
