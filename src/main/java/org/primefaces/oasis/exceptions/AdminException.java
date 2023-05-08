package org.primefaces.oasis.exceptions;
/**
 * Clase excepción de la clase AdminService.
 */
public class AdminException extends Exception {

    public static final String NOMBRE_VACIO = "Se debe ingresar el nombre del usuario del administrador.";
    public static final String CONTRASENA_VACIA = "No se ha ingresado una contraseña.";
    public static final String NOMBRE_INVALIDO = "El administrador con este nombre de usuario no existe.";
    public static final String CONSTRASENA_INVALIDA = "La contrasena es incorrecta.";

    /**
     * Constructor para objetos de clase AdminException.
     * @param message Mensaje de la excepción.
     */
    public AdminException(String message) {
        super(message);
    }
}