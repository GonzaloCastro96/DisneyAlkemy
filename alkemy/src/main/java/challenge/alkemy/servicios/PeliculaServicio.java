/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import challenge.alkemy.entidades.Pelicula;
import challenge.alkemy.errores.ErrorOrdenPelicula;
import challenge.alkemy.errores.ErrorPeliculaIDGenero;
import challenge.alkemy.errores.ErrorPeliculaId;
import challenge.alkemy.errores.ErrorPeliculaPorNombre;
import challenge.alkemy.errores.ErroresDeListaDePeliculas;
import challenge.alkemy.guardado.PeliculaGuardado;
import challenge.alkemy.repositorios.PeliculaRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gonzalo
 */
@Service
public class PeliculaServicio {
 
    @Autowired
	private PeliculaRepositorio pr;

	@Transactional
	public List<PeliculaGuardado> getAllMovies() {
		
		List<PeliculaGuardado> list = pr.getPeliculas();
		
		if(list.isEmpty()) {			
			throw new ErroresDeListaDePeliculas("No se encuentra la lista");
		}else {
			return list;
		}
		
	}

	
	@Transactional
	public List<Pelicula> ListaPeliculasOrden(String order) {
		List<Pelicula> peliculas = null;
		
		if (order.equalsIgnoreCase("asc")) {
			peliculas = this.pr.listPeliculasASC();
			
			
		} else if (order.equalsIgnoreCase("desc")) {
			 peliculas = this.pr.listPeliculasASC();
		}else {
			throw new ErrorOrdenPelicula("Orden invalido");
		}
		
		if(peliculas.isEmpty()) {
			throw new ErroresDeListaDePeliculas("No se encuentra la lista");
		}
		
		return peliculas;
	}

	
	@Transactional
        public Pelicula getPeliculaPorNombre(String name) {
		
		Pelicula pelicula = this.pr.getpeliculaPorNombre(name); 
	
		if(pelicula == null) {
			throw new ErrorPeliculaPorNombre("Pelicula no encontrada por el nombre");
		}else {
			return pelicula;
		}
	}

	
	@Transactional
    
	public Pelicula getPeliculaPorId(String id) {
            Optional<Pelicula> respuesta= pr.findById(id);
            Pelicula p;
            if (respuesta== null){
                throw new ErrorPeliculaId("No se encuentra la pelicula con el id: " + id);
            }else{
                p=respuesta.get();
            }
            return p;
        }
	
	@Transactional
	public Pelicula GuardarPelicula(Pelicula pelicula) {
		return this.pr.save(pelicula);
	}


	
	@Transactional
	public List<Pelicula> getPeliculasPorIdGenero(String id) {
		List<Pelicula> movies = this.pr.getPeliculasPorIdGenero(id);		
		if(movies.isEmpty()) {
			throw new ErrorPeliculaIDGenero("No se encuentra el ID del Genero");
		}else {
			return movies;
		}	 
	}

	
	@Transactional
	public void borrarPelicula(String id) {
		
		// CREATE EXCEPTION NOT MOVIE ID	
		Pelicula peliResultado = this.pr.getPeliculaPorId(id);
		
		if(peliResultado != null) {
			peliResultado.borrarPersonajes();
			this.pr.delete(peliResultado);
		}else {
			throw new ErrorPeliculaId("Not found id movie deleted");
		}
	}

	
	@Transactional
	public Pelicula updatepelicula(String id, Pelicula pelicula) {
		
		Optional<Pelicula> movieResult = Optional.of(this.pr.getPeliculaPorId(id));
		Pelicula peliculaUpdate = null;
		
		if(movieResult.isPresent()) {
			peliculaUpdate = movieResult.get();
			peliculaUpdate.setPersonajes(pelicula.getPersonajes());
			peliculaUpdate.setFecha(pelicula.getFecha());
			peliculaUpdate.setGenero(pelicula.getGenero());
			peliculaUpdate.setImagen(pelicula.getImagen());
			peliculaUpdate.setCalificacion(pelicula.getCalificacion());
			peliculaUpdate.setTitulo(pelicula.getTitulo());
			
			peliculaUpdate = this.pr.save(peliculaUpdate);
		}else {
			throw new ErrorPeliculaId("Not found movie id");
		}	
		
		return peliculaUpdate;
	}
}
