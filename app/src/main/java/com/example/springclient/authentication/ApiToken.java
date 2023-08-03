package com.example.springclient.authentication;

public class ApiToken {
    private String accessToken;
    private String refreshToken;
    private String ruolo;

    public ApiToken(String accessToken, String refreshToken, String ruolo){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.ruolo = ruolo;
    }
    public String getAccessToken(){
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getRuolo(){return ruolo;}
}
