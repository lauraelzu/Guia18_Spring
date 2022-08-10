/*
controlador para gestionar libros
 */
package com.mendoza.biblioteca.controladores;

import com.mendoza.biblioteca.entidades.Autor;
import com.mendoza.biblioteca.entidades.Editorial;
import com.mendoza.biblioteca.entidades.Libro;
import com.mendoza.biblioteca.excepciones.MiExcepcion;
import com.mendoza.biblioteca.servicios.AutorServicio;
import com.mendoza.biblioteca.servicios.EditorialServicio;
import com.mendoza.biblioteca.servicios.LibroServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;
    
    //método qu devuelve la vista con el formulario de carga
    @GetMapping("/registrar")  //localhost:8080/libro/registrar
    public String registrar(ModelMap modelo){
        //se usa Model Map para inyectar los listados al formulario
        //mostrar la lista de autores y editoriales para poderlos seleccionar en el formulario 
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("autores", autores); //para enviar la coleccion autores al html a través de la clave "autores" (thymeleaf)
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }
    
    //método que recibe la informacion del formulario para enviarlo al repo
    @PostMapping("/guardar")  //localhosta:8080/libro/registro
                        //required=false en caso que sea NULL ingresa al controlador y la excep se maneja desde el servicio
    public String guardar(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
                            @RequestParam(required = false) Integer ejemplares,@RequestParam String idAutor,
                            @RequestParam String idEditorial, ModelMap modelo){
                                              //Para modelar la inyeccion de los errores que pueden traer algunas de las validaciones
                                              //clase ModelMap sirve para insertar en el modelo toda la informacion a mostrar por pantalla o en la UI
                                              //enviar objetos a la vista y que ésta pueda identificarlos y saber qué tiene que hacer con ellos 
                                              //en este casso se usará para insertar el mensaje de error en la UI
                                              //como es un mapa tiene 2 argumentos "llave" y "valor"
        
        try {
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial); //si todo sale bien, crear un libro y devuelve la vista index
            
            modelo.put("exito", "El libro fue cargado correctamente"); //bajo la llave "exito", va el mensaje de éxito
        
        } catch (MiExcepcion ex) {
            // Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);//si ocurre un error muestra error por consola
            //en lugar de enviar el mensaje de error por consola (Output del Netbeans), 
            //se usará la clase ModelMap para enviarlo a través de modelo al html
            
            modelo.put("error", ex.getMessage()); //en caso de error se envia el mensaje dentro de la validación del método servicio
            
            List<Autor> autores = autorServicio.listarAutores();
            List<Editorial> editoriales = editorialServicio.listarEditoriales();
            modelo.addAttribute("autores", autores); //vuelva a mostrar el listado en las listas desplegables
            modelo.addAttribute("editoriales", editoriales);
            
            return "libro_form.html";                      //y recarga el formulario de carga de libro
        }
        
        return "redirect:../"; 
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List<Libro> libros = libroServicio.listarLibros();
        
        modelo.addAttribute("libros",libros);
        
        return "libro_list.html";
    }
    
    
    //*************************************************************************
    @GetMapping("/listautores")
    public String mostrarAutores(ModelMap modelo){
        
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
           
        return "libro_formautor.html";
    }
    
    @PostMapping("/librosautor")
    public String listarLibrosAutor(@RequestParam String idAutor,ModelMap modelo){
        List<Libro> libros = libroServicio.listarPorAutor(idAutor);
        
        modelo.addAttribute("libros",libros);
      
           
        return "libro_list.html";
    }
    //**************************************************************
    
    
    //**************************************************************
    @GetMapping("/eliminar/{isbn}")
    public String eliminar(@PathVariable Long isbn,ModelMap modelo){
            
        libroServicio.eliminarPorId(isbn);
        
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros",libros);
        
        return "libro_list.html";
    }
    //*****************************************************************    

   
}
