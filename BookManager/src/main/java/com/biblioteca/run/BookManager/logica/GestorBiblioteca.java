package com.biblioteca.run.BookManager.logica;

import com.biblioteca.run.BookManager.model.Libro;
import com.biblioteca.run.BookManager.model.Prestamo;
import org.openxava.jpa.XPersistence;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GestorBiblioteca {

    // Ruta del archivo de backup (se creará en la raíz del proyecto o donde se ejecute)
    private static final String BACKUP_PATH = "biblioteca_backup.txt";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Lógica ejecutada al registrar un nuevo préstamo.
     * Disminuye el stock del libro y realiza el backup.
     */
    public static void registrarPrestamo(Prestamo prestamo) throws Exception {
        Libro libro = prestamo.getLibro();

        // 1. Validación de Stock
        if (libro.getCantidadDisponible() <= 0) {
            throw new Exception("El libro '" + libro.getTitulo() + "' no tiene unidades disponibles para préstamo.");
        }

        // 2. Actualización de Stock
        libro.setCantidadDisponible(libro.getCantidadDisponible() - 1);
        XPersistence.getEntityManager().merge(libro); // Guarda el cambio en el libro

        // 3. Backup a TXT
        realizarBackup(prestamo, "PRESTAMO");
    }

    /**
     * Lógica ejecutada al registrar una devolución.
     * Aumenta el stock del libro y realiza el backup.
     */
    public static void registrarDevolucion(Prestamo prestamo) throws Exception {
        // Solo si se está registrando la devolución (fechaDevolucionReal no es null)
        if (prestamo.getFechaDevolucionReal() != null) {
            Libro libro = prestamo.getLibro();

            // 1. Actualización de Stock
            libro.setCantidadDisponible(libro.getCantidadDisponible() + 1);
            XPersistence.getEntityManager().merge(libro); // Guarda el cambio en el libro

            // 2. Backup a TXT
            realizarBackup(prestamo, "DEVOLUCION");
        }
    }

    /**
     * Requerimiento No Funcional: Backup mediante archivos .txt
     * Escribe un registro de la transacción en el archivo de texto.
     */
    private static void realizarBackup(Prestamo prestamo, String tipoEvento) {
        try (FileWriter fw = new FileWriter(BACKUP_PATH, true)) {
            String linea = String.format("[%s] %s | Prestamo ID: %d | Libro: %s (%s) | Usuario: %s (%s) | Fecha: %s\n",
                    tipoEvento,
                    LocalDateTime.now().format(FORMATTER),
                    prestamo.getId(),
                    prestamo.getLibro().getTitulo(),
                    prestamo.getLibro().getIsbn(),
                    prestamo.getUsuario().getNombre(),
                    prestamo.getUsuario().getIdentificacion(),
                    prestamo.getFechaPrestamo()
            );
            fw.write(linea);
        } catch (IOException e) {
            // En un entorno real, se debería loggear o notificar
            System.err.println("Error al escribir en el archivo de backup: " + e.getMessage());
        }
    }
}
