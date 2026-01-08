/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

/**
 *
 * @author PC_BASMA
 */
import javax.swing.JPanel;
import java.awt.GridLayout;
import juego.Tablero;
import juego.Casilla;

public class TableroGUI extends JPanel {

    private Tablero tablero;
    private CasillaGUI[][] botones;

    public TableroGUI(Tablero tablero) {
        this.tablero = tablero;

        int tamaño = tablero.getTamaño();
        this.botones = new CasillaGUI[tamaño][tamaño];

        setLayout(new GridLayout(tamaño, tamaño));

        crearBotones();
    }

    private void crearBotones() {
        int tamaño = tablero.getTamaño();

        for (int y = tamaño - 1; y >= 0; y--) {
            for (int x = 0; x < tamaño; x++) {
                Casilla casilla = tablero.obtenerCasilla(x, y);
                CasillaGUI boton = new CasillaGUI(casilla);

                botones[x][y] = boton;
                add(boton);
            }
        }
    }

    public void actualizarTablero() {
        int tamaño = tablero.getTamaño();

        for (int x = 0; x < tamaño; x++) {
            for (int y = 0; y < tamaño; y++) {
                botones[x][y].actualizar();
            }
        }

        repaint();
    }
}
