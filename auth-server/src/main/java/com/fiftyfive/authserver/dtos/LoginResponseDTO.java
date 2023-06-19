package com.fiftyfive.authserver.dtos;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String access_token;
    private String expires_in;
    private String refresh_expires_in;
    private String token_type;
    private String refresh_token;
}
