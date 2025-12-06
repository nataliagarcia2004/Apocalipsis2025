/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public class AtaqueEspecial extends Ataque{
    public AtaqueEspecial(String nombre,int potencia, int valorExito, int alcance) {
        super(nombre,potencia, valorExito, alcance);
        // LA CONFIGURACIÓN FINAL VIENE DEL IMPORTADOR
    }

    @Override
    public int ejecutar(Casilla casillaDestino) {
        // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
        // aplicar reglas especiales de impacto y prioridades
        return potencia;
    }
}
