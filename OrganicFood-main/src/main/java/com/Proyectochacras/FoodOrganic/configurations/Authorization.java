package com.Proyectochacras.FoodOrganic.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
@EnableWebSecurity
@Configuration
public class Authorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Configuración de autorizaciones
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/web/index.html").permitAll()  // Permite acceso sin autenticación
                        .requestMatchers("/web/js/index.js").permitAll() // Descomentar si es necesario
                        .requestMatchers("/web/css/style.css").permitAll() // Descomentar si es necesario
                        .requestMatchers("/web/img/**").permitAll() // Descomentar si es necesario
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll() // Permite acceso a la página de login
                        .requestMatchers(HttpMethod.POST, "/api/logout").permitAll() // Permite acceso a la URL de logout
                        .requestMatchers(HttpMethod.POST, "/api/clients").permitAll() // Permite acceso a este endpoint sin autenticación
                        .requestMatchers(HttpMethod.POST, "/api/payments").permitAll() // Lo mismo con payments
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Solo ADMIN puede acceder a /admin/**
                        .requestMatchers("/web/**").hasAuthority("CLIENT") // Solo CLIENT puede acceder a /web/**
                        .requestMatchers(HttpMethod.POST, "/api/loans/create").hasAuthority("ADMIN") // Requiere autoridad de ADMIN para crear préstamos
                        .requestMatchers(HttpMethod.GET, "/api/clients/current").hasAnyAuthority("ADMIN", "CLIENT") // Puede acceder ADMIN o CLIENT
                        .requestMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN") // Solo ADMIN puede ver clientes
                        .requestMatchers(HttpMethod.GET, "/api/accounts").hasAuthority("ADMIN") // Solo ADMIN puede ver cuentas
                        .requestMatchers("/api/**").hasAuthority("CLIENT") // Solo CLIENT puede acceder a los endpoints de /api/**
                        .anyRequest().denyAll()  // Niega acceso a cualquier otra solicitud
                )
                .formLogin(form -> form
                        .loginPage("/api/login")               // Página de login personalizada
                        .usernameParameter("correo")           // Nombre del parámetro de usuario
                        .passwordParameter("password")         // Nombre del parámetro de contraseña
                        .permitAll()                           // Permite acceso a la página de login sin autenticación
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // URL de logout
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK)) // Respuesta exitosa al hacer logout
                )
                .csrf(csrf -> csrf.disable() // Deshabilita la protección CSRF (si no se necesita)
                )
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self';") // Permite marcos solo del mismo origen
                        )
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")) // Error de autenticación
                );

        return http.build();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION); // Limpia atributos de autenticación en sesión
        }
    }
}
