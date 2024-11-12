package com.Proyectochacras.FoodOrganic.configurations;

import com.Proyectochacras.FoodOrganic.models.Rol;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import practicas2.homebanking.models.Cliente;
import practicas2.homebanking.models.Rol;
import practicas2.homebanking.repositories.ClienteRepository;

@Configuration
public class Authentication extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inputName-> {
            Usuario usuario = UsuarioRepository.findByCorreo(inputName);
            if (usuario != null) {
                if(usuario.getRole().equals(Rol.USUARIO)){
                    return new User(usuario.getUsername(), usuario.getPassword(),
                            AuthorityUtils.createAuthorityList("USUARIO"));
                }
                if(usuario.getRole().equals(Rol.ADMINISTRADOR)){
                    return new User(usuario.getUsername(), usuario.getPassword(),
                            AuthorityUtils.createAuthorityList("ADMINISTRADOR"));
                }
                throw new UsernameNotFoundException("Unknown user: " + inputName);

            } else {
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }

}
