package com.Proyectochacras.FoodOrganic.controllers;

import com.Proyectochacras.FoodOrganic.models.Publicacion;
import com.Proyectochacras.FoodOrganic.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;


    // Obtener todas las publicaciones
    @GetMapping
    public ResponseEntity<List<Publicacion>> listarPublicaciones() {
        List<Publicacion> publicaciones = publicacionService.obtenerTodasLasPublicaciones();
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);
    }

    // Crear una nueva publicación
    @PostMapping("/crear")
    public ResponseEntity<String> crearPublicacion(@RequestBody Publicacion publicacion) {
        try {
            publicacionService.crearPublicacion(
                    publicacion.getProductor().getId(),  // Usamos el productor que se pasa
                    publicacion.getNombreChacra(),
                    publicacion.getDescripcion(),
                    publicacion.getUbicacionChacra(),
                    publicacion.getEstado().toString()  // Asegúrate de pasar un valor de tipo String
            );
            return new ResponseEntity<>("Publicación creada correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la publicación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Modificar una publicación
    @PutMapping("/modificar/{id}")
    public ResponseEntity<String> modificarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacion) {
        Publicacion result = publicacionService.modificarPublicacion(id, publicacion);
        if (result != null) {
            return new ResponseEntity<>("Publicación modificada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Publicación no encontrada", HttpStatus.NOT_FOUND);
    }


    // Eliminar (marcar como eliminada) una publicación
    @PutMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable Long id) {
        boolean ELIMINADO = publicacionService.eliminarPublicacion(id);  // Llamada al servicio
        if (ELIMINADO) {
            return new ResponseEntity<>("Publicación eliminada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("Publicación no encontrada", HttpStatus.NOT_FOUND);
    }

}

