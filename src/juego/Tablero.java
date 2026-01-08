/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;
/**
 *
 * @author Basma Kasstali
 */
import java.util.ArrayList;
import java.util.List;

public class Tablero {

    private int tamaño;
    private Casilla[][] casillas;
    private Casilla casillaSalida;

    // --- COLORES ANSI ---
    private static final String RESET = "\u001B[0m";
    
    // Colores de Texto
    private static final String ROJO = "\u001B[31m";      // Zombis
    private static final String VERDE = "\u001B[32m";     // Humanos
    private static final String AMARILLO = "\u001B[33m";  // Conejos
    private static final String AZUL = "\u001B[34m";      // Salida
    
    // Colores de Fondo (Para el turno activo)
    private static final String FONDO_MAGENTA = "\u001B[45m"; 
    private static final String BLANCO_BRILLANTE = "\u001B[97m";

    public Tablero(int numeroZombis) {
        tamaño = calcularTamaño(numeroZombis);
        casillas = new Casilla[tamaño][tamaño];
        inicializarCasillas();
        casillaSalida = casillas[tamaño - 1][tamaño - 1];
        casillaSalida.setEsSalida(true);
    }

    private int calcularTamaño(int numeroZombis) {
        if (numeroZombis < 1 || numeroZombis > 4) numeroZombis = 1;
        return 6 + numeroZombis;
    }

    private void inicializarCasillas() {
        for (int x = 0; x < tamaño; x++) {
            for (int y = 0; y < tamaño; y++) {
                casillas[x][y] = new Casilla(x, y);
            }
        }
    }

    public Casilla obtenerCasilla(int x, int y) {
        if (x < 0 || x >= tamaño || y < 0 || y >= tamaño) 
            throw new IllegalArgumentException("Coordenadas fuera del tablero");
        return casillas[x][y];
    }

    public Casilla obtenerCasillaAleatoria() {
        int x = (int) (Math.random() * tamaño);
        int y = (int) (Math.random() * tamaño);
        return casillas[x][y];
    }

    public Casilla obtenerCasillaInicial() {
        return casillas[0][0];
    }

    public List<Casilla> obtenerCasillasAdyacentes(int x, int y) {
        List<Casilla> adyacentes = new ArrayList<>();
        if (y > 0) adyacentes.add(casillas[x][y - 1]);
        if (y < tamaño - 1) adyacentes.add(casillas[x][y + 1]);
        if (x > 0) adyacentes.add(casillas[x - 1][y]);
        if (x < tamaño - 1) adyacentes.add(casillas[x + 1][y]);
        return adyacentes;
    }

    public Casilla calcularSiguienteCasillaHaciaSalida(Casilla desde) {
        Casilla mejor = desde;
        int distanciaActual = calcularDistancia(desde, casillaSalida);
        for (Casilla c : obtenerCasillasAdyacentes(desde.getCoordenadaX(), desde.getCoordenadaY())) {
            int distancia = calcularDistancia(c, casillaSalida);
            if (distancia < distanciaActual) {
                distanciaActual = distancia;
                mejor = c;
            }
        }
        return mejor;
    }

    public Casilla calcularSiguienteCasillaHaciaZombi(Casilla desde) {
        Casilla objetivo = buscarZombiMasCercano(desde);
        if (objetivo == null) return desde;
        Casilla mejor = desde;
        int distanciaActual = calcularDistancia(desde, objetivo);
        for (Casilla c : obtenerCasillasAdyacentes(desde.getCoordenadaX(), desde.getCoordenadaY())) {
            int distancia = calcularDistancia(c, objetivo);
            if (distancia < distanciaActual) {
                distanciaActual = distancia;
                mejor = c;
            }
        }
        return mejor;
    }

    public Casilla buscarZombiMasCercano(Casilla desde) {
        Casilla masCercano = null;
        int distanciaMinima = 0;
        for (int x = 0; x < tamaño; x++) {
            for (int y = 0; y < tamaño; y++) {
                Casilla c = casillas[x][y];
                if (c.hayZombis()) {
                    int distancia = calcularDistancia(desde, c);
                    if (masCercano == null || distancia < distanciaMinima) {
                        masCercano = c;
                        distanciaMinima = distancia;
                    }
                }
            }
        }
        return masCercano;
    }

    private int calcularDistancia(Casilla c1, Casilla c2) {
        return Math.abs(c1.getCoordenadaX() - c2.getCoordenadaX()) + 
               Math.abs(c1.getCoordenadaY() - c2.getCoordenadaY());
    }

    // --- VISUALIZACIÓN MEJORADA ---
    public void mostrarTablero(Entidad activo) {
        System.out.println("\n   TABLERO " + tamaño + "x" + tamaño);
        
        // Eje X
        System.out.print("   ");
        for (int x = 0; x < tamaño; x++) System.out.print(" " + x + "  ");
        System.out.println();
        System.out.print("   ");
        for (int x = 0; x < tamaño; x++) System.out.print("--- ");
        System.out.println();

        for (int y = tamaño - 1; y >= 0; y--) {
            System.out.print(y + " |"); 
            
            for (int x = 0; x < tamaño; x++) {
                Casilla c = casillas[x][y];
                
                String simbolo = "[ ] ";
                String colorTexto = RESET;
                String colorFondo = ""; // Por defecto sin fondo

                // Lógica de símbolos y colores base
                if (c.esSalida()) {
                    simbolo = "[S] ";
                    colorTexto = AZUL;
                }
                
                if (c.getNumeroOcupantes() > 0) {
                     // Detectar si el ACTIVO está aquí
                    boolean esSuCasilla = (activo != null && c.getOcupantes().contains(activo));

                    if (c.hayZombis()) {
                        colorTexto = ROJO;
                        // Si es la casilla del turno actual, cambiamos forma y fondo
                        if(esSuCasilla) {
                            simbolo = "{Z} "; 
                            colorFondo = FONDO_MAGENTA;
                            colorTexto = BLANCO_BRILLANTE; // Texto blanco sobre fondo magenta
                        } else {
                            simbolo = "[Z] ";
                        }
                    } else if (c.hayHumanos()) {
                        colorTexto = VERDE;
                         if(esSuCasilla) {
                            simbolo = "{H} "; 
                            colorFondo = FONDO_MAGENTA;
                            colorTexto = BLANCO_BRILLANTE;
                        } else {
                            simbolo = "[H] ";
                        }
                    } else {
                        colorTexto = AMARILLO;
                        simbolo = "[C] ";
                    }
                }
                
                System.out.print(colorFondo + colorTexto + simbolo + RESET);
            }
            System.out.println();
        }
    }

    public void mostrarTablero() {
        mostrarTablero(null);
    }

    public int getTamaño() { return tamaño; }
    public Casilla getCasillaSalida() { return casillaSalida; }
    public Casilla[][] getCasillas() { return casillas; }
    public void setCasillaSalida(int x, int y) {
        if (this.casillaSalida != null) this.casillaSalida.setEsSalida(false);
        if (x >= 0 && x < tamaño && y >= 0 && y < tamaño) {
            this.casillaSalida = casillas[x][y];
            this.casillaSalida.setEsSalida(true);
        }
    }
}