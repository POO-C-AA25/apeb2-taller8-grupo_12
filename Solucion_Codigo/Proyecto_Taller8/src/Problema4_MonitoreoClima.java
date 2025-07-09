import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

/**
 * Problema 4 - Sistema de monitoreo de impactos del cambio climático en Ecuador
 * Una red de monitoreo ambiental tiene como objetivo registrar, analizar y 
 * reportar los impactos del cambio climático en diferentes regiones. En cada 
 * ubicación se instalan dispositivos capaces de medir distintos indicadores 
 * climáticos como temperatura, precipitación, calidad del aire, y humedad del suelo. 
 * Dependiendo de la región (costa, sierra y oriente), los dispositivos pueden variar 
 * en capacidades y protocolos de recolección.

* Los datos recolectados deben almacenarse y analizarse periódicamente. 
* Además, ciertas ubicaciones requieren generar reportes personalizados que 
* destaquen riesgos ambientales como sequías, deslizamientos o contaminación del
* aire. Algunos dispositivos pueden comportarse de forma especializada para detectar 
* únicamente ciertos tipos de indicadores dependiendo de la región (costa, sierra y oriente).

* Requisitos funcionales:
* Representar diferentes tipos de dispositivos y sus especializaciones, para la costa, sierra y oriente.
* Implementar métodos polimórficos que permitan procesar los datos según los tipos 
* de dispositivos y sus especializaciones, para la costa, sierra y oriente.
* Generar reportes dinámicos en función del tipo de riesgo ambiental detectado según la región
* Note

* Plantee una solución polimórfica dada una jerarquía de clases con ventajas de herencia. Y para la generación de reportería, use los toString() base.
 
 * @author Mateo Gonzáles y Mateo Rivera
 */

public class Problema4_MonitoreoClima {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        char continuar;

        String[] ubicacionesCosta = {"Manta", "Guayaquil", "Esmeraldas"};
        String[] ubicacionesSierra = {"Quito", "Cuenca", "Loja"};
        String[] ubicacionesOriente = {"Puyo", "Tena", "Macas"};

        String[] protocolosCosta = {"HTTP", "MQTT", "FTP"};
        String[] protocolosSierra = {"LoRa", "ZigBee", "NB-IoT"};
        String[] protocolosOriente = {"Sigfox", "WiFi", "GSM"};
        
        System.out.println("- INSTITUTO DEL CAMBIO CLIMÁTICO DEL ECUADOR -");

        do {
            ArrayList<Dispositivo> dispositivos = new ArrayList<>();

            // Crear 3 dispositivos por región (uno por cada tipo)
                dispositivos.add(new DispositivoCosta(
                    "COST-" + UUID.randomUUID().toString().substring(0, 5),
                    ubicacionesCosta[random.nextInt(ubicacionesCosta.length)],
                    protocolosCosta[random.nextInt(protocolosCosta.length)]
                ));

                dispositivos.add(new DispositivoSierra(
                    "SIER-" + UUID.randomUUID().toString().substring(0, 5),
                    ubicacionesSierra[random.nextInt(ubicacionesSierra.length)],
                    protocolosSierra[random.nextInt(protocolosSierra.length)]
                ));

                dispositivos.add(new DispositivoOriente(
                    "ORIE-" + UUID.randomUUID().toString().substring(0, 5),
                    ubicacionesOriente[random.nextInt(ubicacionesOriente.length)],
                    protocolosOriente[random.nextInt(protocolosOriente.length)]
                ));

            // Procesar datos y mostrar reportes
            System.out.println("\n=== Reporte de Dispositivos ===\n");
            for (Dispositivo d : dispositivos) {
                d.procesarDatos();
                System.out.println(d.toString());
                System.out.println("----------------------------------------");
            }

            System.out.print("¿Desea generar otro conjunto de reportes? (S/N): ");
            continuar = sc.nextLine().toUpperCase().charAt(0);

        } while (continuar == 'S');
        
        System.out.println("Fin del monitoreo.");
        
    }
}

abstract class Dispositivo {
    public String id;
    public String ubicacion;
    public String protocolo;

    public Dispositivo() {
        // Constructor vacío
    }

    public Dispositivo(String id, String ubicacion, String protocolo) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.protocolo = protocolo;
    }

    public abstract void procesarDatos();
    public abstract String toString();
}

class DispositivoCosta extends Dispositivo {
    public double temperatura;     // en °C
    public double humedadSuelo;    // en %

    public DispositivoCosta() {
        // Constructor vacío
    }
    
    public DispositivoCosta(String id, String ubicacion, String protocolo) {
        super(id, ubicacion, protocolo);
    }

    @Override
    public void procesarDatos() {
        temperatura = 28 + Math.random() * 10;      // entre 28 y 38
        humedadSuelo = 10 + Math.random() * 30;     // entre 10 y 40
    }

    @Override
    public String toString() {
        String riesgo = (temperatura > 35 && humedadSuelo < 20)
            ? "Riesgo ambiental: Posible sequía"
            : "Condiciones climáticas estables";
        return "[Costa]\nID: " + id +
               "\nUbicación: " + ubicacion +
               "\nProtocolo: " + protocolo +
               "\nTemperatura: " + String.format("%.1f", temperatura) + " °C" +
               "\nHumedad del suelo: " + String.format("%.1f", humedadSuelo) + " %" +
               "\n" + riesgo;
    }
}

class DispositivoSierra extends Dispositivo {
    public double precipitacion;   // en mm
    public double humedadSuelo;    // en %

    public DispositivoSierra(String id, String ubicacion, String protocolo) {
        super(id, ubicacion, protocolo);
    }

    @Override
    public void procesarDatos() {
        precipitacion = 100 + Math.random() * 200;  // 100 - 300 mm
        humedadSuelo = 60 + Math.random() * 30;     // 60 - 90 %
    }

    @Override
    public String toString() {
        String riesgo = (precipitacion > 250 && humedadSuelo > 80)
            ? "Riesgo ambiental: Posible deslizamiento"
            : "Condiciones climáticas estables";
        return "[Sierra]\nID: " + id +
               "\nUbicación: " + ubicacion +
               "\nProtocolo: " + protocolo +
               "\nPrecipitación: " + String.format("%.1f", precipitacion) + " mm" +
               "\nHumedad del suelo: " + String.format("%.1f", humedadSuelo) + " %" +
               "\n" + riesgo;
    }
}

class DispositivoOriente extends Dispositivo {
    public double calidadAire;  // en AQI (Air Quality Index)

    public DispositivoOriente(String id, String ubicacion, String protocolo) {
        super(id, ubicacion, protocolo);
    }

    @Override
    public void procesarDatos() {
        calidadAire = 50 + Math.random() * 150;   // entre 50 y 200 AQI
    }

    @Override
    public String toString() {
        String riesgo = (calidadAire > 120)
            ? "Riesgo ambiental: Contaminación del aire"
            : "Calidad del aire dentro de parámetros normales";
        return "[Oriente]\nID: " + id +
               "\nUbicación: " + ubicacion +
               "\nProtocolo: " + protocolo +
               "\nÍndice de calidad del aire: " + String.format("%.1f", calidadAire) + " AQI" +
               "\n" + riesgo;
    }
}