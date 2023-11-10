package com.example.springclient.authentication;

public class AuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String ruolo;
    private String email;

    public AuthenticationResponse(String accessToken, String refreshToken, String ruolo, String email){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.ruolo = ruolo;
        this.email = email;
    }
    public String getAccessToken(){
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getRuolo(){return ruolo;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
