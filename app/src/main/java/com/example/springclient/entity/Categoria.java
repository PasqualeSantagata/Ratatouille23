package com.example.springclient.entity;

import java.util.List;

public class Categoria {
    private Long id;
    private String nome;
    private List<ElementoMenu> elementi;

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
}
