package org.example.BookManager.model;

import org.openxava.annotations.*;

@View(members = "nombreCompleto, email, totalPrestamos")
public class UsuarioActivo {

    private String nombreCompleto;
    private String email;
    private Integer totalPrestamos;

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getTotalPrestamos() { return totalPrestamos; }
    public void setTotalPrestamos(Integer totalPrestamos) { this.totalPrestamos = totalPrestamos; }
}