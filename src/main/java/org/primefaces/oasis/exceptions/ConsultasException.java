package org.primefaces.oasis.exceptions;

public class ConsultasException extends Exception{
    public static String NOMBRE_INCORRECTO = "PARECE QUE SU NOMBRE DE USUARIO ES INCORRECTO PORFAVOR VERIFIQUE";
    public static  String CONTRASEÑA_INCORRECTA = "PARECE QUE LA CONTRASEÑA ES INCORRECTA PORFAVOR VERIFICA";
    public static String CONSULTA_SIN_HORA = "NO SE PUEDE TENER UNA CONSULTA SIN HORA";
    public static String CONSULTA_SIN_FECHA = "NO SE PUEDE HACER UNA CONSULTA SIN UNA FECHA DEFINIDA";
    public static String LLENO_DE_CONSULTAS = "LO SENTIMOS PARA ESTA FECHA SELECICONADA NO QUEDAN MAS CITAS PORFAVOR PRUEBE CON OTRA FECHA";
    public ConsultasException(String e){
        super(e);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
