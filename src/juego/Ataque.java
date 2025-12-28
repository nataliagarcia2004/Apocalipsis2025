/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Shuyi Qu
 */
public abstract class Ataque {
    protected String nombre;
    protected int potencia; //el número de dados que se lanzan. 
    protected int valorExito;//valor mínimo que tiene que salir en el dado para que se considere un impacto. 
    protected int alcance;//el número de casillas de distancia de la casilla objetivo
    //Constructor
    public Ataque(String nombre,int potencia, int valorExito, int alcance) {
        this.nombre=nombre;
        this.potencia = potencia;
        this.valorExito = valorExito;
        this.alcance = alcance;
    }
    public String getNombre(){
        return nombre;
    }
    public int getPotencia() {
        return potencia; }
    public int getValorExito() { 
        return valorExito; }
    public int getAlcance() {
        return alcance; }

    // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
    // ejecutará el ataque y devolverá los impactos generados
    public abstract boolean ejecutar(Zombi zombi,Casilla casillaObjetivo);
    public int calcularImpactos(int hambreZombi) {
        // Calcular cuántos dados lanzar
        int dadosTotales = potencia + hambreZombi;
        int impactos = 0;

        for (int i = 0; i < dadosTotales; i++) {
            int resultado = (int) (Math.random() * 6) + 1;
            if (resultado >= valorExito) {
                impactos++;
            }
        }

        System.out.println("  " + dadosTotales + " dados -> " + impactos + " impactos");
        return impactos;
       
    }
    //Para saber si una casilla objetivo esta dentro de alcance
    public boolean estaEnAlcance(Casilla casillaZombi,Casilla casillaObjetivo){
        //fórmula:|x2-x1| + |y2-y1|,distancia Manhattan
        int distanciaX = Math.abs(casillaObjetivo.getCoordenadaX() - casillaZombi.getCoordenadaX());
        int distanciaY= Math.abs(casillaObjetivo.getCoordenadaY()- casillaZombi.getCoordenadaY());
        int distancia= distanciaX + distanciaY;
        return distancia<=alcance;
        
    }
    //toString
    @Override
    public String toString(){
        return nombre + " (Potencia:" + potencia + ", Exito:" + valorExito + "+ Alcance:" + alcance + ")";
    }
    }
