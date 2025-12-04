package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@View(name="Simple", members="clave, valor")
public class Configuracion {

    @Id
    @Column(length = 50)
    @Required
    private String clave;

    @Column(length = 255)
    @Required
    private String valor;

    @TextArea
    private String descripcion;

    // Getters y Setters
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}