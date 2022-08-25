/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Alejandro Birolo
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
    @Autowired
    private LibroServicio libroServicio;
    
    @Autowired
    private AutorServicio autorServicio;
    
    @Autowired
    private EditorialServicio editorialServicio;
    
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo){
        
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        
        modelo.addAttribute("autores", autores);
        modelo.addAttribute("editoriales", editoriales);
        return "libro_form.html";
    }
    
    @PostMapping("/registro")
    public String registro (@RequestParam(required = false) Long isbn,
                            @RequestParam String titulo,
                            @RequestParam(required = false) Integer ejemplares,
                            @RequestParam String idAutor,
                            @RequestParam String idEditorial,
                            ModelMap modelo){
        
        try {
            
            System.out.println("ANTES");
            libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);
            System.out.println("DESPUES");
            modelo.put("exito", "El libro fue cargado correctamente!");
        } catch (MiException ex) {
            System.out.println("HUBO UNA EXCEPCION");
            modelo.put("error", ex.getMessage());
            return "libro_form.html";
        }
        System.out.println("FINAL");
        return "index.html";
        
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        
        List <Libro> libros = libroServicio.listarLibros();
        
        modelo.addAttribute("libros", libros);
        return "libro_list.html";
    }
}
