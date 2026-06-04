package com.ironhack.sanctuary.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ironhack.sanctuary.dto.LoginRequest;
import org.springframework.security.core.userdetails.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

//1. Se lee el texto JSON de POSTMAN y lo metemos en el LoginRequest
            ObjectMapper mapper = new ObjectMapper();
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);

//2. Se extrae el EMAIL y el PASSWORD (¿?SALVATORE¿?)
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            log.info("Probando inicio de sesión con email: {}", loginRequest.getEmail());

//3. Se lanzan los datos a Spring Security para comprobar en MySQL si son correctos.
            return authenticationManager.authenticate(authenticationToken);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

//1. Si la contraseña es correcta, se rescatan los datos del usuario.
        User user = (User) authentication.getPrincipal();

//2. Se elige la contraseña para firmar tokens (password: "secret").
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

//3. Fabricar el token JWT.
        String access_token = JWT.create()
                .withSubject(user.getUsername())//Username = email
                .withExpiresAt(new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000)) //Caducidad 18Junio'26.
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

//4. Se envia de vuelta al usuario para que lo guarde.
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
