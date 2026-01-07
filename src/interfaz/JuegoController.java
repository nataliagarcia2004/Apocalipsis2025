/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import juego.*;



public class JuegoController {

    private Juego juego;
    private LogPanel logPanel;
    private PanelZombi panelZombi;

    
    public void setPanelZombi(PanelZombi panel) {
    this.panelZombi = panel;
}

   public void actualizarPanelZombi() {
    Zombi z = getZombiActual();
    if (panelZombi != null && z != null) {
        panelZombi.actualizarZombi(z);
    }
}



    public JuegoController(Juego juego) {
        this.juego = juego;
    }
    public void setLogPanel(LogPanel logPanel) {
        this.logPanel = logPanel;
    }

    public void log(String mensaje) {
        if (logPanel != null) {
            logPanel.addMensaje(mensaje);
        }
    }


    // Devuelve el zombi que juega actualmente
   public Zombi getZombiActual() {
    if (juego.getZombis().isEmpty()) {
        return null;
    }
    return juego.getZombis().get(0);
}


    // Ejecuta un turno completo del juego
    public void ejecutarTurno() {
        juego.ejecutarTurno();
    }

    // Mueve el zombi actual a una casilla
    public void mover(int x, int y) {
        Zombi z = getZombiActual();
        z.mover(juego.getTablero().obtenerCasilla(x, y));
    }

    // Realiza un ataque normal o especial
    public void atacar(int x, int y, boolean especial) {
        Zombi z = getZombiActual();
        Ataque ataque = especial ? z.getAtaqueEspecial() : z.getAtaqueNormal();
        z.atacar(juego.getTablero().obtenerCasilla(x, y), ataque);
    }

    // Devuelve el tablero (para la interfaz)
    public Tablero getTablero() {
        return juego.getTablero();
    }

    // Indica si el juego ha terminado
    public boolean isJuegoTerminado() {
        return juego.isJuegoTerminado();
    }

// ====== MÉTODOS PARA LA INTERFAZ (PanelAccionesZombi) ======

public void modoMover() {
    log("Modo mover activado");
    // más adelante aquí se pedirá la casilla destino
}

public void buscarComida() {
    Zombi z = getZombiActual();
    z.buscarComida(juego);
    log("El zombi busca comida");
    actualizarPanelZombi();
}

public void modoAtacar() {
    SelectorAtaqueDialog dialog =
        new SelectorAtaqueDialog(null, true);

    dialog.setVisible(true); // espera a que el usuario elija

    boolean especial = dialog.isAtaqueEspecial();

    log("Ataque seleccionado: " + (especial ? "ESPECIAL" : "NORMAL"));

    // De momento NO atacamos aún
    // Más adelante pediremos la casilla objetivo
}

public void noHacerNada() {
    Zombi z = getZombiActual();
    z.noHacerNada();
    log("El zombi no hace nada");
    actualizarPanelZombi();
}

public void terminarTurnoZombi() {
    log("Turno del zombi terminado");
    ejecutarTurno();
    actualizarPanelZombi();
}

}
