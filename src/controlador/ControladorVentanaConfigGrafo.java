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

	public void reubicarProvincia(String nombre, int x, int y) {
		disenioRegiones.setPosicionProvincia(nombre, x, y);
	}

	public void eliminarProvincia(String nombre) {
		disenioRegiones.eliminarProvincia(nombre);
	}

	public void agregarConexion(String origen, String destino, Integer similaridad) {
		disenioRegiones.agregarConexion(origen, destino, similaridad);
	}

	public void eliminarConexion(String origen, String destino) {
		disenioRegiones.eliminarConexion(origen, destino);
	}

	public void cargarProvinciasDesdeJSON(String ruta) {
		ProvinciaJSON archivo = ProvinciaJSON.leerJSON(ruta);

		if (archivo == null || archivo.obtenerProvincias() == null) {
			throw new IllegalArgumentException("No se pudo leer el JSON de provincias.");
		}

		for (Provincia provincia : archivo.obtenerProvincias()) {
			disenioRegiones.agregarProvincia(
					provincia.getNombre(),
					provincia.getX(),
					provincia.getY());
		}

		for (ProvinciaJSON.ConexionJSON conexion : archivo.obtenerConexiones()) {
			disenioRegiones.agregarConexion(
					conexion.getProvinciaOrigen(),
					conexion.getProvinciaDestino(),
					conexion.getSimilaridad());
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
