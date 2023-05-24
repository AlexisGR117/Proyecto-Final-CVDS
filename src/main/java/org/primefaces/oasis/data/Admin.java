package org.primefaces.oasis.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad de base de datos que guarda la información del Administrador.
 *
 * @author Daniel Santiago Gomez Zabala
 * @version 4/27/2023
 */
@Entity
@Table(name = "ADMINISTRADORES")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Admin {

    @Id
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CONTRASENA")
    private String contrasena;

    /**
     * Constructor de la clase user.
     *
     * @param nombre     Cadena con el nombre del usuario
     * @param contrasena Cadena con la contrasena del usuario
     */
    public Admin(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Admin() {
    }
}