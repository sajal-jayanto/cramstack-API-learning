package com.cramstack.cramstackAPIlearning.Controllers;

import com.cramstack.cramstackAPIlearning.Models.Authentication.AuthenticationRequest;
import com.cramstack.cramstackAPIlearning.Models.Authentication.AuthenticationResponse;
import com.cramstack.cramstackAPIlearning.Services.LoginService;
import com.cramstack.cramstackAPIlearning.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private LoginService loginService;
    private JwtUtil jwtUtil;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, LoginService loginService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        UsernamePasswordAuthenticationToken userInfoWithToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword());

        try {
            authenticationManager.authenticate(userInfoWithToken);
        }catch (BadCredentialsException e){
            throw  new Exception("User name or password are not valid" , e);
        }

        final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
