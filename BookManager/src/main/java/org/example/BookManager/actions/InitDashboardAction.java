package org.example.BookManager.actions;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import javax.persistence.*;
import java.util.*;
import java.time.LocalDate; // Importante para manejar tus fechas
import org.example.BookManager.model.*;

public class InitDashboardAction extends ViewBaseAction {

    @Override
    public void execute() throws Exception {
        EntityManager em = XPersistence.getManager();

        // 1. Total Libros
        Number totalLibros = (Number) em.createQuery("select count(l) from Libro l").getSingleResult();

        // 2. Total Usuarios
        Number totalUsuarios = (Number) em.createQuery("select count(u) from Usuario u").getSingleResult();

        // 3. Préstamos Activos (Donde fechaDevolucion es null)
        Number prestamosActivos = (Number) em.createQuery(
                "select count(p) from Prestamo p where p.fechaDevolucion is null"
        ).getSingleResult();

        // 4. Préstamos Vencidos (LÓGICA CALCULADA)
        // Regla: Se considera vencido si lleva más de 15 días prestado y no se ha devuelto.
        LocalDate fechaLimite = LocalDate.now().minusDays(15);

        Number vencidos = (Number) em.createQuery(
                        "select count(p) from Prestamo p where p.fechaDevolucion is null and p.fechaPrestamo < :fechaLimite"
                ).setParameter("fechaLimite", fechaLimite)
                .getSingleResult();

        // 5. Listas (Tablas)
        // Últimos 5 préstamos
        List<Prestamo> ultimos = em.createQuery("select p from Prestamo p order by p.fechaPrestamo desc")
                .setMaxResults(5)
                .getResultList();

        // Libros populares (Top 5 con más ejemplares)
        List<Libro> populares = em.createQuery("select l from Libro l order by l.cantidadEjemplares desc")
                .setMaxResults(5)
                .getResultList();

        // 6. Pasar datos a la Vista
        getView().setValue("totalLibros", totalLibros.intValue());
        getView().setValue("totalUsuarios", totalUsuarios.intValue());
        getView().setValue("totalPrestamosActivos", prestamosActivos.intValue());
        getView().setValue("prestamosVencidos", vencidos.intValue());

        // Cálculo simple de Disponibilidad
        int disponibles = totalLibros.intValue() - prestamosActivos.intValue();
        getView().setValue("librosDisponibles", disponibles);
        getView().setValue("librosPrestados", prestamosActivos.intValue());

        // Asignar listas
        getView().setValue("ultimosPrestamos", ultimos);
        getView().setValue("librosMasPopulares", populares);
    }
}