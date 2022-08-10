package com.mendoza.biblioteca.repositorios;

import com.mendoza.biblioteca.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LibroRepositorio extends JpaRepository<Libro, Long>{   //interface que hereda de la clase JpaRepository todos sus métodos
    
    @Query("SELECT l FROM Libro l WHERE l.titulo = :titulo")
    public Libro buscarPorTitulo (@Param("titulo") String titulo);
    
    @Query("SELECT l FROM Libro l WHERE l.autor.id = :id")
    public List<Libro> buscarPorAutor (@Param("id") String id);
}
