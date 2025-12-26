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
    private int tamaño;                
    private Casilla[][] casillas;      
    private Casilla casillaSalida; 
    
// Constructor del tablero:
public Tablero(int numeroZombis) {
     tamaño = calcularTamaño(numeroZombis);
     casillas = new Casilla[tamaño][tamaño];
         inicializarCasillas();

// Pondremos la slaida en la otra esquina lo típico de juegos: 
     casillaSalida = casillas[tamaño - 1][tamaño - 1];
      casillaSalida.setEsSalida(true);
    }


//Teniendo en cuenta el numero de zombis se calcula el tamaño de nuestro tablero:
private int calcularTamaño(int numeroZombis) {
     // Usamos lo mínimo en caso de que el numero no es válido
    if (numeroZombis < 1 || numeroZombis > 4) {
            numeroZombis = 1;
        }
        return 6 + numeroZombis;
    }

//Inicializamos todas las casillas de nuestro tablero:
private void inicializarCasillas() {
   for (int x = 0; x < tamaño; x++) {
     for (int y = 0; y < tamaño; y++) {
       casillas[x][y] = new Casilla(x, y);
            }
        }
    }

//Obtener la casilla en una posición concreta:
public Casilla obtenerCasilla(int x, int y) {
    if (x < 0 || x >= tamaño || y < 0 || y >= tamaño) {
        throw new IllegalArgumentException("Coordenadas fuera del tablero");
    }
    return casillas[x][y];
}


//Obtener una casilla aleatoria del tablero:
public Casilla obtenerCasillaAleatoria() {
        int x = (int) (Math.random() * tamaño);
        int y = (int) (Math.random() * tamaño);
        return casillas[x][y];
    }

//Devolver la casilla inicial:
public Casilla obtenerCasillaInicial() {
    return casillas[0][0];
    }

//Obtener las casillas adyacentes:
public List<Casilla> obtenerCasillasAdyacentes(int x, int y) {
    List<Casilla> adyacentes = new ArrayList<>();
  if (y > 0) adyacentes.add(casillas[x][y - 1]);
  if (y < tamaño - 1) adyacentes.add(casillas[x][y + 1]);
  if (x > 0) adyacentes.add(casillas[x - 1][y]);
  if (x < tamaño - 1) adyacentes.add(casillas[x + 1][y]);
     return adyacentes;
    }

//Calcular la siguiente casilla más cercana a la salida:
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

//Calcular la siguiente casilla hacia el zombi más cercano:
public Casilla calcularSiguienteCasillaHaciaZombi(Casilla desde) {
  Casilla objetivo = buscarZombiMasCercano(desde);
         if (objetivo == null) {
            return desde;
        }

         
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

//Buscar la casilla que contiene el zombi más cercano:
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

//Calcular5 la distancia Manhattan entre dos casillas:
private int calcularDistancia(Casilla c1, Casilla c2) {
   return Math.abs(c1.getCoordenadaX() - c2.getCoordenadaX())
        + Math.abs(c1.getCoordenadaY() - c2.getCoordenadaY());
    }

//Mostrar el tablero por consola para visualizar:
    public void mostrarTablero() {
        System.out.println("\nTABLERO " + tamaño + "x" + tamaño);

        for (int y = tamaño - 1; y >= 0; y--) {
            for (int x = 0; x < tamaño; x++) {
                Casilla c = casillas[x][y];

                if (c.esSalida()) {
                    System.out.print("[S] ");
                } else if (c.hayZombis()) {
                    System.out.print("[Z] ");
                } else if (c.hayHumanos()) {
                    System.out.print("[H] ");
                } else if (c.getNumeroOcupantes() > 0) {
                    System.out.print("[C] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println();
        }
    }

// los Getters
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
