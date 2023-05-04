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
public class Admin{

    @Id
    @Column(name = "USER")
    private String id;
    @Column(name = "PASSWORD")
    private String password;

    /**
     * Constructor de la clase user que tiene como parametros el id y la password del usuario
     * @param id
     * @param password
     */
    public Admin(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public Admin() {
    }

    /**
     * Método que devuelve el id de del user como entidad
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Método que ingresa un nuevo id al user como entidad
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Método que devuelve la password del usuario como entidad
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que ingresa una nueva password al user como entidad
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin usuario = (Admin) o;
        return id.equals(usuario.id) && password.equals(usuario.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}