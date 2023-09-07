package com.example.springclient.entity;

import java.io.Serializable;
import java.util.List;

public class Categoria implements Serializable {
    private Long id;
    private String nome;
    private List<ElementoMenu> elementi;
    private int image;

    public Categoria(String nome) {
        this.nome = nome;
    }
    public Categoria(Long id, String nome){
        this.nome = nome;
        this.id = id;
    }

    public Categoria(String nome, List<ElementoMenu> elementi) {
        this.nome = nome;
        this.elementi = elementi;
    }

    public Categoria(Long id, String nome, List<ElementoMenu> elementi, int image) {
        this.id = id;
        this.nome = nome;
        this.elementi = elementi;
        this.image = image;
    }

    public Categoria(String nome, int image) {
        this.nome = nome;
        this.image = image;
    }

    public Categoria(String nome, List<ElementoMenu> elementi, int image) {
        this.nome = nome;
        this.elementi = elementi;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
