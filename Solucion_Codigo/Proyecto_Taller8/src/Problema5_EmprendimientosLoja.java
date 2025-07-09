import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
* Problema 5 - Plataforma de apoyo a emprendimientos en Loja
* Una organización local ha creado una plataforma digital para registrar y promover 
* emprendimientos de la ciudad de Loja. Los emprendimientos pueden clasificarse por
* tipo (tecnológico, artesanal, agrícola, gastronómico, etc.), y cada uno presenta 
* información detallada sobre su misión, productos o servicios, y datos de contacto.
* Algunos emprendimientos requieren acompañamiento técnico por parte de mentores 
* especializados, quienes brindan asesoría en áreas como marketing, contabilidad o 
* desarrollo de software. Además, los emprendimientos pueden participar en ferias locales, 
* donde presentan sus productos y compiten por reconocimientos. Existen emprendimientos 
* que evolucionan a lo largo del tiempo y extienden sus líneas de productos o abren nuevas sedes.
* 
* Requisitos funcionales:
* Diferenciar los distintos tipos de emprendimientos mediante herencia.
* Asociar uno o más mentores a los emprendimientos que lo requieran.
* Implementar comportamientos polimórficos en función del tipo de feria o actividad en la que participan.
* Permitir registrar productos o servicios que ofrece cada emprendimiento.
* Simular la evolución de un emprendimiento con el tiempo (crecimiento, diversificación, expansión).
* 
* Note

* Plantee una solución polimórfica dada una jerarquía de clases con ventajas de herencia. Para la visualización de resultados use los toString() base.
 * 
 * @author Mateo Gonzáles y Mateo Rivera
 */
public class Problema5_EmprendimientosLoja {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();
        ArrayList<Emprendimiento> emprendimientos = new ArrayList<>();
        char opcionPrincipal;
        
        System.out.println("- REGISTRO DE EMPRENDIMIENTOS LOJA -\n");
        
        do {
            System.out.println("    1. Agregar emprendimiento");
            System.out.println("    2. Ver emprendimientos");
            System.out.println("    3. Participación de un emprendimiento");
            System.out.println("    4. Evolucionar emprendimiento");
            System.out.println("    5. Salir");
            System.out.print("Elija una opcion: ");
            opcionPrincipal = sc.nextLine().charAt(0);
            
            switch(opcionPrincipal) {
                case '1':
                    System.out.println("Tipos de emprendimiento");
                    System.out.println("1. Emprendimiento Tecnológico");
                    System.out.println("2. Emprendimiento Artesanal");
                    System.out.println("3. Emprendimiento Agrícola");
                    System.out.println("4. Emprendimiento Gastronómico");
                    System.out.print("Ingrese el emprendmiento a agregar(1-4): ");
                    char opcion = sc.nextLine().toUpperCase().charAt(0);
                    agregarEmprendimiento(emprendimientos, opcion);
                    break;
                case '2': 
                    if(emprendimientos.isEmpty()) {
                        System.out.println("\nNo hay emprendimientos registrados.");
                    } else {
                        System.out.println("\n=== EMPRENDIMIENTOS REGISTRADOS ===");
                        for (Emprendimiento emprendimiento : emprendimientos) {
                            System.out.println(emprendimiento);
                            System.out.println("-----------------------------");
                        }
                    }
                    break;
                case '3':
                    if (emprendimientos.isEmpty()) {
                        System.out.println("No hay emprendimientos.");
                        break;
                    }

                    // Mostrar lista de emprendimientos
                    System.out.println("\n=== EMPRENDIMIENTOS REGISTRADOS ===");
                    for (int i = 0; i < emprendimientos.size(); i++) {
                        System.out.println((i + 1) + ". " + emprendimientos.get(i).nombre);
                    }

                    System.out.print("Seleccione el número del emprendimiento que participará en la feria: ");
                    int indiceFeria = Integer.parseInt(sc.nextLine()) - 1;

                    if (indiceFeria >= 0 && indiceFeria < emprendimientos.size()) {
                        // Ferias predefinidas
                        String[] ferias = {"Expo Loja", "Feria de la Innovación", "Sabores y Tradiciones"};

                        String feriaSeleccionada = ferias[random.nextInt(ferias.length)];

                        Emprendimiento elegido = emprendimientos.get(indiceFeria);
                        System.out.println("\n> " + elegido.nombre + " participa en la feria aleatoria: " + feriaSeleccionada);
                        elegido.participarEnFeria(feriaSeleccionada);

                    } else {
                        System.out.println("Número de emprendimiento inválido.");
                    }
                    break;
                case '4':
                    if (emprendimientos.isEmpty()) {
                        System.out.println("No hay emprendimientos.");
                        break;
                    }

                    System.out.println("\n=== EMPRENDIMIENTOS REGISTRADOS ===");
                    for (int i = 0; i < emprendimientos.size(); i++) {
                        System.out.println((i + 1) + ". " + emprendimientos.get(i).nombre);
                    }

                    System.out.print("Seleccione el número del emprendimiento a evolucionar: ");
                    int indiceEvolucion = Integer.parseInt(sc.nextLine()) - 1;

                    if (indiceEvolucion >= 0 && indiceEvolucion < emprendimientos.size()) {
                        Emprendimiento elegido = emprendimientos.get(indiceEvolucion);
                        elegido.evolucionar();
                        System.out.println("\n> " + elegido.nombre + " ha evolucionado, aumentando una sucursal en Quito y agregando un nuevo producto.");
                        System.out.println("Nuevo estado:\n" + elegido);
                    } else {
                        System.out.println("Número inválido.");
                    }
                    break;
                case '5': 
                    System.out.println("¡Gracias por usar nuestro servicio!");
                    System.exit(0);
                default:
                    System.err.println("Opcion incorrecta.");
                    break;
            }        
        } while (opcionPrincipal != '5');       
    }
    
    public static void agregarEmprendimiento(ArrayList<Emprendimiento> emprendimientos, char opcion) {
        Random random = new Random();
        Scanner sc = new Scanner(System.in);
        char agregar;
        switch(opcion) {
                case '1':
                    String[] nombresTecnologicos = {"CodeLoja", "AndesTech", "SmartFarm", "DigitalArte"};
                    String[] misionesTecnologicas = {
                        "Impulsar el desarrollo de software local",
                        "Soluciones IoT para la agricultura",
                        "Educación tecnológica para niños",
                        "Arte digital con identidad lojana",
                    };
                    String[] productosTecnologicos = {"Apps móviles", "Sensores agrícolas", "Kits de robótica", "NFTs de arte local"};
                    
                    String[] descripcionProd = {"Sistemas web", "Plataforma IoT", "Cursos programación","Diseño 3D"};
                    String[] contactosTecnologicos = {
                        "code.loja@mail.com - 072345678",
                        "andes.tech@mail.com - 072987654",
                        "robokids@mail.com - 072111222",
                        "digital.arte@mail.com - 072333444"
                    };
                    String[] mentoresTecnologicos = {
                        "Ing. Carlos Maldonado",
                        "Dra. Ana Vélez",
                        "Mg. Diego Ramírez",
                        "Lic. Sofía Torres"
                    };

                    String[] especialidadesTecnologicas = {
                        "Desarrollo de Software",
                        "Inteligencia Artificial",
                        "Marketing Digital",
                        "Ciberseguridad"
                    };
                    Emprendimiento tecnologico = new Tecnologico(nombresTecnologicos[random.nextInt(nombresTecnologicos.length)],
                    misionesTecnologicas[random.nextInt(misionesTecnologicas.length)], 
                            contactosTecnologicos[random.nextInt(contactosTecnologicos.length)]);
                    Mentor mentor = new Mentor(mentoresTecnologicos[random.nextInt(mentoresTecnologicos.length)], 
                            especialidadesTecnologicas[random.nextInt(especialidadesTecnologicas.length)]);
                    tecnologico.agregarMentor(mentor);
                    ProductoServicio prod_servicio = new ProductoServicio(productosTecnologicos[random.nextInt(productosTecnologicos.length)], 
                            descripcionProd[random.nextInt(descripcionProd.length)]);
                    tecnologico.agregarProducto(prod_servicio);
                    System.out.println("Emprendimiento teconlógico agregado: " + tecnologico.nombre);
                    
                    do {
                        System.out.print("¿Agregar otro prducto/servicio? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if(agregar == 'S') {
                            ProductoServicio prod_servicioAD = new ProductoServicio(productosTecnologicos[random.nextInt(productosTecnologicos.length)], 
                                descripcionProd[random.nextInt(descripcionProd.length)]);
                            tecnologico.agregarProducto(prod_servicioAD);
                        }
                    } while (agregar == 'S');                    
                    
                    do {
                        System.out.print("¿Agregar otro mentor? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if(agregar == 'S') {
                            Mentor mentorAD = new Mentor(mentoresTecnologicos[random.nextInt(mentoresTecnologicos.length)], 
                                especialidadesTecnologicas[random.nextInt(especialidadesTecnologicas.length)]);
                            tecnologico.agregarMentor(mentorAD);
                        }
                    } while (agregar == 'S');
                    System.out.println(tecnologico.toString());
                    emprendimientos.add(tecnologico);
                    break;
                case '2': {
                    String[] nombresArtesanales = {"BarroLoja", "LanaAndina", "TaguaStyle", "CueroZamora"};
                    String[] misionesArtesanales = {
                        "Preservar la alfarería tradicional",
                        "Tejidos con lana de oveja",
                        "Arte en tagua (marfil vegetal)",
                        "Artesanías en cuero curtido"
                    };
                    String[] productosArtesanales = {"Vajillas", "Ponchos", "Collares", "Billeteras"};
                    String[] descripcionProdArtesanal = {"Hecho a mano", "Con materiales naturales", "Diseño tradicional", "Técnica ancestral"};
                    String[] contactosArtesanales = {
                        "barro.loja@mail.com - 072234567",
                        "lana.andina@mail.com - 072876543",
                        "tagua.style@mail.com - 072112233",
                        "cuero.zamora@mail.com - 072334455"
                    };

                    String[] mentoresArtesanales = {
                        "Sra. María López",
                        "Sr. Juan Pérez",
                        "Dra. Ana Martínez",
                        "Lic. Pedro Castillo"
                    };
                    String[] especialidadesArtesanales = {
                        "Técnicas Tradicionales",
                        "Diseño y Creatividad",
                        "Marketing Artesanal",
                        "Gestión de Negocios"
                    };

                    int indexA = random.nextInt(nombresArtesanales.length);
                    Emprendimiento artesanal = new Artesanal(nombresArtesanales[indexA], misionesArtesanales[indexA], contactosArtesanales[indexA]);

                    // Producto inicial obligatorio
                    ProductoServicio producto = new ProductoServicio(
                        productosArtesanales[random.nextInt(productosArtesanales.length)],
                        descripcionProdArtesanal[random.nextInt(descripcionProdArtesanal.length)]
                    );
                    artesanal.agregarProducto(producto);

                    Mentor mentorA = new Mentor(
                        mentoresArtesanales[random.nextInt(mentoresArtesanales.length)],
                        especialidadesArtesanales[random.nextInt(especialidadesArtesanales.length)]
                    );
                    artesanal.agregarMentor(mentorA);

                    do {
                        System.out.print("¿Agregar otro producto artesanal? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if (agregar == 'S') {
                            ProductoServicio productoExtra = new ProductoServicio(
                                productosArtesanales[random.nextInt(productosArtesanales.length)],
                                descripcionProdArtesanal[random.nextInt(descripcionProdArtesanal.length)]
                            );
                            artesanal.agregarProducto(productoExtra);
                        }
                    } while (agregar == 'S');

                    do {
                        System.out.print("¿Agregar otro mentor artesanal? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if (agregar == 'S') {
                            Mentor mentorExtra = new Mentor(
                                mentoresArtesanales[random.nextInt(mentoresArtesanales.length)],
                                especialidadesArtesanales[random.nextInt(especialidadesArtesanales.length)]
                            );
                            artesanal.agregarMentor(mentorExtra);
                        }
                    } while (agregar == 'S');

                    System.out.println("Emprendimiento artesanal agregado: " + artesanal.nombre);
                    System.out.println(artesanal);
                    emprendimientos.add(artesanal);
                }
                break;

                case '3': {
                    String[] nombresAgricolas = {"AromaCafé", "MielVilcabamba", "OrgánicosZamora", "AgroQuinua"};
                    String[] misionesAgricolas = {
                        "Café especial de altura",
                        "Miel orgánica de bosque nativo",
                        "Verduras libres de pesticidas",
                        "Cultivo ancestral de quinua"
                    };
                    String[] productosAgricolas = {"Café tostado", "Miel pura", "Lechugas", "Quinua"};
                    String[] descripcionProdAgro = {"Cultivo orgánico", "Proceso artesanal", "Producto fresco", "Sin químicos"};
                    String[] contactosAgricolas = {
                        "aroma.cafe@mail.com - 072345679",
                        "miel.vilcabamba@mail.com - 072987655",
                        "organicos.zamora@mail.com - 072111223",
                        "agro.quinua@mail.com - 072333446"
                    };

                    String[] mentoresAgricolas = {
                        "Ing. Pedro Zamora",
                        "Dra. Laura Ruiz",
                        "Mg. Carlos Torres",
                        "Lic. Elena Medina"
                    };
                    String[] especialidadesAgricolas = {
                        "Agronomía",
                        "Producción Orgánica",
                        "Comercialización",
                        "Sostenibilidad"
                    };

                    int indexAgro = random.nextInt(nombresAgricolas.length);
                    Emprendimiento agricola = new Agricola(nombresAgricolas[indexAgro], misionesAgricolas[indexAgro], contactosAgricolas[indexAgro]);

                    ProductoServicio productoInicial = new ProductoServicio(
                        productosAgricolas[random.nextInt(productosAgricolas.length)],
                        descripcionProdAgro[random.nextInt(descripcionProdAgro.length)]
                    );
                    agricola.agregarProducto(productoInicial);

                    Mentor mentorAgro = new Mentor(
                        mentoresAgricolas[random.nextInt(mentoresAgricolas.length)],
                        especialidadesAgricolas[random.nextInt(especialidadesAgricolas.length)]
                    );
                    agricola.agregarMentor(mentorAgro);

                    do {
                        System.out.print("¿Agregar otro producto agrícola? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if (agregar == 'S') {
                            ProductoServicio productoExtra = new ProductoServicio(
                                productosAgricolas[random.nextInt(productosAgricolas.length)],
                                descripcionProdAgro[random.nextInt(descripcionProdAgro.length)]
                            );
                            agricola.agregarProducto(productoExtra);
                        }
                    } while (agregar == 'S');

                    do {
                        System.out.print("¿Agregar otro mentor agrícola? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if (agregar == 'S') {
                            Mentor mentorExtra = new Mentor(
                                mentoresAgricolas[random.nextInt(mentoresAgricolas.length)],
                                especialidadesAgricolas[random.nextInt(especialidadesAgricolas.length)]
                            );
                            agricola.agregarMentor(mentorExtra);
                        }
                    } while (agregar == 'S');

                    System.out.println("Emprendimiento agrícola agregado: " + agricola.nombre);
                    System.out.println(agricola);
                    emprendimientos.add(agricola);
                }
                break;

                case '4': {
                    String[] nombresGastronomicos = {"DulceLoja", "CuyAsado", "TradiciónCeviche", "QuesoArtesanal"};
                    String[] misionesGastronomicos = {
                        "Postres tradicionales lojanos",
                        "Cuy horneado al estilo ancestral",
                        "Mariscos con ingredientes locales",
                        "Quesos con leche de vaca pastoreada"
                    };
                    String[] productosGastronomicos = {"Roscones", "Cuy entero", "Ceviche", "Queso fresco"};
                    String[] descripcionProdGast = {"Receta tradicional", "Preparación casera", "Sabor local", "Ingredientes frescos"};
                    String[] contactosGastronomicos = {
                        "dulce.loja@mail.com - 072345680",
                        "cuy.asado@mail.com - 072987656",
                        "tradicion.ceviche@mail.com - 072111224",
                        "queso.artesanal@mail.com - 072333447"
                    };

                    String[] mentoresGastronomicos = {
                        "Chef Ana Gómez",
                        "Chef Luis Martínez",
                        "Nutricionista Carla Ruiz",
                        "Lic. Jorge Salazar"
                    };
                    String[] especialidadesGastronomicos = {
                        "Gastronomía Tradicional",
                        "Técnicas Culinarias",
                        "Nutrición",
                        "Gestión Gastronómica"
                    };

                    int indexG = random.nextInt(nombresGastronomicos.length);
                    Emprendimiento gastronomico = new Gastronomico(nombresGastronomicos[indexG], misionesGastronomicos[indexG], contactosGastronomicos[indexG]);

                    ProductoServicio productoG = new ProductoServicio(
                        productosGastronomicos[random.nextInt(productosGastronomicos.length)],
                        descripcionProdGast[random.nextInt(descripcionProdGast.length)]
                    );
                    gastronomico.agregarProducto(productoG);

                    Mentor mentorG = new Mentor(
                        mentoresGastronomicos[random.nextInt(mentoresGastronomicos.length)],
                        especialidadesGastronomicos[random.nextInt(especialidadesGastronomicos.length)]
                    );
                    gastronomico.agregarMentor(mentorG);

                    do {
                        System.out.print("¿Agregar otro producto gastronómico? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if (agregar == 'S') {
                            ProductoServicio productoExtra = new ProductoServicio(
                                productosGastronomicos[random.nextInt(productosGastronomicos.length)],
                                descripcionProdGast[random.nextInt(descripcionProdGast.length)]
                            );
                            gastronomico.agregarProducto(productoExtra);
                        }
                    } while (agregar == 'S');

                    do {
                        System.out.print("¿Agregar otro mentor gastronómico? (S/N): ");
                        agregar = sc.nextLine().toUpperCase().charAt(0);
                        if (agregar == 'S') {
                            Mentor mentorExtra = new Mentor(
                                mentoresGastronomicos[random.nextInt(mentoresGastronomicos.length)],
                                especialidadesGastronomicos[random.nextInt(especialidadesGastronomicos.length)]
                            );
                            gastronomico.agregarMentor(mentorExtra);
                        }
                    } while (agregar == 'S');

                    System.out.println("Emprendimiento gastronómico agregado: " + gastronomico.nombre);
                    System.out.println(gastronomico);
                    emprendimientos.add(gastronomico);
                }
                break;
                default:
                    System.err.println("Opcion incorrecta.");
                    break;
        }        
    }
}

abstract class Emprendimiento {
    public String nombre;
    public String mision;
    public String contacto;
    public ArrayList<ProductoServicio> productos;
    public ArrayList<Mentor> mentores;
    
    public Emprendimiento() {
        // Constructor vacío
    }

    public Emprendimiento(String nombre, String mision, String contacto) {
        this.nombre = nombre;
        this.mision = mision;
        this.contacto = contacto;
        this.productos = new ArrayList<>();
        this.mentores = new ArrayList<>();
    }

    public void agregarProducto(ProductoServicio ps) {
        productos.add(ps);
    }

    public void agregarMentor(Mentor m) {
        mentores.add(m);
    }

    public abstract void participarEnFeria(String tipoFeria);

    public abstract void evolucionar();

    @Override
    public String toString() {
        return "Emprendimiento: " + nombre + "\nMisión: " + mision +
               "\nContacto: " + contacto +
               "\nProductos: " + productos +
               "\nMentores: " + mentores;
    }
}

class Tecnologico extends Emprendimiento{
    public Tecnologico() {
        // Constructor vacío
    }
    
    public Tecnologico(String nombre, String mision, String contacto) {
        super(nombre, mision, contacto);
    }

    @Override
    public void participarEnFeria(String tipoFeria) {
        System.out.println(nombre + " participa en la feria tecnológica '" + tipoFeria +
                           "' mostrando prototipos de software e innovación digital.");
    }
    
    @Override
    public void evolucionar() {
        String[] nuevosProductos = {"App de finanzas", "Sistema IoT", "Chatbot", "Sitio web"};
        String[] descripciones = {"Automatiza pagos", "Monitoreo remoto", "Atención al cliente", "Reservas en línea"};
        int i = (int) (Math.random() * nuevosProductos.length);
        agregarProducto(new ProductoServicio(nuevosProductos[i], descripciones[i]));
    }
}

class Artesanal extends Emprendimiento{
    public Artesanal() {
        // Constructor vacío
    }
    
    public Artesanal(String nombre, String mision, String contacto) {
        super(nombre, mision, contacto);
    }

    @Override
    public void participarEnFeria(String tipoFeria) {
        System.out.println(nombre + " participa en la feria artesanal '" + tipoFeria +
                           "' exhibiendo obras hechas a mano y técnicas tradicionales.");
    }   
    
    @Override
    public void evolucionar() {
        String[] nuevosProductos = {"Joyería con tagua", "Mochilas bordadas", "Decoraciones de madera", "Cerámica artística"};
        String[] descripciones = {"Diseño personalizado", "Hechas a mano", "Talla local", "Estilo tradicional"};
        int i = (int) (Math.random() * nuevosProductos.length);
        agregarProducto(new ProductoServicio(nuevosProductos[i], descripciones[i]));
    }
}

class Agricola extends Emprendimiento{
    public Agricola() {
        // Constructor vacío
    }
    
    public Agricola(String nombre, String mision, String contacto) {
        super(nombre, mision, contacto);
    }

    @Override
    public void participarEnFeria(String tipoFeria) {
        System.out.println(nombre + " participa en la feria agrícola '" + tipoFeria +
                           "' exhibiendo productos del campo y prácticas sostenibles.");
    }
    
    @Override
    public void evolucionar() {
        String[] nuevosProductos = {"Mermelada andina", "Infusiones medicinales", "Aceites esenciales", "Snacks orgánicos"};
        String[] descripciones = {"Natural", "Saludable", "Hecho localmente", "Cultivo ecológico"};
        int i = (int) (Math.random() * nuevosProductos.length);
        agregarProducto(new ProductoServicio(nuevosProductos[i], descripciones[i]));
    }    
}

class Gastronomico extends Emprendimiento{
    public Gastronomico() {
        // Constructor vacío
    }
    
    public Gastronomico(String nombre, String mision, String contacto) {
        super(nombre, mision, contacto);
    }

    @Override
    public void participarEnFeria(String tipoFeria) {
        System.out.println(nombre + " participa en la feria gastronómica '" + tipoFeria +
                           "' presentando desgustaciones de sus productos.");
    }
    
    @Override
    public void evolucionar() {
        String[] nuevosProductos = {"Helado artesanal", "Combo regional", "Línea gourmet", "Menú saludable"};
        String[] descripciones = {"Sabores locales", "Presentación moderna", "Ingredientes selectos", "Recetas light"};
        int i = (int) (Math.random() * nuevosProductos.length);
        agregarProducto(new ProductoServicio(nuevosProductos[i], descripciones[i]));
    }
}

class ProductoServicio {
    public String nombre;
    public String descripcion;
    
    public ProductoServicio() {
        // Constructor vacío
    }
    
    public ProductoServicio(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}

class Mentor {
    public String nombre;
    public String especialidad;
    
    public Mentor() {
        // Constructor vacío
    }

    public Mentor(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    @Override
    public String toString() {
        return nombre + " (Especialidad: " + especialidad + ")";
    }
}