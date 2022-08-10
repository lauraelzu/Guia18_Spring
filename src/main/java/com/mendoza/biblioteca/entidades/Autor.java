package com.mendoza.biblioteca.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Autor {
    
    @Id
    @GeneratedValue(generator = "uuid") //el valor de id s genera en forma automática al momento en q el repositorio persista la entidad
    @GenericGenerator(name = "uuid", strategy = "uuid2") //como estrategia alternativa genere un uuid2. para asegura que no se repitan las id generando una cadena alfanumérica única
    private String id;
    private String nombre;

    public Autor() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
}
