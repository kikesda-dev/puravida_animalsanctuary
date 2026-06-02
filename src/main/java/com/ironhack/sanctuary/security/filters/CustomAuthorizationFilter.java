package com.ironhack.sanctuary.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import tools.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//1. Si intentan hacer login, deja pasar la petición sin pedir token.
       if (request.getServletPath().equals("/api/login")){
            filterChain.doFilter(request,response);
        } else {

//2. Si van a cualquier otra ruta, busca la cabecera "Authorization".
            String authorizationHeader = request.getHeader(AUTHORIZATION);

//3. Comprueba si ha enviado el token (debe empezar por "Bearer ")
          if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
               try {

                   //Se quita la palabra "Bearer " para que se quede solo con el codigo del token.
                   String token = authorizationHeader.substring("Bearer ".length());

                   //Se usa la misma clave secreta que he usado para crearlo.
                   Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                   JWTVerifier verifier = JWT.require(algorithm).build();

                   //Se verifica y decodifica el token
                   DecodedJWT decodedJWT = verifier.verify(token);

                   //Se extrae el email (subject) y los roles
                   String email = decodedJWT.getSubject();
                   String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

                   Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                   stream(roles).forEach(role -> {
                       authorities.add(new SimpleGrantedAuthority(role));
                   });

                   //Se dice a Spring Security: "el usuario es válido y tiene estos permisos"
                   UsernamePasswordAuthenticationToken authenticationToken =
                           new UsernamePasswordAuthenticationToken(email, null, authorities);
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                   //Deja que la petición continúe hacia el controlador.
                   filterChain.doFilter(request, response);

                   } catch (Exception exception) {
                       log.error("Error validando el token: {}", exception.getMessage());

       //Si el token es falso o ha caducado, devuelve un error 403 (prohibido)
        response.setHeader("error", exception.getMessage());
        response.setStatus(FORBIDDEN.value());

                   Map<String, String> error = new HashMap<>();
                   error.put("error_message", exception.getMessage());
                   response.setContentType(APPLICATION_JSON_VALUE);
                   new ObjectMapper().writeValue(response.getOutputStream(), error);
                   }
               } else {
              // Si no trae token, simplemente se deja que siga (SecurityConfig decidirá si la bloquea o no).
              filterChain.doFilter(request,response);
           }
        }
    }
}
