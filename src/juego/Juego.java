/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
import java.util.List;
     
public class Juego {
    private List<Humano> humanos;
    private List<Conejo> conejos;
    private int turnoActual;
    private boolean juegoTerminado;
    private int contadorHumanos;  // Para generar nombres únicos
    private int contadorConejos;  // Para generar nombres únicos
    ///debe hacer registarr herida para que humano funcione su metodo de:  public void registrarHerida(Zombi z) { 
    // natalia: lo he metido para que combatiente funcione
    private Tablero tablero;

    public Juego(int numeroZombis) {
        this.tablero = new Tablero(numeroZombis);
    }

    public Tablero getTablero() {
        return tablero;
    }
   //Buscar comida aleatoria para que pueda implementar el metodo buscar comida en zombi
  public void generarComidaAleatoria() {
        double prob = Math.random();
        
        if (prob < 0.3) {
            // 30% Humano Huidizo
            Casilla casillaAleatoria = tablero.obtenerCasillaAleatoria();
            String nombre = "Huidizo" + contadorHumanos++;
            Humano huidizo = new Huidizo(nombre, casillaAleatoria);
            humanos.add(huidizo);
            System.out.println("  ¡Aparece Humano Huidizo en (" + 
                             casillaAleatoria.getCoordenadaX() + ", " + 
                             casillaAleatoria.getCoordenadaY() + ")!");
            
        } else if (prob < 0.8) {
            // 50% Conejo
            Casilla casillaAleatoria = tablero.obtenerCasillaAleatoria();
            String nombre = "Conejo" + contadorConejos++;
            Conejo conejo = new Conejo(nombre, casillaAleatoria);
            conejos.add(conejo);
            System.out.println("  ¡Aparece Conejo en (" + 
                             casillaAleatoria.getCoordenadaX() + ", " + 
                             casillaAleatoria.getCoordenadaY() + ")!");
            
        } else {
            // 20% Nada
            System.out.println("  No aparece nada");
        }
    }
}
