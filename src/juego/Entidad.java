/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public abstract class Entidad {
    protected String nombre; //'protected' permite que las subclases lo usen directamente.
    protected Casilla casillaActual;
    
    //Constructor de Entidad,ya que es superclase,sus atributos serán heredados a sus subclases,por lo tanto necesito crearlo este cosntructor antes
    public Entidad(String nombre,Casilla casillaActual){
        this.nombre=nombre;
        this.casillaActual=casillaActual;
    }
    
    ////getter y setters
    public String getNombre() {
        return nombre;
    }

    public Casilla getCasillaActual() {
        return casillaActual;
    }

    public void setCasillaActual(Casilla c) {
        this.casillaActual = c;
    }
    
    //Metodo abstract, no implementacion, mover que será utilizado por sus subclases donde ahis e implementará
    public abstract void mover(Casilla casillaDestino);
}
