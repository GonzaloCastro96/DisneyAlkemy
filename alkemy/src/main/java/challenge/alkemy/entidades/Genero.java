package challenge.alkemy.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "generos")
public class Genero {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    
    @NotEmpty(message= "Digite un nombre")
    private String nombre;
    
    @OneToOne
    @NotEmpty(message = "Se requiere URL de la imagen")
    private String imagen;
    
    @JsonBackReference
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "generos")
    private List<Pelicula> peliculas;

    public Genero() {
    }

    public Genero(String id, String nombre, String imagen, List<Pelicula> peliculas) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.peliculas = peliculas;
    }
    
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
        
}
