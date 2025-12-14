/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 * 
 */
public abstract class Humano extends Entidad implements Comestible  {
    protected int aguante;        // Resistencia del humano
    protected int activaciones;   // Número de acciones que hace por turno
    
    // Indica si el humano ya está eliminado del juego
    protected boolean eliminado = false;//REDUDANTE LIS
   
    public Humano(String nombre, Casilla casillaInicial, int aguante, int activaciones) {
        super(nombre, casillaInicial);
        this.aguante = aguante;
        this.activaciones = activaciones;
    }
    
    public int getAguante() {
        return aguante;
    }

    public int getActivaciones() {
        return activaciones;
    }
    
    public boolean estaEliminado() {
        return eliminado;
        //return aguante <=0;
    }
    
    // Cada tipo de humano tendrá una manera diferente de moverse, se implementará en los subtipos
    @Override
    public abstract void mover(Casilla casillaDestino);

    ///Cada subtipo define su comportamiento al activarse, se implementara en los subtipos
    public abstract void activar(Juego juego);
    
 
    
    // MÉTODOS COMUNES PARA TODOS LOS HUMANOS
    public void recibirAtaque(int impactos) {
        if (impactos >= aguante) {
            eliminado = true;//quitar
            aguante = 0;
            casillaActual.eliminarEntidad(this);
            System.out.println(nombre + " ha sido eliminado por un ataque directo.");
        } else {
            System.out.println(nombre + " recibió ataques, pero sigue vivo.");
        }
    }
    
    public void registrarHerida(Zombi z) {
       //cunaod haya juego porgramado igual hay q cabiar cosas
        aguante--;

        if (aguante <= 0) {
            eliminado = true;
            aguante = 0;
            casillaActual.eliminarEntidad(this);
            
            z.registrarHumanoEliminado(this);
            System.out.println(nombre + " ha muerto por heridas infligidas por el zombi " + z.getNombre());
        } else {
            System.out.println(nombre + " ha recibido 1 herida de " + z.getNombre() +
                               ". Aguante restante: " + aguante);
        }

    }
    //EL METODO REGISTRARHERIDA Y RECIBIR ATAQUE SE PUEDE UNIFICAR
    /*public void recibirImpactos(int impactos, Zombi agresor) {
    if (impactos >= aguante) {
        aguante = 0;
        casillaActual.eliminarEntidad(this);
        agresor.registrarEliminacion(this);  // Siempre registrar
        System.out.println(getNombre() + " eliminado por " + agresor.getNombre());
    } else {
        System.out.println(getNombre() + " sobrevive (" + impactos + "/" + aguante + " impactos)");
    }*/

    @Override
    public void serComido(Zombi z) {
        eliminado = true;
        aguante = 0;
        
        casillaActual.eliminarEntidad(this);  
        z.registrarComestible(this);          
        z.registrarHumanoEliminado(this);//MAL,PORQUE SERCOMIDO = DEVORADO POR ZOMBI PERO ELIMINADO ES DE ATAQUE ESPECIAL
        
        z.setHambre(0);
        System.out.println(nombre + " ha sido comido por el zombi " + z.getNombre());
    }
    
}
