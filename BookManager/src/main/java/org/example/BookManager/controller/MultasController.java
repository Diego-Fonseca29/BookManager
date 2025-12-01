package org.example.BookManager.controller;

import org.openxava.actions.*;
import org.openxava.model.*;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class MultasController extends BaseAction {

    public void execute() throws Exception {
        verificarMultasAutomaticas();
    }

    public void verificarMultasAutomaticas() throws Exception {
        Connection conn = null;
        int multasGeneradas = 0;
        StringBuilder detalles = new StringBuilder();

        try {
            conn = getConnection();
            System.out.println(" Iniciando verificación de multas...");

            String sqlPrestamosVencidos =
                    "SELECT p.ID, p.USUARIO_ID, u.NOMBRE, u.APELLIDO, " +
                            "       l.TITULO, p.FECHA_PRESTAMO " +
                            "FROM PRESTAMO p " +
                            "JOIN USUARIO u ON p.USUARIO_ID = u.ID " +
                            "JOIN LIBRO l ON p.LIBRO_ID = l.ID " +
                            "LEFT JOIN MULTA m ON p.ID = m.PRESTAMO_ID " +
                            "WHERE p.FECHA_DEVOLUCION IS NULL " +
                            "  AND m.ID IS NULL " +
                            "  AND DATEDIFF('DAY', p.FECHA_PRESTAMO, CURRENT_DATE) > 15";

            System.out.println(" Buscando préstamos vencidos...");

            try (PreparedStatement stmt = conn.prepareStatement(sqlPrestamosVencidos);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Long prestamoId = rs.getLong("ID");
                    Long usuarioId = rs.getLong("USUARIO_ID");
                    String nombreUsuario = rs.getString("NOMBRE") + " " + rs.getString("APELLIDO");
                    String tituloLibro = rs.getString("TITULO");
                    java.sql.Date fechaPrestamoSQL = rs.getDate("FECHA_PRESTAMO");

                    java.util.Date fechaPrestamo = null;
                    if (fechaPrestamoSQL != null) {
                        fechaPrestamo = new java.util.Date(fechaPrestamoSQL.getTime());
                    }

                    long diasRetraso = calcularDiasRetraso(fechaPrestamo);

                    if (diasRetraso > 0) {
                        Map<String, Object> valoresMulta = new HashMap<>();
                        valoresMulta.put("usuario.id", usuarioId);
                        valoresMulta.put("prestamo.id", prestamoId);
                        valoresMulta.put("diasRetraso", (int) diasRetraso);
                        valoresMulta.put("fechaMulta", new java.util.Date());
                        valoresMulta.put("monto", new BigDecimal(diasRetraso * 10)); // $10 por día
                        valoresMulta.put("estado", "PENDIENTE");

                        MapFacade.create("Multa", valoresMulta);
                        multasGeneradas++;

                        detalles.append("• ").append(nombreUsuario)
                                .append(" - '").append(tituloLibro).append("'")
                                .append(" (").append(diasRetraso).append(" días, $").append(diasRetraso * 10).append(")\n");

                        System.out.println(" Multa generada para préstamo " + prestamoId +
                                " - Usuario: " + nombreUsuario);
                    }
                }
            }

            mostrarResultados(multasGeneradas, detalles.toString(), conn);

        } catch (Exception e) {
            manejarError(e);
        } finally {
            cerrarConexion(conn);
        }
    }

    private long calcularDiasRetraso(java.util.Date fechaPrestamo) {
        if (fechaPrestamo == null) return 0;

        java.util.Date ahora = new java.util.Date();
        long diferenciaMs = ahora.getTime() - fechaPrestamo.getTime();
        long diasTotales = diferenciaMs / (1000 * 60 * 60 * 24);

        long diasRetraso = diasTotales - 15;
        return Math.max(0, diasRetraso);
    }

    private void mostrarResultados(int multasGeneradas, String detalles, Connection conn) throws SQLException {
        if (multasGeneradas > 0) {
            String mensaje =
                    "<div style='background-color: #d4edda; padding: 15px; border-radius: 8px; border: 1px solid #c3e6cb;'>" +
                            "<h4 style='color: #155724; margin-top: 0;'> <strong>MULTAS GENERADAS: " + multasGeneradas + "</strong></h4>" +
                            "<div style='max-height: 200px; overflow-y: auto; background-color: white; padding: 10px; border-radius: 5px;'>" +
                            "<pre style='margin: 0;'>" + detalles + "</pre>" +
                            "</div>" +
                            "</div>";

            addMessage(mensaje);
        } else {
            addMessage("<div style='background-color: #fff3cd; padding: 15px; border-radius: 8px; border: 1px solid #ffeaa7;'>" +
                    "<h4 style='color: #856404; margin-top: 0;'> NO HAY PRÉSTAMOS VENCIDOS</h4>" +
                    "<p>Todos los préstamos están al día o ya tienen multas asignadas.</p>" +
                    "</div>");
        }

        // Mostrar resumen de multas
        mostrarResumenMultas(conn);
    }

    private void mostrarResumenMultas(Connection conn) throws SQLException {
        String sqlResumen =
                "SELECT " +
                        "  COUNT(*) as total_multas, " +
                        "  SUM(CASE WHEN ESTADO = 'PENDIENTE' THEN 1 ELSE 0 END) as multas_pendientes, " +
                        "  SUM(CASE WHEN ESTADO = 'PAGADA_TOTAL' THEN 1 ELSE 0 END) as multas_pagadas, " +
                        "  COALESCE(SUM(MONTO), 0) as monto_total, " +
                        "  COALESCE(SUM(MONTO_PAGADO), 0) as monto_pagado " +
                        "FROM MULTA";

        try (PreparedStatement stmt = conn.prepareStatement(sqlResumen);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int totalMultas = rs.getInt("total_multas");
                int multasPendientes = rs.getInt("multas_pendientes");
                int multasPagadas = rs.getInt("multas_pagadas");
                BigDecimal montoTotal = rs.getBigDecimal("monto_total");
                BigDecimal montoPagado = rs.getBigDecimal("monto_pagado");

                BigDecimal saldoPendiente = montoTotal.subtract(montoPagado);

                String resumen =
                        "<div style='background-color: #e8f4fd; padding: 15px; border-radius: 8px; border: 1px solid #b6d4fe; margin-top: 15px;'>" +
                                "<h4 style='color: #084298; margin-top: 0;'> RESUMEN GENERAL DE MULTAS</h4>" +
                                "<table style='width: 100%; border-collapse: collapse;'>" +
                                "<tr style='border-bottom: 1px solid #dee2e6;'>" +
                                "<td style='padding: 8px;'><strong>Total Multas:</strong></td>" +
                                "<td style='padding: 8px; text-align: right;'><strong>" + totalMultas + "</strong></td>" +
                                "</tr>" +
                                "<tr style='border-bottom: 1px solid #dee2e6; background-color: #f8f9fa;'>" +
                                "<td style='padding: 8px;'><strong>Multas Pendientes:</strong></td>" +
                                "<td style='padding: 8px; text-align: right; color: #dc3545;'><strong>" + multasPendientes + "</strong></td>" +
                                "</tr>" +
                                "<tr style='border-bottom: 1px solid #dee2e6;'>" +
                                "<td style='padding: 8px;'><strong>Multas Pagadas:</strong></td>" +
                                "<td style='padding: 8px; text-align: right; color: #28a745;'><strong>" + multasPagadas + "</strong></td>" +
                                "</tr>" +
                                "<tr style='border-bottom: 1px solid #dee2e6; background-color: #f8f9fa;'>" +
                                "<td style='padding: 8px;'><strong>Monto Total:</strong></td>" +
                                "<td style='padding: 8px; text-align: right;'><strong>$" + montoTotal + "</strong></td>" +
                                "</tr>" +
                                "<tr style='border-bottom: 1px solid #dee2e6;'>" +
                                "<td style='padding: 8px;'><strong>Monto Pagado:</strong></td>" +
                                "<td style='padding: 8px; text-align: right; color: #28a745;'><strong>$" + montoPagado + "</strong></td>" +
                                "</tr>" +
                                "<tr style='background-color: #fff3cd;'>" +
                                "<td style='padding: 8px;'><strong>SALDO PENDIENTE:</strong></td>" +
                                "<td style='padding: 8px; text-align: right; color: #dc3545; font-size: 16px;'>" +
                                "<strong>$" + saldoPendiente + "</strong></td>" +
                                "</tr>" +
                                "</table>" +
                                "</div>";

                addMessage(resumen);
            }
        }
    }

    private void manejarError(Exception e) {
        System.err.println(" Error en MultasController: " + e.getMessage());
        e.printStackTrace();

        String errorMsg =
                "<div style='background-color: #f8d7da; padding: 15px; border-radius: 8px; border: 1px solid #f5c6cb;'>" +
                        "<h4 style='color: #721c24; margin-top: 0;'>❌ ERROR AL PROCESAR MULTAS</h4>" +
                        "<p><strong>Detalles:</strong> " + e.getMessage() + "</p>" +
                        "<p><strong>Solución:</strong></p>" +
                        "<ol>" +
                        "<li>Verifica que las tablas existan en la base de datos</li>" +
                        "<li>Reinicia la aplicación si es necesario</li>" +
                        "<li>Contacta al administrador del sistema</li>" +
                        "</ol>" +
                        "</div>";

        addMessage(errorMsg);
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:hsqldb:file:data/BookManager-db";
        String user = "SA";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    private void cerrarConexion(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Conexión cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar conexión: " + e.getMessage());
            }
        }
    }
}