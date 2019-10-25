package ar.com.ada.api.billeteravirtual.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.billeteravirtual.entities.Usuario;
import ar.com.ada.api.billeteravirtual.services.UsuarioService;

/**
 * UsuarioController
 */
@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios()
    {
        List<Usuario> us = usuarioService.getUsuarios();
        
        return us;
    }
    @GetMapping("/usuarios/{id}")
    public Usuario getUsuarioById (@PathVariable int id) {
        Usuario u = usuarioService.buscarPorId(id);
        
        return u;
    }

    
}