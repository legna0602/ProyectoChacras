package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para guardar un usuario
    public void saveUsuario(Usuario usuario) {
        // Aquí puedes agregar lógica adicional, como encriptar la contraseña si es necesario
        usuarioRepository.save(usuario); // Guardar el usuario en la base de datos
    }
    public Usuario getUsuarioByCorreo(String correo) {
        // Llamar al método findByCorreo desde una instancia del repositorio
        List<Usuario> usuarios = usuarioRepository.findByCorreo(correo);
        if (!usuarios.isEmpty()) {
            return usuarios.get(0); // Devolver el primer usuario (o manejar el caso de múltiples usuarios)
        }
        return null; // O manejar el caso de no encontrar usuarios
    }
}

