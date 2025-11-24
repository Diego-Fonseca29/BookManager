package com.biblioteca.run.BookManager.model;

import javax.persistence.*;
import javax.validation.constraints.Min;

import org.openxava.annotations.*;
import lombok.*;

@Entity // Mapea la clase a una tabla de la base de datos
@Getter @Setter // Anotaciones de Lombok para generar getters y setters
@View(name="Simple", members="isbn, titulo") // Vista simple para referencias en otras entidades
public class Libro {

    @Id @Column(length=13) // Clave primaria (ISBN)
    @Required // Campo obligatorio en la interfaz de usuario
    private String isbn;

    @Column(length=100)
    @Required
    private String titulo;

    @Column(length=50)
    @Required
    private String autor;

    @Column(length=50)
    private String editorial;

    @Required
    private int anioPublicacion;

    @Required
    @Min(0) // Asegura que la cantidad no sea negativa
    private int cantidadDisponible; // Stock actual

    @Required
    private boolean disponible = true; // Estado general del libro
}
