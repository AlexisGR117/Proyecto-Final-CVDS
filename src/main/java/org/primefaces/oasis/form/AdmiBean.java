package org.primefaces.oasis.form;

import org.primefaces.oasis.data.Admin;
import org.primefaces.oasis.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.primefaces.oasis.services.AdminService;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

/**
 * Clase que permite tener el usuario como objeto además de conectar y añadir usuarios a la base de datos
 * fecha: 4/27/2023
 * Hecho por: Daniel Santiago Gomez Zabala
 * Modificado por:
 * fecha de modificación:
 */
@Component
@ManagedBean(name = "admiBean")
@SessionScoped
public class AdmiBean  implements Serializable {
    private final AdminRepository userRepository;
    @Autowired
    AdminService AdminService;
    private String adminNombre;
    private String contrasena;
    private List<Admin> admins;

    /**
     * Constructor de la clase UserBean
     * @param userRepository
     */
    public AdmiBean(AdminRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Método que permite obtener la lista de usuarios registrados en la base de datos
     * @return
     */
    public List<Admin> getAdmins(){
        this.admins = AdminService.getAllUsuario();
        return admins;
    }

    /**
     * Método que permite obtener el nombre de usuario
     * @return
     */
    public String getAdminNombre() {
        return adminNombre;
    }

    /**
     * Método que permite añadir a un nuevo usuario
     * @param adminNombre
     */
    public void setAdminNombre(String adminNombre) {
        this.adminNombre = adminNombre;
    }

    /**
     * Método que permite obtener la clave del usuario
     * @return
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Método que permite añadir una nueva clave a determinado usuario
     * @param contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * Método que valida que el usuario exista en la base de datos y permitE el acceso desde el service
     * @return
     */
    public String login() {
        if(AdminService.login(adminNombre, contrasena)){
            return "dashboard.xhtml";
        }
        FacesContext.getCurrentInstance().addMessage("@all", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales erroneas", null));
        return null;
    }


    /**
     * Método donde se puede añadir usuarios a la base de datos y además los muestra en consola
     * @return
     * @throws Exception
     */
    @Bean
    public CommandLineRunner currentUser() throws Exception{
        return args -> {
            AdminService.addUsuario(new Admin("admin", "admin"));
            AdminService.getAllUsuario().forEach(System.out::println);
        };
    }
}
