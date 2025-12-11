package org.example.BookManager.controller;

import org.example.BookManager.model.Libro;
import org.example.BookManager.model.Prestamo;
import org.example.BookManager.model.Usuario;

import org.openxava.jpa.XPersistence;

import javax.persistence.EntityManager;
import java.util.List;

public class DashboardController {

    private EntityManager em;

    public DashboardController(EntityManager em) {
        this.em = em;
    }

    public DashboardController() {
        this.em = XPersistence.getManager();
    }

    public long totalLibros() {
        return (long) em.createQuery("select count(l) from Libro l").getSingleResult();
    }

    public long totalUsuarios() {
        return (long) em.createQuery("select count(u) from Usuario u").getSingleResult();
    }

    public long totalPrestamosActivos() {
        return (long) em.createQuery("select count(p) from Prestamo p where p.devuelto = false").getSingleResult();
    }

    public long prestamosVencidos() {
        return (long) em.createQuery(
                "select count(p) from Prestamo p where p.fechaDevolucion < current_date and p.devuelto = false"
        ).getSingleResult();
    }

    public long librosDisponibles() {
        return (long) em.createQuery("select sum(l.cantidadEjemplares) from Libro l").getSingleResult();
    }

    public long librosPrestados() {
        return (long) em.createQuery("select count(p) from Prestamo p where p.devuelto = false").getSingleResult();
    }

    public List<Prestamo> ultimosPrestamos() {
        return em.createQuery(
                "select p from Prestamo p order by p.fechaPrestamo desc",
                Prestamo.class
        ).setMaxResults(10).getResultList();
    }

    public List<Libro> librosMasPopulares() {
        return em.createQuery(
                "select l from Libro l order by l.cantidadEjemplares desc",
                Libro.class
        ).setMaxResults(10).getResultList();
    }
}
