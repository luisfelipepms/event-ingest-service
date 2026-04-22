package com.felipesilva.event_ingest_service.auth;

public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;

    public AuthResponseDTO(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken(){
        return this.accessToken;
    }

    public String getRefreshToken(){
        return this.refreshToken;
    }
}
