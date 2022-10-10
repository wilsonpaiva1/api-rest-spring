package br.com.wilson.paiva.apirestspring.service;

import br.com.wilson.paiva.apirestspring.data.vo.v1.security.AccountCredentialsVO;
import br.com.wilson.paiva.apirestspring.data.vo.v1.security.TokenVO;
import br.com.wilson.paiva.apirestspring.repositories.UserRepository;
import br.com.wilson.paiva.apirestspring.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository repository;

    public ResponseEntity signin(AccountCredentialsVO data){
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            var user = repository.findByUserName(username);
            var tokenResponse = new TokenVO();
            if (user!= null){
                tokenResponse = tokenProvider.createAccessToken(username,user.getRoles());
            }else {
                throw  new UsernameNotFoundException("Username "+username+" not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        }catch (Exception e){
            throw  new BadCredentialsException("Invalid username/password supplied!");
        }
    }


    public ResponseEntity refleshToken(String username,String refleshToken){
        var user = repository.findByUserName(username);
        var tokenResponse = new TokenVO();
        if (user!= null){
            tokenResponse = tokenProvider.refreshToken(refleshToken);
        }else {
            throw  new UsernameNotFoundException("Username "+username+" not found!");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
