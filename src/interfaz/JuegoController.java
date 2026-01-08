/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;
/**
 *
 * @author PC_BASMA
 */

import juego.*;

public class JuegoController {

    private Juego juego;
    private LogPanel logPanel;
    private PanelZombi panelZombi;

    public JuegoController(Juego juego) {
        this.juego = juego;
    }

    /* ================= CONEXIONES CON LA VISTA ================= */

    public void setPanelZombi(PanelZombi panel) {
        this.panelZombi = panel;
    }

    public void setLogPanel(LogPanel logPanel) {
        this.logPanel = logPanel;
    }

    private void log(String mensaje) {
        if (logPanel != null) {
            logPanel.addMensaje(mensaje);
        }
    }

    public void actualizarPanelZombi() {
        if (panelZombi != null && !juego.getZombis().isEmpty()) {
            panelZombi.actualizarZombi(getZombiActual());
        }
    }

    /* ================= ACCESO AL MODELO ================= */

    public Zombi getZombiActual() {
        return juego.getZombis().get(0); // versión simple (1 zombi)
    }

    public Tablero getTablero() {
        return juego.getTablero();
    }

    public boolean isJuegoTerminado() {
        return juego.juegoTerminado(); // ✅ MÉTODO REAL
    }

    /* ================= ACCIONES DEL PANEL ================= */

    public void modoMover() {
        log("Modo mover activado");
        // más adelante: seleccionar casilla desde el tablero
    }

    public void buscarComida() {
        Zombi z = getZombiActual();
        z.buscarComida(juego);
        log(z.getNombre() + " busca comida");
        actualizarPanelZombi();
    }

    public void modoAtacar() {
        log("Modo atacar activado");
        // más adelante: selector de ataque + casilla
    }

    public void noHacerNada() {
        Zombi z = getZombiActual();
        z.noHacerNada();
        log(z.getNombre() + " no hace nada");
        actualizarPanelZombi();
    }

    public void terminarTurnoZombi() {
        log("Turno del zombi terminado");
        juego.turnos();   // ✅ MÉTODO REAL
        actualizarPanelZombi();
    }
}