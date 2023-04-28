package org.primefaces.oasis.data;


import javax.persistence.*;
import java.util.Objects;

/**
 * Esta clase sera la encargada de contener los datos del usuario a la hora de la pedida  de la cita
 */
@Entity
@Table(name = "USUARIOS")
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

    /**
     * Constructor vacio por temas de posibles fallos dentro de el tiempo de ejecucion
     */
    public Usuario(){}

    /**
     * Constructor con parametros menos el id ya que este es un autoincrementable
     * @param nombreUsuario Corresponde a la parte de Nombre en el formulario
     * @param emailUsuario Corresponde a la parte de Email en el Fomrulario
     * @param telefonoUsuario Corresponde a la parte de Telefono del Formulario
     * @param ciudadUsuario Corresponde a la Parte de Ciudad del Formulario
     * @param documentoUsuario Corresponde a la parte de No.Identificacion de Formulario
     */
    public Usuario(String nombreUsuario, String emailUsuario, String telefonoUsuario, String ciudadUsuario, String documentoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.telefonoUsuario = telefonoUsuario;
        this.ciudadUsuario = ciudadUsuario;
        this.documentoUsuario = documentoUsuario;
    }


    public Long getUsuaioId(){
        return usuaioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }

    public String getCiudadUsuario() {
        return ciudadUsuario;
    }

    public void setCiudadUsuario(String ciudadUsuario) {
        this.ciudadUsuario = ciudadUsuario;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Usuario{");
        sb.append("nombreUsuario='").append(nombreUsuario).append('\'');
        sb.append(", emailUsuario='").append(emailUsuario).append('\'');
        sb.append(", telefonoUsuario='").append(telefonoUsuario).append('\'');
        sb.append(", ciudadUsuario='").append(ciudadUsuario).append('\'');
        sb.append(", documentoUsuario='").append(documentoUsuario).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getUsuaioId(), usuario.getUsuaioId()) && Objects.equals(getNombreUsuario(), usuario.getNombreUsuario()) && Objects.equals(getEmailUsuario(), usuario.getEmailUsuario()) && Objects.equals(getTelefonoUsuario(), usuario.getTelefonoUsuario()) && Objects.equals(getCiudadUsuario(), usuario.getCiudadUsuario()) && Objects.equals(getDocumentoUsuario(), usuario.getDocumentoUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsuaioId(), getNombreUsuario(), getEmailUsuario(), getTelefonoUsuario(), getCiudadUsuario(), getDocumentoUsuario());
    }
}
