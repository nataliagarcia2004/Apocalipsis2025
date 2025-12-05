/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public class AtaqueNormal extends Ataque {
     public AtaqueNormal() {
        super("Devorar",1, 4, 0); 
        // POTENCIA 1, ÉXITO 4+, ALCANCE 0
        // Los valores finales podrían ajustarse después
    }

    @Override
    public int ejecutar(Casilla casillaDestino) {
        // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
        // hacer tirada de dado y devolver impactos si acierta
        return potencia;
    }

}
