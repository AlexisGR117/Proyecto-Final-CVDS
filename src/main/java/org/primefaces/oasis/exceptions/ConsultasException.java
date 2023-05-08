package org.primefaces.oasis.exceptions;

public class ConsultasException extends Exception{
    public static String NOMBRE_INCORRECTO = "Parece que su nombre de Usuario es incorrecto porfavor verifique";
    public static  String CONTRASEÑA_INCORRECTA = "Parece que la contraseña es incorrecta porfavor verifique";
    public static String CONSULTA_SIN_HORA = "No se puede crear una consuulta sin hora seleccionada";
    public static String CONSULTA_SIN_FECHA = "No se puede crear una consulta sin una fecha definida";
    public static String LLENO_DE_CONSULTAS = "Lo siento para esta fecha no queda disponibilidad horaria porfavor " +
            "verifique";
    public ConsultasException(String e){
        super(e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
