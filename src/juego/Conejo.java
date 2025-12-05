/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Shuyi Qu
 */
//El conejo implementa el comestible
//Se queda en la misma casilla ya que no se mueve
public class Conejo extends Entidad implements Comestible {
    private String nombre;
    //Constructor de Conejo
    public Conejo (String nombre,Casilla casillaActual){
        super(nombre,casillaActual);
        this.nombre=nombre;
    }
    //Metodo mover
    @Override
    public void mover(Casilla casillaDestino){
        //los conejos no se mueven
    }
    //como implementa el comestible utilizará el método de serDevorado
    @Override
    public void serComido(Zombi z){
        //cuando es devorado por el zombi se reduce Una unidad de hambre
    }
    
}
