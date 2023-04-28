package org.primefaces.oasis.controllers;

import org.primefaces.oasis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import org.primefaces.oasis.repositories.UserRepository;
import org.primefaces.oasis.services.UserService;
import org.primefaces.oasis.model.User;

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
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean  implements Serializable {
    private final UserRepository userRepository;
    @Autowired
    UserService userService;
    private String userName;
    private String password;
    private List<User> usuarios;

    /**
     * Constructor de la clase UserBean
     * @param userRepository
     */
    public UserBean(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Método que permite obtener la lista de usuarios registrados en la base de datos
     * @return
     */
    public List<User> getUsuarios(){
        this.usuarios = userService.getAllUsuario();
        return usuarios;
    }

    /**
     * Método que permite obtener el nombre de usuario
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Método que permite añadir a un nuevo usuario
     * @param user
     */
    public void setUserName(String user) {
        this.userName = user;
    }

    /**
     * Método que permite obtener la clave del usuario
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que permite añadir una nueva clave a determinado usuario
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Método que valida que el usuario exista en la base de datos y permitE el acceso
     * NOTA: SE DEBE COLOCAR A DONDE SE VA DIRIGIR DESPUES DE QUE SE VALIDE EL ACCESO
     * @return
     */
    public String login() {
        User usuario = userRepository.findById(userName);
        if (usuario == null || !usuario.getPassword().equals(password)) {
            FacesContext.getCurrentInstance().addMessage("@all", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Credenciales erroneas", null));
            return null;
        } else {
            return "welcome.xhtml";
        }
    }

    /**
     * Método donde se puede añadir usuarios a la base de datos y además los muestra en consola
     * @return
     * @throws Exception
     */
    @Bean
    public CommandLineRunner currentUser() throws Exception{
        return args -> {
            userService.addUsuario(new User("admin", "admin"));
            userService.getAllUsuario().forEach(System.out::println);
        };
    }
}
