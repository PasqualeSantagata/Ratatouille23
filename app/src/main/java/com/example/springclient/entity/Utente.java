package com.example.springclient.entity;


public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private boolean firstAccess;
    public Utente() {super();}

    public Utente(
            String nome,
            String cognome,
            String email,
            boolean firstAccess
            ) {
        super();
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.firstAccess = firstAccess;
    }

    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {

        // TODO Auto-generated method stub
        return "Student{" +
                ", firstName= " + nome +
                ", lastName= " + cognome +
                ", email= " + email +
                "}";
    }





}
