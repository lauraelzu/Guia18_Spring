/*
- crear un libro, los datos provendrán de un formulario
-editar los datos de un libro
-habilitar/deshabilitar un libro
-Listar todos los libros existentes
 */
package com.mendoza.biblioteca.servicios;

import com.mendoza.biblioteca.entidades.Autor;
import com.mendoza.biblioteca.entidades.Editorial;
import com.mendoza.biblioteca.entidades.Libro;
import com.mendoza.biblioteca.excepciones.MiExcepcion;
import com.mendoza.biblioteca.repositorios.AutorRepositorio;
import com.mendoza.biblioteca.repositorios.EditorialRepositorio;
import com.mendoza.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service              
public class LibroServicio {
    
    //atributos globales
    @Autowired  //inyección de dependencias, va a ser inicializado por el servidor de aplicaciones
    private LibroRepositorio libroRepositorio; //instanciar para persistir el objeto creado y usar los métodos intrínsecos
    @Autowired
    private AutorRepositorio autorRepositorio; //usar metodo de JpaRepostory "findById"
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    //- crear un libro, los datos provendrán de un formulario
    @Transactional  //establece que es un método que genera modificaciones en la BD
    public void crearLibro (Long isbn,String titulo,Integer ejemplares,String idAutor,String idEditorial) throws MiExcepcion{
       
       validar(isbn,titulo,ejemplares,idAutor,idEditorial);
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date()); //setear con la fecha actual
        
        Autor autor = autorRepositorio.findById(idAutor).get(); //obtener el obj autor desde el repositorio a partir del id
        libro.setAutor(autor);
        
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        libro.setEditorial(editorial);
                
        libroRepositorio.save(libro);  //recibe una entidad y la persiste en la BD
        
    }
    
    //-Listar todos los libros existentes
    public List<Libro> listarLibros(){

        List<Libro> libros = new ArrayList();
        
        libros = libroRepositorio.findAll();
        
        return libros;
    }
    
    //-editar los datos de un libro
    @Transactional
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiExcepcion{

        validar(isbn,titulo,ejemplares,idAutor,idEditorial);
/*     Libro libro = libroRepositorio.findById(isbn).get();  -->daría error si el isbn no existe o está mal escrito

 Java provee clase genérica Optional para manejar la ausencia de valor (null) en el retorno de un método 
y solucionar en forma más sencilla que la captura de excepciones (NullPointerException) */

        //comprobar que exiten
        Optional<Libro> respuestaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();  
        Editorial editorial = new Editorial();
        
        if (respuestaAutor.isPresent()){   //si findById devuelve un valor no nulo (existe el idautor)
            autor = respuestaAutor.get();  //obtener el autor
        }
        
        if (respuestaEditorial.isPresent()){  //si findById devuelve un valor no nulo (existe el ideditorial)
            editorial = respuestaEditorial.get();  //obtener la editorial
        }
        
        if (respuestaLibro.isPresent()){  //si findById devuelve un valor no nulo (existe el isbn)

            Libro libro = respuestaLibro.get();  //obtener el libro
            
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);   
        }
    }
    //*******************************
    public List<Libro> listarPorAutor(String id){
        List<Libro> libros = new ArrayList();
        libros = libroRepositorio.buscarPorAutor(id);
        return libros;
    }
    //*******************************
    
    //******************************* 
    @Transactional
    public void eliminarPorId(Long isbn){
       libroRepositorio.deleteById(isbn);       
    }
    //*******************************
    
    private void validar(Long isbn,String titulo,Integer ejemplares,String idAutor,String idEditorial) throws MiExcepcion{
            
        //validación de datos 
        if(isbn == null){
            throw new MiExcepcion("El ISBN no puede ser nulo");
        }
        if(titulo.isEmpty() || titulo == null){
            throw new MiExcepcion("El título no puede ser nulo o estar vacío");
        }
        if(ejemplares == null){
            throw new MiExcepcion("La cantidad de ejemplares no puede ser nulo");
        }
        if(idAutor.isEmpty() || idAutor == null){
            throw new MiExcepcion("El identificador del autor no puede ser nulo o estar vacío");
        }
        if(idEditorial.isEmpty() || idEditorial == null){
            throw new MiExcepcion("El identificador de la editorial no puede ser nulo o estar vacío");
        }
    }
        
}
