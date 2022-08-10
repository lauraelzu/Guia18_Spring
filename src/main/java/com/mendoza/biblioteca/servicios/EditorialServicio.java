package com.mendoza.biblioteca.servicios;

import com.mendoza.biblioteca.entidades.Editorial;
import com.mendoza.biblioteca.excepciones.MiExcepcion;
import com.mendoza.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial (String nombre) throws MiExcepcion{
        
        validar(nombre);
        
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }
    
    public List<Editorial> listarEditoriales(){
        
        List<Editorial> editoriales = new ArrayList();
        editoriales = editorialRepositorio.findAll();
        return editoriales;
    }
      
    @Transactional
    public void modificarEditorial(String nombre, String idEditorial) throws MiExcepcion{
        
        validar(nombre);
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(idEditorial);
        
        if (respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            editorial.setNombre(nombre);
            editorialRepositorio.save(editorial);
        }
    }
    
    
    public Editorial obtenerEditorial(String id){
        return editorialRepositorio.getOne(id);
    }
    
    
     private void validar (String nombre) throws MiExcepcion{
        
        if(nombre.isEmpty() || nombre ==null){
            throw new MiExcepcion("El nombre de la editorial no puede ser nulo o estar vacío");
        }
    }
}
