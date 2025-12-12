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
//priorizar humanos
    public List <Humano> priorizarHumano(List <Entidad> ocupantes){
        List <Humano> prior= new ArrayList<>();
        return prior;
    }
    @Override
    public boolean ejecutar(Zombi zombi,Casilla casillaDestino) {
        // ESTE MÉTODO TIENE QUE SER DESARROLLADO DESPUÉS:
        // aplicar reglas especiales de impacto y prioridades
        return false;
    }
}
