/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenge.alkemy.entidades;

/**
 *
 * @author Gonzalo
 */
public class AuthRequest {

    public AuthRequest() {
    }

    public AuthRequest(String nombre, String clave) {
        this.nombre = nombre;
        this.clave = clave;
    }
    
        
    private String nombre;
    private String clave;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    
}
