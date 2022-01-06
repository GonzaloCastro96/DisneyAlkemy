/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import org.springframework.transaction.annotation.Transactional;
import challenge.alkemy.entidades.Personaje;
import challenge.alkemy.errores.ErrorPersonajePorEdad;
import challenge.alkemy.errores.ErrorPersonajePorID;
import challenge.alkemy.errores.ErrorPersonajePorNombre;
import challenge.alkemy.errores.ErrorPersonajes;
import challenge.alkemy.errores.ErrorPesonajePorPelicula;
import challenge.alkemy.guardado.PersonajeGuardado;
import challenge.alkemy.repositorios.PersonajeRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gonzalo
 */
@Service
public class PersonajeServicio implements IPersonajeServicio {

    @Autowired
    private PersonajeRepositorio pr;

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> getAllByMovieId(String id) {
        List<Personaje> personajes = this.pr.personajePorIdPelicula(id);

        if (personajes.isEmpty()) {
            throw new ErrorPesonajePorPelicula("No se encuentra el ID de la Pelicula");
        } else {
            return personajes;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Personaje getCharacterById(String id) {
        return this.pr.findById(id).orElseThrow(() -> new ErrorPersonajePorID(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonajeGuardado> listAllCharacters() {
        List<PersonajeGuardado> listReturn = this.pr.listAll();

        if (listReturn.isEmpty()) {
            throw new ErrorPersonajes(" No se encuentra la lista de personajes");
        } else {
            return listReturn;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Personaje getCharacterByName(String name) {
        Personaje personaje = null;
        personaje = this.pr.personajePorNombre(name);
        if (personaje == null) {
            throw new ErrorPersonajePorNombre("No se encuentra el nombre");
        } else {
            return personaje;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Personaje> getAllCharacterByAge(Integer age) {
        List<Personaje> personajes = this.pr.personajePorEdad(age);

        if (personajes.isEmpty()) {
            throw new ErrorPersonajePorEdad("No hay coincidencia de personajes con esa edad");
        } else {
            return personajes;
        }
    }

    @Override
    public Personaje saveCharacter(Personaje personaje) {
        return this.pr.save(personaje);
    }

    @Override
    public Personaje updateCharacter(String id, Personaje personaje) {
        Optional<Personaje> characterResult = Optional.of(this.pr.getById(id));
        Personaje characterUpdate = null;

        if (characterResult.isPresent()) {
            characterUpdate = characterResult.get();

            characterUpdate.setEdad(personaje.getEdad());
            characterUpdate.setHistoria(personaje.getHistoria());
            characterUpdate.setImagen(personaje.getImagen());
            characterUpdate.setPeliculas(personaje.getPeliculas());
            characterUpdate.setNombre(personaje.getNombre());
            characterUpdate.setPeso(personaje.getPeso());

            characterUpdate = this.saveCharacter(characterUpdate);
        } else {
            throw new ErrorPersonajePorID(id);
        }

        return characterUpdate;
    }

    @Override
    public void deleteCharacter(String id) {
        Optional<Personaje> personaje = this.pr.findById(id);

        if (personaje.isPresent()) {
            Personaje characterDelete = personaje.get();
            characterDelete.eliminarpeliculas();
            this.pr.delete(characterDelete);
        } else {
            throw new ErrorPersonajePorID(id);
        }
    }

}
