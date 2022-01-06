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
public class ErrorPesonajePorPelicula extends RuntimeException {
    
    public ErrorPesonajePorPelicula(String message){
		super(message);
    }
}
