/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.errores;

/**
 *
 * @author Gonzalo
 */
public class ErrorOrdenPelicula extends RuntimeException{
    
    public ErrorOrdenPelicula(String message) {
		super(message);
	}
}
