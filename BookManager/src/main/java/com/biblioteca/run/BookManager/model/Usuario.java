package com.biblioteca.run.BookManager.model;

import javax.persistence.*;
import javax.validation.constraints.Email;

import org.openxava.annotations.*;
import lombok.*;

@Entity
@Getter @Setter
@View(name="Simple", members="identificacion, nombre") // Vista simple para referencias
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria autogenerada
    private int id;

    @Column(length = 20)
    @Required
    @Index() // Requerimiento de optimización: Crea un índice para búsquedas rápidas
    private String identificacion; // Cédula o ID del usuario

    @Column(length=100)
    @Required
    private String nombre;

    @Column(length=100)
    @Email // Validación de formato de correo electrónico
    private String email;

    @Column(length=20)
    private String telefono;
}
