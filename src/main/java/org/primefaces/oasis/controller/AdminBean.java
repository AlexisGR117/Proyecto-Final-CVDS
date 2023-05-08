package org.primefaces.oasis.form;

import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.service.AdminService;
import org.primefaces.oasis.service.ServiceException;
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
 * Hecho por: Daniel Santiago Gomez Zabala
 * Modificado por:
 * fecha de modificación:
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
     * Método que verifica que el usuario exista en la base de datos y permite el acceso desde el service
     */
    public String login() {
        try {
            adminService.login(adminNombre, contrasena);
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage("@all", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    e.getMessage(), null));
            return null;
        }
        return "consultas.xhtml";
    }

    /**
     * Método donde se puede añadir usuarios a la base de datos y además los muestra en consola
     */
    @Bean
    public CommandLineRunner currentUser() {
        return args -> {
            adminService.addAdmin(new Admin("admin", "admin"));
            adminService.getAllAdmin().forEach(System.out::println);
        };
    }
}