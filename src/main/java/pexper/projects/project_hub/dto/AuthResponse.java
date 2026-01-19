package pexper.projects.project_hub.dto;

import java.time.Instant;

public class AuthResponse {

    private String token;
    private String tokenType;
    private Instant expiresAt;

    public AuthResponse(String token, String tokenType, Instant expiresAt) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresAt = expiresAt;
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
}
