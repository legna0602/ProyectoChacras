package com.Proyectochacras.FoodOrganic.dto;

import com.Proyectochacras.FoodOrganic.models.Usuario;

public class UsuarioDTO {

    private Long id;
    private String username;
    private String role;

    // Constructor
    public UsuarioDTO(Usuario usuario) {

        this.username = usuario.getUsername();
        this.role = usuario.getRole();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
