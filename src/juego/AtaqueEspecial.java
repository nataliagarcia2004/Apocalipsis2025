/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Shuyi Qu
 */
import java.util.ArrayList;
import java.util.List;
public class AtaqueEspecial extends Ataque{
    private int idAtaque;
    public AtaqueEspecial(int idA,String nombre,int potencia, int valorExito, int alcance) {
        super(nombre,potencia, valorExito, alcance);
        this.idAtaque=idA;
        // LA CONFIGURACIÓN FINAL VIENE DEL IMPORTADOR
        
    }
    //get
    public int getIdAtaque(){
        return idAtaque;
    }
//priorizar humanos Sin incluir conejo "El Ataque Especial ignora a los Conejos"
    private List <Humano> priorizarHumano(List <Entidad> ocupantes){
        List <Humano> prior= new ArrayList<>();
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
        return prior;
        
    }
    
    @Override
    public boolean ejecutar(Zombi zombi,Casilla casillaObjetivo) {
        System.out.println(zombi.getNombre() + "usa" + getNombre());
        // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
        //Verificar alcance (puede ser > 0)
         if (!estaEnAlcance(zombi.getCasillaActual(), casillaObjetivo)) {
           System.err.println("Fuera de alcance");
           return false;
        }

        //Obtener SOLO humanos 
        List<Humano> objetivos = priorizarHumano(casillaObjetivo.getOcupantes());
        if(objetivos.isEmpty()){
            System.out.println("No hay humanos");
            return false;
        }
        //Calcular impactos
        int impactos= calcularImpactos(zombi.getHambre());
        if(impactos==0){
            System.out.println("Sin impactos,el ataque se falla");
            return false;
        }
        
        //Repartir impactos
        int eliminados=repartirImpactos(zombi,objetivos,impactos,casillaObjetivo);
        if(eliminados>0){
            System.out.println(eliminados+"Humanos eliminados");
            return true;
        }else{
            System.out.println("El ataque fallo");
            return false;
        }
    }
    private int repartirImpactos(Zombi zombi, List<Humano> objetivos, int impactos, Casilla casilla){
        //Inicializar variables
        int impactosRestantes=impactos;
        int Eliminado=0;
        System.out.println("Repartiendo"+ impactos + "impactos");
        //Recorrer objetivos EN ORDEN DE PRIORIDAD 
        for(Humano objetivo:objetivos){
            if(impactosRestantes<=0)break;
            if(impactosRestantes>=objetivo.getAguante()){
                // Eliminar
                System.out.println(objetivo.getNombre()+ "Ha sido eliminado");
                zombi.registrarHumanoEliminado(objetivo);
                casilla.eliminarEntidad(objetivo);
                impactosRestantes-=objetivo.getAguante();
                Eliminado++;
                
            }else{
                //No se puede eliminar
                System.out.println("Impactos insuficientes,no se puede eliminar");
                break;
            }
        }
        return Eliminado;
    }
    
}
