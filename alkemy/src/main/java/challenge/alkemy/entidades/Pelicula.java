package challenge.alkemy.entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotEmpty(message = "Digite el titulo de la pelicula")
    private String titulo;

    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date Fecha;
    
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "genero_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Genero genero;

    @Min(1)
    @Max(5)
    private Integer calificacion;

    @NotEmpty(message = "Se necesita URL de imagen")
    private String imagen;

    @JsonIgnoreProperties("peliculas")
    @ManyToMany(mappedBy = "peliculas",cascade = {CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Personaje> personajes;

    public Pelicula() {
    }

    public Pelicula(String id, String titulo, Date Fecha, Genero genero, Integer calificacion, String imagen, List<Personaje> personajes) {
        this.id = id;
        this.titulo = titulo;
        this.Fecha = Fecha;
        this.genero = genero;
        this.calificacion = calificacion;
        this.imagen = imagen;
        this.personajes = personajes;
    }
    
    public void borrarPersonajes() {
		this.personajes.removeAll(personajes);
	}
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return Fecha;
    }

    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(List<Personaje> personajes) {
        this.personajes = personajes;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    
    
    
}
