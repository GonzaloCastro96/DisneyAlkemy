/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.repositorios;

import challenge.alkemy.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Gonzalo
 */
public interface UsuarioRepositorio extends CrudRepository<Usuario, String> {

    public Usuario findByUsername(String username);

    public Usuario findByUsernameOrEmail(String nombre, String mail);

}
