package agenda.datos;

import agenda.modelo.Agenda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AgendaFichero {

    private File fichero;

    public AgendaFichero(String ruta) {
        this.fichero = new File(ruta);
    }

    // Si el fichero no existe (primera ejecucion), devuelve una agenda vacia.
    public Agenda cargar() throws IOException, ClassNotFoundException {
        if (!fichero.exists()) {
            return new Agenda();
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichero))) {
            return (Agenda) in.readObject();
        }
    }

    public void guardar(Agenda agenda) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichero))) {
            out.writeObject(agenda);
        }
    }
}
