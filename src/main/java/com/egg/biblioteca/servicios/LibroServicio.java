/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alejandro Birolo
 */
@Service
public class LibroServicio {
    
    // inyeccion de dependencias
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Autowired
    private AutorRepositorio autorRepositorio;
    
    @Autowired
    private EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
         
        validar(isbn, titulo, idAutor, idEditorial, ejemplares);
       
        Autor autor = autorRepositorio.findById(idAutor).get();
        Editorial editorial = editorialRepositorio.findById(idEditorial).get();
        
        Libro libro = new Libro();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        System.out.println(libro);
        
        libroRepositorio.save(libro);
        
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros = new ArrayList();
        
        libros = libroRepositorio.findAll();
        
        return libros;
    }
    
    @Transactional
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        
        validar(isbn, titulo, idAutor, idEditorial, ejemplares);
        
        /// por si nos llega un id mal
        Optional<Libro> rtaLibro = libroRepositorio.findById(isbn);
        Optional<Autor> rtaAutor = autorRepositorio.findById(idAutor);
        Optional<Editorial> rtaEditorial = editorialRepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (rtaAutor.isPresent()){
            autor = rtaAutor.get();
        }
        
        if (rtaEditorial.isPresent()){
            editorial = rtaEditorial.get();
        }
        
        if (rtaLibro.isPresent()){
            Libro libro = rtaLibro.get();
            
            libro.setTitulo(titulo);
            
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            
            libro.setEjemplares(ejemplares);
            
            libroRepositorio.save(libro);
        }
    }
    
    private void validar(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        
        if (isbn == null){
            throw new MiException("El ISBN no puede ser nulo");
        }
        
        if (titulo.isEmpty() || titulo == null){
            throw new MiException("El Titulo no puede ser nulo o estar vacio");
        }
        
        if (ejemplares == null  ){
            throw new MiException("Los ejemplares no puede ser nulo");
        }
        
        if (idAutor.isEmpty() || idAutor == null){
            throw new MiException("El id Autor no puede ser nulo o estar vacio");
        }
        
        if (idEditorial.isEmpty() ||idEditorial == null){
            throw new MiException("El id Editorial no puede ser nulo o estar vacio");
        }
    }
}
