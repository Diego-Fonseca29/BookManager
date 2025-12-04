package org.example.BookManager.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.openxava.annotations.*;
import java.time.LocalDate;

@Entity
@Table(name="reserva")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Libro libro;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @DescriptionsList
    private Empleado empleado; // Nuevo campo

    @Stereotype("DATE")
    private LocalDate fechaReserva;

    @Stereotype("DATE")
    private LocalDate fechaExpiracion;

    @Column(length = 20)
    @Required
    private Estado estado = Estado.PENDIENTE;


}
