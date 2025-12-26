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
    
    /**Constructor*/
   
    public Casilla(int coordenadaX, int coordenadaY) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.esSalida = false;
        this.ocupantes = new ArrayList<>();
    }
    
    // Método Agregar Entidad
    public void agregarEntidad(Entidad elemento) {
        if (elemento != null && !ocupantes.contains(elemento)) {
            ocupantes.add(elemento);
        }
    }
    
   //Método Eliminar Entidad 
    public boolean eliminarEntidad(Entidad elemento) {
        return ocupantes.remove(elemento);
    }
    
    //Obtener la lista de ocupantes por casilla
    
    public List<Entidad> getOcupantes() {
        return new ArrayList<>(ocupantes); // Retorna una copia
    }
    
    //Método para verificar sila casilla està vacía 
    public boolean estaVacia() {
        return ocupantes.isEmpty();
    }
    
    //Método para Contar Número de entidades en la casilla
    public int getNumeroOcupantes() {
        return ocupantes.size();
    }
    
    //Método para verifcar si hay zombis en la casilla
    public boolean hayZombis() {
        for (Entidad e : ocupantes) {
            if (e instanceof Zombi) {
                return true;
            }
        }
        return false;
    }
    
    //Método para verificsr si hay humanos en la casilla 
    public boolean hayHumanos() {
        for (Entidad e : ocupantes) {
            if (e instanceof Humano) {
                return true;
            }
        }
        return false;
    }
    
    //Método para obtener todos los zombis de la casilla(Lista)
    public List<Zombi> getZombis() {
        List<Zombi> zombis = new ArrayList<>();
        for (Entidad e : ocupantes) {
            if (e instanceof Zombi) {
                zombis.add((Zombi) e);
            }
        }
        return zombis;
    }
    
     //Método para obtener todos los humanos de la casilla(Lista)
    public List<Humano> getHumanos() {
        List<Humano> humanos = new ArrayList<>();
        for (Entidad e : ocupantes) {
            if (e instanceof Humano) {
                humanos.add((Humano) e);
            }
        }
        return humanos;
    }
    
    // Getters y Setters
    
    public int getCoordenadaX() {
        return coordenadaX;
    }
    
    public int getCoordenadaY() {
        return coordenadaY;
    }
    
    public boolean esSalida() {
        return esSalida;
    }
    
    public void setEsSalida(boolean esSalida) {
        this.esSalida = esSalida;
    }
    
    //Método para demostrar información de la casilla
    public void mostrarInfo() {
        System.out.println("Casilla (" + coordenadaX + ", " + coordenadaY + ")");
        if (esSalida) {
            System.out.println("  [SALIDA]");
        }
        if (ocupantes.isEmpty()) {
            System.out.println("  (vacía)");
        } else {
            System.out.println("  Ocupantes:");
            for (Entidad e : ocupantes) {
                System.out.println("    - " + e.getNombre());
            }
        }
    }
    
    @Override
    public String toString() {
        String tipo = esSalida ? "[SALIDA]" : "";
        return "(" + coordenadaX + "," + coordenadaY + ")" + tipo + 
               " [" + ocupantes.size() + " ocupantes]";
    }
    // 
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