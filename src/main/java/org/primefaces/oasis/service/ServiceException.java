package org.primefaces.oasis.service;
/**
 * Clase excepción de clases service.
 */
public class ServiceException extends Exception {

    public static final String NOMBRE_INVALIDO = "El administrador con este nombre de usuario no existe.";
    public static final String CONSTRASENA_INVALIDA = "La contrasena es incorrecta.";

    /**
     * Constructor para objetos de clase serviceException.
     * @param message Mensaje de la excepción.
     */
    public ServiceException(String message) {
        super(message);
    }
}