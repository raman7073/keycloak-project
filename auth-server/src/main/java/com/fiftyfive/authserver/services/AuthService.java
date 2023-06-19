package com.fiftyfive.authserver.services;

import com.fiftyfive.authserver.dtos.*;
import org.springframework.http.ResponseEntity;

public interface AuthService {
     LoginResponseDTO login(LoginDTO loginDTO);
     Response logout(TokenDTO tokenDTO);
     IntrospectResponse introspect(TokenDTO tokenDTO);

}
