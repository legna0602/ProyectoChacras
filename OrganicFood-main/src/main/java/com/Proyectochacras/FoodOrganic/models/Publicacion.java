package com.Proyectochacras.FoodOrganic.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreChacra;
    private String descripcion;
    private String ubicacionChacra;

    @Enumerated(EnumType.STRING) // Esto convierte el enum en un valor de texto (String)
    private EstadoChacra estado = EstadoChacra.DISPONIBLE; // El valor predeterminado es ACTIVO


    @ManyToOne
    @JoinColumn(name = "productor_id")

    @JsonBackReference //Que no sea un JSON LOOP .-----------
    private Productor productor; // Relación con Productor

    // Constructor vacío
    public Publicacion() {}

    // Constructor con parámetros
    public Publicacion(String nombreChacra, String descripcion, String ubicacionChacra, EstadoChacra estado, Productor productor) {
        this.nombreChacra = nombreChacra;
        this.descripcion = descripcion;
        this.ubicacionChacra = ubicacionChacra;
        this.estado = estado;
        this.productor = productor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreChacra() {
        return nombreChacra;
    }

    public void setNombreChacra(String nombreChacra) {
        this.nombreChacra = nombreChacra;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacionChacra() {
        return ubicacionChacra;
    }

    public void setUbicacionChacra(String ubicacionChacra) {
        this.ubicacionChacra = ubicacionChacra;
    }

    public EstadoChacra getEstado() {
        return estado;
    }

    public void setEstado(EstadoChacra estado) {
        this.estado = estado;
    }

    public Productor getProductor() {
        return productor;
    }

    public void setProductor(Productor productor) {
        this.productor = productor;
    }
}
