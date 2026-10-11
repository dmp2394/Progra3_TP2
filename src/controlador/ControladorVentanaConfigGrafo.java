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

	public void ubicarProvincia(String provincia, double latitud, double longitud) {
		disenioRegiones.agregarProvincia(provincia, latitud, longitud);
	}

	public void reubicarProvincia(String nombre, double latitud, double longitud) {
		disenioRegiones.setPosicionProvincia(nombre, latitud, longitud);
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

	public Integer cargarProvinciasDesdeJSON(String ruta) {
		ProvinciaJSON archivo = ProvinciaJSON.leerJSON(ruta);

		if (archivo == null || archivo.obtenerProvincias() == null) {
			throw new IllegalArgumentException(
					"No se pudo leer el JSON de provincias.");
		}

		Integer k = archivo.obtenerCantidadRegiones();
		int cantidadProvincias = archivo.obtenerProvincias().size();

		if (k != null && (k < 1 || k > cantidadProvincias)) {
			throw new IllegalArgumentException(
					"La cantidad de regiones debe estar entre 1 y la cantidad de provincias.");
		}

		for (Provincia provincia : archivo.obtenerProvincias()) {
			disenioRegiones.agregarProvincia(
					provincia.obtenerNombre(),
					provincia.obtenerLatitud(),
					provincia.obtenerLongitud());
		}

		for (ProvinciaJSON.ConexionJSON conexion : archivo.obtenerConexiones()) {
			disenioRegiones.agregarConexion(
					conexion.getProvinciaOrigen(),
					conexion.getProvinciaDestino(),
					conexion.getSimilaridad());
		}

		return k;
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
