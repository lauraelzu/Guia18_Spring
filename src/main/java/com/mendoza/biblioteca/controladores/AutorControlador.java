/*
controlador para gestionar autores
 */
package com.mendoza.biblioteca.controladores;

import com.mendoza.biblioteca.entidades.Autor;
import com.mendoza.biblioteca.excepciones.MiExcepcion;
import com.mendoza.biblioteca.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/autor")    //se ingresa a AutorControlador al colocar la URL localhost:8080/autor
public class AutorControlador {
    
    @Autowired
    private AutorServicio autorServicio;
    
    
    //método de registro que abrirá la vista autor.html (formulario de carga de autor)
    @GetMapping("/registrar") //petición GET va a responder al llamado de la URL localhost:8080/autor/registrar ingresando al método registrar
    public String registrar(){
        return "autor_form.html";  //devuelve la vista que se renderizará en el navegador, el formulario para cargar un nuevo autor  
    }
    
    //método que recibe la información del formulario a través de un método POST a la URL "localhost:8080/autor/guardar"
    //toma el dato que llega y derivarlo al componente de servicio donde está el método que llamaba al repositorio para impactar los cambios en la BD 
    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) String nombre, ModelMap modelo){ 
        //@RequestParam indica al controlador que 'nombre' va a viajar por la URL
        // System.out.println("Nombre: " + nombre); //mostrar por consola(Netbeams) para ver que esté viajando la información a través del localhost
        
        try {  //al estar en la última capa del proyecto las excepciones deben ser manejados por try-catch                   
            autorServicio.crearAutor(nombre);
            modelo.put("exito", "El autor fue registrado correctamente");
        } catch (MiExcepcion ex) {     
            //Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error",ex.getMessage());
            return "autor_form.html";  //si hay un error que se vuelva a cargar el error.
        }
         return "index.html"; //si todo sale bien volver al inicio
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Autor> autores = autorServicio.listarAutores();
        
        modelo.addAttribute("autores",autores);
        
        return "autor_list.html";
    }
    
    //el id del autor específico a modificar, viaja como parte de la URL (URL parametrizada)
    //@PathVariable avisa que {id} es la variable
    //devuelve la vista con los datos precargados del autor especifico (según su id), inyectados a través del ModelMap
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("autor", autorServicio.obtenerAutor(id));
        return "autor_modificar.html";
    }
    
    //recibe los datos del formulario y los va a modificar  
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            autorServicio.modificarAutor(nombre, id);
            //si no hay error, debe renderizar la lista de autores actualizada con 'redirect:'
            return "redirect:../lista";
            
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "autor_modificar.html";
        }
        
    }
    
}
