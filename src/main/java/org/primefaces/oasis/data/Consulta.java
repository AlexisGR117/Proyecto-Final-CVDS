package org.primefaces.oasis.data;

import lombok.*;
import org.primefaces.oasis.service.EstadoConsulta;

import javax.persistence.*;
import java.io.File;
import java.util.StringJoiner;


/**
 * El @Table es para que si so si asi se llama la tabla en la base de datos.
 * Lombock generara equals y hashcode automaticamente es decir no aparece ene l codigo pero en running time
 * existen y pueden ser llamados
 * Lombock generara los getter y setters segun se modificquen es decir existen mas  no aparecen el codigo
 * Lombock creara el consstructor con solo algunos de los atributos estos son los que tienen la etiqueta @nOnULL
 */
@Entity
@Table(name="CONSULTAS")
@EqualsAndHashCode
@Getter @Setter
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
     * Constructor vacio
     */
    public Consulta(){}

    public Consulta(String razonConsulta, ConsultaId consultaid, Usuario usuario) {
        this.razonConsulta = razonConsulta;
        id = consultaid;
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
