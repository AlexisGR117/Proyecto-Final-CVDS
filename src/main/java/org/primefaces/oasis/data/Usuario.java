package org.primefaces.oasis.data;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Esta clase sera la encargada de contener los datos del usuario a la hora de la pedida de la cita
 */
@Entity
@Table(name = "USUARIOS")
@Getter @Setter @EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USUARIO_ID")
    private Long usuarioId;   /* Variable que sera el Id auto incrementable */
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TELEFONO")
    private String telefono;
    @Column(name = "CIUDAD")
    private String ciudad;
    @Column(name = "DOCUMENTO")
    private String documento;
    @Lob
    @Column(name = "FIRMA")
    private String firma;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Consulta> consultas = new ArrayList<>();

    /**
     * Constructor vacio por temas de posibles fallos dentro del tiempo de ejecucion
     */
    public Usuario(){}

    /**
     * Constructor para objetos de clase Usuario.
     * @param nombre Nombre del usuario que va a programar la consulta.
     * @param email Email del usuario que va a programar la consulta.
     * @param telefono Telefono del usuario que va a programar la consulta.
     * @param documento Documento de identidad del usuario que va a programar la consulta.
     * @param firma Firma del usuario que va a programar la consulta.
     */
    public Usuario(String nombre, String email, String telefono, String documento,
                   String firma) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.documento = documento;
        this.firma = firma;
    }

    /**
     * Constructor para objetos de clase Usuario.
     * @param nombre Nombre del usuario que va a programar la consulta.
     * @param email Email del usuario que va a programar la consulta.
     * @param telefono Telefono del usuario que va a programar la consulta.
     * @param ciudad Ciudad del usuario que va a programar la consulta.
     * @param documento Documento de identidad del usuario que va a programar la consulta.
     * @param firma Firma del usuario que va a programar la consulta.
     */
    public Usuario(String nombre, String email, String telefono, String ciudad,
                   String documento, String firma) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.documento = documento;
        this.firma = firma;
    }

    /**
     * Este toString es nada mas que para que me muestre el nombre del usuario en caso tal de necesitar
     * mas informacion tambien se puede agregar
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]")
                .add("nombreUsuario='" + nombre + "'")
                .toString();
    }

    /**
     * Este metodo añade consulta por consulta al usuario, ya que esta es una lista o coleccion.
     * @param consulta Es de tipo Consulta
     */
    public void anadirConsulta(Consulta consulta){
        consultas.add(consulta);
    }
}
