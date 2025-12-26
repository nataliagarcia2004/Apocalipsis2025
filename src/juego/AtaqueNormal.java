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
    public boolean ejecutar(Zombi zombi,Casilla casillaObjetivo) {
        System.out.println(zombi.getNombre()+ "usa devora en casilla"+casillaObjetivo.getCoordenadaX()+ casillaObjetivo.getCoordenadaY());
        //Verificar alcance (alcance 0 = misma casilla)
         if(!estaEnAlcance(zombi.getCasillaActual(),casillaObjetivo)){
            System.err.println("devora solo funciona en casilla actual");
            return false;
        }
        //Obtener objetivos priorizados
        List<Comestible> objetivos = priorizar(casillaObjetivo.getOcupantes());
        if(objetivos.isEmpty()){
            System.out.println("No hay objetivos comestibles");
            return false;
        }
        //Calcular impactos
         int impactos= calcularImpactos(zombi.getHambre());
        if(impactos==0){
            System.out.println("Sin impactos,el ataque se falla");
            return false;
        }
        //Intentar devorar el PRIMER objetivo (no reparte imapctos)
        Comestible objetivo = objetivos.get(0);
        //Verificar aguante si es humano
        if(objetivo instanceof Humano){
            Humano humano=(Humano)objetivo;
            if(impactos >= humano.getAguante()){
                System.out.println(humano.getNombre()+"ha sido devorado");
                devorarObjetivo(zombi,objetivo,casillaObjetivo);
                return true;
            }
            else{
                System.out.println("Insuficientes impactos.("+ impactos + "/"+ humano.getAguante()+")");
                return false;
            }
        }else{ //Si es un conejo
            System.out.println("Conejo devorado");
            devorarObjetivo(zombi,objetivo,casillaObjetivo);
            return true;
        }
    }
    
    /*Priorizar:
    1. Ingeniero
      2. Soldado
     3. Blindado
      4. Especialista
      5. Huidizo
      6. Conejo */
    private List<Comestible> priorizar(List<Entidad> ocupantes){
        List <Comestible>prior = new ArrayList<>();
         //1.Ingeniero
        for(Entidad e : ocupantes){
            if(e instanceof Ingeniero){
                prior.add((Humano)e);
            }
           
                    }
         //2.Soldados
            for(Entidad e: ocupantes){
                if(e instanceof Combatiente){
                    Combatiente c= (Combatiente)e;
                    if("SOLDADO".equals(c.getTipo())){
                        prior.add((Combatiente)e);
                }
            }
        }
        //3.Blindados
        for(Entidad e : ocupantes){
            if(e instanceof Combatiente c){
                if("BLINDADO".equals(c.getTipo())){
                    prior.add((Combatiente)e);
                }
            }
        }
            //4.Especialistas
            for(Entidad e : ocupantes){
            if(e instanceof Combatiente){
                Combatiente c= (Combatiente)e;
                if("ESPECIALISTA".equals(c.getTipo())){
                    prior.add((Combatiente)e);
                }
            }
        }
            //5.Humanos huidizos
             for(Entidad e : ocupantes){
                 if(e instanceof Huidizo){
                     prior.add((Humano)e);
                 }
             }
        //6.conejo
        for(Entidad e: ocupantes){
            if(e instanceof Conejo){
                prior.add((Comestible)e);
            }
        }
        return prior;
    }
//efecto de devorar propio de esta clase
    private void devorarObjetivo(Zombi zombi,Comestible objetivo,Casilla casilla){
        objetivo.serComido(zombi);
        if(objetivo instanceof Entidad){ //si el objetivo es del tipo Entidad
            casilla.eliminarEntidad((Entidad) objetivo);
        }
    }
}