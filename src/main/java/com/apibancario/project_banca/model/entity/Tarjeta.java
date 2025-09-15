package com.apibancario.project_banca.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tarjeta")
public class Tarjeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Integer idTarjeta;

    @Column(name = "numero_tarjeta", nullable = false, length = 16, unique = true)
    private String numeroTarjeta;

    @Column(name = "tipo_tarjeta", nullable = false)
    private String tipoTarjeta;

    @Column(name = "fecha_vencimiento", nullable = false, length = 5)
    private String fechaVencimiento;

    @Column(name = "pin_tarjeta", nullable = false)
    private String pinTarjeta;

    @Column(name = "cvv_tarjeta", nullable = false)
    private String cvvTarjeta;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    @OneToMany(mappedBy = "tarjeta")
    private List<Transaccion> transacciones;
}
