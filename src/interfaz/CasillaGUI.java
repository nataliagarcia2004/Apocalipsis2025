/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author PC_BASMA
 */
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;

import juego.Casilla;
import juego.Entidad;
import juego.Zombi;
import juego.Humano;
import juego.Conejo;
import juego.Huidizo;
import juego.Combatiente;
import juego.Ingeniero;

public class CasillaGUI extends JButton {

    private Casilla casilla;

    public CasillaGUI(Casilla casilla) {
        this.casilla = casilla;

        setFocusable(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setIcon(RecursosGUI.CASILLA);
        setText(null);

        actualizar();
    }

    public Casilla getCasilla() {
        return casilla;
    }

    public void actualizar() {

       

        // Limpiar por defecto
        setIcon(RecursosGUI.CASILLA);
        setText(null);

        for (Entidad e : casilla.getOcupantes()) {

            // Zombi (máxima prioridad)
            if (e instanceof Zombi) {
                setIcon(RecursosGUI.ZOMBI);
                return;
            }

            // Ingeniero (NO combatiente)
            if (e instanceof Ingeniero) {
                setIcon(RecursosGUI.HUMANO_INGENIERO);
                return;
            }

            // Humano huidizo
            if (e instanceof Huidizo) {
                setIcon(RecursosGUI.HUMANO_HUIDIZO);
                return;
            }

            // Humanos combatientes
            if (e instanceof Combatiente c) {
                switch (c.getTipo()) {
                    case SOLDADO:
                        setIcon(RecursosGUI.HUMANO_SOLDADO);
                        return;
                    case BLINDADO:
                        setIcon(RecursosGUI.HUMANO_BLINDADO);
                        return;
                    case ESPECIALISTA:
                        setIcon(RecursosGUI.HUMANO_ESPECIALISTA);
                        return;
                }
            }

            // Conejo
            if (e instanceof Conejo) {
                setIcon(RecursosGUI.CONEJO);
                return;
            }
        }
    }
}