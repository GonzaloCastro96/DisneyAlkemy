/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import challenge.alkemy.entidades.Pelicula;
import challenge.alkemy.guardado.PeliculaGuardado;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public interface IPeliculaServicio {
    
    public List<PeliculaGuardado> getAllMovies();

	public List<Pelicula> listMovieOrder(String order);
	
	public Pelicula getMovieByName(String name);
	
	public Pelicula getMovieById(String id);
	
	public Pelicula saveMovie(Pelicula movie);
	
	public void deleteMovie(String id);
	
	public List<Pelicula> getMoviesByIdGender(String id);
	
	public Pelicula updateMovie(String id, Pelicula movie);
}
