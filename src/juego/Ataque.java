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
 protected int potencia;
    protected int valorExito;
    protected int alcance;

    public Ataque(int potencia, int valorExito, int alcance) {
        this.potencia = potencia;
        this.valorExito = valorExito;
        this.alcance = alcance;
    }

    public int getPotencia() { return potencia; }
    public int getValorExito() { return valorExito; }
    public int getAlcance() { return alcance; }

    // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
    // ejecutará el ataque y devolverá los impactos generados
    public abstract int ejecutarAtaque();

}
