package pexper.projects.project_hub.dto;

import java.time.Instant;

public class AuthResponse {

    private String token;
    private String tokenType;
    private Instant expiresAt;
    private String username;

    public AuthResponse(String token, String tokenType, Instant expiresAt) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.username = null;
    }

    public AuthResponse(String token, String tokenType, Instant expiresAt, String username) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public String getUsername() {
        return username;
    }
}
