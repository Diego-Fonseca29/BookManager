package org.example.BookManager.model;

import org.openxava.annotations.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "REPORTE")
@Views({
        @View(name = "Default", members = "nombreReporte, fechaGeneracion"),
        @View(name = "Estadisticas", members =
                "nombreReporte, fechaGeneracion;" +
                        "totalLibros, totalUsuarios, totalPrestamosActivos;" +
                        "prestamosVencidos, librosDisponibles, librosPrestados"
        )
})
@Tab(properties = "nombreReporte, fechaGeneracion, totalLibros, totalUsuarios, totalPrestamosActivos, prestamosVencidos, librosDisponibles, librosPrestados")
public class Reporte {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "NOMBRE_REPORTE")
    @Required
    private String nombreReporte;

    @Column(name = "FECHA_GENERACION")
    private Date fechaGeneracion;

    @Column(name = "TOTAL_LIBROS")
    private Integer totalLibros;

    @Column(name = "TOTAL_USUARIOS")
    private Integer totalUsuarios;

    @Column(name = "TOTAL_PRESTAMOS_ACTIVOS")
    private Integer totalPrestamosActivos;

    @Column(name = "PRESTAMOS_VENCIDOS")
    private Integer prestamosVencidos;

    @Column(name = "LIBROS_DISPONIBLES")
    private Integer librosDisponibles;

    @Column(name = "LIBROS_PRESTADOS")
    private Integer librosPrestados;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreReporte() { return nombreReporte; }
    public void setNombreReporte(String nombreReporte) { this.nombreReporte = nombreReporte; }

    public Date getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(Date fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public Integer getTotalLibros() { return totalLibros; }
    public void setTotalLibros(Integer totalLibros) { this.totalLibros = totalLibros; }

    public Integer getTotalUsuarios() { return totalUsuarios; }
    public void setTotalUsuarios(Integer totalUsuarios) { this.totalUsuarios = totalUsuarios; }

    public Integer getTotalPrestamosActivos() { return totalPrestamosActivos; }
    public void setTotalPrestamosActivos(Integer totalPrestamosActivos) { this.totalPrestamosActivos = totalPrestamosActivos; }

    public Integer getPrestamosVencidos() { return prestamosVencidos; }
    public void setPrestamosVencidos(Integer prestamosVencidos) { this.prestamosVencidos = prestamosVencidos; }

    public Integer getLibrosDisponibles() { return librosDisponibles; }
    public void setLibrosDisponibles(Integer librosDisponibles) { this.librosDisponibles = librosDisponibles; }

    public Integer getLibrosPrestados() { return librosPrestados; }
    public void setLibrosPrestados(Integer librosPrestados) { this.librosPrestados = librosPrestados; }
}