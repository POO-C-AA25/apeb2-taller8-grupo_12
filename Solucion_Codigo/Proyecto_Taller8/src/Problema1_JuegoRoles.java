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

        char opcion = 'S';
        String mensaje = "Ganó el guerrero";
        
        System.out.println("Guerrero vs Mago");
        do {
            boolean gana = guerrero.ataque(mago);

            if (gana) {
                mago.vidas -= 1;
            } else {
                guerrero.vidas -= 1;
            }

            if (mago.vidas <= 0) {
                guerrero.experiencia += 1;
                guerrero.batallasGanadas += 1;
                mensaje = "Ganó el Guerrero";
                break;
            } else if (guerrero.vidas <= 0) {
                mago.experiencia += 1;
                mago.batallasGanadas += 1;
                mensaje = "Ganó el Mago";
                break;
            }

            int defensa = guerrero.defensa(mago);
            if (defensa < 0) {
                guerrero.defensa += defensa;
                if (guerrero.defensa <= 0) {
                    guerrero.vidas -= 1;
                    guerrero.defensa = 0;
                }
            }

            if (guerrero.vidas <= 0) {
                mago.experiencia += 1;
                mago.batallasGanadas += 1;
                mensaje = "Ganó el Mago";
                break;
            } else if (mago.vidas <= 0) {
                guerrero.experiencia += 1;
                guerrero.batallasGanadas += 1;
                mensaje = "Ganó el Guerrero";
                break;
            }            
            System.out.println("Ataque y defensa hechos");
            System.out.print("¿Desea seguir haciéndolos competir? (S/N): ");
            opcion = sc.nextLine().toUpperCase().charAt(0);

        } while (opcion == 'S');

        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println("Guerrero: " + guerrero);
        System.out.println("Mago: " + mago);
        System.out.println("Resultado: " + mensaje);
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