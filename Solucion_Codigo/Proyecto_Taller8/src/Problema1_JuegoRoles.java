import java.util.Scanner;

/**
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
    }

    public static String pelear(Personaje atacante, Personaje defensor) {
        Scanner sc = new Scanner(System.in);
        char opcion = 'S';
        String mensaje = "Ganó el atacante";

        do {
            boolean gana = atacante.ataque(defensor);

            if (gana) {
                defensor.vidas -= 1;
            } else {
                atacante.vidas -= 1;
            }

            if (defensor.vidas <= 0) {
                atacante.experiencia += 1;
                atacante.batallasGanadas += 1;
                mensaje = "Ganó el atacante";
                break;
            } else if (atacante.vidas <= 0) {
                defensor.experiencia += 1;
                defensor.batallasGanadas += 1;
                mensaje = "Ganó el defensor";
                break;
            }

            int defensa = atacante.defensa(defensor);
            if (defensa < 0) {
                atacante.defensa += defensa;
                if (atacante.defensa <= 0) {
                    atacante.vidas -= 1;
                    atacante.defensa = 0;
                }
            }

            if (atacante.vidas <= 0) {
                defensor.experiencia += 1;
                defensor.batallasGanadas += 1;
                mensaje = "Ganó el defensor";
                break;
            } else if (defensor.vidas <= 0) {
                atacante.experiencia += 1;
                atacante.batallasGanadas += 1;
                mensaje = "Ganó el atacante";
                break;
            }

            System.out.println("Ataque y defensa hechos");
            System.out.print("¿Desea seguir haciéndolos competir? (S/N): ");
            opcion = sc.nextLine().toUpperCase().charAt(0);

        } while (opcion == 'S');

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