/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
public class Combatiente extends Humano{
    // Tipos posibles del combatiente
    public enum TipoCombatiente {
        SOLDADO,
        ESPECIALISTA,
        BLINDADO,
        INGENIERO
    }
    
    private TipoCombatiente tipo;
    
    public Combatiente(String nombre, Casilla casillaInicial,
                       int aguante, int activaciones,
                       TipoCombatiente tipo) {

        super(nombre, casillaInicial, aguante, activaciones);
        this.tipo = tipo;
    }

    public TipoCombatiente getTipo() {
        return tipo;
    }

    @Override
    public void mover(Casilla casillaDestino) {
        // se tiene que revisar despues cunaod juego implementado
        /*
        if (casillaDestino == null) return;

        Tablero tablero = Tablero.getInstancia();
        tablero.moverEntidad(this, casillaDestino); // mover en tablero

        this.casillaActual = casillaDestino;

        System.out.println(nombre + " se mueve hacia " + casillaDestino);
        */
    }

    @Override
    public void activar() {
        /*
        // se tiene que revisar despues cunaod juego implementado
        Tablero tablero = Tablero.getInstancia();

        // 1. Buscar zombis en la casilla actual
        List<Zombi> zombisAqui = tablero.obtenerZombisEnCasilla(casillaActual);

        switch (tipo) {


            case SOLDADO:
            case ESPECIALISTA:
            case BLINDADO:

                if (!zombisAqui.isEmpty()) {
                    // Atacar zombi aleatorio
                    Zombi objetivo = zombisAqui.get(random.nextInt(zombisAqui.size()));

                    System.out.println(nombre + " ataca a " + objetivo.getNombre());
                    objetivo.recibirHerida(1);

                } else {
                    // No hay zombis → moverse hacia el más cercano
                    Casilla destino = tablero.obtenerCasillaMasCercanaConZombi(casillaActual);
                    mover(destino);
                }
                break;

            //ingeniero tieneataque especial
            case INGENIERO:

                if (!zombisAqui.isEmpty()) {
                    // 2 heridas si está en la misma casilla
                    Zombi objetivo = zombisAqui.get(random.nextInt(zombisAqui.size()));
                    System.out.println(nombre + " (Ingeniero) inflige 2 heridas a " + objetivo.getNombre());
                    objetivo.recibirHerida(2);

                } else {
                    // Buscar zombis a distancia 1
                    List<Zombi> zombisDist1 = tablero.obtenerZombisEnRadio(casillaActual, 1);

                    if (!zombisDist1.isEmpty()) {
                        // 1 herida a distancia
                        Zombi objetivo = zombisDist1.get(random.nextInt(zombisDist1.size()));
                        System.out.println(nombre + " (Ingeniero) inflige 1 herida a distancia a " + objetivo.getNombre());
                        objetivo.recibirHerida(1);

                    } else {
                        // Si no hay zombis cerca → moverse 2 casillas hacia el zombi más cercano
                        Casilla destino = tablero.obtenerCasillaMasCercanaConZombi(casillaActual);

                        // Suponemos que el tablero calculará la ruta
                        System.out.println(nombre + " (Ingeniero) se mueve 2 casillas hacia " + destino);

                        mover(destino);  
                    }
                }
                break;
        }
    }
        
    }
*/
    }
}
    
