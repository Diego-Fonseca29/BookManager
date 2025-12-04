package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import java.time.LocalDate;

@Entity
public class Empleado {

    @Id
    @Column(length = 10)
    @Required
    private String codigo;

    @Column(length = 50)
    @Required
    private String nombre;

    @Column(length = 50)
    @Required
    private String apellido;

    @Stereotype("EMAIL")
    private String email;

    @Stereotype("TELEPHONE")
    private String telefono;

    @Stereotype("DATE")
    private LocalDate fechaContratacion;

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}