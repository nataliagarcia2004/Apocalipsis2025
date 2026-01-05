/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package juego;

/**
 *
 * @author Maria Fernandez
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class Juego {

    // Listas principales
    private List<Zombi> zombis;
    private List<Humano> humanos;
    private List<Conejo> conejos;
    private List<AtaqueEspecial> ataquesEspeciales;

    private Tablero tablero;
    private int turnoActual;
    private boolean juegoTerminado;
    private boolean victoria;
    private int contadorHumanos;
    private int contadorConejos;
    private static final Random random = new Random();
    private StringBuilder logPartida;

    // Scanner compartido para la interacción por consola
    private static final Scanner SC = new Scanner(System.in);
    
    // MAIN 
    public static void main(String[] args) {

        System.out.println("=== APOCALIPSIS 2025 ===");

        int numeroZombis;
        while (true) {
            System.out.print("Introduce numero de zombis (1-4): ");
            numeroZombis = leerEntero();
            if (numeroZombis >= 1 && numeroZombis <= 4) {
                break;
            }
            System.out.println("Valor no valido. Debe estar entre 1 y 4.");
        }

        Juego juego = new Juego(numeroZombis);

        // Crear zombis con nombres básicos (se podría pedir nombre al usuario)
        for (int i = 1; i <= numeroZombis; i++) {
            Zombi z = new Zombi("Zombi_" + i, null, null);
            juego.agregarZombi(z);
        }

        // Bucle principal
        while (!juego.isJuegoTerminado()) {
            juego.ejecutarTurno();
            // Permitir salir manualmente
            System.out.print("\n¿Continuar? (S/N): ");
            String resp = SC.next().trim().toUpperCase();
            if (resp.equals("N")) {
                juego.finalizarJuego();
                break;
            }
        }

        // Registros finales
        juego.mostrarRegistroComestibles();
        juego.mostrarRegistroEliminaciones();

        String resultado = juego.isVictoria()
                ? "LOS ZOMBIS HAN GANADO"
                : "LOS ZOMBIS HAN PERDIDO";
        juego.registrarEvento("\n" + resultado);
    }

    private static int leerEntero() {
        while (!SC.hasNextInt()) {
            System.out.print("Introduce un numero entero: ");
            SC.next();
        }
        return SC.nextInt();
    }

    
    // CONSTRUCTOR
    
    public Juego(int numeroZombis) {
        if (numeroZombis < 1 || numeroZombis > 4) {
            throw new IllegalArgumentException("Numero de zombis debe ser entre 1 y 4");
        }

        this.zombis = new ArrayList<>();
        this.humanos = new ArrayList<>();
        this.conejos = new ArrayList<>();
        this.ataquesEspeciales = new ArrayList<>();
        this.tablero = new Tablero(numeroZombis);
        this.turnoActual = 0;
        this.juegoTerminado = false;
        this.victoria = false;
        this.contadorHumanos = 1;
        this.contadorConejos = 1;
        this.logPartida = new StringBuilder();

        registrarEvento("=== NUEVA PARTIDA INICIADA CON " + numeroZombis + " ZOMBI(S) ===\n");
    }

    // INICIALIZACIÓN ENTIDADES
    
    public void agregarZombi(Zombi zombi) {
        Casilla casillaInicial = tablero.obtenerCasillaInicial();
        zombi.setCasillaActual(casillaInicial);
        casillaInicial.agregarEntidad(zombi);
        zombis.add(zombi);

        registrarEvento("Zombi '" + zombi.getNombre() + "' agregado en posicion inicial (0,0)");

        // 3 humanos combatientes aleatorios por zombi
        for (int i = 0; i < 3; i++) {
            generarHumanoCombatienteAleatorio();
        }
    }

    public void registrarAtaqueEspecial(int id, AtaqueEspecial ataque) {
        while (ataquesEspeciales.size() <= id) {
            ataquesEspeciales.add(null);
        }
        ataquesEspeciales.set(id, ataque);
        registrarEvento("Ataque especial registrado: ID=" + id + ", Nombre=" + ataque.getNombre());
    }

    // 
    // GENERACIÓN DE ENTIDADES
    // 
    public void generarHumanoCombatienteAleatorio() {
        double prob = random.nextDouble();
        Combatiente.Tipo tipo;

        if (prob < 0.40) {
            tipo = Combatiente.Tipo.SOLDADO;
        } else if (prob < 0.65) {
            tipo = Combatiente.Tipo.ESPECIALISTA;
        } else if (prob < 0.90) {
            tipo = Combatiente.Tipo.BLINDADO;
        } else {
            generarIngeniero();
            return;
        }

        String nombre = tipo.toString() + "_" + contadorHumanos++;
        Casilla casillaAleatoria = tablero.obtenerCasillaAleatoria();
        Combatiente humano = new Combatiente(nombre, casillaAleatoria, tipo);
        casillaAleatoria.agregarEntidad(humano);
        humanos.add(humano);

        registrarEvento("Aparece " + tipo + " en (" +
                casillaAleatoria.getCoordenadaX() + "," +
                casillaAleatoria.getCoordenadaY() + ")");
    }

    private void generarIngeniero() {
        String nombre = "INGENIERO_" + contadorHumanos++;
        Casilla casillaAleatoria = tablero.obtenerCasillaAleatoria();
        Ingeniero ingeniero = new Ingeniero(nombre, casillaAleatoria);
        casillaAleatoria.agregarEntidad(ingeniero);
        humanos.add(ingeniero);

        registrarEvento("¡Aparece INGENIERO en (" +
                casillaAleatoria.getCoordenadaX() + "," +
                casillaAleatoria.getCoordenadaY() + ")!");
    }

    public void generarComidaAleatoria() {
        double prob = random.nextDouble();

        if (prob < 0.30) {
            Casilla casillaAleatoria = tablero.obtenerCasillaAleatoria();
            String nombre = "Huidizo_" + contadorHumanos++;
            Huidizo huidizo = new Huidizo(nombre, casillaAleatoria);
            casillaAleatoria.agregarEntidad(huidizo);
            humanos.add(huidizo);
            registrarEvento("  ¡Aparece Humano Huidizo en (" +
                    casillaAleatoria.getCoordenadaX() + "," +
                    casillaAleatoria.getCoordenadaY() + ")!");
        } else if (prob < 0.80) {
            Casilla casillaAleatoria = tablero.obtenerCasillaAleatoria();
            String nombre = "Conejo_" + contadorConejos++;
            Conejo conejo = new Conejo(nombre, casillaAleatoria);
            casillaAleatoria.agregarEntidad(conejo);
            conejos.add(conejo);
            registrarEvento("  ¡Aparece Conejo en (" +
                    casillaAleatoria.getCoordenadaX() + "," +
                    casillaAleatoria.getCoordenadaY() + ")!");
        } else {
            registrarEvento("  No aparece nada...");
        }
    }

    
    // FLUJO DEL JUEGO
    
    public void ejecutarTurno() {
        if (juegoTerminado) {
            registrarEvento("El juego ya ha terminado.");
            return;
        }

        turnoActual++;
        registrarEvento("\n========== TURNO " + turnoActual + " ==========");

        // FASE 1: Turnos de cada zombi (INTERACTIVOS)
        for (Zombi zombi : zombis) {
            if (zombi.getEstado() == Estado.ACTIVO) {
                ejecutarTurnoZombiInteractivo(zombi);
            }
        }

        // FASE 2: Activación de humanos
        activarHumanos();

        // FASE 3: Nuevos humanos combatientes
        registrarEvento("\n--- Fase de aparicion de nuevos humanos ---");
        for (int i = 0; i < zombis.size(); i++) {
            generarHumanoCombatienteAleatorio();
        }

        // FASE 4: Comprobar victoria/derrota
        verificarCondicionesFinales();

        // Mostrar tablero
        tablero.mostrarTablero();
    }

    /**
     * Turno interactivo de un zombi: el jugador elige las acciones desde consola
     */
    private void ejecutarTurnoZombiInteractivo(Zombi zombi) {
        registrarEvento("\n--- Turno de " + zombi.getNombre() + " ---");
        zombi.iniciarTurno();

        if (zombi.getEstado() == Estado.ELIMINADO) {
            registrarEvento(zombi.getNombre() + " ha sido eliminado por heridas.");
            return;
        }

        while (zombi.getAccionesRestantes() > 0) {
            System.out.println("\nAcciones restantes de " + zombi.getNombre() + ": " + zombi.getAccionesRestantes());
            System.out.println("Casilla actual: (" +
                    zombi.getCasillaActual().getCoordenadaX() + "," +
                    zombi.getCasillaActual().getCoordenadaY() + ")");
            System.out.println("Hambre: " + zombi.getHambre() + " | Heridas: " + zombi.getHeridas());

            System.out.println("\nElige accion:");
            System.out.println("1) Moverse");
            System.out.println("2) Atacar (Devorar)");
            System.out.println("3) Atacar (Especial)");
            System.out.println("4) Buscar comida");
            System.out.println("5) No hacer nada");
            System.out.print("Opcion: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1:
                    manejarMovimiento(zombi);
                    break;
                case 2:
                    manejarAtaque(zombi, false);
                    break;
                case 3:
                    manejarAtaque(zombi, true);
                    break;
                case 4:
                    zombi.buscarComida(this);
                    break;
                case 5:
                    zombi.noHacerNada();
                    break;
                default:
                    System.out.println("Opcion no va"
                            + "lida.");
            }

            if (zombi.getEstado() == Estado.ELIMINADO) {
                registrarEvento(zombi.getNombre() + " ha sido eliminado durante su turno.");
                break;
            }
        }

        zombi.finalizarTurno();
    }

    private void manejarMovimiento(Zombi zombi) {
        Casilla actual = zombi.getCasillaActual();
        System.out.println("Mover desde (" + actual.getCoordenadaX() + "," + actual.getCoordenadaY() + ")");
        System.out.print("Introduce destino X: ");
        int x = leerEntero();
        System.out.print("Introduce destino Y: ");
        int y = leerEntero();

        Casilla destino;
        try {
            destino = tablero.obtenerCasilla(x, y);
        } catch (IllegalArgumentException e) {
            System.out.println("Casilla fuera de tablero.");
            return;
        }
        zombi.mover(destino);
    }

    private void manejarAtaque(Zombi zombi, boolean especial) {
        Casilla actual = zombi.getCasillaActual();
        System.out.println("Ataque desde (" + actual.getCoordenadaX() + "," + actual.getCoordenadaY() + ")");
        System.out.print("Introduce X objetivo: ");
        int x = leerEntero();
        System.out.print("Introduce Y objetivo: ");
        int y = leerEntero();

        Casilla objetivo;
        try {
            objetivo = tablero.obtenerCasilla(x, y);
        } catch (IllegalArgumentException e) {
            System.out.println("Casilla fuera de tablero.");
            return;
        }

        Ataque ataque = especial ? zombi.getAtaqueEspecial() : zombi.getAtaqueNormal();
        if (ataque == null) {
            System.out.println("El zombi no tiene ataque especial configurado.");
            return;
        }
        zombi.atacar(objetivo, ataque);
    }

    private void activarHumanos() {
        registrarEvento("\n--- Fase de activacion de humanos ---");
        List<Humano> humanosActivos = new ArrayList<>(humanos);

        for (Humano humano : humanosActivos) {
            if (!humano.estaEliminado()) {
                humano.activar(this);
            }
        }
        humanos.removeIf(Humano::estaEliminado);
    }

    private void verificarCondicionesFinales() {
        for (Zombi zombi : zombis) {
            if (zombi.getEstado() == Estado.ELIMINADO) {
                juegoTerminado = true;
                victoria = false;
                registrarEvento("\n*** DERROTA: " + zombi.getNombre() + " ha sido eliminado ***");
                return;
            }
        }

        boolean todosEnSalida = true;
        boolean todosComieronHuidizo = true;

        for (Zombi zombi : zombis) {
            if (!zombi.getCasillaActual().esSalida()) {
                todosEnSalida = false;
            }
            boolean comioHuidizo = false;
            for (Comestible c : zombi.getComestiblesConsumidos()) {
                if (c instanceof Huidizo) {
                    comioHuidizo = true;
                    break;
                }
            }
            if (!comioHuidizo) {
                todosComieronHuidizo = false;
            }
        }

        if (todosEnSalida && todosComieronHuidizo) {
            juegoTerminado = true;
            victoria = true;
            registrarEvento("\n*** ¡VICTORIA! Todos los zombis han llegado a la salida ***");
        }
    }

    
    // IMPORTAR / GUARDAR PARTIDA
    
    public void importarPartida(String rutaArchivo) throws IOException {
        registrarEvento("\n=== IMPORTANDO PARTIDA DESDE: " + rutaArchivo + " ===");
        Zombi ultimoZombi = null;

        BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
        String linea;

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split("\t");
            if (partes.length == 0) continue;

            String tipo = partes[0];

            switch (tipo) {
                case "A":
                    int idAtaque = Integer.parseInt(partes[1]);
                    String nombreAtaque = partes[2];
                    int potencia = Integer.parseInt(partes[3]);
                    int valorExito = Integer.parseInt(partes[4]);
                    int alcance = Integer.parseInt(partes[5]);
                    AtaqueEspecial ataque = new AtaqueEspecial(idAtaque, nombreAtaque, potencia, valorExito, alcance);
                    registrarAtaqueEspecial(idAtaque, ataque);
                    break;

                case "Z":
                    String nombreZombi = partes[1];
                    int heridas = Integer.parseInt(partes[2]);
                    int hambre = Integer.parseInt(partes[3]);
                    int xZ = Integer.parseInt(partes[4]);
                    int yZ = Integer.parseInt(partes[5]);
                    int idAtaqueZ = Integer.parseInt(partes[6]);
                    int nComidos = Integer.parseInt(partes[7]);

                    AtaqueEspecial ataqueZombi = ataquesEspeciales.get(idAtaqueZ);
                    Casilla casillaZ = tablero.obtenerCasilla(xZ, yZ);
                    Zombi zombi = new Zombi(nombreZombi, casillaZ, ataqueZombi);
                    zombi.setHeridas(heridas);
                    zombi.setHambre(hambre);
                    casillaZ.agregarEntidad(zombi);
                    zombis.add(zombi);
                    ultimoZombi = zombi;
                    break;

                case "Comido":
                    if (ultimoZombi != null) {
                        String tipoComido = partes[1];
                        Comestible c = crearComestibleHistorico(tipoComido);
                        if (c != null) {
                            ultimoZombi.registrarComestible(c);
                            registrarEvento("Zombi " + ultimoZombi.getNombre() +
                                    " registro comestible historico: " + tipoComido);
                        }
                    }
                    break;

                case "S":
                case "B":
                case "E":
                case "I":
                case "H":
                    crearHumanoDesdeCodigo(tipo, Integer.parseInt(partes[1]), Integer.parseInt(partes[2]));
                    break;

                case "C":
                    String nombreConejo = partes[1];
                    int xC = Integer.parseInt(partes[2]);
                    int yC = Integer.parseInt(partes[3]);
                    Casilla casillaC = tablero.obtenerCasilla(xC, yC);
                    Conejo conejo = new Conejo(nombreConejo, casillaC);
                    casillaC.agregarEntidad(conejo);
                    conejos.add(conejo);
                    break;

                case "F":
                    registrarEvento("Casilla de salida confirmada en: (" + partes[1] + "," + partes[2] + ")");
                    break;
            }
        }
        br.close();
        registrarEvento("=== PARTIDA IMPORTADA EXITOSAMENTE ===\n");
    }

    private Comestible crearComestibleHistorico(String codigo) {
        switch (codigo) {
            case "S":
                return new Combatiente("Soldado_hist", null, Combatiente.Tipo.SOLDADO);
            case "H":
                return new Huidizo("Huidizo_hist", null);
            case "I":
                return new Ingeniero("Ingeniero_hist", null);
            case "C":
                return new Conejo("Conejo_hist", null);
            default:
                return null;
        }
    }

    private void crearHumanoDesdeCodigo(String codigo, int x, int y) {
        Casilla casilla = tablero.obtenerCasilla(x, y);
        switch (codigo) {
            case "S":
                Combatiente soldado = new Combatiente("Soldado_" + contadorHumanos++, casilla,
                        Combatiente.Tipo.SOLDADO);
                casilla.agregarEntidad(soldado);
                humanos.add(soldado);
                break;
            case "B":
                Combatiente blindado = new Combatiente("Blindado_" + contadorHumanos++, casilla,
                        Combatiente.Tipo.BLINDADO);
                casilla.agregarEntidad(blindado);
                humanos.add(blindado);
                break;
            case "E":
                Combatiente especialista = new Combatiente("Especialista_" + contadorHumanos++, casilla,
                        Combatiente.Tipo.ESPECIALISTA);
                casilla.agregarEntidad(especialista);
                humanos.add(especialista);
                break;
            case "I":
                Ingeniero ingeniero = new Ingeniero("Ingeniero_" + contadorHumanos++, casilla);
                casilla.agregarEntidad(ingeniero);
                humanos.add(ingeniero);
                break;
            case "H":
                Huidizo huidizo = new Huidizo("Huidizo_" + contadorHumanos++, casilla);
                casilla.agregarEntidad(huidizo);
                humanos.add(huidizo);
                break;
        }
    }

    public void guardarPartida(String rutaArchivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo));

        for (int i = 0; i < ataquesEspeciales.size(); i++) {
            AtaqueEspecial ataque = ataquesEspeciales.get(i);
            if (ataque != null) {
                bw.write("A\t" + i + "\t" + ataque.getNombre() + "\t" +
                        ataque.getPotencia() + "\t" + ataque.getValorExito() + "\t" +
                        ataque.getAlcance() + "\n");
            }
        }

        for (Zombi zombi : zombis) {
            int idAtaque = ataquesEspeciales.indexOf(zombi.getAtaqueEspecial());
            List<Comestible> comidos = zombi.getComestiblesConsumidos();

            bw.write("Z\t" + zombi.getNombre() + "\t" + zombi.getHeridas() + "\t" +
                    zombi.getHambre() + "\t" + zombi.getCasillaActual().getCoordenadaX() + "\t" +
                    zombi.getCasillaActual().getCoordenadaY() + "\t" + idAtaque + "\t" +
                    comidos.size() + "\n");

            for (Comestible c : comidos) {
                String tipoComestible = obtenerCodigoComestible(c);
                bw.write("Comido\t" + tipoComestible + "\n");
            }
        }

        for (Humano humano : humanos) {
            String codigo = obtenerCodigoHumano(humano);
            bw.write(codigo + "\t" + humano.getCasillaActual().getCoordenadaX() + "\t" +
                    humano.getCasillaActual().getCoordenadaY() + "\n");
        }

        for (Conejo conejo : conejos) {
            bw.write("C\t" + conejo.getNombre() + "\t" +
                    conejo.getCasillaActual().getCoordenadaX() + "\t" +
                    conejo.getCasillaActual().getCoordenadaY() + "\n");
        }

        Casilla salida = tablero.getCasillaSalida();
        bw.write("F\t" + salida.getCoordenadaX() + "\t" + salida.getCoordenadaY() + "\n");

        bw.close();
        registrarEvento("Partida guardada en: " + rutaArchivo);
    }

    private String obtenerCodigoComestible(Comestible c) {
        if (c instanceof Ingeniero) return "I";
        if (c instanceof Combatiente) {
            Combatiente.Tipo tipo = ((Combatiente) c).getTipo();
            switch (tipo) {
                case SOLDADO:
                    return "S";
                case ESPECIALISTA:
                    return "E";
                case BLINDADO:
                    return "B";
            }
        }
        if (c instanceof Huidizo) return "H";
        if (c instanceof Conejo) return "C";
        return "?";
    }

    private String obtenerCodigoHumano(Humano h) {
        if (h instanceof Ingeniero) return "I";
        if (h instanceof Combatiente) {
            Combatiente.Tipo tipo = ((Combatiente) h).getTipo();
            switch (tipo) {
                case SOLDADO:
                    return "S";
                case ESPECIALISTA:
                    return "E";
                case BLINDADO:
                    return "B";
            }
        }
        if (h instanceof Huidizo) return "H";
        return "?";
    }

    
    // LOG Y REGISTROS
    
    public void registrarEvento(String mensaje) {
        logPartida.append(mensaje).append("\n");
        System.out.println(mensaje);
    }

    public String obtenerLog() {
        return logPartida.toString();
    }

    public void guardarLog(String rutaArchivo) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo));
        bw.write(logPartida.toString());
        bw.close();
        System.out.println("Log guardado en: " + rutaArchivo);
    }

    public void mostrarRegistroComestibles() {
        registrarEvento("\n========== REGISTRO DE COMESTIBLES ==========");
        for (Zombi zombi : zombis) {
            registrarEvento("\n" + zombi.getNombre() + ":");
            registrarEvento("  Comestibles consumidos: " + zombi.getComestiblesConsumidos().size());
            for (Comestible c : zombi.getComestiblesConsumidos()) {
                registrarEvento("    - DEVORADO: " + obtenerNombreComestible(c));
            }
        }
    }

    public void mostrarRegistroEliminaciones() {
        registrarEvento("\n========== REGISTRO DE ELIMINACIONES ==========");
        for (Zombi zombi : zombis) {
            registrarEvento("\n" + zombi.getNombre() + ":");
            registrarEvento("  Humanos eliminados: " + zombi.getHumanosEliminados().size());
            for (Humano h : zombi.getHumanosEliminados()) {
                registrarEvento("    - ELIMINADO: " + h.getNombre());
            }
        }
    }

    private String obtenerNombreComestible(Comestible c) {
        if (c instanceof Entidad) {
            return ((Entidad) c).getNombre();
        }
        return c.getClass().getSimpleName();
    }

    
    // GETTERS / CONTROL
    
    public Tablero getTablero() { return tablero; }
    public List<Zombi> getZombis() { return new ArrayList<>(zombis); }
    public List<Humano> getHumanos() { return new ArrayList<>(humanos); }
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public boolean isVictoria() { return victoria; }
    public int getTurnoActual() { return turnoActual; }

    public void finalizarJuego() {
        juegoTerminado = true;
        registrarEvento("\n*** JUEGO FINALIZADO MANUALMENTE ***");
    }
}
