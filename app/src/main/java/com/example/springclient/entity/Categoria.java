package com.example.springclient.entity;

import java.util.List;

public class Categoria {
    private String nome;
    private List<ElementoMenu> elementoMenuList;

    public Categoria(String nome) {
        this.nome = nome;
    }

    public Categoria(String nome, List<ElementoMenu> elementoMenuList) {
        this.nome = nome;
        this.elementoMenuList = elementoMenuList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ElementoMenu> getElementoMenuList() {
        return elementoMenuList;
    }

    public void setElementoMenuList(List<ElementoMenu> elementoMenuList) {
        this.elementoMenuList = elementoMenuList;
    }
}
