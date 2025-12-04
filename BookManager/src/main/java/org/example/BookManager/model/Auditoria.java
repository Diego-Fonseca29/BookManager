package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import java.time.LocalDateTime;

@Entity
@View(name="ReadOnly", members="fechaHora, usuario, entidad, accion, detalles")
@Tab(properties="fechaHora, usuario, entidad, accion")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private int id;

    @Stereotype("DATETIME")
    @Column(name = "FECHA_HORA")
    private LocalDateTime fechaHora;

    @Column(length = 50)
    private String usuario;

    @Column(length = 50)
    private String entidad;

    @Column(length = 20)
    private String accion; // CREATE, UPDATE, DELETE

    @TextArea
    private String detalles;

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
}