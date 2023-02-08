package es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.services;

import es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.entities.Usuario;
import es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public Iterable<Usuario> findAll(){
        return this.repository.findAll();
    }

    public Usuario save(Usuario usuario){
        return this.repository.save(usuario);
    }

    public void deleteById(int idUsuario){
        this.repository.deleteById(idUsuario);
    }

    public Iterable<Usuario> findAllByApellidosOrNombre(String search){
        search = String.format("%%%s%%", search);
        return this.repository.findAllByApellidosLikeIgnoreCaseOrNombreLikeIgnoreCase (search, search);
    }

}
