/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author shuyi qu
 */
public class Zombi extends Entidad {
    public enum Estado{
        ACTIVO,ELIMINADO
    }
    private Estado estado;
    private int heridas;
    private int hambre;
    private int maxAcciones;
    private int accionesRestantes;
    //Constructor de Zombi
    public Zombi(String nombre,Casilla casillaActual,int h,int ham,int max,int a){
        super(nombre,casillaActual);
        this.heridas=h;
        this.hambre=ham;
        this.maxAcciones=max;
        this.accionesRestantes=a;
    }
    
    //prueba1
    @Override
    public void mover(Casilla casillaDestino){
        
    }
    //Buscar comida
    public void buscarComida(){
        
    }
    //Atacar
    public void atacar(Casilla casillaObjetivo,Ataque ataqueseleccionado){
        
    }
    //Recibir herida
    public void recibirHerida(int cantidad){
    }
    //Registrar Comestible 
    public void registrarComestible(Comestible c){
    }
}
