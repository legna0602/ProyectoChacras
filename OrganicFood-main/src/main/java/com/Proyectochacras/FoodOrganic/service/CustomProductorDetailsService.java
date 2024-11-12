package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.Productor;
import com.Proyectochacras.FoodOrganic.repositories.ProductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomProductorDetailsService implements UserDetailsService {

    @Autowired
    private ProductorRepository productorRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Usar orElseThrow para manejar el Optional correctamente
        Productor productor = productorRepository.findByUsuario(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Productor no encontrado con el correo: " + correo));

        // Crear el UserDetails a partir del productor encontrado
        return new User(
                productor.getCorreo(),
                productor.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority(productor.getRol().name()))
        );
    }
}
