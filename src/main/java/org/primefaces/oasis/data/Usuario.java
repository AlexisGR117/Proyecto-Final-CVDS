package org.primefaces.oasis.data;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Esta clase sera la encargada de contener los datos del usuario a la hora de la pedida  de la cita
 */
@Entity
@Table(name = "USUARIOS")
@Getter @Setter @EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USUARIO_ID")
    private Long usuaioId;   /* Variable que sera el Id auto incrementable */

    @Column(name = "NOMBRE_USUARIO")
    private String nombreUsuario;

    @Column(name = "EMAIL_USUARIO")
    private String emailUsuario;

    @Column(name = "TELEFONO_USUARIO")
    private String telefonoUsuario;

    @Column(name = "CIUDAD_USUARIO")
    private String ciudadUsuario;

    @Column(name = "IDENTIFICACION_USUARIO")
    private String documentoUsuario;

    @Column(name = "FIRMA_USUARIO")
    private String firmaUsuario;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Consulta> consultas = new ArrayList<>();
    /**
     * Constructor vacio por temas de posibles fallos dentro de el tiempo de ejecucion
     */
    public Usuario(){}

    public Usuario(String nombreUsuario, String emailUsuario, String telefonoUsuario, String ciudadUsuario, String documentoUsuario, String firmaUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.ciudadUsuario = ciudadUsuario;
        this.documentoUsuario = documentoUsuario;
        this.firmaUsuario = firmaUsuario;
    }

    /**
     * Este toString es nada mas que para que me muestre el nombre del usuario en caso tal de necesitar mas informacion tambien se puede agregar
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", Usuario.class.getSimpleName() + "[", "]")
                .add("nombreUsuario='" + nombreUsuario + "'")
                .toString();
    }

    /**
     * Este metodo añade consulta por consulta a el ususario ya que esta es una lista o coleccion.
     * @param consulta Es de tipo Consulta
     */
    public void añadirConsulta(Consulta consulta){
        consultas.add(consulta);
    }
}
