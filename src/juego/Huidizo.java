/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public class Huidizo extends Humano {

    public Huidizo(String nombre, Casilla casillaInicial) {
        super(nombre, casillaInicial, 1, 1); // aguante = 1, activaciones = 1
    }

    @Override
    public void activar(Juego juego) {
        if (eliminado) return;

        Casilla salida = juego.getTablero().getCasillaSalida();

        // 1- si ya está en la salida abandona el tablero
        if (casillaActual.equals(salida)) {
            System.out.println(nombre + " llega a la salida y se escapa del juego.");
            casillaActual.eliminarEntidad(this);
            eliminado = true;
            return;
        }

        //2- Si NO está en la salida moverse hacia ella
        Casilla siguiente =
                juego.getTablero().calcularSiguienteCasillaHaciaSalida(casillaActual);

        mover(siguiente);
        System.out.println(nombre + " (Huidizo) huye hacia la salida.");
    }

    @Override
    public void mover(Casilla destino) {
        if (destino == null) return;

        casillaActual.eliminarEntidad(this);
        destino.agregarEntidad(this);
        casillaActual = destino;
    }

    
    
    @Override
    public void serComido(Zombi z) {
        eliminado = true;
        aguante = 0;

        casillaActual.eliminarEntidad(this);

        //registrar en el zombi
        z.registrarComestible(this);
        z.registrarHumanoEliminado(this);

        //todos los humanos (excepto ingeniero) reducen el hambre a 0
        z.setHambre(0);

        //El Huidizo añade una acción extra permanente al zombi
        z.incrementarAcciones();

        System.out.println(nombre + 
            " (Huidizo) ha sido comido por " + z.getNombre() + 
            ". Hambre puesta a 0 y el zombi gana +1 acción permanente.");
    }

    
    
    
    
}
