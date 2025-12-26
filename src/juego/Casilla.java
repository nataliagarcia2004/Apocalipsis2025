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

public class Casilla {

    private int coordenadaX;
    private int coordenadaY;
    private boolean esSalida;
    private List<Entidad> ocupantes;

// Constructor de la clase:
    public Casilla(int coordenadaX, int coordenadaY) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.esSalida = false;
        this.ocupantes = new ArrayList<>();
    }

// Agregamos una entidad a la casilla:
 public void agregarEntidad(Entidad elemento) {
    if (elemento != null && !ocupantes.contains(elemento)) {
            ocupantes.add(elemento);
        }
    }

 // Se elimina una entidad de la casilla:
  public boolean eliminarEntidad(Entidad elemento) {
      
    return ocupantes.remove(elemento);
    }

// Devuelve una copia de la lista de ocupantes:
  public List<Entidad> getOcupantes() {
    return new ArrayList<>(ocupantes);
    }

// Indicar si la casilla está vacía o no:
public boolean estaVacia() {
        return ocupantes.isEmpty();
    }

// Devuelve el número total de entidades existentes en la casilla:
public int getNumeroOcupantes() {
        return ocupantes.size();
    }

// Método que comprueba si hay al menos un zombi en la casilla:
public boolean hayZombis() {
     return !getZombis().isEmpty();
    }

// Método que comprueba si hay al menos un humano en la casilla:
public boolean hayHumanos() {
     return !getHumanos().isEmpty();
    }

// Devuelve una lista con todos los zombis de la casilla.
    public List<Zombi> getZombis() {
        List<Zombi> zombis = new ArrayList<>();
        
    for (Entidad e : ocupantes) {
       if (e instanceof Zombi)  
       {
                zombis.add((Zombi) e);
            }
       }
        return zombis;
    }

// Devuelve una lista con todos los humanos de la casilla:
    public List<Humano> getHumanos() {
        List<Humano> humanos = new ArrayList<>();
        
     for (Entidad e : ocupantes) {
            if (e instanceof Humano) {
                humanos.add((Humano) e);
            }
        }
        return humanos;
    }

// Devuelve el número de humanos en la casilla.
public int getNumeroHumanos() {
        return getHumanos().size();
    }

// Devuelve una lista con todos los conejos de la casilla.
public List<Conejo> getConejos() {
        List<Conejo> conejos = new ArrayList<>();
        for (Entidad e : ocupantes) {
            if (e instanceof Conejo) {
                conejos.add((Conejo) e);
            }
        }
        return conejos;
    }

// Indica si esta casilla es la salida del tablero.
public boolean esSalida() {
        return esSalida;
    }

public void setEsSalida(boolean esSalida) {
        this.esSalida = esSalida;
    }

public int getCoordenadaX() {
        return coordenadaX;
    }

public int getCoordenadaY() {
        return coordenadaY;
    }

    @Override
public String toString() {
        return "(" + coordenadaX + "," + coordenadaY + ")" +
               (esSalida ? " [SALIDA]" : "") +
               " - ocupantes: " + ocupantes.size();
    }

//COmparando las coordenadas podemos indicar si dos casillas son iguales o no 
    @Override
public boolean equals(Object obj) {
     if (this == obj) return true;
     if (obj == null || getClass() != obj.getClass()) return false;
        Casilla casilla = (Casilla) obj;
        return coordenadaX == casilla.coordenadaX &&
               coordenadaY == casilla.coordenadaY;
    }

    @Override
    public int hashCode() {
        return 31 * coordenadaX + coordenadaY;
    }
}
