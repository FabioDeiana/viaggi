package org.example.viaggi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.viaggi.entities.Utente;
import org.example.viaggi.services.JwtService;
import org.example.viaggi.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // leggo l'header Authorization dalla richiesta
        String authHeader = request.getHeader("Authorization");

        // se non c'è il token passo alla prossima richiesta
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // estraggo il token rimuovendo "Bearer "
        String token = authHeader.substring(7);

        // verifico il token e autentico l'utente
        if (jwtService.isTokenValid(token)) {
            String email = jwtService.extractEmail(token);
            Utente utente = utenteService.findByEmail(email);

            // dico a Spring Security che l'utente è autenticato
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            utente, null, utente.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}