package agenda.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Agenda implements Serializable {

    private static final long serialVersionUID = 1L;

    private ArrayList<Contacto> contactos = new ArrayList<>();


    public void agregar(Contacto contacto) {
        contactos.add(contacto);
    }

    // Borra el contacto de la posicion indicada (0 es el primero).
    // Devuelve false si la posicion no existe.
    public boolean borrar(int posicion) {
        if (posicion < 0 || posicion >= contactos.size()) {
            return false;
        }
        contactos.remove(posicion);
        return true;
    }

    // Muestra los contactos numerados desde 1.
    public void listar() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda esta vacia");
            return;
        }
        int numero = 1;
        for (Contacto c : contactos) {
            System.out.println(numero + ". " + c);
            numero++;
        }
    }

    public boolean estaVacia() {
        return contactos.isEmpty();
    }
}
