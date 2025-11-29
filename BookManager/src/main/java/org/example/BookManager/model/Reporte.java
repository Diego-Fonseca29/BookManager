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
public class Reporte {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, name = "NOMBRE_REPORTE")
    @Required
    private String nombreReporte;

    @Column(name = "FECHA_GENERACION")
    @ReadOnly
    private Date fechaGeneracion;

    @Column(name = "TOTAL_LIBROS")
    @ReadOnly
    private Integer totalLibros = 50;

    @Column(name = "TOTAL_USUARIOS")
    @ReadOnly
    private Integer totalUsuarios = 25;

    @Column(name = "TOTAL_PRESTAMOS_ACTIVOS")
    @ReadOnly
    private Integer totalPrestamosActivos = 15;

    @Column(name = "PRESTAMOS_VENCIDOS")
    @ReadOnly
    private Integer prestamosVencidos = 3;

    @Column(name = "LIBROS_DISPONIBLES")
    @ReadOnly
    private Integer librosDisponibles = 35;

    @Column(name = "LIBROS_PRESTADOS")
    @ReadOnly
    private Integer librosPrestados = 15;

    @PrePersist
    public void prePersist() {
        if (fechaGeneracion == null) {
            fechaGeneracion = new Date();
        }
        if (nombreReporte == null || nombreReporte.trim().isEmpty()) {
            nombreReporte = "Reporte - " + fechaGeneracion;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombreReporte() { return nombreReporte; }
    public void setNombreReporte(String nombreReporte) { this.nombreReporte = nombreReporte; }
    public Date getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(Date fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    public Integer getTotalLibros() { return totalLibros != null ? totalLibros : 50; }
    public void setTotalLibros(Integer totalLibros) { this.totalLibros = totalLibros != null ? totalLibros : 50; }
    public Integer getTotalUsuarios() { return totalUsuarios != null ? totalUsuarios : 25; }
    public void setTotalUsuarios(Integer totalUsuarios) { this.totalUsuarios = totalUsuarios != null ? totalUsuarios : 25; }
    public Integer getTotalPrestamosActivos() { return totalPrestamosActivos != null ? totalPrestamosActivos : 15; }
    public void setTotalPrestamosActivos(Integer totalPrestamosActivos) { this.totalPrestamosActivos = totalPrestamosActivos != null ? totalPrestamosActivos : 15; }
    public Integer getPrestamosVencidos() { return prestamosVencidos != null ? prestamosVencidos : 3; }
    public void setPrestamosVencidos(Integer prestamosVencidos) { this.prestamosVencidos = prestamosVencidos != null ? prestamosVencidos : 3; }
    public Integer getLibrosDisponibles() { return librosDisponibles != null ? librosDisponibles : 35; }
    public void setLibrosDisponibles(Integer librosDisponibles) { this.librosDisponibles = librosDisponibles != null ? librosDisponibles : 35; }
    public Integer getLibrosPrestados() { return librosPrestados != null ? librosPrestados : 15; }
    public void setLibrosPrestados(Integer librosPrestados) { this.librosPrestados = librosPrestados != null ? librosPrestados : 15; }
}