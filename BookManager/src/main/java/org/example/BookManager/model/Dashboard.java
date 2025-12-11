package org.example.BookManager.model;

import org.openxava.annotations.*;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@View(name = "Dashboard", members =
        "estadisticasGenerales [" +
                "  totalLibros, totalUsuarios, totalPrestamosActivos;" +
                "  prestamosVencidos, librosDisponibles, librosPrestados" +
                "];" +
                "ultimosPrestamos;" +
                "librosMasPopulares"
)
public class Dashboard {

    @Hidden
    private Long id = 1L;

    @Transient @ReadOnly
    private Date fechaActualizacion = new Date();

    // --- KPIs / Estadísticas ---
    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer totalLibros = 0;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer totalUsuarios = 0;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer totalPrestamosActivos = 0;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer prestamosVencidos = 0;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer librosDisponibles = 0;

    @Transient @ReadOnly @LabelFormat(LabelFormatType.SMALL)
    private Integer librosPrestados = 0;

    // --- Listas ---

    @Transient
    // CORREGIDO: Usamos usuario.nombre y usuario.apellido (que sí existen)
    @ListProperties("libro.titulo, usuario.nombre, usuario.apellido, fechaPrestamo")
    private List<Prestamo> ultimosPrestamos = new ArrayList<>();

    @Transient
    // CORREGIDO: Usamos 'autor' directo (porque en tu entidad Libro es un String, no un objeto)
    @ListProperties("titulo, autor, cantidadEjemplares")
    private List<Libro> librosMasPopulares = new ArrayList<>();

    // ======================
    //  GETTERS Y SETTERS
    // ======================
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

    // GETTERS DEFENSORES (Anti-Nulos)
    public List<Prestamo> getUltimosPrestamos() {
        if (ultimosPrestamos == null) return new ArrayList<>();
        return ultimosPrestamos;
    }
    public void setUltimosPrestamos(List<Prestamo> ultimosPrestamos) { this.ultimosPrestamos = ultimosPrestamos; }

    public List<Libro> getLibrosMasPopulares() {
        if (librosMasPopulares == null) return new ArrayList<>();
        return librosMasPopulares;
    }
    public void setLibrosMasPopulares(List<Libro> librosMasPopulares) { this.librosMasPopulares = librosMasPopulares; }
}