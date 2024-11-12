package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.configurations.Authentication;
import com.Proyectochacras.FoodOrganic.dto.CrearClienteDTO;
import com.Proyectochacras.FoodOrganic.dto.UsuarioDTO;
import com.Proyectochacras.FoodOrganic.models.Rol;
import com.Proyectochacras.FoodOrganic.models.Usuario;
import com.Proyectochacras.FoodOrganic.repositories.UsuarioRepository;
import com.Proyectochacras.FoodOrganic.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public List<UsuarioDTO> getUsuarios(){
        return usuarioRepository.findAll().stream()
                .map(usuario -> new UsuarioDTO(usuario))
                .collect(Collectors.toList());
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Object> getUsuario(@PathVariable long id){
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if(usuario == null){
            return new ResponseEntity<>("El usuario buscado no existe.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new UsuarioDTO(usuario), HttpStatus.OK);
    }

    @GetMapping("/usuarios/current")
    public ResponseEntity<Object> getUsuarioActual(Authentication authentication){
        Usuario usuario = usuarioRepository.findByCorreo(authentication.getCorreo());
        if (usuario == null) {
            return new ResponseEntity<>("El usuario no está registrado.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new UsuarioDTO(usuario), HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Object> createUsuario(@RequestBody CrearClienteDTO CrearClienteDTO){
        if(CrearClienteDTO.getNombre().isBlank()){
            return new ResponseEntity<>("El campo nombre no puede estar vacío.", HttpStatus.FORBIDDEN);
        }
        // Suponiendo que la contraseña es encriptada
        Usuario usuario = new Usuario(
                CrearClienteDTO.getNombre(),
                CrearClienteDTO.getApellido(),
                CrearClienteDTO.getCorreo(),
                passwordEncoder.encode(CrearClienteDTO.getPassword())
        );
        usuario.setRole(Rol.USUARIO); // Establecer el rol, si es necesario
        usuarioRepository.save(usuario);


        return new ResponseEntity<>("Usuario creado correctamente.", HttpStatus.CREATED);
    }

}
