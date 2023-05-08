package org.primefaces.oasis.exceptions;

public class ConsultasException extends Exception{
    public static final String NOMBRE_INCORRECTO = "Parece que su nombre de Usuario es incorrecto por favor verifique";
    public static final  String CONTRASENA_INCORRECTA = "Parece que la contraseña es incorrecta por favor verifique";
    public static final String CONSULTA_SIN_HORA = "No se puede crear una consulta sin hora seleccionada";
    public static final String CONSULTA_SIN_FECHA = "No se puede crear una consulta sin una fecha definida";
    public static final String LLENO_DE_CONSULTAS = "Lo siento para esta fecha no queda disponibilidad horaria por " +
            "favor verifique";
    public static final String ERROR_ARCHIVO = "Disculpe las molestias hubo un error al guardar el archivo.";

    /**
     * Constructor para objetos de clase ConsultasException.
     * @param message Mensaje de la excepción.
     */
    public ConsultasException(String message) {
        super(message);
    }
}
