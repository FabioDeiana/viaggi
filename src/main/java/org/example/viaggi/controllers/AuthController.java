package org.example.viaggi.controllers;

import org.example.viaggi.entities.Utente;
import org.example.viaggi.payload.LoginPayload;
import org.example.viaggi.services.JwtService;
import org.example.viaggi.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // POST /auth/register - registra nuovo utente
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Utente utente) {
        try {
            Utente saved = utenteService.save(utente);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // POST /auth/login - login e generazione token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginPayload payload) {
        try {
            // verifico email e password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            payload.getEmail(), payload.getPassword()));

            // se ok cerco l'utente e genero il token
            Utente utente = utenteService.findByEmail(payload.getEmail());
            String token = jwtService.generateToken(utente);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o password errati");
        }
    }
}