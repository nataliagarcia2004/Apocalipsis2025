/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
import java.util.ArrayList;
import java.util.List;

public class Tablero {
   
    private int tamaño;                // Tamaño del tablero (7, 8, 9 o 10)
    private Casilla[][] casillas;      // Matriz de casillas
    private Casilla casillaSalida;     // Casilla objetivo (esquina opuesta)
    
    
    //Constructor
    
       public Tablero(int numeroZombis) {
        // PASO 1: Calcular tamaño
        this.tamaño = calcularTamaño(numeroZombis);
        
        // PASO 2: Crear todas las casillas
        this.casillas = new Casilla[tamaño][tamaño];
        inicializarCasillas();
        
        // PASO 3: Marcar la salida (esquina opuesta)
        this.casillaSalida = casillas[tamaño - 1][tamaño - 1];
        casillaSalida.setEsSalida(true);
        
        System.out.println("Tablero creado: " + tamaño + "x" + tamaño);
        System.out.println("Salida en: (" + (tamaño-1) + ", " + (tamaño-1) + ")");
    }
    
    
    // Calcular tamaño( 1 Zombi:7x7; 2 Zombis:8x8; 3 Zombis:9x9;  4 Zombis:10x10)
    private int calcularTamaño(int numeroZombis) {
        if (numeroZombis < 1 || numeroZombis > 4) {
            throw new IllegalArgumentException("Número de zombis debe ser entre 1 y 4");
        }
        
        // Fórmula simple: 6 + numeroZombis
        return 6 + numeroZombis;
    }
    
    
    //Crear una casilla para cada posición para crear todas las casillas del tablero 
    private void inicializarCasillas() {
        for (int x = 0; x < tamaño; x++) {
            for (int y = 0; y < tamaño; y++) {
                casillas[x][y] = new Casilla(x, y);
            }
        }
    }
    
    
    //método para obtener Casilla en una posición especifica 
    public Casilla obtenerCasilla(int x, int y) {
        if (x < 0 || x >= tamaño || y < 0 || y >= tamaño) {
            throw new IllegalArgumentException("Coordenadas fuera del tablero: (" + x + ", " + y + ")");
        }
        return casillas[x][y];
    }
    
    
    //Método para obtener casilla aleatoria 
    public Casilla obtenerCasillaAleatoria() {
        int x = (int)(Math.random() * tamaño);
        int y = (int)(Math.random() * tamaño);
        return casillas[x][y];
    }
    
    
    //Método para obtener casilla inicial 
    public Casilla obtenerCasillaInicial() {
        return casillas[0][0];
    }
    
    
    //Método para obtener casillas adyacentes 
    public List<Casilla> obtenerCasillasAdyacentes(int x, int y) {
        List<Casilla> adyacentes = new ArrayList<>();
        
        // Arriba
        if (y - 1 >= 0) {
            adyacentes.add(casillas[x][y - 1]);
        }
        
        // Abajo
        if (y + 1 < tamaño) {
            adyacentes.add(casillas[x][y + 1]);
        }
        
        // Izquierda
        if (x - 1 >= 0) {
            adyacentes.add(casillas[x - 1][y]);
        }
        
        // Derecha
        if (x + 1 < tamaño) {
            adyacentes.add(casillas[x + 1][y]);
        }
        
        return adyacentes;
    }
    
    
    //Método para Calcular la siguiente casilla hacia salida(para movernos) 
    public Casilla calcularSiguienteCasillaHaciaSalida(Casilla desde) {
        int x = desde.getCoordenadaX();
        int y = desde.getCoordenadaY();
        
        List<Casilla> adyacentes = obtenerCasillasAdyacentes(x, y);
        
        Casilla mejorCasilla = desde;
        int menorDistancia = calcularDistancia(desde, casillaSalida);
        
        for (Casilla adyacente : adyacentes) {
            int distancia = calcularDistancia(adyacente, casillaSalida);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                mejorCasilla = adyacente;
            }
        }
        
        return mejorCasilla;
    }
    
    //Método para calcular siguiente casilla hacia el Zombi
    public Casilla calcularSiguienteCasillaHaciaZombi(Casilla desde) {
        // Buscar el zombi más cercano
        Casilla casillaZombiCercano = buscarZombiMasCercano(desde);
        
        if (casillaZombiCercano == null) {
            return desde; // No hay zombis
        }
        
        int x = desde.getCoordenadaX();
        int y = desde.getCoordenadaY();
        
        List<Casilla> adyacentes = obtenerCasillasAdyacentes(x, y);
        
        Casilla mejorCasilla = desde;
        int menorDistancia = calcularDistancia(desde, casillaZombiCercano);
        
        for (Casilla adyacente : adyacentes) {
            int distancia = calcularDistancia(adyacente, casillaZombiCercano);
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                mejorCasilla = adyacente;
            }
        }
        
        return mejorCasilla;
    }
    
    
    //método para buscar el zombi mas cercano 
    public Casilla buscarZombiMasCercano(Casilla desde) {
        Casilla casillaZombiCercano = null;
        int menorDistancia = Integer.MAX_VALUE;
        
        // Recorrer todo el tablero buscando zombis
        for (int x = 0; x < tamaño; x++) {
            for (int y = 0; y < tamaño; y++) {
                Casilla casilla = casillas[x][y];
                
                if (casilla.hayZombis()) {
                    int distancia = calcularDistancia(desde, casilla);
                    if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        casillaZombiCercano = casilla;
                    }
                }
            }
        }
        
        return casillaZombiCercano;
    }
    
    
    //Método para calcular la distancia Manhattan entre la primera y la segunda casilla
    private int calcularDistancia(Casilla c1, Casilla c2) {
        return Math.abs(c1.getCoordenadaX() - c2.getCoordenadaX()) + 
               Math.abs(c1.getCoordenadaY() - c2.getCoordenadaY());
    }
    
    
    //Mostrar Tablero final 
    
    
    public void mostrarTablero() {
        System.out.println("\n========== TABLERO " + tamaño + "x" + tamaño + " ==========");
        
        for (int y = tamaño - 1; y >= 0; y--) {
            System.out.print("Y=" + y + " | ");
            for (int x = 0; x < tamaño; x++) {
                Casilla casilla = casillas[x][y];
                
                if (casilla.esSalida()) {
                    System.out.print("[S] ");
                } else if (casilla.hayZombis()) {
                    System.out.print("[Z] ");
                } else if (casilla.hayHumanos()) {
                    System.out.print("[H] ");
                } else if (casilla.getNumeroOcupantes() > 0) {
                    System.out.print("[C] "); // Conejo
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
        
        System.out.print("     ");
        for (int x = 0; x < tamaño; x++) {
            System.out.print(" " + x + "  ");
        }
        System.out.println("\n     X");
        
        System.out.println("\nLeyenda: [S]=Salida [Z]=Zombi [H]=Humano [C]=Conejo [ ]=Vacía");
    }
    
    
    //Getters
    
    public int getTamaño() {
        return tamaño;
    }
    
    public Casilla getCasillaSalida() {
        return casillaSalida;
    }
    
    public Casilla[][] getCasillas() {
        return casillas;
    }
}
