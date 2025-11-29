package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import javax.validation.constraints.Email;

@Entity
@Views({
        @View(name = "Simple", members = "nombre, apellido, correo"),
        @View(name = "Completo", members =
                "nombre, apellido;" +
                        "correo, telefono;" +
                        "direccion"
        )
})
@Tab(properties = "nombre, apellido, correo, telefono")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @Column(length = 50) @Required
    private String nombre;

    @Column(length = 50) @Required
    private String apellido;

    @Column(length = 100) @Email @Required
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(length = 200) @TextArea
    private String direccion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}