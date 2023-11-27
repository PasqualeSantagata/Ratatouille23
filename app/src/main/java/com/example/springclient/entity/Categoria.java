package com.example.springclient.entity;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Categoria implements Serializable {
    private Long id;
    private String nome;
    private List<ElementoMenu> elementi;
    private Bitmap image;
    private Integer flagOrdinamento = -1;

    public Categoria(String nome) {
        this.nome = nome;
    }
    public Categoria(Long id, String nome){
        this.nome = nome;
        this.id = id;
    }

    public Categoria(String nome, Bitmap image) {
        this.nome = nome;
        this.image = image;
    }

    public Categoria(String nome, List<ElementoMenu> elementi) {
        this.nome = nome;
        this.elementi = elementi;
    }

    public Categoria(Long id, String nome, List<ElementoMenu> elementi, int image) {
        this.id = id;
        this.nome = nome;
        this.elementi = elementi;

    }

    public void ordinaCategoria(){
        switch (flagOrdinamento){
            case 1:
                elementi.sort(ElementoMenu.compareNomeCrescente);
                break;
            case 2:
                elementi.sort(ElementoMenu.compareNomeDecrescente);
                break;
            case 3:
                elementi.sort(ElementoMenu.comparePrezzoCrescente);
                break;
            case 4:
                elementi.sort(ElementoMenu.comparePrezzoDecrescente);
        }
    }


    public Categoria(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ElementoMenu> getElementi() {
        return elementi;
    }

    public void setElementi(List<ElementoMenu> elementi) {
        this.elementi = elementi;
    }

    public Long getId(){
        return id;
    }

    public Integer getFlagOrdinamento() {
        return flagOrdinamento;
    }

    public void setFlagOrdinamento(Integer flagOrdinamento) {
        this.flagOrdinamento = flagOrdinamento;
    }
}
