package org.example.BookManager.services;

import org.example.BookManager.model.Libro;
import org.example.BookManager.model.Prestamo;
import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import java.util.List;

public class DashboardService {

    private EntityManager em() {
        return XPersistence.getManager();
    }

    public long totalLibros() {
        return (long) em().createQuery("SELECT COUNT(l) FROM Libro l").getSingleResult();
    }

    public long totalUsuarios() {
        return (long) em().createQuery("SELECT COUNT(u) FROM Usuario u").getSingleResult();
    }

    public long totalPrestamosActivos() {
        return (long) em().createQuery(
                "SELECT COUNT(p) FROM Prestamo p WHERE p.fechaDevolucion IS NULL"
        ).getSingleResult();
    }

    public long prestamosVencidos() {
        return (long) em().createQuery(
                "SELECT COUNT(p) FROM Prestamo p WHERE p.fechaDevolucion IS NULL AND p.fechaPrestamo < CURRENT_DATE"
        ).getSingleResult();
    }

    public long librosDisponibles() {
        return (long) em().createQuery(
                "SELECT COUNT(l) FROM Libro l WHERE l.cantidadEjemplares > 0"
        ).getSingleResult();
    }

    public long librosPrestados() {
        return (long) em().createQuery(
                "SELECT COUNT(p) FROM Prestamo p WHERE p.fechaDevolucion IS NULL"
        ).getSingleResult();
    }

    public List<Prestamo> ultimosPrestamos() {
        return em().createQuery(
                        "SELECT p FROM Prestamo p ORDER BY p.fechaPrestamo DESC",
                        Prestamo.class
                )
                .setMaxResults(10)
                .getResultList();
    }

    public List<Libro> librosMasPopulares() {
        return em().createQuery(
                        "SELECT l FROM Libro l ORDER BY l.cantidadEjemplares DESC",
                        Libro.class
                )
                .setMaxResults(10)
                .getResultList();
    }
}
