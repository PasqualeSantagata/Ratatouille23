package com.example.springclient.apiUtils;

public class ApiToken {
    private String token;

    public ApiToken(String token){
        this.token = token;
    }
    public String getToken(){
        return token;
    }
}
