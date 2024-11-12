package com.Proyectochacras.FoodOrganic.repositories;

import com.Proyectochacras.FoodOrganic.models.Productor;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByCorreo(String correo);  // Cambié 'username' por 'correo'
}
