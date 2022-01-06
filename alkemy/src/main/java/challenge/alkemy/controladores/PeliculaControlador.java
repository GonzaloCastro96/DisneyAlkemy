/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.controladores;

import challenge.alkemy.entidades.Pelicula;
import challenge.alkemy.errores.ErrorServicio;
import challenge.alkemy.guardado.PeliculaGuardado;
import challenge.alkemy.servicios.IPeliculaServicio;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
@RequestMapping(value = "/peliculas")
public class PeliculaControlador {
    @Autowired
	private IPeliculaServicio ps;
	Map<String,Object> respuesta = new HashMap<>();

	@ApiOperation(value="Todas las Peliculas")
	@GetMapping
	public ResponseEntity<List<PeliculaGuardado>> getAllMovies() {
		List<PeliculaGuardado> movies = this.ps.getAllMovies();
		return new ResponseEntity<List<PeliculaGuardado>>(movies, HttpStatus.OK);
	}
	@ApiOperation(value="Filtrar Pelicula por Nombre")
	@RequestMapping(value="",params = "name",method = RequestMethod.GET)
	public ResponseEntity<?> getMovieByName(@RequestParam(name="name") String name){
		Pelicula  movie = this.ps.getMovieByName(name);	
			return new ResponseEntity<>(movie,HttpStatus.OK);
		}
			
	@ApiOperation(value="Filtrar Pelicula por Genero")
	@RequestMapping(value="",params ="genero",method = RequestMethod.GET)
	public ResponseEntity<?> getMovieByGenderId(@RequestParam(name="genero") String genero) {
		
		List<Pelicula> movies = Optional.of(this.ps.getMoviesByIdGender(genero))
					.orElseThrow(()-> new NumberFormatException());
	
		return new ResponseEntity<>(movies, HttpStatus.NOT_FOUND);	
	}
	
	@ApiOperation(value="Peliculas en orden 'asc' o 'desc'")
	@RequestMapping(value="" , params="orden", method=RequestMethod.GET)
	public ResponseEntity<?> getAllMoviesOrder(@RequestParam(name="orden") String orden) throws DataAccessException {
		List<Pelicula> movies = this.ps.listMovieOrder(orden);
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
	@ApiOperation(value="Detalles de pelicula por ID")
	@GetMapping("/details/{id}")
	public ResponseEntity<?> getMovieById(@PathVariable String id){	
		
		Pelicula movie = Optional.of(this.ps.getMovieById(id))
					.orElseThrow(()-> new NumberFormatException());
		
		return new ResponseEntity<>(movie,HttpStatus.OK);
	}

	@ApiOperation(value="GuardarPelicula")
	@PostMapping("/save")
	public ResponseEntity<?> saveMovie(@Valid @RequestBody Pelicula movie){
		
		Pelicula movieNew = null;	
			
		try {
			movieNew =	this.ps.saveMovie(movie);
		}catch(DataAccessException e) {
			respuesta.put("message", "#ERROR: PELICULA NO CREADA");
			respuesta.put("error", e.getLocalizedMessage());
			return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.INTERNAL_SERVER_ERROR);
		}

			respuesta.put("message","La Pelicula se guardo con exito " + movie.getTitulo());
			respuesta.put("movie", movieNew);
		return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Update pelicula por ID")
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateMovie(@PathVariable String id,@Valid @RequestBody Pelicula movie){
		Pelicula movieResult = null;
				
		try {
			movieResult = Optional.of(this.ps.updateMovie(id, movie))
					.orElseThrow( ()-> new NumberFormatException() );	
		}catch (DataAccessException e) {
			respuesta.put("mesagge", "La pelicula no se actualizo");
			respuesta.put("error", e.getLocalizedMessage());
			return new ResponseEntity<Map<String,Object>>(respuesta,HttpStatus.INTERNAL_SERVER_ERROR);
		}
			respuesta.put("message", "Se actulizo la pelicula con exito");
			respuesta.put("movie", movieResult);		
		return new ResponseEntity<>(respuesta,HttpStatus.CREATED);
	}
	
	@ApiOperation(value="Eliminar pelicula por id")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delMovieById(@PathVariable String id) throws ErrorServicio{
            if (Optional.of(this.ps.getMovieById(id)).isPresent()){
                this.ps.deleteMovie(id);
                respuesta.put("message", "La pelicula se elimino con exito");
            }else{
                throw new ErrorServicio("La pelicula no se encontro");
            }				
		return new ResponseEntity<>(respuesta,HttpStatus.OK);
	}
}
