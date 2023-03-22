package com.example.springclient.entity;




import java.time.LocalDate;

public class Utente {


    private Long id;

    private String nome;

    private String cognome;

    private String email;

    private String password;


    public Utente() {
        super();
    }

    public Utente(
            Long id,
            String nome,
            String cognome,
            String email,
            LocalDate dob) {
        super();
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;

    }

    public Utente(
            String nome,
            String cognome,
            String email,
            LocalDate dob) {
        super();
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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
                "id= "+ id +
                ", firstName= " + nome +
                ", lastName= " + cognome +
                ", email= " + email +
                "}";
    }





}
