/*
controlador del portal inicial de la aplicación
 */
package com.mendoza.biblioteca.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")    //indica la URL que va a escuchar al controlador  (localhost:8080/)
public class PortalControlador {
    
    @GetMapping("/")       //se accede mediante un GET de HTML, mapea la URL cuando se ingrese 'localhost:8080/'
    public String index(){   //1° método que se ejecuta al iniciar la app
        return "index.html";
    }
}
