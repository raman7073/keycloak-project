package com.fiftyfive.authserver.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenDTO {
    @NotBlank
    private String token;
}
