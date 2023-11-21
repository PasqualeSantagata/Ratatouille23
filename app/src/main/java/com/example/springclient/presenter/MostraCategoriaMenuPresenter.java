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
    private MostraCategoriaContract.View categoriaView;

    public MostraCategoriaMenuPresenter(MostraCategoriaContract.View categoriaView) {
        this.categoriaView = categoriaView;
    }

    @Override
    public void getAllCategorie() {
        categoriaModel.getAllCategorie(new CallbackResponse<List<Categoria>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<Categoria>> retData) {
                if(retData.isSuccessful()){
                    categoriaView.setCategorie(retData.body());
                }
            }
        });
    }

    @Override
    public void getFotoCategoriaById(Categoria categoria, int posizione) {
        categoriaModel.getFotoCategoriaById(categoria.getId().toString(), new CallbackResponse<ResponseBody>() {

            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ResponseBody> retData) {
                if(retData.isSuccessful()){
                    Bitmap bitmap = BitmapFactory.decodeStream(retData.body().byteStream());
                    categoria.setImage(bitmap);
                    categoriaView.mostraImmagineCategoria(posizione);
                }
            }
        });
    }







}
