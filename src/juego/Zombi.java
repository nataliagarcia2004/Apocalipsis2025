/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author shuyi qu
 */
public class Zombi extends Entidad {
    private Estado estado;
    private List<Comestible> comestiblesConsumidos;
    private List<Humano>humanosEliminados;//Lista vacia para registrar
    private int heridas;//contador de heridas
    private int hambre;//nivel de hambre entre 0 y 5
    ///private int maxAcciones;//inicialmente serán 3 acciones
    //añadido natalia:
    private int maxAcciones = 3;
    //private int accionesRestantes;//Acciones disponibles en este turno
    private int accionesRestantes;
    private static final int MAX_HERIDAS=5;//5 heridas = eliminado
    private static final int MAX_HAMBRE=5;//hambre máximo permitido
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
        if(ham<0){
            this.hambre=0;
        }else if(ham>=MAX_HAMBRE){
            this.hambre=MAX_HAMBRE;
        }else{
        this.hambre=ham;
        }
    }
    public int getMaxAcciones(){
        return maxAcciones;
    }
    public int getAccionesRestantes(){
        return accionesRestantes;
    }
    public List<Comestible> getComestiblesConsumidos(){
        return new ArrayList<>(comestiblesConsumidos);
    }
    public List <Humano> getHumanosEliminados(){
        return new ArrayList<>(humanosEliminados);
    }
    public AtaqueNormal getAtaqueNormal(){
        return ataqueNormal;
    }
    public AtaqueEspecial getAtaqueEspecial(){
        return ataqueEspecial;
    }
    public void setAtaqueEspecial(AtaqueEspecial a){
        this.ataqueEspecial=a;
    }
    
    /*Inicia el turno de zombi y puede realizar 3 acciones
    -Se inicia el turno con hambre 5, se suma 1 herida
    -Restaurar las acciones disponibles
    - Mostrar información del turno
    */
    public void iniciarTurno(){
        System.out.println("Empieza el turno de"+ getNombre());
        //verificamos el hambre
        if(hambre>=MAX_HAMBRE){
            System.out.println(getNombre()+ "tiene hambre maxima ,se sufrira una herida");
            recibirHerida(1);
        }
        //restaurar acciones
        accionesRestantes=maxAcciones;
        
    }
    //Cuando finaliza su turno,se incrementa una unidad de hambre
    public void finalizarTurno(){
        if(hambre<MAX_HAMBRE)
            hambre++;
        System.out.println(getNombre()+ "finaliza su turno.Hambre"+ hambre);
    }
    /*Acción 1 : mover
    - +1 acción mover a casilla adyacente
    -  +1acción extra por cada humano que hay en la casilla actual
    -  si hay 4 + humanos,está rodeado y no puede mover
   */ 
    @Override
    public void mover(Casilla casillaDestino){
        //Verifico si puede mover
        if(estado!=Estado.ACTIVO || accionesRestantes<=0){
           System.out.println(getNombre() + "Ya no tiene acciones para mover");
           return;
        }
        
        //Contar humano en casilla actual
        int humanoC=0;
        for(Entidad e: getCasillaActual().getOcupantes()){
            if(e instanceof Humano){
                humanoC++;
            }
        }
        //Gastar una accion por humano
        int gasto= 1+ humanoC;
        // En el caso de que haya 4 humanos o más, no se puede mover, está rodeado
        if (humanoC>=4){
            System.out.println(getNombre()+ "No puede mover,está rodeado de"+ humanoC+"Humanos");
            return;
        }
        // Verificar acciones suficientes
        if(accionesRestantes<=gasto){
            System.out.println(getNombre()+"No tiene suficientes accciones.");
            return;
        }
        // Verificar que es adyacente
        int distanciaX=Math.abs(getCasillaActual().getCoordenadaX()- casillaDestino.getCoordenadaX());
        int distanciaY= Math.abs(getCasillaActual().getCoordenadaY() - casillaDestino.getCoordenadaY());
        int distancia= distanciaX + distanciaY;
        if(distancia!=1){
            System.out.println("La casilla no es adyacente");
            return;
        }
        // Realizar movimiento
        getCasillaActual().eliminarEntidad(this);
        setCasillaActual(casillaDestino);
        casillaDestino.agregarEntidad(this);
        accionesRestantes-=gasto;
        System.out.println(getNombre()+ "se mueve a (" + casillaDestino.getCoordenadaX() + casillaDestino.getCoordenadaY()+").Sus acciones:"+accionesRestantes);
    }
    /*Buscar comida,esta acción hará que aparezca elementos aleatorios
    -un humano huidizo (30% de probabilidad), 
     -un conejo (50% de probabilidad)
     -nada (20% de probabilidad)
    paso:
        verificar si hay acción
        gastar una acción
        generar comida*/
    public void buscarComida(Juego juego){
        if(estado!=Estado.ACTIVO || accionesRestantes <= 0){
            System.out.println(getNombre()+ "Ya no tiene acciones disponibles,no puede buscar comida");
            return;
        }
        accionesRestantes--;
        System.out.println(getNombre() + "buscando comida...");
        //El juego gestiona la aparicion aleatoria
        juego.generarComidaAleatoria();
        
    }
    //Atacar pero puede eligir ataque si es normal o especial
    public void atacar(Casilla casillaObjetivo,Ataque ataqueSeleccionado){
        if(estado!=Estado.ACTIVO || accionesRestantes <= 0){
            System.out.println(getNombre()+ "Ya no tiene acciones disponibles,no puede atacar");
            return ;
        }
        accionesRestantes--;
        ataqueSeleccionado.ejecutar(this,casillaObjetivo);
    }
    //Recibir herida,El zombi es eliminado al recibir quinta herida
    public void recibirHerida(int cantidad){
        heridas+=cantidad;
        System.out.println(getNombre() + "recibe "+ cantidad + "heridas.Total de heridas"+ heridas);
        if(heridas>=MAX_HERIDAS){
            estado= estado.ELIMINADO;
            System.out.println(getNombre() + "Ha sido Eliminado");
        }
        
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
        comestiblesConsumidos.add(c);
    }
    //Registrar humanos eliminados
    public void registrarHumanoEliminado(Humano humano){
        humanosEliminados.add(humano);
    }
    //si Zombi come un huidizo,obtendra acciones extra
    public void incrementarAcciones(){
        maxAcciones++;
        System.out.println(getNombre()+ "Ha conseguido tener"+ maxAcciones + "acciones por turno");
    }
    
}
