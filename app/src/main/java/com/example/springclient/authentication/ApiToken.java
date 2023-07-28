package com.example.springclient.authentication;

import com.example.springclient.entity.Utente;

public class ApiToken {
    private String accessToken;
    private String refreshToken;
    private boolean passwordChanged;

    public ApiToken(String accessToken, String refreshToken, boolean passwordChanged){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.passwordChanged = passwordChanged;
    }
    public String getAccessToken(){
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public boolean isPasswordChanged() {
        return passwordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
}
