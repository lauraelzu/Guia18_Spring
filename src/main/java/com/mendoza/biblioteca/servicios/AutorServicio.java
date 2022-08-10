package com.mendoza.biblioteca.servicios;

import com.mendoza.biblioteca.entidades.Autor;
import com.mendoza.biblioteca.excepciones.MiExcepcion;
import com.mendoza.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MiExcepcion{

        validar(nombre);
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorRepositorio.save(autor);
    }
    
    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList();
        autores = autorRepositorio.findAll();
        return autores;
    }
    
    @Transactional
    public void modificarAutor(String nombre, String idAutor) throws MiExcepcion{

        validar(nombre);
        
        Optional<Autor> respuesta = autorRepositorio.findById(idAutor);
        
        if(respuesta.isPresent()){
            Autor autor = respuesta.get();
            autor.setNombre(nombre);
            autorRepositorio.save(autor);
        }
        
    }
    
    public Autor obtenerAutor(String id){
        return autorRepositorio.getOne(id);
    }
    
    private void validar (String nombre) throws MiExcepcion{
        
        if(nombre.isEmpty() || nombre ==null){
            throw new MiExcepcion("El nombre de autor no puede ser nulo o estar vacío");
        }
    }
}
