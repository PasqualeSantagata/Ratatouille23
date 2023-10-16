package com.example.springclient.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.view.gestioneMenu.CreaCategoriaActivity;
import com.example.springclient.view.gestioneMenu.EsploraCategorieMenuActivity;
import com.example.springclient.view.gestioneMenu.HomeNuovoElementoActivity;
import com.example.springclient.view.gestioneMenu.ModificaElementoActivity;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class CategoriaMenuPresenter implements CategoriaContract.Presenter {
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private EsploraCategorieMenuActivity esploraCategorieMenuActivity;
    private HomeNuovoElementoActivity homeNuovoElementoActivity;
    private CreaCategoriaActivity creaCategoriaActivity;
    private List<String> nomiCategoria = new ArrayList<>();

    private CategoriaContract.View categoriaView;


    public CategoriaMenuPresenter(EsploraCategorieMenuActivity esploraCategorieMenuActivity) {
        this.esploraCategorieMenuActivity = esploraCategorieMenuActivity;
    }

    public CategoriaMenuPresenter(HomeNuovoElementoActivity homeNuovoElementoActivity) {
        this.homeNuovoElementoActivity = homeNuovoElementoActivity;
    }

    public CategoriaMenuPresenter(CreaCategoriaActivity creaCategoriaActivity) {
        this.creaCategoriaActivity = creaCategoriaActivity;
    }
    public CategoriaMenuPresenter(EsploraCategorieActivity esploraCategorieActivity){
        this.categoriaView = esploraCategorieActivity;
    }


    public CategoriaMenuPresenter(ModificaElementoActivity modificaElementoActivity){
        this.categoriaView = modificaElementoActivity;
    };

    @Override
    public void getAllCategorie() {
        categoriaModel.getAllCategorie(new CallbackResponse<List<Categoria>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<Categoria>> retData) {
                if(retData.isSuccessful()){
                    esploraCategorieMenuActivity.setCategorie(retData.body());
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
                    creaCategoriaActivity.salvaImmagine(retData.body().getId());
                    Log.d("retData: ", retData.body().toString());
                }
            }
        });
    }

    public void addFotoCategoria(String id, File foto){
        categoriaModel.addFotoCategoria(id, foto, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    creaCategoriaActivity.continuaInserimento();
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
                    esploraCategorieMenuActivity.notifyAdapter(posizione);
                }
            }
        });
    }
    @Override
    public void getNomiCategorie(){
        categoriaModel.getNomiCategorie(new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<String>> retData) {
                if(retData.isSuccessful()){
                    categoriaView.setNomiCategorie(retData.body());
                }
            }
        });

    }
    public void getNomiCategoriaDisponibili(String id){
        categoriaModel.getNomiCategoriaDisponibili(id, new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<String>> retData) {
                if(retData.isSuccessful()){
                    categoriaView.setNomiCategorie(retData.body());
                }
            }
        });

    }
    public void aggiungiElemento(String nome, ElementoMenu elementoMenu){
        categoriaModel.aggiungiElemento(nome, elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    //elemento salvato correttamente
                }
            }
        });
    }





}
