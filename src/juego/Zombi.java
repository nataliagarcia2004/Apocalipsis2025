/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;
import java.util.ArrayList;
/**
 *
 * @author shuyi qu
 */
public class Zombi extends Entidad {
    private Estado estado;
    private ArrayList<Comestible> comestiblesConsumidos;
    private ArrayList<Humano>humanosEliminados;//Lista vacia para registrar
    private int heridas;//contador de heridas
    private int hambre;//nivel de hambre entre 0 y 5
    private int maxAcciones;//inicialmente serán 3 acciones
    private int accionesRestantes;//Acciones disponibles en este turno
    private static final int MAX_HERIDAS=5;//5 heridas = eliminado
    private static final int MAX_HAMBRE=5;//hambre máximo permitido
    private static final int MIN_HAMBRE=0;
    private AtaqueEspecial ataqueEspecial;//cada zombi tiene su propio ataque especial
    private AtaqueNormal ataqueNormal;//ataque común de zombi
    //Constructor de Zombi
    public Zombi(String nombre,Casilla casillaActual,AtaqueEspecial ataqueEspecial){
        super(nombre,casillaActual);
        this.estado=Estado.ACTIVO;//se inicia el estado en Activo
        this.comestiblesConsumidos= new ArrayList <>();
        this.humanosEliminados= new ArrayList<>();
        this.heridas=0;//inicializar contadores
        this.hambre=0;
        this.ataqueNormal= new AtaqueNormal();
        this.ataqueEspecial= ataqueEspecial;//Asignar un ataque especial para todo
        this.accionesRestantes=maxAcciones;//Empieza con todas las acciones
    }
    //get
    public Estado getEstado(){
        return estado;
    }
    public int getHeridas(){
        return heridas;
    }
    public void setHeridas(int h){
        this.heridas=h;
        if(heridas>=MAX_HERIDAS){
           estado=Estado.ELIMINADO;
        }
    }
    public int getHambre(){
        return hambre;
    }
    public void setHambre(int ham){
        this.hambre=ham;
        if(hambre>=MAX_HAMBRE){
            estado=Estado.ELIMINADO;
        }
    }
    public int getMaxAcciones(){
        return maxAcciones;
    }
    public int getAccionesRestantes(){
        return accionesRestantes;
    }
    
    /*Inicia el turno de zombi y puede realizar 3 acciones
    -Se inicia el turno con hambre 5, se suma 1 herida
    -Restaurar las acciones disponibles
    - Mostrar información del turno
    */
    public void iniciarTurno(){
        
    }
    //Cuando finaliza su turno,se incrementa una unidad de hambre
 
    /*Acción 1 : mover
    - +1 acción mover a casilla adyacente
    -  +1acción extra por cada humano que hay en la casilla actual
    -  si hay 4 + humanos,está rodeado y no puede mover
   */ 
    @Override
    public void mover(Casilla casillaDestino){
        
    }
    /*Buscar comida,esta acción hará que aparezca elementos aleatorios
    -un humano huidizo (30% de probabilidad), 
     -un conejo (50% de probabilidad)
     -nada (20% de probabilidad)
    paso:
        verificar si hay acción
        gastar una acción
        generar comida*/
    public void buscarComida(){
        
    }
    //Atacar
    public void atacar(Casilla casillaObjetivo,Ataque ataqueseleccionado){
        
    }
    //Recibir herida
    public void recibirHerida(int cantidad){
    }
    //No hacer nada
    public void noHacerNada(){
        if(estado!=Estado.ACTIVO || accionesRestantes<=0){
            System.out.println(getNombre()+ "Ya no tiene acciones disponibles");
            return;
        }
        accionesRestantes--;
    }
    //Registrar Comestible 
    public void registrarComestible(Comestible c){
        
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;
import java.util.ArrayList;
/**
 *
 * @author shuyi qu
 */
public class Zombi extends Entidad {
    private Estado estado;
    private ArrayList<Comestible> comestiblesConsumidos;
    private ArrayList<Humano>humanosEliminados;//Lista vacia para registrar
    private int heridas;//contador de heridas
    private int hambre;//nivel de hambre entre 0 y 5
    private int maxAcciones;//inicialmente serán 3 acciones
    private int accionesRestantes;//Acciones disponibles en este turno
    private static final int MAX_HERIDAS=5;//5 heridas = eliminado
    private static final int MAX_HAMBRE=5;//hambre máximo permitido
    private static final int MIN_HAMBRE=0;
    private AtaqueEspecial ataqueEspecial;//cada zombi tiene su propio ataque especial
    private  AtaqueNormal ataqueNormal;//ataque común de zombi
    //Constructor de Zombi
    public Zombi(String nombre,Casilla casillaActual,AtaqueEspecial ataqueEspecial){
        super(nombre,casillaActual);
        this.estado=Estado.ACTIVO;//se inicia el estado en Activo
        this.comestiblesConsumidos= new ArrayList <>();
        this.humanosEliminados= new ArrayList<>();
        this.heridas=0;//inicializar contadores
        this.hambre=0;
        this.ataqueNormal= new AtaqueNormal();
        this.ataqueEspecial= ataqueEspecial;//Asignar un ataque especial para todo
        this.accionesRestantes=maxAcciones;//Empieza con todas las acciones
    }
    //get
    public Estado getEstado(){
        return estado;
    }
    public int getHeridas(){
        return heridas;
    }
    public void setHeridas(int h){
        this.heridas=h;
        if(heridas>=MAX_HERIDAS){
           estado=Estado.ELIMINADO;
        }
    }
    public int getHambre(){
        return hambre;
    }
    public void setHambre(int ham){
        this.hambre=ham;
        if(hambre>=MAX_HAMBRE){
            estado=Estado.ELIMINADO;
        }
    }
    public int getMaxAcciones(){
        return maxAcciones;
    }
    public int getAccionesRestantes(){
        return accionesRestantes;
    }
    
    /*Inicia el turno de zombi y puede realizar 3 acciones
    -Se inicia el turno con hambre 5, se suma 1 herida
    -Restaurar las acciones disponibles
    - Mostrar información del turno
    */
    public void iniciarTurno(){
        
    }
    //Cuando finaliza su turno,se incrementa una unidad de hambre
 
    /*Acción 1 : mover
    - +1 acción mover a casilla adyacente
    -  +1acción extra por cada humano que hay en la casilla actual
    -  si hay 4 + humanos,está rodeado y no puede mover
   */ 
    @Override
    public void mover(Casilla casillaDestino){
        
    }
    /*Buscar comida,esta acción hará que aparezca elementos aleatorios
    -un humano huidizo (30% de probabilidad), 
     -un conejo (50% de probabilidad)
     -nada (20% de probabilidad)
    paso:
        verificar si hay acción
        gastar una acción
        generar comida*/
    public void buscarComida(){
        
    }
    //Atacar
    public void atacar(Casilla casillaObjetivo,Ataque ataqueseleccionado){
        
    }
    //Recibir herida
    public void recibirHerida(int cantidad){
    }
    //No hacer nada
    public void noHacerNada(){
        if(estado!=Estado.ACTIVO || accionesRestantes<=0){
            System.out.println(getNombre()+ "Ya no tiene acciones disponibles");
            return;
        }
        accionesRestantes--;
    }
    //Registrar Comestible 
    public void registrarComestible(Comestible c){
        
    }
}
