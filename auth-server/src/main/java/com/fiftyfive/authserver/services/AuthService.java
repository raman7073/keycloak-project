package com.fiftyfive.authserver.services;

import com.fiftyfive.authserver.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface AuthService {
     LoginResponseDTO login(LoginDTO loginDTO);
     Response logout(String refreshToken);
}
