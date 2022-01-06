package challenge.alkemy.repositorios;

import challenge.alkemy.entidades.Personaje;
import challenge.alkemy.guardado.PersonajeGuardado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface PersonajeRepositorio extends JpaRepository<Personaje,String>{
    
   @Query(value="SELECT nombre, imagen FROM personajes",nativeQuery = true)
	public List<PersonajeGuardado> listAll();

	@Query("SELECT c FROM personaje AS c WHERE c.nombre=?1")
	public Personaje personajePorNombre(String nombre);
	
	@Query("SELECT c FROM personaje AS c WHERE c.edad =?1")
	public List<Personaje> personajePorEdad(Integer edad);
	
	@Query(value="SELECT * FROM personaje INNER JOIN personaje_pelicula ON personaje.id= personaje_id where pelicula_id =?1",nativeQuery = true)
	public List<Personaje> personajePorIdPelicula(String id);
    
}
