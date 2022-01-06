/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.controladores;

import challenge.alkemy.entidades.Personaje;
import challenge.alkemy.errores.ErrorPersonajeNoCreado;
import challenge.alkemy.guardado.PersonajeGuardado;
import challenge.alkemy.servicios.IPersonajeServicio;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Gonzalo
 */
@RestController
@RequestMapping(value = "/personajes")
public class PersonajesControlador {
    
    @Autowired
	private IPersonajeServicio ps;
	private Map<String, Object> response = new HashMap<>();

	@GetMapping
	@ApiOperation(value="Todos los Personajes")
	public ResponseEntity<?> getAllMovie() {
		List<PersonajeGuardado> characters = this.ps.listAllCharacters();
		return new ResponseEntity<>(characters, HttpStatus.OK);
	}

	@ApiOperation(value="Filtrar personaje por nombre")
	@RequestMapping(value = "", params = "nombre", method = RequestMethod.GET)
	public ResponseEntity<?> getCharacterByName(@RequestParam(name = "nombre") String name) {

		Personaje character = Optional.of(this.ps.getCharacterByName(name))
				.orElseThrow( () -> new NumberFormatException());
		
		return new ResponseEntity<>(character, HttpStatus.OK);
	}
	

	@ApiOperation(value="Detalles de personajes por id")
	@RequestMapping(value = "", params = "id", method = RequestMethod.GET)
	public ResponseEntity<?> getCharacterById(@RequestParam(name = "id") String id) {
		
		Personaje character = Optional.of(this.ps.getCharacterById(id))
				.orElseThrow( () -> new NumberFormatException());		
		
		return new ResponseEntity<>(character, HttpStatus.OK);
	}

	@ApiOperation(value="Filtrar personaje por edad")
	@RequestMapping(value = "", params = "age", method = RequestMethod.GET)
	public ResponseEntity<?> getCharactersByAge(@RequestParam(name = "age") Integer age) {
		List<Personaje> characters = null;
		
		characters = Optional.of(this.ps.getAllCharacterByAge(age))
				.orElseThrow( () -> new NumberFormatException());
		
		return new ResponseEntity<>(characters, HttpStatus.OK);
	}

	@ApiOperation(value="Lista de personajes por ID de peliculas")
	@RequestMapping(value = "", params = "movies", method = RequestMethod.GET)
	public ResponseEntity<?> listCharacterByIdMovie(@RequestParam(name = "movies") String movies) {
		
		List<Personaje> characters = Optional.of(this.ps
				.getAllByMovieId(movies))
			    .orElseThrow( () -> new NumberFormatException());

		return new ResponseEntity<>(characters, HttpStatus.OK);
	}
	

	@ApiOperation(value="Guardar Personajes")
	@PostMapping("/save")
	public ResponseEntity<?> saveCharacter(@Valid @RequestBody Personaje character) {

		Personaje characterNew = null;

			characterNew = Optional.of( this.ps.saveCharacter(character) )
					.orElseThrow( () -> new ErrorPersonajeNoCreado("#ERROR: El personaje no fue creado"));
			
		response.put("character", characterNew);
		response.put("message", "Los personajes se guardaron en la base de datos " + characterNew.getNombre());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value="Update Personaje por id")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateCharacter(@Valid @RequestBody Personaje character,
			@PathVariable String id) {
		
		Personaje characterResult = Optional.of(this.ps.updateCharacter(id,character))
				.orElseThrow( () -> new NumberFormatException());

		response.put("message", "El personaje se actualizo con exito");
		response.put("character",characterResult);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@ApiOperation(value="Eliminar personaje por id")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteCharacterById(@PathVariable String id) throws ErrorPersonajeNoCreado{
            if (Optional.of(this.ps.getCharacterById(id)).isPresent()){
                this.ps.deleteCharacter(id);
            }else
		response.put("message", "Personaje eliminado por id: " + id);		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
