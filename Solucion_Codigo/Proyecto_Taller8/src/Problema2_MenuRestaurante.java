import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Problema 2 - Gestión de menus en un Restaurant
 * En un restaurant se tiene diferentes tipos de menú para ofrecer a los clientes.
* Una cuenta por pagar está compuesta por características como: nombre del cliente,
* listado de todos las cartas(menú) solicitados por el cliente, valor a cancelar total, subtotal, Iva.

* Los tipos de menú del restaurant son:

* Menú a la carta
* nombre del plato
* valor del menú
* valor inicial del menú
* valor de porción de guarnición
* valor de bebida
* porcentaje adicional por servicio en relación del valor inicial del menú

* Menú del día
* nombre del plato
* valor del menú
* valor inicial del menú
* valor de postre
* valor de bebida
*
* Menú de niños
* nombre del plato
* valor del menú
* valor inicial del menú
* valor de porción de helado
* valor de porción de pastel

* Menú económico
* nombre del plato
* valor del menú
* valor inicial del menú
* porcentaje de descuento, en referencia al valor inicial del menú

* Note
* Para solucionar lo anterior se debe generar lo siguiente:
* Un diagrama exclusivo que involucren las clases de tipo Menú (usar polimorfismo)
* Una solución en lenguaje de programación Java. Usar Polimorfismo en la solución. 
* Hacer uso del método toString() para presentar toda la información posible del objeto 
* (nombre del cliente, subtotal, iva, listado de todos los menú, valor a cancelar a total.
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class Problema2_MenuRestaurante {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        String[] nombresPlatos = {"Bistec", "Arroz con pollo", "Hamburguesa", "Ensalada", "Pechuga", "Sopa de mariscos"};
        String nombreCliente = "Juan Pérez";

        Cuenta cuenta = new Cuenta(nombreCliente);

        int opcion;
        do {
            System.out.println("\nSeleccione el tipo de menú a agregar:");
            System.out.println("1. Menú a la carta");
            System.out.println("2. Menú del día");
            System.out.println("3. Menú de niños");
            System.out.println("4. Menú económico");
            System.out.println("0. Terminar pedido");
            System.out.print("Opción: ");
            opcion = sc.nextInt();

            if (opcion >= 1 && opcion <= 4) {
                String plato = nombresPlatos[rand.nextInt(nombresPlatos.length)];
                double valorInicial = 5 + rand.nextDouble() * 10; // entre 5 y 15

                switch (opcion) {
                    case 1 -> {
                        double guarnicion = 1 + rand.nextDouble() * 3;
                        double bebida = 1 + rand.nextDouble() * 2;
                        double servicio = 5 + rand.nextDouble() * 10; // 5% a 15%
                        MenuCarta mc = new MenuCarta(plato, valorInicial, guarnicion, bebida, servicio);
                        cuenta.agregarMenu(mc);
                        System.out.println("Menú a la carta agregado.");
                    }
                    case 2 -> {
                        double postre = 1 + rand.nextDouble() * 2;
                        double bebida = 1 + rand.nextDouble() * 2;
                        MenuDia md = new MenuDia(plato, valorInicial, postre, bebida);
                        cuenta.agregarMenu(md);
                        System.out.println("Menú del día agregado.");
                    }
                    case 3 -> {
                        double helado = 0.5 + rand.nextDouble();
                        double pastel = 0.5 + rand.nextDouble();
                        MenuNiños mn = new MenuNiños(plato, valorInicial, helado, pastel);
                        cuenta.agregarMenu(mn);
                        System.out.println("Menú de niños agregado.");
                    }
                    case 4 -> {
                        double descuento = 5 + rand.nextDouble() * 20; // 5% a 25%
                        MenuEconomico me = new MenuEconomico(plato, valorInicial, descuento);
                        cuenta.agregarMenu(me);
                        System.out.println("Menú económico agregado.");
                    }
                }
            }

        } while (opcion != 0);

        cuenta.calcularTotales();
        System.out.println("\n======= CUENTA FINAL =======");
        System.out.println(cuenta);
    }
}

abstract class Menu {
    public String nombrePlato;
    public double valorMenu;
    public double valorInicial;

    public Menu(String nombrePlato, double valorInicial) {
        this.nombrePlato = nombrePlato;
        this.valorInicial = valorInicial;
    }
    
    public abstract void calcularValorMenu();

    @Override
    public String toString() {
        return String.format("Menu{nombrePlato=%s, valorMenu=%.2f, valorInicial=%.2f}\n",
                nombrePlato, valorMenu, valorInicial);
    }
}

class MenuCarta extends Menu {
    public double valorGuarnicion;
    public double valorBebida;
    public double porcentajeAdicional;

    public MenuCarta(String nombrePlato, double valorInicial, double valorGuarnicion, double valorBebida, double porcentajeAdicional) {
        super(nombrePlato, valorInicial);
        this.valorGuarnicion = valorGuarnicion;
        this.valorBebida = valorBebida;
        this.porcentajeAdicional = porcentajeAdicional;
    }

    @Override
    public void calcularValorMenu() {
        this.valorMenu = valorInicial + valorGuarnicion + valorBebida + (valorInicial * porcentajeAdicional / 100);
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("Guarnición: %.2f\nBebida: %.2f\nPorc. Servicio: %.2f%%\n", 
               valorGuarnicion, valorBebida, porcentajeAdicional);
    }
}

class MenuDia extends Menu {
    public double valorPostre;
    public double valorBebida;

    public MenuDia(String nombrePlato, double valorInicial, double valorPostre, double valorBebida) {
        super(nombrePlato, valorInicial);
        this.valorPostre = valorPostre;
        this.valorBebida = valorBebida;
    }

    @Override
    public void calcularValorMenu() {
        this.valorMenu = valorInicial + valorPostre + valorBebida;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("Postre: %.2f\nBebida: %.2f\n", 
               valorPostre, valorBebida);
    }
}

class MenuNiños extends Menu {
    public double valorHelado;
    public double valorPastel;

    public MenuNiños(String nombrePlato, double valorInicial, double valorHelado, double valorPastel) {
        super(nombrePlato, valorInicial);
        this.valorHelado = valorHelado;
        this.valorPastel = valorPastel;
    }

    @Override
    public void calcularValorMenu() {
        this.valorMenu = valorInicial + valorHelado + valorPastel;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("Helado: %.2f\nPastel: %.2f\n", 
               valorHelado, valorPastel);
    }
}

class MenuEconomico extends Menu {
    public double porcentajeDescuento;

    public MenuEconomico(String nombrePlato, double valorInicial, double porcentajeDescuento) {
        super(nombrePlato, valorInicial);
        this.porcentajeDescuento = porcentajeDescuento;
    }

    @Override
    public void calcularValorMenu() {
        this.valorMenu = valorInicial - (valorInicial * porcentajeDescuento / 100);
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("Descuento: %.2f%%\n", porcentajeDescuento);
    }
}

class Cuenta {
    public String nombreCliente;
    public ArrayList<Menu> listaMenu;
    public double subtotal;
    public double iva;
    public double total;
    
    public Cuenta() {
        // Constructor vacío
    }

    public Cuenta(String nombreCliente) {
        this.nombreCliente = nombreCliente;
        this.listaMenu = new ArrayList<>();
    }

    public void agregarMenu(Menu menu) {
        listaMenu.add(menu);
    }

    public void calcularTotales() {
        this.subtotal = 0;
        for (Menu m : listaMenu) {
            m.calcularValorMenu();
            this.subtotal += m.valorMenu;
        }
        this.iva = subtotal * 0.15; // 15% de IVA
        this.total = subtotal + iva;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cuenta del cliente: ").append(nombreCliente).append("\n\n");
        for (Menu m : listaMenu) {
            sb.append(m.toString()).append("----------------------\n");
        }
        sb.append(String.format("Subtotal: %.2f\nIVA (12%%): %.2f\nTOTAL: %.2f\n", subtotal, iva, total));
        return sb.toString();
    }
}