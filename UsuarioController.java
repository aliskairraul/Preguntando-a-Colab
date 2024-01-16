package com.example.atl.controllers;

// import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;

import com.example.atl.dao.UsuarioDao;
import com.example.atl.models.Usuario;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    // @RequestMapping(value = "/usuario/{id}")
    @GetMapping("/usuario/{id}")
    public Usuario getUsuario(@PathVariable Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setApellido("Rodriguez");
        usuario.setEmail("alis@palante.com");
        usuario.setNombre("Aliskair");
        usuario.setPassword("facilito");
        usuario.setTelefono("5555555");
        return usuario;
    }

    // @RequestMapping(value = "/usuarios", method = RequestMethod.GET)
    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return usuarioDao.getUsuarios();
    }

    // @RequestMapping(value = "/usuarioshtml", method = RequestMethod.GET)
    @GetMapping("/usuario-html")
    public String getUsuaString(Model model) {
        Iterable<Usuario> usuarios = usuarioDao.getUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "response";
    }

    // @RequestMapping("/usuario")
    // public Usuario eliminar() {
    // Usuario usuario = new Usuario();
    // usuario.setApellido("Rodriguez");
    // usuario.setEmail("alis@palante.com");
    // usuario.setNombre("Aliskair");
    // usuario.setPassword("facilito");
    // usuario.setTelefono("5555555");
    // return usuario;
    // }

    // @RequestMapping("/usuario")
    // public Usuario buscar() {
    // Usuario usuario = new Usuario();
    // usuario.setApellido("Rodriguez");
    // usuario.setEmail("alis@palante.com");
    // usuario.setNombre("Aliskair");
    // usuario.setPassword("facilito");
    // usuario.setTelefono("5555555");
    // return usuario;
    // }

}
