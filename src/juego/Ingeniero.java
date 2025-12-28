/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Natalia Garcia
 */
public class Ingeniero extends Humano{
    private static final Random rand = new Random();

    public Ingeniero(String nombre, Casilla casillaInicial) {
        super(nombre, casillaInicial, 3, 1);  // aguante=3, activaciones=1
    }
    
    @Override
    public void activar(Juego juego) {
        if (eliminado) return;

        //1- si hay zombis en su casilla: 2 heridas
        List<Zombi> zombisAqui = casillaActual.getZombis();
        if (!zombisAqui.isEmpty()) {
            Zombi objetivo = zombisAqui.get(rand.nextInt(zombisAqui.size()));
            objetivo.recibirHerida(2);

            System.out.println(nombre + " (Ingeniero) inflige 2 heridas a " + objetivo.getNombre());
            return;
        }

        //2- si hay zombis a distancia 1: 1 herida
        List<Casilla> adyacentes = juego.getTablero().obtenerCasillasAdyacentes(
                casillaActual.getCoordenadaX(),
                casillaActual.getCoordenadaY()
        );

        Zombi zombiDist1 = buscarZombiEnCasillas(adyacentes);

        if (zombiDist1 != null) {
            zombiDist1.recibirHerida(1);
            System.out.println(nombre + " (Ingeniero) ataca a distancia infligiendo 1 herida a " + zombiDist1.getNombre());
            return;
        }

        //3-si no tiene zombis cerca: moverse hasta 2 casillas hacia el más cercano
        moverHasta2(juego);
    }

 
    //MÉTODOS AUXILIARES
    
    private Zombi buscarZombiEnCasillas(List<Casilla> casillas) {
        for (Casilla c : casillas) {
            if (!c.getZombis().isEmpty()) {
                return c.getZombis().get(rand.nextInt(c.getZombis().size()));
            }
        }
        return null;
    }

    private void moverHasta2(Juego juego) {
        Casilla objetivo = juego.getTablero().buscarZombiMasCercano(casillaActual);
        if (objetivo == null) return;

        for (int i = 0; i < 2; i++) {
            Casilla siguiente = juego.getTablero().calcularSiguienteCasillaHaciaZombi(casillaActual);
            if (siguiente == null || siguiente == casillaActual) break;

            mover(siguiente);
        }

        System.out.println(nombre + " (Ingeniero) se mueve hasta 2 casillas hacia los zombis.");
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

        z.registrarComestible(this);
        z.registrarHumanoEliminado(this);


        // el Ingeniero NO reduce el hambre del zombi (A DIFERENCIA DE OTROS HUMANOS)
        // tiene un 50% de probabilidad de causar 1 herida al zombi
        if (rand.nextBoolean()) {   // 50% exacto
            z.recibirHerida(1);
            System.out.println(nombre + 
                " (Ingeniero) sienta MAL al zombi " + z.getNombre() +
                ": recibe 1 herida por la mala digestion.");
        } else {
            System.out.println(nombre + 
                " (Ingeniero) es comido, pero esta vez no provoca herida.");
        }
        
        if (z.getHambre() >= 4) {
        z.setHambre(z.getHambre() - 2);
        System.out.println("El hambre de " + z.getNombre() + 
            " se reduce en 2 puntos por comerse al Ingeniero.");
        } else {
        System.out.println("El hambre de " + z.getNombre() + 
            " NO se reduce porque era menor de 4.");
        }
    }

    
}
