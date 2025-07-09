import java.util.Scanner;

/**
 * Problema 1 - Juego de roles
 * En un juego de rol, se desea implementar un sistema de combate en el que
 * participen diferentes tipos de personajes: guerreros, magos y arqueros. 
 * Cada personaje tiene atributos y habilidades únicas, así como diferentes métodos de ataque y defensa.

* El objetivo del juego es enfrentar a los personajes en batallas y determinar 
* el ganador en función de sus habilidades, estrategias y atributos. Los guerreros 
* se destacan por su fuerza y habilidades cuerpo a cuerpo, los magos por sus hechizos 
* y poderes mágicos, y los arqueros por su precisión y habilidades a distancia.

* El sistema debe permitir crear nuevos personajes de cada tipo, asignarles 
* atributos iniciales, como puntos de vida y nivel de experiencia, y permitirles 
* subir de nivel a medida que ganan batallas. Además, se debe implementar un algoritmo 
* de combate que evalúe las habilidades de cada personaje y determine el resultado de la batalla.

* Utilizando programación orientada a objetos, herencia y polimorfismo, implementa 
* el sistema de combate y las clases necesarias para representar a los diferentes 
* tipos de personajes. Asegúrate de que cada tipo de personaje tenga sus propias 
* habilidades y métodos de ataque y defensa, y que puedan interactuar entre sí en las batallas.

* Note

* Para solucionar lo anterior se debe generar lo siguiente:

* Un diagrama exclusivo que involucren las funcionalidades principales del juego.
* Una solución en lenguaje de programación Java. Usar Polimorfismo en la solución.
* Clase de prueba/ejecutor, que demuestre la funcionalidad del juego.
* 
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class Problema1_JuegoRoles {
    static Personaje guerrero;
    static Personaje mago;
    static Personaje arquero;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        guerrero = new Guerrero("Gigante", 3, 100);
        mago = new Mago("Invisible", 3, 200);
        arquero = new Arquero("Larga distancia", 3, 150);

        System.out.println("Guerrero(Atacante) vs Mago(defensor)");
        String resultado = pelear(guerrero, mago);    
        
        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println("Guerrero: " + guerrero);
        System.out.println("Mago: " + mago);
        System.out.println("Resultado: " + resultado);
        
        System.out.println("\nMago(Atacante) vs Arquero(defensor)");
        String resultado2 = pelear(guerrero, arquero);    
        
        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println("Mago: " + mago);
        System.out.println("Arquero: " + arquero);
        System.out.println("Resultado: " + resultado2);
    }

    public static String pelear(Personaje atacante, Personaje defensor) {
        Scanner sc = new Scanner(System.in);
        char opcion = 'S';
        String mensaje = "";

        System.out.println("¡Comienza el combate entre Atacante y Defensor!");
        int ronda = 1;

        do {
            System.out.println("\n--- Ronda " + ronda + " ---");

            boolean ganaAtaque = atacante.ataque(defensor);
            if (ganaAtaque) {
                defensor.vidas--;
                System.out.println("Atacante ataca con éxito.");
            } else {
                atacante.vidas--;
                System.out.println("Defensor esquiva el ataque.");
            }

            // Verificar si alguien murió tras ataque
            if (defensor.vidas <= 0) {
                atacante.experiencia++;
                atacante.batallasGanadas++;
                mensaje = "Ganó el atacante";
                System.out.println("Defensor ha caído.");
                break;
            } else if (atacante.vidas <= 0) {
                defensor.experiencia++;
                defensor.batallasGanadas++;
                mensaje = "Ganó el defensor";
                System.out.println("Atacante ha caído.");
                break;
            }

            // Defensa
            int defensaResultado = atacante.defensa(defensor);
            if (defensaResultado < 0) {
                atacante.defensa += defensaResultado;
                System.out.println("Defensor redujo la defensa de atacante" +
                                   " en " + (-defensaResultado) + " puntos. Defensa actual: " + atacante.defensa);
                if (atacante.defensa <= 0) {
                    atacante.vidas--;
                    atacante.defensa = 0;
                    System.out.println("Atacante ha perdido una vida por quedarse sin defensa.");
                }
            } else {
                System.out.println("Atacante bloqueó el ataque con éxito.");
            }

            // Verificar nuevamente vidas
            if (atacante.vidas <= 0) {
                defensor.experiencia++;
                defensor.batallasGanadas++;
                mensaje = "Ganó el defensor";
                System.out.println("Atacante ha caído.");
                break;
            } else if (defensor.vidas <= 0) {
                atacante.experiencia++;
                atacante.batallasGanadas++;
                mensaje = "Ganó el atacante";
                System.out.println("Defensor ha caído.");
                break;
            }

            // Mostrar estado
            System.out.println("Atacante" + " -> Vidas: " + atacante.vidas + ", Defensa: " + atacante.defensa);
            System.out.println("Defensor" + " -> Vidas: " + defensor.vidas + ", Defensa: " + defensor.defensa);

            // Continuar pelea
            System.out.print("¿Desea continuar el combate? (S/N): ");
            opcion = sc.nextLine().toUpperCase().charAt(0);

            ronda++;

        } while (opcion == 'S');

        if (mensaje.equals("")) {
            mensaje = "El combate terminó sin un vencedor.";
        }

        return mensaje;
    }

}


abstract class Personaje {
    public int vidas;
    public int experiencia;
    public int defensa;
    public int batallasGanadas;

    public Personaje() {
        // Constructor vacío
    }

    public Personaje(int vidas, int defensa) {
        this.vidas = vidas;
        this.defensa = defensa;
    }
    
    public abstract boolean ataque(Personaje personaje);
    public abstract int defensa(Personaje personaje);

    @Override
    public String toString() {
        return "Personaje{" + "vidas=" + vidas + ", experiencia=" + experiencia + ", batallasGanadas=" + batallasGanadas + ", defensa=" + defensa + '}';
    }
}

class Guerrero extends Personaje {
    public String habilidades;

    public Guerrero() {
        // Constructor vacío
    }

    public Guerrero(String habilidades, int vidas, int defensa) {
        super(vidas, defensa);
        this.habilidades = habilidades;
    }
    
    public boolean ataque(Personaje personaje) {
        return ((int) (Math.random() * 2) == 1) ? true : false;
    }
    
    public int defensa(Personaje personaje) {
        boolean efectivo = ((int) ((Math.random() * 2)) == 1) ? true : false;
        return (efectivo) ? 0 : -50;
    }

    @Override
    public String toString() {
        return "Guerrero{" + "habilidades=" + habilidades + '}' + super.toString();
    }
    
    
}

class Mago extends Personaje {
    public String estrategia;

    public Mago() {
        // Constructor
    }

    public Mago(String estrategia, int vidas, int defensa) {
        super(vidas, defensa);
        this.estrategia = estrategia;
    }
    
    public boolean ataque(Personaje personaje) {
        return false;
    }
    
    public int defensa(Personaje personaje) {
        boolean efectivo = ((int) ((Math.random() * 2)) == 1) ? true : false;
        return (efectivo) ? 0 : -100;
    }

    @Override
    public String toString() {
        return "Mago{" + "estrategia=" + estrategia + '}' + super.toString();
    }
}

class Arquero extends Personaje {
    public String atributo;

    public Arquero() {
        // Constructor vacío
    }
    
    public Arquero(String atributo, int vidas, int defensa) {
        super(vidas, defensa);
        this.atributo = atributo;
    }
    
    public boolean ataque(Personaje personaje) {
        return false;
    }
    
    public int defensa(Personaje personaje) {
        boolean efectivo = ((int) ((Math.random() * 2)) == 1) ? true : false;
        return (efectivo) ? 0 : -75;
    }
}