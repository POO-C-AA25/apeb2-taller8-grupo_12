import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Problema 3 - Juego de fútbol "Estadísticas"
 * Se desea realizar una aplicación que permita a un periodista deportivo llevar 
 * las estadísticas de los jugadores de un equipo de fútbol para poder valorar su actuación en el partido.
 * Cada jugador se identifica por su nombre, número de dorsal y Rut
 * 
 * Los jugadores se dividen en tres categorías:
 * Atacantes
 * Defensores
 * Porteros
 * Para todos los jugadores se desea contabilizar el número de goles marcados, además en el caso de los jugadores de campo se contabilizan los pases realizados con éxito y el número de balones recuperados. En el caso de los porteros se contabilizan las atajadas realizadas.

* Valoración del jugador
* Cálculo base para todos los jugadores:
* valor_goles = goles * 30
* Valor adicional según tipo de jugador:

* Atacantes
* valor += recuperaciones * 3

* Defensores
* valor += recuperaciones * 4

* Porteros
* valor += atajadas * 5

* Note

* Se debe aplicar polimorfismo mediante la aplicación de herencia, encapsulamiento
* de atributos y comportamientos comunes, y especializar comportamiento según el tipo de jugador.
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class Problema2_EstadisticasJugadores {
    static Jugador atacante;
    static Jugador defensor;
    static Jugador portero;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        
        String[] nombres = {"Ramos", "Neuer", "Puyol", "Messi", "Ronaldo", "Navas"};
        char opcion = 'S';
        
        System.out.println("RESULTADOS ESTADÍSTICAS JUGADORES EN EL PARTIDO");
        System.out.println("================================================\n");           
        do {
            atacante = new Atacante(nombres[random.nextInt(nombres.length)], (int) (Math.random() * 30), 
                    String.valueOf((long) (Math.random() * (9999999999L - 1000000000L + 1)) + 1000000000L), 
                    (int) (Math.random() * 50), (int) (Math.random() * 100), (int) (Math.random() * 20));
            defensor = new Defensor(nombres[random.nextInt(nombres.length)], (int) (Math.random() * 30), 
                    String.valueOf((long) (Math.random() * (9999999999L - 1000000000L + 1)) + 1000000000L), 
                    (int) (Math.random() * 50), (int) (Math.random() * 100), (int) (Math.random() * 20));
            portero = new Portero(nombres[random.nextInt(nombres.length)], (int) (Math.random() * 30),
                    String.valueOf((long) (Math.random() * (9999999999L - 1000000000L + 1)) + 1000000000L), 
                    (int) (Math.random() * 50), (int) (Math.random() * 15));

            ArrayList<Jugador> jugadores = new ArrayList<>(Arrays.asList(atacante, portero, defensor));

            calculoEstadisticas(jugadores);            
             
            System.out.println(atacante.toString());
            System.out.println(defensor.toString());
            System.out.println(portero.toString());
            
            System.out.print("\n¿Desea calcular las estadísticas de otros jugadores? (S/N): ");
            opcion = sc.nextLine().toUpperCase().charAt(0);   
            System.out.println();
        } while (opcion == 'S');
    }
    
    public static void calculoEstadisticas(ArrayList<Jugador> jugadores) {
        atacante.calcularValor();
        defensor.calcularValor();
        portero.calcularValor();
    }
}


abstract class Jugador {
    public String nombre;
    public int numDorsal;
    public String rut;
    public int golesMarcados;
    public int valorTotal;

    public Jugador() {
        // Constructor vacío
    }   

    public Jugador(String nombre, int numDorsal, String rut, int golesMarcados) {
        this.nombre = nombre;
        this.numDorsal = numDorsal;
        this.rut = rut;
        this.golesMarcados = golesMarcados;
    }   
    
    public abstract void calcularValor();

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Dorsal: " + numDorsal + 
               " | Goles: " + golesMarcados + " | Valor: " + valorTotal;
    }
}

class Atacante extends Jugador {
    public int pasesExitosos;
    public int recuperaciones;
    
     public Atacante() {
        // Constructor vacío
    }
    
    public Atacante(String nombre, int numDorsal, String rut, int golesMarcados, int pasesExitosos, int recuperaciones) {
        super(nombre, numDorsal, rut, golesMarcados);
        this.pasesExitosos = pasesExitosos;
        this.recuperaciones = recuperaciones;
    }  
    
    @Override
    public void calcularValor() {
        int valor_goles = this.golesMarcados * 30;
        int valor_adicional = recuperaciones * 3;
        this.valorTotal = valor_goles + valor_adicional; 
        
    }

    @Override
    public String toString() {
        return "=== ATACANTE ===\n" +
               "Pases exitosos: " + pasesExitosos + "\n" +
               "Recuperaciones: " + recuperaciones + "\n" +
               super.toString();
    }
}

class Defensor extends Jugador {
    public int pasesExitosos;
    public int recuperaciones;
    
    public Defensor() {
        // Constructor vacío
    }
    
    public Defensor(String nombre, int numDorsal, String rut, int golesMarcados, int pasesExitosos, int recuperaciones) {
        super(nombre, numDorsal, rut, golesMarcados);
        this.pasesExitosos = pasesExitosos;
        this.recuperaciones = recuperaciones;
    }
    
    @Override
    public void calcularValor() {
        int valor_goles = this.golesMarcados * 30;
        int valor_adicional = recuperaciones * 4;
        this.valorTotal = valor_goles + valor_adicional; 
    }

    @Override
    public String toString() {
        return "=== DEFENSOR ===\n" +
               "Pases exitosos: " + pasesExitosos + "\n" +
               "Recuperaciones: " + recuperaciones + "\n" +
               super.toString();
    }
}

class Portero extends Jugador {
    public int atajadas;
    
    public Portero() {
        // Constructor vacío
    }
    
    public Portero(String nombre, int numDorsal, String rut, int golesMarcados, int atajadas) {
        super(nombre, numDorsal, rut, golesMarcados);
        this.atajadas = atajadas;
    }
    
    @Override
    public void calcularValor() {
        int valor_goles = this.golesMarcados * 30;
        int valor_adicional = atajadas * 5;
        this.valorTotal = valor_goles + valor_adicional; 
    }

    @Override
    public String toString() {
        return "=== PORTERO ===\n" +
               "Atajadas: " + atajadas + "\n" +
               super.toString();
    }
}