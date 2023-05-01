package org.primefaces.oasis.data;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * el @Table es para que si so si asi se llama la tabla en la base de datos.
 * Lombock generara  equals y hashcode automaticamente es decir no aparece ene l codigo pero en running time existen y pueden ser llamados
 * Lombock generara los getter y setters segun se modificquen es decir existen mas  no aparecen el codigo
 * Lombock creara el consstructor con solo algunos de los atributos estos son los que tienen la etiqueta @nOnULL
 */

@Entity
@Table(name="CONSULTAS")
@EqualsAndHashCode
@Getter @Setter
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "ID_CONSULTA")
    private Long idConsulta;

    /**
    * @Column(name = "USUARIO_ID")
    * private Usuario usuarioId;
    */
    @Column(name = "RAZON_CONSULTA")
    private String razonConsulta;

    @Column(name = "FECHA_CONSULTA")
    private LocalDate fechaConsulta;

    @Column(name = "DIA_CONSULTA")
    private String diaConsulta;

    @Column(name= "MES_CONSULTA")
    private String mesConsulta;

    @Column(name = "ANO_CONSULTA")
    private String anoConsulta;

    @Column(name = "HORA_CONSULTA")
    private String horaConsulta;

    @Column(name = "COMPROBANTE_PAGO")
    private File comprobandoPago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    /**
     * Constructor vacio
     */
    public Consulta(){}

    public Consulta(String razonConsulta, LocalDate fechaConsulta, String diaConsulta, String mesConsulta, String anoConsulta, String horaConsulta, File comprobandoPago, Usuario usuario) {
        this.razonConsulta = razonConsulta;
        this.fechaConsulta = fechaConsulta;
        this.diaConsulta = diaConsulta;
        this.mesConsulta = mesConsulta;
        this.anoConsulta = anoConsulta;
        this.horaConsulta = horaConsulta;
        this.comprobandoPago = comprobandoPago;
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Consulta.class.getSimpleName() + "[", "]")
                .add("idConsulta=" + idConsulta)
                .add("usuario=" + usuario)
                .toString();
    }
}
