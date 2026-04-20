package com.felipesilva.event_ingest_service.auth;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.felipesilva.event_ingest_service.service.UserDetailServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;


    public JwtAuthFilter(UserDetailServiceImpl userDetailService, JwtService jwtService){
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
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
            // 6. Busca o usuário no banco
            UserDetails userDetails = userDetailService.loadUserByUsername(username);

            // 7. Valida o token
            if(jwtService.isTokenValid(token, userDetails)){

                // 8. Cria o objeto de autenticação e registra no contexto
                UsernamePasswordAuthenticationToken authToken = 
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            
        }

        // 9. Continua o fluxo para o próximo filtro/controller
        filterChain.doFilter(request, response);
    }
}
