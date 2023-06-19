package com.fiftyfive.authserver.controllers;

import com.fiftyfive.authserver.dtos.*;
import com.fiftyfive.authserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {

        LoginResponseDTO loginResponseDTO = authService.login(loginDTO);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Response> logout(@RequestBody TokenDTO tokenDTO) {

        Response response = authService.logout(tokenDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/introspect")
    public ResponseEntity<IntrospectResponse> introspect(@RequestBody TokenDTO token) {

        IntrospectResponse introspectResponse = authService.introspect(token);
        return new ResponseEntity<>(introspectResponse, HttpStatus.OK);
    }
}
