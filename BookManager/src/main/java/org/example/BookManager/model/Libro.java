package org.example.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;

@Entity
@Views({
        @View(name = "Default", members = "titulo, autor, isbn"),
        @View(name = "Completo", members =
                "titulo, autor;" +
                        "isbn, añoPublicacion;" +
                        "descripcion;" +
                        "cantidadEjemplares"
        ),
        @View(name = "Dashboard", members =
                "informacion [" +
                        "  'SISTEMA BOOKMANAGER'," +
                        "  ''," +
                        "  'Bienvenido al sistema de gestión de biblioteca.'," +
                        "  ''," +
                        "  'Funcionalidades disponibles:'," +
                        "  ' Gestión de Libros'," +
                        "  ' Gestión de Usuarios'," +
                        "  ' Gestión de Préstamos'," +
                        "  ' Reportes y Estadísticas'," +
                        "  ''," +
                        "  'Acciones:'," +
                        "  '• Haz clic en VER ESTADÍSTICAS para ver datos'," +
                        "  '• Navega entre módulos usando el menú superior'" +
                        "]"
        )
})
public class Libro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    @Column(length = 100) @Required
    private String titulo;

    @Column(length = 100)
    private String autor;

    @Column(length = 50)
    private String isbn;

    @Column(length = 500) @TextArea
    private String descripcion;

    private Integer añoPublicacion;

    private Integer cantidadEjemplares;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getAñoPublicacion() { return añoPublicacion; }
    public void setAñoPublicacion(Integer añoPublicacion) { this.añoPublicacion = añoPublicacion; }
    public Integer getCantidadEjemplares() { return cantidadEjemplares; }
    public void setCantidadEjemplares(Integer cantidadEjemplares) { this.cantidadEjemplares = cantidadEjemplares; }
}