package com.example.springclient.authentication;

public class ApiToken {
    private String accessToken;
    private String refreshToken;

    public ApiToken(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public String getAccessToken(){
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
