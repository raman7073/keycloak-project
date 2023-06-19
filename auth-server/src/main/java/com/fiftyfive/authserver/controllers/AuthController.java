package com.fiftyfive.authserver.controllers;

import com.fiftyfive.authserver.dtos.LoginDTO;
import com.fiftyfive.authserver.dtos.LoginResponseDTO;
import com.fiftyfive.authserver.dtos.Response;
import com.fiftyfive.authserver.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private AuthService authService;
    private final String LOGIN = "/login";
    private final String LOGOUT = "/logout";
    private final String REFRESH_TOKEN = "refresh-token";

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginDTO loginDTO) {

        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping(LOGOUT)
    public ResponseEntity<Response> logout(@RequestHeader(REFRESH_TOKEN) String refreshToken) {

        Response response = authService.logout(refreshToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
