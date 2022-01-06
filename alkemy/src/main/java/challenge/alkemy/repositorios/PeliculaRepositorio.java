/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.repositorios;

import challenge.alkemy.entidades.Pelicula;
import challenge.alkemy.guardado.PeliculaGuardado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author Gonzalo
 */
public interface PeliculaRepositorio extends JpaRepository<Pelicula, String> {

    @Query("SELECT m FROM pelicula m WHERE m.titulo =?1")
    public Pelicula getpeliculaPorNombre(String nombre);

    @Query("SELECT m FROM pelicula m WHERE m.id =?1")
    public Pelicula getPeliculaPorId(String id);

    @Query("SELECT m FROM pelicula m ORDER BY m.fecha ASC")
    public List<Pelicula> listPeliculasASC();

    @Query("SELECT m FROM pelicula m ORDER BY m.fecha DESC")
    public List<Pelicula> listPeliculasDESC();

    @Query(value = "SELECT fecha, titulo, imagen FROM peliculas", nativeQuery = true)
    public List<PeliculaGuardado> getPeliculas();

    @Query("SELECT m FROM pelicula m WHERE m.genero.id =?1")
    public List<Pelicula> getPeliculasPorIdGenero(String id);
}
