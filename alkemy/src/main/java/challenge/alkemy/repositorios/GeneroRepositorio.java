package challenge.alkemy.repositorios;

import challenge.alkemy.entidades.Genero;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneroRepositorio extends CrudRepository<Genero,String> {
    
    @Query("SELECT g FROM Genero AS g WHERE g.id =?1")
    public Genero getGeneroId(String id);
    
}
