/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public abstract class Ataque {
    protected String nombre;
    protected int potencia; //el número de dados que se lanzan. 
    protected int valorExito;//valor mínimo que tiene que salir en el dado para que se considere un impacto. 
    protected int alcance;//el número de casillas de distancia de la casilla objetivo
    //Constructor
    public Ataque(String nombre,int potencia, int valorExito, int alcance) {
        this.nombre=nombre;
        this.potencia = potencia;
        this.valorExito = valorExito;
        this.alcance = alcance;
    }
    public String getNombre(){
        return nombre;
    }
    public int getPotencia() {
        return potencia; }
    public int getValorExito() { 
        return valorExito; }
    public int getAlcance() {
        return alcance; }

    // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
    // ejecutará el ataque y devolverá los impactos generados
    public abstract int ejecutar(Casilla casillaObjectivo);

}
