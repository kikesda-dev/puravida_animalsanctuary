package com.ironhack.sanctuary.security;

import com.ironhack.sanctuary.security.filters.CustomAuthenticationFilter;
import com.ironhack.sanctuary.security.filters.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManagerBuilder authManagerBuilder;

//1. Gestor principal de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//2. Cadena de filtros y reglas de seguridad
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //Se configura el filtro de Login (Fábrica de tokens)
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authManagerBuilder.getOrBuild());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        //Apagamos la seguridad clásica y activamos STATELESS (sin estado)
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        //Reglas de acceso a las rutas
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/login/**").permitAll() //Puerta pública

        //AQUÍ SE AÑADIRÁN LAS REGLAS DE ANIMALES Y DONACIONES

                .anyRequest().authenticated() //El resto bloqueado
        );

        //Se coloca a los dos guardias en la puerta
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}