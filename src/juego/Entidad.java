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
    private String nombre;
    private Casilla casillaActual;
    //Constructor de Entidad,ya que es superclase,sus atributos serán heredados a sus subclases,por lo tanto necesito crearlo este cosntructor antes.
    public Entidad(String nombre,Casilla casillaActual){
        this.nombre=nombre;
        this.casillaActual=casillaActual;
    }
    //Metodo abstract mover que será utilizado por sus subclases
   public abstract void mover(Casilla casillaDestino);
}
