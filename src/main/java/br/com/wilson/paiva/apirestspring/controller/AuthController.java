package br.com.wilson.paiva.apirestspring.controller;

import br.com.wilson.paiva.apirestspring.data.vo.v1.security.AccountCredentialsVO;
import br.com.wilson.paiva.apirestspring.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Authenticates a user and returns a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if (checkIfParamIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = authService.signin(data);
        if (token == null)return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return  token;
    }

    @Operation(summary = "Refresh token for authenticates a user and returns a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String refreshToken){
        if (checkIsParamIsNotNull(username, refreshToken))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = authService.refleshToken(username,refreshToken);
        if (token == null)return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        return  token;
    }

    private static boolean checkIsParamIsNotNull(String username, String refreshToken) {
        return refreshToken == null || refreshToken.isBlank()
                || username == null || username.isBlank();
    }

    private static boolean checkIfParamIsNotNull(AccountCredentialsVO data) {
        return data == null
                || data.getUsername() == null
                || data.getUsername().isBlank()
                || data.getPassword() == null
                || data.getPassword().isBlank();
    }
}
