package org.maslov.bot.app.auth.controllers;


import org.maslov.bot.app.auth.JwtTokenProvider;
import org.maslov.bot.app.auth.component.AuthenticationService;
import org.maslov.bot.app.auth.model.LoginResponse;
import org.maslov.bot.app.auth.model.LoginTechnicalUserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {



    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthenticationService authenticationService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationService = authenticationService;
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginTechnicalUserDto loginUserDto) {
        UserDetails authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtTokenProvider.createToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtTokenProvider.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }




}
