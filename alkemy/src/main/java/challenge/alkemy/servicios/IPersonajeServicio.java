/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import challenge.alkemy.entidades.Personaje;
import challenge.alkemy.guardado.PersonajeGuardado;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public interface IPersonajeServicio {
    
        public List<PersonajeGuardado> listAllCharacters();
	
	public List<Personaje> getAllByMovieId(String id);
	
	public List<Personaje> getAllCharacterByAge(Integer age);
	
	public Personaje getCharacterById(String id);
	
	public Personaje getCharacterByName(String name);
	
	public Personaje saveCharacter(Personaje personaje);
	
	public Personaje updateCharacter(String id, Personaje personaje );
	
	public void deleteCharacter(String id);
}
