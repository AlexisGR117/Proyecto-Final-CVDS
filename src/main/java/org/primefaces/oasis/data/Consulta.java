package org.primefaces.oasis.data;

import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;


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
@AllArgsConstructor
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

    @Column(name = "DIA_CONSULTA")
    @NonNull private String diaConsulta;

    @Column(name= "MES_CONSULTA")
    @NonNull private String mesConsulta;

    @Column(name = "ANO_CONSULTA")
    @NonNull private String anoConsulta;
    @Column(name = "HORA_CONSULTA")
    @NonNull private String horaConsulta;

    @Column(name = "COMPROBANTE_PAGO")
    @NonNull private File comprobandoPago;
    /**
     * Constructor vacio
     */
    public Consulta(){}
}
