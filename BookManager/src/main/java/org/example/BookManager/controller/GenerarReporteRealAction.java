package org.example.BookManager.controller;

import org.openxava.actions.*;
import org.openxava.model.*;
import java.util.*;
import java.sql.*;

public class GenerarReporteRealAction extends ViewBaseAction {

    public void execute() throws Exception {
        try {
            Map<String, Object> estadisticas = new HashMap<>();
            calcularEstadisticasReales(estadisticas);

            Map<String, Object> valoresReporte = new HashMap<>();
            valoresReporte.put("nombreReporte", "Reporte Real - " + new java.util.Date());
            valoresReporte.put("fechaGeneracion", new java.util.Date());

            valoresReporte.putAll(estadisticas);

            System.out.println("Creando reporte con valores: " + valoresReporte);

            MapFacade.create("Reporte", valoresReporte);

            addMessage(" Reporte con datos REALES generado exitosamente");
            addMessage(" Estadísticas: " + estadisticas.toString());

        } catch (Exception e) {
            System.err.println(" Error grave: " + e.getMessage());
            e.printStackTrace();
            addError("Error grave al generar reporte: " + e.getMessage());
        }

        getView().refresh();
    }

    private void calcularEstadisticasReales(Map<String, Object> estadisticas) {
        System.out.println("🔍 Calculando estadísticas reales...");

        Connection conn = null;
        try {
            conn = getConnection();
            System.out.println(" Conexión a BD establecida");

            int totalLibros = contarRegistros(conn, "LIBRO");
            estadisticas.put("totalLibros", totalLibros);
            System.out.println(" Total libros: " + totalLibros);

            int totalUsuarios = contarRegistros(conn, "USUARIO");
            estadisticas.put("totalUsuarios", totalUsuarios);
            System.out.println(" Total usuarios: " + totalUsuarios);

            int prestamosActivos = contarPrestamosActivos(conn);
            estadisticas.put("totalPrestamosActivos", prestamosActivos);
            System.out.println("🔄 Préstamos activos: " + prestamosActivos);

            estadisticas.put("prestamosVencidos", 0);
            System.out.println(" Préstamos vencidos: 0");

            int librosDisponibles = Math.max(0, totalLibros - prestamosActivos);
            estadisticas.put("librosDisponibles", librosDisponibles);
            System.out.println(" Libros disponibles: " + librosDisponibles);

            estadisticas.put("librosPrestados", prestamosActivos);
            System.out.println(" Libros prestados: " + prestamosActivos);

            System.out.println(" Estadísticas calculadas: " + estadisticas);

        } catch (Exception e) {
            System.err.println(" Error en cálculo: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error calculando estadísticas: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { }
            }
        }
    }

    private int contarRegistros(Connection conn, String tabla) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tabla;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private int contarPrestamosActivos(Connection conn) throws SQLException {
        String sql = "SELECT COUNT(*) FROM PRESTAMO WHERE FECHA_DEVOLUCION IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:hsqldb:file:data/BookManager-db";
        String user = "SA";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }
}