package controlador;

import modelo.DisenioRegiones;
import modelo.Provincia;
import modelo.ProvinciaJSON;

public class ControladorVentanaConfigGrafo {

	private final DisenioRegiones disenioRegiones;
	private boolean jsonCargado;

	public ControladorVentanaConfigGrafo(DisenioRegiones disenioRegiones) {
		this.disenioRegiones = disenioRegiones;
	}

	public boolean puedeCargarMapa() {
		return disenioRegiones.obtenerProvincias().isEmpty();
	}

	public void ubicarProvincia(String provincia, int x, int y) {
		disenioRegiones.agregarProvincia(provincia, x, y);
	}

	public void eliminarProvincia(String nombre) {
		disenioRegiones.eliminarProvincia(nombre);
	}

	public void agregarConexion(String origen, String destino, Integer peso) {
		disenioRegiones.agregarConexion(origen, destino, peso);
	}

	public void eliminarConexion(String origen, String destino) {
		disenioRegiones.eliminarConexion(origen, destino);
	}

	public void cargarProvinciasDesdeJSON(String ruta) {
		ProvinciaJSON archivo = ProvinciaJSON.leerJSON(ruta);

		for (Provincia provincia : archivo.obtenerProvincias()) {
			disenioRegiones.agregarProvincia(provincia.getNombre(), 0, 0);
		}
	}

	public void agregarProvincia(String provincia) {
		disenioRegiones.agregarProvincia(provincia, 0, 0);
	}

	public boolean existeProvincia(String nombre) {
		return disenioRegiones.existeProvincia(nombre);
	}

	public boolean existenProvincias() {
		return disenioRegiones.existenProvincias();
	}

}
