package com.example.springclient.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class CategoriaPresenter implements CategoriaContract.Presenter {

    private CategoriaModel categoriaModel;
    private EsploraCategorieActivity esploraCategorieActivity;

    public CategoriaPresenter(EsploraCategorieActivity esploraCategorieActivity) {
        this.esploraCategorieActivity = esploraCategorieActivity;
        categoriaModel = new CategoriaModel(RetrofitService.getIstance());
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
                    esploraCategorieActivity.setCategorie(retData.body());
                    esploraCategorieActivity.caricaImmagini();
                }
            }
        });
    }

    @Override
    public void saveCategoria(Categoria categoria) {
        categoriaModel.saveCategoria(categoria, new CallbackResponse<Categoria>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Categoria> retData) {
                if(retData.isSuccessful()){
                    Log.d("retData: ", retData.body().toString());
                }
            }
        });
    }

    @Override
    public void getFotoCategoriaById(Categoria categoria) {
        categoriaModel.getFotoCategoriaById(categoria.getId().toString(), new CallbackResponse<ResponseBody>() {

            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ResponseBody> retData) {
                if(retData.isSuccessful()){
                    Bitmap bitmap = BitmapFactory.decodeStream(retData.body().byteStream());
                    categoria.setImage(bitmap);
                }
            }
        });
    }


}
