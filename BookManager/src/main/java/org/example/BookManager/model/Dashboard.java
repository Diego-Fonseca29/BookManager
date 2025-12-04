package org.example.BookManager.model;

import org.openxava.annotations.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@View(name = "Dashboard", members =
        "estadisticasGenerales [" +
                "  totalLibros, totalUsuarios, totalPrestamosActivos;" +
                "  prestamosVencidos, librosDisponibles, librosPrestados" +
                "];" +
                "ultimosPrestamos;" +
                "librosMasPopulares"
)
@Tab(properties = "fechaActualizacion")
public class Dashboard {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FECHA_ACTUALIZACION")
    @ReadOnly
    private Date fechaActualizacion;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer totalLibros;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer totalUsuarios;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer totalPrestamosActivos;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer prestamosVencidos;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer librosDisponibles;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer librosPrestados;

    @Transient
    @ListProperties("libro.titulo, usuario.nombre, fechaPrestamo")
    @XOrderBy("fechaPrestamo desc")
    @RowStyle(style="font-weight: bold", property="fechaDevolucion", value="null")
    private java.util.Collection<Prestamo> ultimosPrestamos;

    @Transient
    @ListProperties("titulo, autor, cantidadEjemplares")
    @XOrderBy("cantidadEjemplares desc")
    private java.util.Collection<Libro> librosMasPopulares;

    @PrePersist
    @PreUpdate
    public void actualizarFecha() {
        this.fechaActualizacion = new Date();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

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

    public java.util.Collection<Prestamo> getUltimosPrestamos() { return ultimosPrestamos; }
    public void setUltimosPrestamos(java.util.Collection<Prestamo> ultimosPrestamos) { this.ultimosPrestamos = ultimosPrestamos; }

    public java.util.Collection<Libro> getLibrosMasPopulares() { return librosMasPopulares; }
    public void setLibrosMasPopulares(java.util.Collection<Libro> librosMasPopulares) { this.librosMasPopulares = librosMasPopulares; }
}