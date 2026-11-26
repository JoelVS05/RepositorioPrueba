package agenda;

import agenda.datos.AgendaFichero;
import agenda.modelo.Agenda;
import agenda.modelo.Contacto;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        AgendaFichero fichero = new AgendaFichero("agenda.dat");

        Agenda agenda;
        try {
            agenda = fichero.cargar();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No se pudo leer agenda.dat. Se empieza con una agenda vacia.");
            agenda = new Agenda();
        }

        String opcion;
        do {
            mostrarMenu();
            opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1":
                    String nombre = pedirNombre();
                    String telefono = pedirTelefono();
                    String email = pedirEmail();
                    agenda.agregar(new Contacto(nombre, telefono, email));
                    System.out.println("Contacto anadido.");
                    break;
                case "2":
                    agenda.listar();
                    break;
                case "3":
                    borrarContacto(agenda);
                    break;
                case "0":
                    if (preguntarSiGuardar()) {
                        guardar(fichero, agenda);
                    }
                    System.out.println("Hasta pronto.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (!opcion.equals("0"));
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== AGENDA ===");
        System.out.println("1. Anadir contacto");
        System.out.println("2. Listar contactos");
        System.out.println("3. Borrar contacto");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
    }

    private static void borrarContacto(Agenda agenda) {
        agenda.listar();
        if (agenda.estaVacia()) {
            return;
        }
        System.out.print("Numero del contacto a borrar: ");
        try {
            int numero = Integer.parseInt(teclado.nextLine().trim());
            if (agenda.borrar(numero - 1)) {
                System.out.println("Contacto borrado.");
            } else {
                System.out.println("No existe ese numero.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Eso no es un numero.");
        }
    }

    // Repite la pregunta hasta que el usuario contesta s o n.
    private static boolean preguntarSiGuardar() {
        while (true) {
            System.out.print("Guardar los cambios? (s/n): ");
            String respuesta = teclado.nextLine().trim().toLowerCase();
            if (respuesta.equals("s")) {
                return true;
            }
            if (respuesta.equals("n")) {
                return false;
            }
            System.out.println("  Escribe s o n.");
        }
    }

    private static void guardar(AgendaFichero fichero, Agenda agenda) {
        try {
            fichero.guardar(agenda);
            System.out.println("Agenda guardada.");
        } catch (IOException e) {
            System.out.println("ERROR: no se pudo guardar la agenda: " + e.getMessage());
        }
    }

    // ---------- Lectura y validacion de datos ----------

    private static String pedirNombre() {
        while (true) {
            System.out.print("Nombre: ");
            String nombre = teclado.nextLine().trim();
            if (nombre.matches("[A-Za-zÁÉÍÓÚÜÑáéíóúüñ ]+")) {
                return nombre;
            }
            System.out.println("  Nombre no valido: solo letras y espacios, entre 2 y 50 caracteres.");
        }
    }

    private static String pedirTelefono() {
        while (true) {
            System.out.print("Telefono: ");
            String telefono = teclado.nextLine().trim();
            if (telefono.matches("[6-9][0-9]{8}")) {
                return telefono;
            }
            System.out.println("  Telefono no valido: 9 digitos, empezando por 6, 7, 8 o 9.");
        }
    }

    private static String pedirEmail() {
        while (true) {
            System.out.print("Email: ");
            String email = teclado.nextLine().trim();
            if (email.matches("[A-Za-z0-9._+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+")) {
                return email;
            }
            System.out.println("  Email no valido: debe tener la forma usuario@dominio.ext");
        }
    }
}
