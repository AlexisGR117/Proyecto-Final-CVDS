package org.primefaces.oasis.controller;

import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.exceptions.AdminException;
import org.primefaces.oasis.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

/**
 * Clase que permite tener el usuario como objeto además de conectar y añadir usuarios a la base de datos
 * fecha: 4/27/2023
 * @author Daniel Santiago Gomez Zabala
 */
@Component
@ManagedBean(name = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    @Autowired
    AdminService adminService;
    private String adminNombre;
    private String contrasena;

    public String getAdminNombre() {
        return adminNombre;
    }

    public void setAdminNombre(String adminNombre) {
        this.adminNombre = adminNombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Método que verifica que el usuario exista en la base de datos y permite el acceso desde el service.
     * @return La página a la que se va a redirigir si el login es exitoso.
     */
    public String login() {
        try {
            adminService.login(adminNombre, contrasena);
        } catch (AdminException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique:", e.getMessage()));
            return null;
        }
        return "consultas.xhtml";
    }

    /**
     * Método donde se puede añadir usuarios a la base de datos y además los muestra en consola.
     */
    @Bean
    public CommandLineRunner currentUser() {
        return args -> {
            adminService.addAdmin(new Admin("admin", "admin"));
            adminService.addAdmin(new Admin("HALS", "password"));
        };
    }
}