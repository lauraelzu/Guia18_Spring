/*Se crea esta clase para diferenciar los errores en la lógica de negocio,
de los errores propios del sistema*/

package com.mendoza.biblioteca.excepciones;

public class MiExcepcion extends Exception{

    public MiExcepcion(String msg) {
        super(msg);
    }
    
}
