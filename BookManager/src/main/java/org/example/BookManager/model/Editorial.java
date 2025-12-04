package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class Editorial {

    @Id
    @Column(length = 5)
    @Required
    private String codigo;

    @Column(length = 50)
    @Required
    private String nombre;

    @Column(length = 100)
    private String direccion;

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}