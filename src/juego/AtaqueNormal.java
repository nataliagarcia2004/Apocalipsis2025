/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Natalia Garcia
 */
import java.util.List;
import java.util.ArrayList;

public class AtaqueNormal extends Ataque {
     public AtaqueNormal() {
        super("Devorar",1, 4, 0); 
        // POTENCIA 1, ÉXITO 4+, ALCANCE 0
        // Los valores finales podrían ajustarse después
    }

    @Override
    public boolean ejecutar(Zombi zombi,Casilla casillaDestino) {
        // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
        // hacer tirada de dado y devolver impactos si acierta
        return false;
    }
    /*Priorizar:
    1. Ingeniero
      2. Soldado
     3. Blindado
      4. Especialista
      5. Huidizo
      6. Conejo */
    public List<Comestible> priorizar(List<Entidad> ocupantes){
        List <Comestible>prior = new ArrayList<>();
        /*Se busca ingeniero
        for(Entidad e: ocupantes){
            if(e instanceof Ingeniero && e instanceof Comestible){
                prior.add((Comestible)e);
            }
        }*/
        //6.conejo
        for(Entidad e: ocupantes){
            if(e instanceof Conejo){
                prior.add((Comestible)e);
            }
        }
        return prior;
    }
//efecto de devorar
    public void devorarObjetivo(Zombi zombi,Comestible objetivo,Casilla casilla){
        objetivo.serComido(zombi);
        if(objetivo instanceof Entidad){ //si el objetivo es del tipo Entidad
            casilla.eliminarEntidad((Entidad) objetivo);
        }
    }
}
