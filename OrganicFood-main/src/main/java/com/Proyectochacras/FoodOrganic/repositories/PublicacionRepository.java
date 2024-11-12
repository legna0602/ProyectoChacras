package com.Proyectochacras.FoodOrganic.repositories;

import com.Proyectochacras.FoodOrganic.models.EstadoChacra;
import com.Proyectochacras.FoodOrganic.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {

    // Método para marcar una publicación como eliminada
    @Modifying
    @Query("UPDATE Publicacion p SET p.estado = :estado WHERE p.id = :id")
    void updateEstado(@Param("id") Long id, @Param("estado") EstadoChacra estado);

    // Método para obtener publicaciones no eliminadas, si es necesario
    List<Publicacion> findByEstadoNot(EstadoChacra estado);
}
