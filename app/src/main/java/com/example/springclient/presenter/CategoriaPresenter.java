package com.example.springclient.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.view.gestioneMenu.EsploraCategorieMenuActivity;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class CategoriaPresenter implements CategoriaContract.Presenter {

    private CategoriaModel categoriaModel;
    private EsploraCategorieActivity esploraCategorieActivity;
    private EsploraCategorieMenuActivity esploraCategorieMenuActivity;

    public CategoriaPresenter(EsploraCategorieMenuActivity esploraCategorieMenuActivity) {
        this.esploraCategorieMenuActivity = esploraCategorieMenuActivity;
        categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    }



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

    public void addFotoCategoria(String id, Bitmap foto){





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
                    esploraCategorieActivity.notifyAdapter(posizione);
                }
            }
        });
    }

    public void getNomiCategorie(){
        categoriaModel.getNomiCategorie(new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<String>> retData) {
                if(retData.isSuccessful()){
                    for(String s: retData.body()) {
                        Log.d("nome cat: ", s);
                    }
                }
            }
        });

    }




}
