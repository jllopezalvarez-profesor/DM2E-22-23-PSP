package es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.repositories;


import es.iesclaradelrey.dm2e2223.jllopez.apirestdemo.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
    //Iterable<Usuario> findAllByApellidosLikeIgnoreCase(String search);

    Iterable<Usuario> findAllByApellidosLikeIgnoreCaseOrNombreLikeIgnoreCase (String apellido, String nombre);
}
