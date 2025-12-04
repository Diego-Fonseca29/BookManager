package org.example.BookManager.model;

import org.openxava.annotations.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Views({
        @View(name = "Simple", members = "usuario, prestamo, fechaMulta, diasRetraso, monto, estado"),
        @View(name = "Completo", members =
                "usuario, prestamo;" +
                        "fechaMulta, fechaPago;" +
                        "diasRetraso, monto, montoPagado;" +
                        "estado, observaciones"
        )
})
@Tab(properties = "usuario.nombre, prestamo.libro.titulo, fechaMulta, diasRetraso, monto, estado")
public class Multa {

    @Id
    @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "nombre, apellido")
    @Required
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @DescriptionsList(descriptionProperties = "libro.titulo, fechaPrestamo")
    @Required
    private Prestamo prestamo;

    @Column(name = "FECHA_MULTA")
    @Required
    private Date fechaMulta;

    @Column(name = "FECHA_PAGO")
    private Date fechaPago;

    @Column(name = "DIAS_RETRASO")
    @Required
    @Min(value = 1, message = "Los días de retraso deben ser al menos 1")
    private Integer diasRetraso;

    @Column(name = "MONTO", precision = 10, scale = 2)
    @Required
    @Stereotype("DINERO")
    private BigDecimal monto;

    @Column(name = "MONTO_PAGADO", precision = 10, scale = 2)
    @Stereotype("DINERO")
    private BigDecimal montoPagado = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", length = 20)
    @Required
    private EstadoMulta estado = EstadoMulta.PENDIENTE;

    @Column(length = 500) @TextArea
    private String observaciones;

    public enum EstadoMulta {
        PENDIENTE, PAGADA_PARCIAL, PAGADA_TOTAL, CANCELADA
    }

    public Multa() {
        this.fechaMulta = new Date();
    }

    public Multa(Usuario usuario, Prestamo prestamo, int diasRetraso) {
        this();
        this.usuario = usuario;
        this.prestamo = prestamo;
        this.diasRetraso = diasRetraso;
        this.monto = calcularMonto(diasRetraso);
        this.estado = EstadoMulta.PENDIENTE;
    }

    private BigDecimal calcularMonto(int diasRetraso) {
        BigDecimal montoPorDia = new BigDecimal("10.00");
        return montoPorDia.multiply(new BigDecimal(diasRetraso));
    }

    public void registrarPago(BigDecimal montoPagado, String observaciones) {
        if (montoPagado == null) {
            montoPagado = BigDecimal.ZERO;
        }

        this.montoPagado = this.montoPagado.add(montoPagado);
        this.fechaPago = new Date();

        if (this.montoPagado.compareTo(this.monto) >= 0) {
            this.estado = EstadoMulta.PAGADA_TOTAL;
        } else if (this.montoPagado.compareTo(BigDecimal.ZERO) > 0) {
            this.estado = EstadoMulta.PAGADA_PARCIAL;
        }

        if (observaciones != null) {
            this.observaciones = (this.observaciones == null ? "" : this.observaciones + "\n")
                    + "Pago: " + montoPagado + " - " + new Date();
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Prestamo getPrestamo() { return prestamo; }
    public void setPrestamo(Prestamo prestamo) { this.prestamo = prestamo; }

    public Date getFechaMulta() { return fechaMulta; }
    public void setFechaMulta(Date fechaMulta) { this.fechaMulta = fechaMulta; }

    public Date getFechaPago() { return fechaPago; }
    public void setFechaPago(Date fechaPago) { this.fechaPago = fechaPago; }

    public Integer getDiasRetraso() { return diasRetraso; }
    public void setDiasRetraso(Integer diasRetraso) {
        this.diasRetraso = diasRetraso;
        // Recalcular monto si cambian los días
        if (diasRetraso != null && diasRetraso > 0) {
            this.monto = calcularMonto(diasRetraso);
        }
    }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public BigDecimal getMontoPagado() { return montoPagado; }
    public void setMontoPagado(BigDecimal montoPagado) { this.montoPagado = montoPagado; }

    public EstadoMulta getEstado() { return estado; }
    public void setEstado(EstadoMulta estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public BigDecimal getSaldoPendiente() {
        if (monto == null) return BigDecimal.ZERO;
        if (montoPagado == null) return monto;
        return monto.subtract(montoPagado);
    }

    public boolean isPagadaTotalmente() {
        return estado == EstadoMulta.PAGADA_TOTAL;
    }
}