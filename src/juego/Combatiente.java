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
public class Combatiente extends Humano {

    public enum Tipo {
        SOLDADO,
        ESPECIALISTA,
        BLINDADO
    }

    private Tipo tipo;
    private static final Random rand = new Random();

    public Combatiente(String nombre, Casilla casillaInicial, Tipo tipo) {
        super(nombre, casillaInicial,
                asignarAguante(tipo),
                asignarActivaciones(tipo));

        this.tipo = tipo;
    }

    private static int asignarAguante(Tipo t) {
        switch (t) {
            case SOLDADO: return 1;
            case ESPECIALISTA: return 1;
            case BLINDADO: return 2;
            default: return 1;
        }
    }

    private static int asignarActivaciones(Tipo t) {
        switch (t) {
            case SOLDADO: return 1;
            case ESPECIALISTA: return 2;
            case BLINDADO: return 1;
            default: return 1;
        }
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public void activar(Juego juego) {
        if (eliminado) return;

        for (int i = 0; i < activaciones; i++) {

            // 1- si hay zombis atacar
            List<Zombi> zombis = casillaActual.getZombis();
            if (!zombis.isEmpty()) {
                Zombi objetivo = zombis.get(rand.nextInt(zombis.size()));
                atacar(objetivo);
                return;
            }

            //2-si no moverse hacia el zombi más cercano
            Casilla destino = juego.getTablero()
                    .calcularSiguienteCasillaHaciaZombi(casillaActual);

            mover(destino);
        }
    }

    @Override
    public void mover(Casilla destino) {
        if (destino == null) return;
        casillaActual.eliminarEntidad(this);
        destino.agregarEntidad(this);
        casillaActual = destino;
    }

    private void atacar(Zombi z) {
        //los tres tipos infligen SIEMPRE 1 herida
        z.recibirHerida(1);
        System.out.println(nombre + " (" + tipo + ") inflige 1 herida a " + z.getNombre());
    }

    @Override
    public void serComido(Zombi z) {
        eliminado = true;
        aguante = 0;
        casillaActual.eliminarEntidad(this);

        z.registrarComestible(this);
        z.registrarHumanoEliminado(this);

        z.setHambre(0);
        System.out.println(nombre + " (" + tipo + ") ha sido devorado por " 
                       + z.getNombre() + ". El hambre del zombi se reduce a 0.");
    }
}
    
