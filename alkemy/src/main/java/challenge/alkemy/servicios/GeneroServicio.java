/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import challenge.alkemy.entidades.Genero;
import challenge.alkemy.repositorios.GeneroRepositorio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Gonzalo
 */
public class GeneroServicio implements IGeneroServicio{
    
    @Autowired
	private GeneroRepositorio gr;
	
        @Override
	public List<Genero> listAllGenero() {
		return (List<Genero>)this.gr.findAll();
	}
        
        @Override
	public Genero save(Genero genero) {
		return this.gr.save(genero);
	}
        
        @Override
	public void deleteGenero(String id) {
		this.gr.deleteById(id);
	}

	@Override
	public Genero getById(String id) {
		return this.gr.getGeneroId(id);
	}
}
