package com.Proyectochacras.FoodOrganic.service;

import com.Proyectochacras.FoodOrganic.models.EstadoChacra;
import com.Proyectochacras.FoodOrganic.models.Publicacion;
import com.Proyectochacras.FoodOrganic.models.Productor;
import com.Proyectochacras.FoodOrganic.repositories.ProductorRepository;
import com.Proyectochacras.FoodOrganic.repositories.PublicacionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ProductorRepository productorRepository;  // Inyectamos el repositorio de Productor

    // Crear una nueva publicación
    public void crearPublicacion(Long productorId, String nombreChacra, String descripcion, String ubicacionChacra, String estado) throws Exception {
        Productor productor = productorRepository.findById(productorId).orElseThrow(() -> new Exception("Productor no encontrado"));
        Publicacion publicacion = new Publicacion();
        publicacion.setNombreChacra(nombreChacra);
        publicacion.setDescripcion(descripcion);
        publicacion.setUbicacionChacra(ubicacionChacra);
        publicacion.setEstado(EstadoChacra.valueOf(estado));
        publicacion.setProductor(productor);

        publicacionRepository.save(publicacion);
    }


    // Obtener todas las publicaciones
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        return publicacionRepository.findAll();
    }

    // Obtener una publicación por ID
    public Optional<Publicacion> obtenerPublicacionPorId(Long id) {
        return publicacionRepository.findById(id);
    }


    // Modificar una publicación existente
    public Publicacion modificarPublicacion(Long id, Publicacion publicacion) {
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(id);
        if (publicacionOpt.isPresent()) {
            Publicacion publicacionExistente = publicacionOpt.get();
            publicacionExistente.setNombreChacra(publicacion.getNombreChacra());
            publicacionExistente.setDescripcion(publicacion.getDescripcion());
            publicacionExistente.setUbicacionChacra(publicacion.getUbicacionChacra());
            publicacionExistente.setEstado(publicacion.getEstado());
            return publicacionRepository.save(publicacionExistente);
        }
        return null; // Devuelve null si la publicación no existe
    }



    // Método para "eliminar" (marcar como eliminada) una publicación
    public boolean eliminarPublicacion(Long id) {
        // Buscar la publicación
        Publicacion publicacion = publicacionRepository.findById(id).orElse(null);
        if (publicacion != null) {
            // Marcar la publicación como eliminada
            publicacionRepository.updateEstado(id, EstadoChacra.ELIMINADO);
            return true;  // Si la publicación existe y se actualiza
        }
        return false;  // Si no se encontró la publicación
    }



}
