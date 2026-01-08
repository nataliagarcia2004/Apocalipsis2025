/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package juego;
/**
 *
 * @author Maria Fernandez
 */
import java.util.*;
import java.io.*;

public class Juego {
    private Tablero tablero;
    private List<Zombi> zombis;
    private List<Humano> humanos;
    private List<Conejo> conejos;
    private List<AtaqueEspecial> ataquesEspeciales;
    private int turnoActual;
    private boolean juegoTerminado;
    private boolean victoria;
    private int contadorHumanos = 1;
    private int contadorConejos = 1;
    private static final Random random = new Random();
    private StringBuilder logPartida;
    private static final Scanner SC = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== APOCALIPSIS 2025 ===");
        Juego juego = configurarPartida();
        if (juego == null) return;
        
        ejecutarBucleJuego(juego);
        juego.mostrarResultados();
    }

    private static Juego configurarPartida() {
        System.out.println("1. Nueva Partida\n2. Cargar Partida");
        System.out.print("Opcion: ");
        int opcion = leerEntero();
        
        if (opcion == 2) {
            return cargarPartidaDesdeArchivo();
        } else {
            return crearNuevaPartida();
        }
    }

    private static Juego cargarPartidaDesdeArchivo() {
        System.out.print("Ruta archivo: ");
        String ruta = SC.next();
        Juego juego = new Juego(1);
        try {
            juego.importarPartida(ruta);
            return juego;
        } catch (IOException e) {
            System.out.println("Error al cargar: " + e.getMessage());
            return null;
        }
    }

    private static Juego crearNuevaPartida() {
        int num = solicitarNumeroZombis();
        Juego juego = new Juego(num);
        juego.iniciarTablero(num);
        return juego;
    }

    private static int solicitarNumeroZombis() {
        int num = 0;
        while (num < 1 || num > 4) {
            System.out.print("Numero de Zombis (1-4): ");
            num = leerEntero();
        }
        return num;
    }

    private static void ejecutarBucleJuego(Juego juego) {
        while (!juego.juegoTerminado()) {
            juego.turnos();
            System.out.println("\n--- FIN TURNO " + juego.turnoActual + " ---");
            if (!procesarComandoUsuario(juego)) break;
        }
    }

    private static boolean procesarComandoUsuario(Juego juego) {
        System.out.println("[ENTER] Seguir | [V] Ver | [G] Guardar | [S] Salir");
        String cmd = SC.next().toUpperCase();
        
        switch (cmd) {
            case "V": 
                juego.visualizarEstado(); 
                return true;
            case "G": 
                guardarPartida(juego); 
                return true;
            case "S": 
                juego.forzarFin(); 
                return false;
            default: 
                return true;
        }
    }

    private static void guardarPartida(Juego juego) {
        try {
            System.out.print("Nombre archivo: ");
            juego.guardarPartida(SC.next());
        } catch (Exception e) {
            System.out.println("Error guardando.");
        }
    }

    private static int leerEntero() {
        while (!SC.hasNextInt()) SC.next();
        return SC.nextInt();
    }

    public Juego(int numZombis) {
        this.zombis = new ArrayList<>();
        this.humanos = new ArrayList<>();
        this.conejos = new ArrayList<>();
        this.ataquesEspeciales = new ArrayList<>();
        this.logPartida = new StringBuilder();
        this.turnoActual = 0;
        if (numZombis >= 1) this.tablero = new Tablero(numZombis);
    }

    public void iniciarTablero(int numZombis) {
        if (this.tablero == null) this.tablero = new Tablero(numZombis);
        registrarEvento("Iniciando con " + numZombis + " zombis.");
        crearAtaquesPorDefecto();
        inicializarZombis(numZombis);
        generarHumanosIniciales(numZombis);
    }

    private void crearAtaquesPorDefecto() {
        registrarAtaque(0, new AtaqueEspecial(0, "Golpe Brutal", 2, 4, 0));
        registrarAtaque(1, new AtaqueEspecial(1, "Zarpazo", 3, 4, 1));
        registrarAtaque(2, new AtaqueEspecial(2, "Mordisco Letal", 2, 3, 0));
    }

    private void inicializarZombis(int numZombis) {
        String[] nombres = {"Matias", "Ramon", "Luisma", "Carlos"};
        for (int i = 0; i < numZombis; i++) {
            Casilla inicio = tablero.obtenerCasillaInicial();
            int idAtaque = i % ataquesEspeciales.size();
            Zombi z = new Zombi(nombres[i % nombres.length], inicio, ataquesEspeciales.get(idAtaque));
            inicio.agregarEntidad(z);
            zombis.add(z);
            registrarEvento("Zombi " + z.getNombre() + " creado.");
        }
    }

    private void generarHumanosIniciales(int numZombis) {
        for (int i = 0; i < 3 * numZombis; i++) {
            generarHumano();
        }
    }

    public void turnos() {
        turnoActual++;
        registrarEvento("\n>>> TURNO " + turnoActual + " <<<");
        
        procesarTurnosZombis();
        procesarActivacionesHumanos();
        generarRefuerzos();
        verificarCondicionesFinJuego();
    }

    private void procesarTurnosZombis() {
        for (Zombi z : zombis) {
            if (z.getEstado() == Estado.ACTIVO) turnoZombi(z);
        }
    }

    private void procesarActivacionesHumanos() {
        registrarEvento("--- Activacion Humanos ---");
        List<Humano> copia = new ArrayList<>(humanos);
        for (Humano h : copia) {
            if (!h.estaEliminado()) h.activar(this);
        }
        humanos.removeIf(Humano::estaEliminado);
    }

    private void generarRefuerzos() {
        registrarEvento("--- Refuerzos ---");
        long vivos = zombis.stream().filter(z -> z.getEstado() == Estado.ACTIVO).count();
        for (int i = 0; i < vivos; i++) {
            generarHumano();
        }
    }

    private void verificarCondicionesFinJuego() {
        if (zombis.stream().anyMatch(z -> z.getEstado() == Estado.ELIMINADO)) {
            registrarEvento("Un zombi ha sido eliminado. DERROTA.");
            juegoTerminado = true;
            victoria = false;
            return;
        }
        
        boolean todosEnSalida = zombis.stream()
            .filter(z -> z.getEstado() == Estado.ACTIVO)
            .allMatch(z -> z.getCasillaActual().equals(tablero.getCasillaSalida()));
        
        boolean todosComieronHuidizo = zombis.stream()
            .filter(z -> z.getEstado() == Estado.ACTIVO)
            .allMatch(z -> z.getComestiblesConsumidos().stream()
                .anyMatch(c -> c instanceof Huidizo));
        
        if (todosEnSalida && todosComieronHuidizo) {
            registrarEvento("VICTORIA! Todos los zombis llegaron a la salida tras comer huidizos.");
            juegoTerminado = true;
            victoria = true;
        }
    }

    private void turnoZombi(Zombi z) {
        registrarEvento("\n> Turno " + z.getNombre());
        z.iniciarTurno();
        if (z.getEstado() == Estado.ELIMINADO) return;
        
        while (z.getAccionesRestantes() > 0 && z.getEstado() == Estado.ACTIVO) {
            mostrarEstadoTurno(z);
            ejecutarAccionZombi(z);
        }
        z.finalizarTurno();
    }

    private void mostrarEstadoTurno(Zombi z) {
        tablero.mostrarTablero(z);
        System.out.println("\n==========================================");
        System.out.println(" TURNO: " + z.getNombre().toUpperCase());
        System.out.println(" Pos: (" + z.getCasillaActual().getCoordenadaX() + "," + 
                          z.getCasillaActual().getCoordenadaY() + ")");
        System.out.println("------------------------------------------");
        System.out.println(" Hambre: " + z.getHambre() + "/5 | Heridas: " + z.getHeridas() + 
                          "/5 | Acciones: " + z.getAccionesRestantes());
        System.out.println("==========================================");
    }

    private void ejecutarAccionZombi(Zombi z) {
        mostrarMenuAcciones(z);
        int op = leerEntero();
        
        switch (op) {
            case 1: moverZombi(z); break;
            case 2: atacarConZombi(z, z.getAtaqueNormal()); break;
            case 3: ejecutarAtaqueEspecial(z); break;
            case 4: z.buscarComida(this); break;
            case 5: z.noHacerNada(); break;
            default: System.out.println("Opcion invalida.");
        }
    }

    private void mostrarMenuAcciones(Zombi z) {
        String nombreEsp = (z.getAtaqueEspecial() != null) ? 
                          z.getAtaqueEspecial().getNombre() : "Sin Ataque";
        System.out.println("1. Mover | 2. Devorar | 3. " + nombreEsp + 
                          " | 4. Buscar Comida | 5. Pasar");
        System.out.print("-> Opcion: ");
    }

    private void moverZombi(Zombi z) {
        System.out.print(" > X: ");
        int x = leerEntero();
        System.out.print(" > Y: ");
        int y = leerEntero();
        try {
            z.mover(tablero.obtenerCasilla(x, y));
        } catch (Exception e) {
            System.out.println(" (!) Movimiento invalido");
        }
    }

    private void atacarConZombi(Zombi z, Ataque a) {
        Casilla objetivo = solicitarCasillaObjetivo();
        if (objetivo == null) return;
        
        if (verificarAlcance(z, objetivo, a)) {
            z.atacar(objetivo, a);
        } else {
            System.out.println(" (!) Fuera de alcance.");
        }
    }

    private void ejecutarAtaqueEspecial(Zombi z) {
        if (z.getAtaqueEspecial() != null) {
            atacarConZombi(z, z.getAtaqueEspecial());
        } else {
            System.out.println(" (!) No tienes ataque especial.");
        }
    }

    private Casilla solicitarCasillaObjetivo() {
        System.out.print(" > Objetivo X: ");
        int x = leerEntero();
        System.out.print(" > Objetivo Y: ");
        int y = leerEntero();
        try {
            return tablero.obtenerCasilla(x, y);
        } catch (Exception e) {
            System.out.println(" (!) Casilla invalida.");
            return null;
        }
    }

    private boolean verificarAlcance(Zombi z, Casilla objetivo, Ataque a) {
        int dist = Math.abs(z.getCasillaActual().getCoordenadaX() - objetivo.getCoordenadaX()) +
                  Math.abs(z.getCasillaActual().getCoordenadaY() - objetivo.getCoordenadaY());
        return dist <= a.getAlcance();
    }

    public void generarHumano() {
        double p = random.nextDouble();
        Casilla c = tablero.obtenerCasillaAleatoria();
        Humano h;
        String id = String.valueOf(contadorHumanos++);
        
        if (p < 0.4) h = new Combatiente("S_" + id, c, Combatiente.Tipo.SOLDADO);
        else if (p < 0.65) h = new Combatiente("E_" + id, c, Combatiente.Tipo.ESPECIALISTA);
        else if (p < 0.9) h = new Combatiente("B_" + id, c, Combatiente.Tipo.BLINDADO);
        else h = new Ingeniero("I_" + id, c);
        
        c.agregarEntidad(h);
        humanos.add(h);
        registrarEvento("Spawn: " + h.getNombre());
    }

    public void generarComidaAleatoria() {
        double p = random.nextDouble();
        Casilla c = tablero.obtenerCasillaAleatoria();
        
        if (p < 0.3) {
            Huidizo h = new Huidizo("H_" + contadorHumanos++, c);
            c.agregarEntidad(h);
            humanos.add(h);
            registrarEvento("Huidizo encontrado!");
        } else if (p < 0.8) {
            Conejo co = new Conejo("C_" + contadorConejos++, c);
            c.agregarEntidad(co);
            conejos.add(co);
            registrarEvento("Conejo encontrado!");
        } else {
            registrarEvento("Nada.");
        }
    }

    public void importarPartida(String fichero) throws IOException {
        limpiarEstado();
        calcularTamanioTablero(fichero);
        cargarDatosDesdeArchivo(fichero);
        registrarEvento("Importado OK.");
        tablero.mostrarTablero(null);
    }

    private void limpiarEstado() {
        zombis.clear();
        humanos.clear();
        conejos.clear();
        ataquesEspeciales.clear();
        contadorHumanos = 1;
        contadorConejos = 1;
    }

    private void calcularTamanioTablero(String fichero) throws IOException {
        int max = 0, nZ = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split("\t");
                if (p.length > 0 && p[0].equals("Z")) nZ++;
                for (String s : p) {
                    try {
                        int v = Integer.parseInt(s);
                        if (v < 20) max = Math.max(max, v);
                    } catch (Exception e) {}
                }
            }
        }
        int zCalc = Math.max(nZ, 1);
        while ((6 + zCalc) < (max + 1) && zCalc < 4) zCalc++;
        this.tablero = new Tablero(zCalc);
        registrarEvento("Tablero importado tam: " + (6 + zCalc));
    }

    private void cargarDatosDesdeArchivo(String fichero) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String l;
            Zombi lastZombi = null;
            while ((l = br.readLine()) != null) {
                String[] p = l.split("\t");
                if (p.length < 1) continue;
                
                lastZombi = procesarLineaImportacion(p, lastZombi);
            }
        }
    }

    private Zombi procesarLineaImportacion(String[] p, Zombi lastZombi) {
        switch (p[0]) {
            case "A":
                cargarAtaque(p);
                return lastZombi;
            case "Z":
                return cargarZombi(p);
            case "Comido":
                if (lastZombi != null) lastZombi.registrarComestible(crearComestibleDummy(p[1]));
                return lastZombi;
            case "C":
                cargarConejo(p);
                return lastZombi;
            case "F":
                establecerSalida(p);
                return lastZombi;
            default:
                cargarHumano(p);
                return lastZombi;
        }
    }

    private void cargarAtaque(String[] p) {
        if (p.length >= 6) {
            int id = Integer.parseInt(p[1]);
            registrarAtaque(id, new AtaqueEspecial(id, p[2], 
                Integer.parseInt(p[3]), Integer.parseInt(p[4]), Integer.parseInt(p[5])));
        }
    }

    private Zombi cargarZombi(String[] p) {
        if (p.length >= 7) {
            int idA = Integer.parseInt(p[6]);
            if (idA >= ataquesEspeciales.size() || ataquesEspeciales.get(idA) == null) {
                registrarAtaque(idA, new AtaqueEspecial(idA, "Def", 1, 4, 0));
            }
            Casilla cz = tablero.obtenerCasilla(Integer.parseInt(p[4]), Integer.parseInt(p[5]));
            Zombi z = new Zombi(p[1], cz, ataquesEspeciales.get(idA));
            z.setHeridas(Integer.parseInt(p[2]));
            z.setHambre(Integer.parseInt(p[3]));
            cz.agregarEntidad(z);
            zombis.add(z);
            return z;
        }
        return null;
    }

    private void cargarConejo(String[] p) {
        if (p.length >= 4) {
            Casilla cc = tablero.obtenerCasilla(Integer.parseInt(p[2]), Integer.parseInt(p[3]));
            Conejo co = new Conejo(p[1], cc);
            cc.agregarEntidad(co);
            conejos.add(co);
        }
    }

    private void establecerSalida(String[] p) {
        if (p.length >= 3) {
            Casilla old = tablero.getCasillaSalida();
            if (old != null) old.setEsSalida(false);
            Casilla nueva = tablero.obtenerCasilla(Integer.parseInt(p[1]), Integer.parseInt(p[2]));
            nueva.setEsSalida(true);
        }
    }

    private void cargarHumano(String[] p) {
        if (p.length >= 3) {
            Casilla ch = tablero.obtenerCasilla(Integer.parseInt(p[1]), Integer.parseInt(p[2]));
            Humano h = null;
            String id = String.valueOf(contadorHumanos++);
            
            if (p[0].equals("S")) h = new Combatiente("S_" + id, ch, Combatiente.Tipo.SOLDADO);
            else if (p[0].equals("B")) h = new Combatiente("B_" + id, ch, Combatiente.Tipo.BLINDADO);
            else if (p[0].equals("E")) h = new Combatiente("E_" + id, ch, Combatiente.Tipo.ESPECIALISTA);
            else if (p[0].equals("I")) h = new Ingeniero("I_" + id, ch);
            else if (p[0].equals("H")) h = new Huidizo("H_" + id, ch);
            
            if (h != null) {
                ch.agregarEntidad(h);
                humanos.add(h);
            }
        }
    }

    private Comestible crearComestibleDummy(String tipo) {
        switch (tipo) {
            case "H": return new Huidizo("dummy", null);
            case "C": return new Conejo("dummy", null);
            case "S": return new Combatiente("dummy", null, Combatiente.Tipo.SOLDADO);
            case "B": return new Combatiente("dummy", null, Combatiente.Tipo.BLINDADO);
            case "E": return new Combatiente("dummy", null, Combatiente.Tipo.ESPECIALISTA);
            case "I": return new Ingeniero("dummy", null);
            default: return new Conejo("dummy", null);
        }
    }

    public void guardarPartida(String ruta) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            guardarAtaques(bw);
            guardarZombis(bw);
            guardarEntidadesTablero(bw);
            guardarSalida(bw);
        }
        System.out.println("Partida guardada en: " + ruta);
    }

    private void guardarAtaques(BufferedWriter bw) throws IOException {
        for (int i = 0; i < ataquesEspeciales.size(); i++) {
            AtaqueEspecial a = ataquesEspeciales.get(i);
            if (a != null) {
                bw.write("A\t" + i + "\t" + a.getNombre() + "\t" + a.getPotencia() + 
                        "\t" + a.getValorExito() + "\t" + a.getAlcance() + "\n");
            }
        }
    }

    private void guardarZombis(BufferedWriter bw) throws IOException {
        for (Zombi z : zombis) {
            int idAtaque = ataquesEspeciales.indexOf(z.getAtaqueEspecial());
            bw.write("Z\t" + z.getNombre() + "\t" + z.getHeridas() + "\t" + z.getHambre() + 
                    "\t" + z.getCasillaActual().getCoordenadaX() + "\t" + 
                    z.getCasillaActual().getCoordenadaY() + "\t" + idAtaque + "\t" + 
                    z.getComestiblesConsumidos().size() + "\n");
            
            for (Comestible c : z.getComestiblesConsumidos()) {
                bw.write("Comido\t" + obtenerTipoComestible(c) + "\n");
            }
        }
    }

    private void guardarEntidadesTablero(BufferedWriter bw) throws IOException {
        for (Humano h : humanos) {
            String tipo = obtenerTipoHumano(h);
            bw.write(tipo + "\t" + h.getCasillaActual().getCoordenadaX() + "\t" + 
                    h.getCasillaActual().getCoordenadaY() + "\n");
        }
        for (Conejo c : conejos) {
            bw.write("C\t" + c.getNombre() + "\t" + c.getCasillaActual().getCoordenadaX() + 
                    "\t" + c.getCasillaActual().getCoordenadaY() + "\n");
        }
    }

    private void guardarSalida(BufferedWriter bw) throws IOException {
        Casilla s = tablero.getCasillaSalida();
        bw.write("F\t" + s.getCoordenadaX() + "\t" + s.getCoordenadaY() + "\n");
    }

    private String obtenerTipoComestible(Comestible c) {
        if (c instanceof Huidizo) return "H";
        if (c instanceof Conejo) return "C";
        if (c instanceof Ingeniero) return "I";
        if (c instanceof Combatiente) {
            Combatiente.Tipo t = ((Combatiente) c).getTipo();
            if (t == Combatiente.Tipo.SOLDADO) return "S";
            if (t == Combatiente.Tipo.BLINDADO) return "B";
            if (t == Combatiente.Tipo.ESPECIALISTA) return "E";
        }
        return "C";
    }

    private String obtenerTipoHumano(Humano h) {
        if (h instanceof Huidizo) return "H";
        if (h instanceof Ingeniero) return "I";
        if (h instanceof Combatiente) {
            Combatiente.Tipo t = ((Combatiente) h).getTipo();
            if (t == Combatiente.Tipo.SOLDADO) return "S";
            if (t == Combatiente.Tipo.BLINDADO) return "B";
            if (t == Combatiente.Tipo.ESPECIALISTA) return "E";
        }
        return "S";
    }

    public void visualizarEstado() {
        tablero.mostrarTablero(null);
        System.out.println("\n=== ESTADO ZOMBIS ===");
        for (Zombi z : zombis) {
            System.out.println(z.getNombre() + ": Hambre=" + z.getHambre() + 
                             ", Heridas=" + z.getHeridas() + ", Estado=" + z.getEstado());
        }
        System.out.println("\n=== HUMANOS VIVOS: " + humanos.size() + " ===");
        System.out.println("=== CONEJOS VIVOS: " + conejos.size() + " ===");
    }

    public void mostrarResultados() {
        System.out.println("\n\n======================================");
        System.out.println(victoria ? "*** VICTORIA ***" : "*** DERROTA ***");
        System.out.println("======================================");
        System.out.println("Turnos jugados: " + turnoActual);
        
        for (Zombi z : zombis) {
            System.out.println("\n--- " + z.getNombre() + " ---");
            System.out.println("Comestibles devorados: " + z.getComestiblesConsumidos().size());
            System.out.println("Humanos eliminados: " + z.getHumanosEliminados().size());
        }
        System.out.println("\n======================================");
    }

    public void forzarFin() {
        juegoTerminado = true;
        victoria = false;
        registrarEvento("Juego finalizado manualmente.");
    }

    private void registrarEvento(String evento) {
        logPartida.append(evento).append("\n");
        System.out.println(evento);
    }

    public void registrarAtaque(int id, AtaqueEspecial ataque) {
        while (ataquesEspeciales.size() <= id) {
            ataquesEspeciales.add(null);
        }
        ataquesEspeciales.set(id, ataque);
    }

    public boolean juegoTerminado() { return juegoTerminado; }
    public Tablero getTablero() { return tablero; }
    public List<Zombi> getZombis() { return zombis; }
    public List<Humano> getHumanos() { return humanos; }
    public String getLog() { return logPartida.toString(); }
}