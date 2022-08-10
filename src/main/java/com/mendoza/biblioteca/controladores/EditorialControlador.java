//controlador para gestionar editoriales

package com.mendoza.biblioteca.controladores;

import com.mendoza.biblioteca.entidades.Editorial;
import com.mendoza.biblioteca.excepciones.MiExcepcion;
import com.mendoza.biblioteca.servicios.EditorialServicio;
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
@RequestMapping("/editorial")  //localhost:8080/editorial para ingresar a este controlador
public class EditorialControlador {
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")  //localhost:8080/editorial/registrar para ingresar al metodo registrarEditorial que abre la vista (formulario de carga)
    public String registrar(){     
        return "editorial_form.html";
    }
    
    @PostMapping("/guardar")
    public String guardar(@RequestParam String nombre, ModelMap modelo){
        
        try {
            editorialServicio.crearEditorial(nombre);
            modelo.put("exito", "La editorial fue registrada correctamente");
        } catch (MiExcepcion ex) {
            modelo.put("error",ex.getMessage());
            return "editorial_form.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.addAttribute("editoriales", editoriales);
        return "editorial_list.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        modelo.put("editorial", editorialServicio.obtenerEditorial(id));
        return "editorial_modificar.html";
    }
    
    //recibe los datos del formulario y los va a modificar  
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, ModelMap modelo){
        try {
            editorialServicio.modificarEditorial(nombre, id);
            //si no hay error, debe renderizar la lista de autores actualizada con 'redirect:'
            return "redirect:../lista";
            
        } catch (MiExcepcion ex) {
            modelo.put("error", ex.getMessage());
            return "editorial_modificar.html";
        }
        
    }
    
    
}
