package com.example.springclient.entity;

import java.util.ArrayList;
import java.util.List;

public class Allergene {

    private String nomeAllergene;
    private List<ElementoMenu> listOfElementi;

    public Allergene(String nome){
        nomeAllergene = nome;
        listOfElementi = new ArrayList<>();
    }

    public String getNome() {
        return nomeAllergene;
    }

    public List<ElementoMenu> getListOfElementi() {
        return listOfElementi;
    }

    public void setNome(String nome) {
        this.nomeAllergene = nome;
    }

    public void setListOfElementi(List<ElementoMenu> listOfElementi) {
        this.listOfElementi = listOfElementi;
    }
}
