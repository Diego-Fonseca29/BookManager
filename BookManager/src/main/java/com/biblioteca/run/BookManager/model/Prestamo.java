package com.biblioteca.run.BookManager.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import java.time.LocalDate;
import lombok.*;

@Entity
@Getter @Setter
@Views({
        @View(name="Default", members="libro, usuario, fechaPrestamo, fechaDevolucionPrevista, fechaDevolucionReal, activo, notas"),
        @View(name="Historial", members="usuario; libro; fechaPrestamo, fechaDevolucionReal") // Vista específica para el historial
})
public class Prestamo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria autogenerada
    private int id;

    // Relación ManyToOne con Libro
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Un préstamo tiene un solo libro
    @JoinColumn(name = "libro_isbn") // Nombre de la columna de la clave foránea
    @ReferenceView("Simple") // Usar la vista 'Simple' de Libro
    @Required
    private Libro libro;

    // Relación ManyToOne con Usuario
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Un préstamo tiene un solo usuario
    @JoinColumn(name = "usuario_id")
    @ReferenceView("Simple") // Usar la vista 'Simple' de Usuario
    @Required
    private Usuario usuario;

    @Required
    private LocalDate fechaPrestamo = LocalDate.now(); // Fecha del préstamo

    @Required
    private LocalDate fechaDevolucionPrevista; // Fecha límite de devolución

    private LocalDate fechaDevolucionReal; // Fecha real de devolución (null si no se ha devuelto)

    @Stereotype("MEMO") // Para notas o comentarios sobre el préstamo
    private String notas;

    // Campo calculado para saber si el préstamo está activo
    @Transient // No se mapea a la base de datos
    @Depends("fechaDevolucionReal") // Se recalcula si cambia fechaDevolucionReal
    public boolean isActivo() {
        return fechaDevolucionReal == null;
    }
// ... dentro de la clase Prestamo ...

    // Callbacks de persistencia (Lógica de negocio)
    @PrePersist
    private void prePersist() {
        try {
            // Llama al gestor para disminuir el stock y hacer el backup
            com.biblioteca.run.BookManager.logica.GestorBiblioteca.registrarPrestamo(this);
        } catch (Exception e) {
            // Lanzar una RuntimeException para abortar la transacción JPA
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreUpdate
    private void preUpdate() {
        // Solo si se está marcando la devolución (fechaDevolucionReal cambia de null a valor)
        // La lógica de GestorBiblioteca se encarga de verificar si ya se devolvió.
        try {
            // Llama al gestor para aumentar el stock y hacer el backup
            com.biblioteca.run.BookManager.logica.GestorBiblioteca.registrarDevolucion(this);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
