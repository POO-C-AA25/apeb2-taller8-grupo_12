import java.util.ArrayList;
import java.util.UUID;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class Problema6_Conflicto {
    public static void main(String[] args) {
        Simulador sim = new Simulador();

        // Crear naciones quemadas
        sim.agregarNacion(new NacionAvanzada("NationA", 50_000_000, 1_200_000_000, 80, 20));
        sim.agregarNacion(new NacionDesarrollo("NationB", 20_000_000,  300_000_000, 60, 0.5));
        sim.agregarNacion(new NacionAvanzada("NationC",100_000_000,2_500_000_000L, 90, 15));
        sim.agregarNacion(new NacionDesarrollo("NationD", 30_000_000,  450_000_000, 70, 0.3));

        sim.iniciarSimulacion();
        System.out.println(sim.reporteFinal());
    }
}

abstract class Nacion {
    public String nombre;
    public long poblacion;
    public double recursosEconomicos;
    public int poderMilitar;        // 1..100
    public boolean enConflicto;
    public ArrayList<Nacion> aliados;
    
    public Nacion() {
        // Constructor vacío
    }

    public Nacion(String nombre, long poblacion, double recursosEconomicos, int poderMilitar) {
        this.nombre = nombre;
        this.poblacion = poblacion;
        this.recursosEconomicos = recursosEconomicos;
        this.poderMilitar = poderMilitar;
        this.enConflicto = false;
        this.aliados = new ArrayList<>();
    }

    public void agregarAliado(Nacion n) {
        aliados.add(n);
    }

    public abstract double calcularImpacto();

    public void iniciarConflicto(Nacion otra) {
        enConflicto = true;
        otra.enConflicto = true;
        Conflicto c = new Conflicto(this, otra);
        c.simular();
        c.aplicarConsecuencias();
    }

    @Override
    public String toString() {
        return String.format(
            "Nación: %s\nPoblación: %d\nRecursos: %.2f\nPoder Militar: %d\nEn Conflicto: %b\nAliados: %d",
            nombre, poblacion, recursosEconomicos, poderMilitar, enConflicto, aliados.size()
        );
    }
}

class NacionAvanzada extends Nacion {
    public double bonoTecnologia;  // porcentaje extra al poder militar

    public NacionAvanzada() {
        // Constructor vacío
    }
    
    public NacionAvanzada(String nombre, long poblacion, double recursos, int poderMilitar, double bonoTecnologia) {
        super(nombre, poblacion, recursos, poderMilitar);
        this.bonoTecnologia = bonoTecnologia;
    }

    @Override
    public double calcularImpacto() {
        double impact = poderMilitar + (poderMilitar * bonoTecnologia / 100);
        if (impact > 100) {
            impact = 100;
        }
        return impact;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("\n(Bono Tecnología: %.1f%%)\nImpacto: %.2f",
                             bonoTecnologia, calcularImpacto());
    }
}

class NacionDesarrollo extends Nacion {
    public double factorLimitado;  // factor de reducción de impacto

    public NacionDesarrollo() {
        // Constructor vacío
    }
    
    public NacionDesarrollo(String nombre, long poblacion, double recursos, int poderMilitar, double factorLimitado) {
        super(nombre, poblacion, recursos, poderMilitar);
        this.factorLimitado = factorLimitado;
    }

    @Override
    public double calcularImpacto() {
        double ratio = recursosEconomicos / poblacion;
        double impact = poderMilitar - (ratio * factorLimitado);
        if (impact < 1) {
            impact = 1;
        }
        return impact;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("\n(Factor Limitado: %.3f)\nImpacto: %.2f",
                             factorLimitado, calcularImpacto());
    }
}

class Conflicto {
    public String id;
    public Nacion nacion1;
    public Nacion nacion2;
    public Nacion ganador;
    
    public Conflicto() {
        // COnstructor vacío
    }

    public Conflicto(Nacion n1, Nacion n2) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.nacion1 = n1;
        this.nacion2 = n2;
    }

    public void simular() {
        double imp1 = nacion1.calcularImpacto();
        double imp2 = nacion2.calcularImpacto();
        if (imp1 > imp2) {
            ganador = nacion1;
        } else if (imp2 > imp1) {
            ganador = nacion2;
        } else {
            ganador = null;  // empate
        }
    }

    public void aplicarConsecuencias() {
        if (ganador == null) {
            // empate: ambas pierden 5% recursos
            nacion1.recursosEconomicos *= 0.95;
            nacion2.recursosEconomicos *= 0.95;
        } else {
            // calcular diferencia sin Math.abs
            int diff;
            if (nacion1.poderMilitar > nacion2.poderMilitar) {
                diff = nacion1.poderMilitar - nacion2.poderMilitar;
            } else {
                diff = nacion2.poderMilitar - nacion1.poderMilitar;
            }
            // reducción de población: 5% por cada punto de diferencia
            nacion1.poblacion -= (long)(nacion1.poblacion * 0.05 * diff);
            nacion2.poblacion -= (long)(nacion2.poblacion * 0.05 * diff);

            // perdedor pierde 10% recursos
            Nacion perdedor = (ganador == nacion1) ? nacion2 : nacion1;
            perdedor.recursosEconomicos *= 0.90;
        }
        // marcar fin de conflicto
        nacion1.enConflicto = false;
        nacion2.enConflicto = false;
    }

    @Override
    public String toString() {
        String res = String.format(
            "Conflicto %s: %s vs %s -> ",
            id, nacion1.nombre, nacion2.nombre
        );
        if (ganador == null) res += "Empate";
        else res += "Ganador: " + ganador.nombre;
        return res;
    }
}

class Simulador {
    public ArrayList<Nacion> naciones;
    public ArrayList<Conflicto> conflictos;
    
    public Simulador() {
        this.naciones = new ArrayList<>();
        this.conflictos = new ArrayList<>();
    }

    public void agregarNacion(Nacion n) {
        naciones.add(n);
    }

    public void iniciarSimulacion() {
        Random rnd = new Random();
        // Simular 3 conflictos aleatorios
        for (int i = 0; i < 3; i++) {
            Nacion a = naciones.get(rnd.nextInt(naciones.size()));
            Nacion b = naciones.get(rnd.nextInt(naciones.size()));
            if (a != b) {
                a.enConflicto = b.enConflicto = true;
                Conflicto c = new Conflicto(a, b);
                c.simular();
                c.aplicarConsecuencias();
                conflictos.add(c);
            }
        }
    }

    public String reporteFinal() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE FINAL ===\n");
        for (Nacion n : naciones) sb.append(n).append("\n-------------------------------------\n");
        sb.append("Total de conflictos simulados: ").append(conflictos.size());
        return sb.toString();
    }
}