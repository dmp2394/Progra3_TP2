package presentador;

import java.util.ArrayList;

import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.Provincia;
import vista.VentanaPrincipal;

public class Presentador {

    private final DisenioRegiones disenioRegiones;

    public Presentador() {
        disenioRegiones = new DisenioRegiones();
    }

    // Constructor temporal para que la ventana actual siga compilando.
    public Presentador(VentanaPrincipal ventanaPrincipal) {
        this();
    }

    // Solicita al modelo agregar una provincia con su posición.
    public void agregarProvincia(String nombre, int x, int y) {
        disenioRegiones.agregarProvincia(nombre, x, y);
    }

    // Solicita eliminar la provincia y sus conexiones.
    public void eliminarProvincia(String nombre) {
        disenioRegiones.eliminarProvincia(nombre);
    }

    // Solicita cambiar la posición de una provincia.
    public void setPosicionProvincia(String nombre, int x, int y) {
        disenioRegiones.setPosicionProvincia(nombre, x, y);
    }

    // Devuelve los datos de una provincia.
    public Provincia getProvincia(String nombre) {
        return disenioRegiones.getProvincia(nombre);
    }

    // Devuelve las provincias para que la vista pueda mostrarlas.
    public ArrayList<Provincia> getProvincias() {
        return disenioRegiones.getProvincias();
    }

    // Solicita agregar una conexión.
    public void agregarConexion(
            String provinciaOrigen,
            String provinciaDestino,
            Integer peso) {

        disenioRegiones.agregarConexion(
                provinciaOrigen,
                provinciaDestino,
                peso);
    }

    // Solicita eliminar una conexión.
    public void eliminarConexion(
            String provinciaOrigen,
            String provinciaDestino) {

        disenioRegiones.eliminarConexion(
                provinciaOrigen,
                provinciaDestino);
    }

    // Devuelve las conexiones para que la vista pueda mostrarlas.
    public ArrayList<Arista<String>> getConexiones() {
        return disenioRegiones.getConexiones();
    }

    // Permite conocer el máximo de regiones que podrá elegirse.
    public int getCantidadProvincias() {
        return disenioRegiones.getProvincias().size();
    }

    // La generación se completará en la etapa 5.
    public void ejecutarAlgoritmo(int k) {
        disenioRegiones.separarEnRegionesConexas(k);
    }

    // Método temporal: la vista actual todavía utiliza esta llamada.
    public void cargarMapa() {
        throw new UnsupportedOperationException(
                "La carga desde la tabla anterior será reemplazada "
                + "por las acciones de la nueva interfaz.");
    }
}