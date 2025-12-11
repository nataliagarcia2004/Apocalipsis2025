/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public class Juego {
    ///debe hacer registarr herida para que humano funcione su metodo de:  public void registrarHerida(Zombi z) { 
    // natalia: lo he metido para que combatiente funcione
    private Tablero tablero;

    public Juego(int numeroZombis) {
        this.tablero = new Tablero(numeroZombis);
    }

    public Tablero getTablero() {
        return tablero;
    }
   
}
