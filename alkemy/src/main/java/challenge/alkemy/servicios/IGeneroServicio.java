/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.servicios;

import challenge.alkemy.entidades.Genero;
import java.util.List;

/**
 *
 * @author Gonzalo
 */
public interface IGeneroServicio {
    
    public List<Genero> listAllGenero();
	
	public Genero save(Genero genero);
	
	public void deleteGenero(String id);
	
	public Genero getById(String id);
}
