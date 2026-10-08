package presentador;

import java.util.ArrayList;
import java.util.List;

import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.ObservadorDisenioRegiones;
import modelo.Provincia;
import vista.VentanaConfigGrafo;

public class PresentadorVentanaConfigGrafo implements ObservadorDisenioRegiones {

	private final DisenioRegiones disenioRegiones;
	private final VentanaConfigGrafo ventana;

	public PresentadorVentanaConfigGrafo(VentanaConfigGrafo ventana) {
		this.disenioRegiones = new DisenioRegiones();
		this.ventana = ventana;

		// Registro este presentador (que es un observador) para que sea notificado
		// cuando el modelo decida
		this.disenioRegiones.registrar(this);
	}

	// Solicita al modelo agregar una provincia con su posición.
	public void agregarProvincia(String nombre, int x, int y) {
		disenioRegiones.agregarProvincia(nombre, x, y);
	}

	// Solicita eliminar la provincia y sus conexiones.
	public void eliminarProvincia(String nombre) {
		disenioRegiones.eliminarProvincia(nombre);
	}

	// Devuelve los datos de una provincia.
	public Provincia obtenerProvincia(String nombre) {
		return disenioRegiones.obtenerProvincia(nombre);
	}

	// Consulta al modelo si ya existe una provincia con ese nombre.
	public boolean existeProvincia(String nombre) {
		return disenioRegiones.existeProvincia(nombre);
	}

	// Devuelve las provincias para que la vista pueda mostrarlas.
	public ArrayList<Provincia> obtenerProvincias() {
		return disenioRegiones.obtenerProvincias();
	}

	// Solicita agregar una conexión.
	public void agregarConexion(String provinciaOrigen, String provinciaDestino, Integer peso) {

		disenioRegiones.agregarConexion(provinciaOrigen, provinciaDestino, peso);
	}

	// Solicita eliminar una conexión.
	public void eliminarConexion(String provinciaOrigen, String provinciaDestino) {

		disenioRegiones.eliminarConexion(provinciaOrigen, provinciaDestino);
	}

	// Devuelve las conexiones para que la vista pueda mostrarlas.
	public ArrayList<Arista<String>> getConexiones() {
		return disenioRegiones.obtenerConexiones();
	}

	// Permite conocer el máximo de regiones que podrá elegirse.
	public int getCantidadProvincias() {
		return disenioRegiones.obtenerProvincias().size();
	}

	// La generación se completará en la etapa 5.
	public void ejecutarAlgoritmo(int k) {
		disenioRegiones.separarEnRegionesConexas(k);
	}

	// Devuelve las regiones generadas por el algoritmo.
	public List<List<String>> getRegiones() {
		return disenioRegiones.getRegiones();
	}

	public void notificar(DisenioRegiones disenioRegiones) {
		ventana.mostrarProvinciasYConexiones(disenioRegiones.obtenerProvincias(), disenioRegiones.obtenerConexiones());
	}
}