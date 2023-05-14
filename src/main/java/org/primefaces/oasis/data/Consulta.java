package org.primefaces.oasis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.oasis.service.EstadoConsulta;

import javax.persistence.*;
import java.io.File;
import java.util.StringJoiner;

/**
 * Entidad de base de datos que guarda la información de la consulta.
 */
@Entity
@Table(name = "CONSULTAS")
@EqualsAndHashCode
@Getter
@Setter
public class Consulta {

    @EmbeddedId
    private ConsultaId id;
    @Column(name = "RAZON_CONSULTA", length = 1000)
    private String razonConsulta;
    @Column(name = "COMPROBANTE_PAGO")
    private File comprobandoPago;
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_CONSULTA")
    private EstadoConsulta estadoConsulta;

    @Column(name = "OBSERVACIONES", length = 1000)
    private String observaciones;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Constructor vacio para objetos de clase consulta.
     */
    public Consulta() {
    }

    /**
     * Constructor para objetos de clase Consulta.
     *
     * @param razonConsulta Motivo de la consulta dada por el usuario.
     * @param consultaId Identificador de la consulta formado por la fecha y la hora.
     * @param usuario Usuario al que corresponde la consulta.
     */
    public Consulta(String razonConsulta, ConsultaId consultaId, Usuario usuario) {
        this.razonConsulta = razonConsulta;
        id = consultaId;
        this.usuario = usuario;
        estadoConsulta = EstadoConsulta.AGENDADA;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Consulta.class.getSimpleName() + "[", "]")
                .add("consultaId=" + id)
                .add("usuario=" + usuario)
                .toString();
    }
}
