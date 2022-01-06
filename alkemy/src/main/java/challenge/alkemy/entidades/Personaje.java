package challenge.alkemy.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "personajes")
public class Personaje {
    
    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    private String id;
    
    @NotEmpty(message = "Digite el nombre del personaje")
    private String nombre;
    
    @NotEmpty(message = "Digite la edad")
    private Integer edad;
    
    @NotEmpty(message = "Digite el peso")
    private Integer peso;
    
    @NotEmpty(message = "Digite la Historia del personaje")
    private String historia;
    
    @JsonIgnoreProperties("personajes")
	@ManyToMany(cascade =  { CascadeType.MERGE,
			CascadeType.REFRESH}, fetch = FetchType.EAGER)
	@JoinTable(name="personaje_pelicula", joinColumns = @JoinColumn(name="personaje_id"), inverseJoinColumns=@JoinColumn(name="pelicula_id"))
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List <Pelicula> peliculas;
    
    @NotEmpty(message = "Se requiere URL de imagen")
    private String imagen;

    public Personaje() {
    }

    public Personaje(String id, String nombre, Integer edad, Integer peso, String historia, List<Pelicula> peliculas, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.peliculas = peliculas;
        this.imagen = imagen;
    }
    
    
    
    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }
    
    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public void eliminarpeliculas() {
		this.peliculas.removeAll(peliculas);
	}
    
}
