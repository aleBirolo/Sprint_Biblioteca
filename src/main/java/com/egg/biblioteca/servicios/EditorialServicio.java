/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
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
public class EditorialServicio {
    
    @Autowired
    EditorialRepositorio editorialRepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
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
    public void modificarEditorial( String idEditorial, String nombre) throws MiException{
        
        Optional<Editorial> rtaEditorial = editorialRepositorio.findById(idEditorial);
        
        if (rtaEditorial.isPresent()){
            Editorial editorial = rtaEditorial.get();
            
            editorial.setNombre(nombre);
            
            editorialRepositorio.save(editorial);
        }
    }
    
    private void validar(String idEditorial, String nombre ) throws MiException{
        
        if (idEditorial.isEmpty() || idEditorial==null){
            throw new MiException("El id Editorial de la editorial no puede ser nulo o estar vacio");
        }
        
        if (nombre.isEmpty() || nombre==null){
            throw new MiException("El nombre de la editorial  no puede ser nulo o estar vacio");
        }
    }
}
