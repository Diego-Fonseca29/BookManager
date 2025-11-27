package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import java.time.LocalDate;

@Entity
@View(name = "Simple", members = "libro, usuario, fechaPrestamo, fechaDevolucion")
@Tab(properties = "libro.titulo, usuario.nombre, fechaPrestamo, fechaDevolucion")
public class Prestamo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "titulo, autor") // CORREGIDO
    private Libro libro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre, apellido") // CORREGIDO
    private Usuario usuario;

    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}