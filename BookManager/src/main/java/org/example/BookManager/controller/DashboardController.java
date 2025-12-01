package org.example.BookManager.controller;

import org.openxava.actions.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DashboardController extends BaseAction {

    public void execute() throws Exception {
        Connection conn = null;
        try {
            conn = getConnectionWithTimeout();
            System.out.println("✅ Conexión a BD establecida");

            String mensaje = calcularEstadisticasRapidas(conn);
            addMessage(mensaje);

            conn.close();

        } catch (SQLException e) {
            manejarErrorConexion(e);
        } finally {
            cerrarConexionSegura(conn);
        }
    }

    private Connection getConnectionWithTimeout() throws SQLException {
        String url = "jdbc:hsqldb:file:data/BookManager-db;hsqldb.lock_file=false;shutdown=true";
        String user = "SA";
        String password = "";

        int intentos = 3;
        for (int i = 1; i <= intentos; i++) {
            try {
                System.out.println(" Intento de conexión #" + i);
                return DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                if (i == intentos) throw e;
                esperar(1000); // Esperar 1 segundo entre intentos
            }
        }
        throw new SQLException("No se pudo conectar después de " + intentos + " intentos");
    }

    private String calcularEstadisticasRapidas(Connection conn) throws SQLException {

        int libros = contarConMultiplesNombres(conn, new String[]{"LIBRO", "Libro", "libro"});
        int usuarios = contarConMultiplesNombres(conn, new String[]{"USUARIO", "Usuario", "usuario"});
        int prestamos = contarPrestamosActivosSimple(conn);
        int disponibles = Math.max(0, libros - prestamos);

        String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        return crearMensajeEstadisticas(libros, usuarios, prestamos, disponibles, fecha);
    }

    private int contarConMultiplesNombres(Connection conn, String[] nombres) {
        for (String nombre : nombres) {
            try {
                String sql = "SELECT COUNT(*) FROM " + nombre;
                try (PreparedStatement stmt = conn.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(" Tabla '" + nombre + "' encontrada");
                        return rs.getInt(1);
                    }
                }
            } catch (SQLException e) {
            }
        }
        return 0;
    }

    private int contarPrestamosActivosSimple(Connection conn) {
        String[] tablas = {"PRESTAMO", "Prestamo", "prestamo"};
        for (String tabla : tablas) {
            try {
                // Probar diferentes nombres de columna
                String[] columnas = {"FECHA_DEVOLUCION", "fechaDevolucion", "fecha_devolucion"};
                for (String columna : columnas) {
                    try {
                        String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE " + columna + " IS NULL";
                        try (PreparedStatement stmt = conn.prepareStatement(sql);
                             ResultSet rs = stmt.executeQuery()) {
                            if (rs.next()) return rs.getInt(1);
                        }
                    } catch (SQLException e) {
                    }
                }
            } catch (Exception e) {
            }
        }
        return 0;
    }

    private String crearMensajeEstadisticas(int libros, int usuarios, int prestamos, int disponibles, String fecha) {
        return "<div style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 10px;'>" +
                "<h3 style='margin-top: 0; text-align: center;'> ESTADÍSTICAS EN TIEMPO REAL</h3>" +
                "<div style='display: grid; grid-template-columns: repeat(2, 1fr); gap: 15px; margin: 20px 0;'>" +
                "<div style='background: rgba(255,255,255,0.2); padding: 15px; border-radius: 8px; text-align: center;'>" +
                "<div style='font-size: 24px; font-weight: bold;'>" + libros + "</div>" +
                "<div> Libros</div>" +
                "</div>" +
                "<div style='background: rgba(255,255,255,0.2); padding: 15px; border-radius: 8px; text-align: center;'>" +
                "<div style='font-size: 24px; font-weight: bold;'>" + usuarios + "</div>" +
                "<div> Usuarios</div>" +
                "</div>" +
                "<div style='background: rgba(255,255,255,0.2); padding: 15px; border-radius: 8px; text-align: center;'>" +
                "<div style='font-size: 24px; font-weight: bold;'>" + prestamos + "</div>" +
                "<div> Préstamos</div>" +
                "</div>" +
                "<div style='background: rgba(255,255,255,0.2); padding: 15px; border-radius: 8px; text-align: center;'>" +
                "<div style='font-size: 24px; font-weight: bold;'>" + disponibles + "</div>" +
                "<div> Disponibles</div>" +
                "</div>" +
                "</div>" +
                "<div style='text-align: center; font-size: 12px; opacity: 0.8;'> Actualizado: " + fecha + "</div>" +
                "</div>";
    }

    private void manejarErrorConexion(SQLException e) {
        System.err.println(" Error de conexión: " + e.getMessage());

        String solucion =
                "<div style='background-color: #fff3cd; border: 1px solid #ffeaa7; padding: 15px; border-radius: 5px;'>" +
                        "<h4 style='color: #856404; margin-top: 0;'>🔧 SOLUCIÓN RÁPIDA</h4>" +
                        "<p><strong>Problema:</strong> Base de datos bloqueada</p>" +
                        "<p><strong>Pasos a seguir:</strong></p>" +
                        "<ol>" +
                        "<li><strong>Cierra IntelliJ completamente</strong></li>" +
                        "<li><strong>Elimina la carpeta 'data/'</strong> manualmente</li>" +
                        "<li><strong>Reinicia IntelliJ</strong></li>" +
                        "<li><strong>Ejecuta BookManager.java</strong></li>" +
                        "</ol>" +
                        "<p><em>Nota: Esto reiniciará la base de datos pero mantendrá tu código.</em></p>" +
                        "</div>";

        addMessage(solucion);
    }

    private void cerrarConexionSegura(Connection conn) {
        if (conn != null) {
            try {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("SHUTDOWN");
                }
                conn.close();
                System.out.println(" Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println(" Error al cerrar conexión: " + e.getMessage());
            }
        }
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}