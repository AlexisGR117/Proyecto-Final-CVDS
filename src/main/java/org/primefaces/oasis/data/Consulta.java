package org.primefaces.oasis.data;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="CONSULTAS")
@EqualsAndHashCode
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "ID_CONSULTA")
    private Long idConsulta;

    /**
    @Column(name = "USUARIO_ID")
    private Usuario usuarioId;
    */
    @Column(name = "RAZON_CONSULTA")
    private String razonConsulta;

    @Column(name = "FECHA_CONSULTA")
    private LocalDate fechaConsulta;

    @Column(name = "HORA_CONSULTA")
    private LocalTime horaConsulta;

    @Column(name = "COMPROBANTE_PAGO")
    private File comprobandoPago;


    /**
     * Constructor vacio
     */
    public Consulta(){}

    /**
     *
     *
     * @param razonConsulta
     * @param fechaConsulta
     * @param horaConsulta
     * @param comprobandoPago
     */
    public Consulta(String razonConsulta, LocalDate fechaConsulta, LocalTime horaConsulta, File comprobandoPago) {
        //this.usuarioId = usuarioId;
        this.razonConsulta = razonConsulta;
        this.fechaConsulta = fechaConsulta;
        this.horaConsulta = horaConsulta;
        this.comprobandoPago = comprobandoPago;
    }

    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    /** public Usuario getUsuarioId() {
        return usuarioId;
    }
    */
    /** public void setUsuarioId(Usuario usuarioId) {
        this.usuarioId = usuarioId;
    }
    */
    public String getRazonConsulta() {
        return razonConsulta;
    }

    public void setRazonConsulta(String razonConsulta) {
        this.razonConsulta = razonConsulta;
    }

    public LocalDate getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(LocalDate fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public LocalTime getHoraConsulta() {
        return horaConsulta;
    }

    public void setHoraConsulta(LocalTime horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

    public File getComprobandoPago() {
        return comprobandoPago;
    }

    public void setComprobandoPago(File comprobandoPago) {
        this.comprobandoPago = comprobandoPago;
    }

}
