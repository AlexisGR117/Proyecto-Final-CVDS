package org.primefaces.oasis.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Clase que se entiende como la creacion de la entidad en la base de datos
 * fecha: 4/27/2023
 * Hecho por: Daniel Santiago Gomez Zabala
 */
@Entity
@Table(name = "ADMINISTRADORES")
public class Admin {

    @Id
    @Column(name = "NOMBRE")
    private String nombre;
    @Column(name = "CONTRASENA")
    private String contrasena;

    /**
     * Constructor de la clase user que tiene como parametros el nombre del usuario y su contraseña
     * @param nombre Cadena con el nombre del usuario
     * @param contrasena Cadena con la contrasena del usuario
     */
    public Admin(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public Admin() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return nombre.equals(admin.nombre) && contrasena.equals(admin.contrasena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, contrasena);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + nombre + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }
}