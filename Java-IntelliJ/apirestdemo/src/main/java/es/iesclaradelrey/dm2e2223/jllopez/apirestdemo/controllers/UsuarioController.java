package es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.controllers;

import es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.entities.Usuario;
import es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public Iterable<Usuario> findAll() {
        return this.service.findAll();
    }

    @PostMapping
    public Usuario save(@RequestBody Usuario usuario) {
        return this.service.save(usuario);
    }

    @DeleteMapping
    @RequestMapping("/{id}")
    public void deleteById(@PathVariable int id){
        this.service.deleteById(id);
    }

    @GetMapping
    @RequestMapping("/search/apellidos/{search}")
    public Iterable<Usuario> findAllByApellidosLikeIgnoreCase(@PathVariable String search){
        return this.service.findAllByApellidosOrNombre(search);
    }

}
