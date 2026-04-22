package com.felipesilva.event_ingest_service.auth;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        // 1. Pega o header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Se não tem token, deixa passar (o Security vai barrar se a rota for protegida)
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token (remove o "Bearer " do início)
        String token = authHeader.substring(7);

        // 4. Extrai o username do token
        String username = jwtService.extractUsername(token);

        // 5. Se tem username e ainda não está autenticado nessa requisição
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            // 7. Valida o token
            if(jwtService.isTokenValid(token, username)){

                List<GrantedAuthority> authorities = jwtService.extractRoles(token);

                // 8. Cria o objeto de autenticação e registra no contexto
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
        }

        // 9. Continua o fluxo para o próximo filtro/controller
        filterChain.doFilter(request, response);
    }
}
