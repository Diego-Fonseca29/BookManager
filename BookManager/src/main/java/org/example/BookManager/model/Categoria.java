package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
public class Categoria {

    @Id
    @Column(length = 5)
    @Required
    private String codigo;

    @Column(length = 50)
    @Required
    private String nombre;

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
}