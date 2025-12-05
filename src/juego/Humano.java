/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 * 
 */
public abstract class Humano extends Entidad implements Comestible  {
    protected int aguante;        // Resistencia del humano
    protected int activaciones;   // Número de acciones que hace por turno
    
    // Indica si el humano ya está eliminado del juego
    protected boolean eliminado = false;
   
    public Humano(String nombre, Casilla casillaInicial, int aguante, int activaciones) {
        super(nombre, casillaInicial);
        this.aguante = aguante;
        this.activaciones = activaciones;
    }
    
    public int getAguante() {
        return aguante;
    }

    public int getActivaciones() {
        return activaciones;
    }
    
    public boolean estaEliminado() {
        return eliminado;
    }
    
    //Cada tipo de humano tendrá una manera diferente de moverse, s implementare en los subtipos
    @Override
    public abstract void mover(Casilla casillaDestino);

    ///Cada subtipo define su comportamiento al activarse, se implementara en los subtipos
    public abstract void activar();
    
 
    
    //metodos comunes
    public void recibirAtaque(int impactos) {
        if (impactos >= aguante) {
            eliminado = true;   //El humano queda fuera del juego
            aguante = 0;        //Ya no tiene resistencia
        }
    }
    
    public void registrarHerida(Zombi z) {
            //cunaod haya sistema de registro
    }
    
    @Override
    public void serComido(Zombi z) {
        eliminado = true;
        aguante = 0;
    }
    
    
    
}
