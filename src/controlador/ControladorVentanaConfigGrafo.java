package controlador;

import modelo.DisenioRegiones;
import modelo.ProvinciaJSON;
import modelo.AristaJSON;
import modelo.DatosDeEntradaJSON;

public class ControladorVentanaConfigGrafo {

	private final DisenioRegiones disenioRegiones;
	private boolean jsonCargado;

	public ControladorVentanaConfigGrafo(DisenioRegiones disenioRegiones) {
		this.disenioRegiones = disenioRegiones;
	}

	// Sirve tanto para ubicar una provincia por primera vez como para moverla.
	public void ubicarProvincia(String nombre, double latitud, double longitud) {
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
		DatosDeEntradaJSON archivo = DatosDeEntradaJSON.leerJSON(ruta);

		if (archivo == null || archivo.obtenerProvincias().isEmpty()) {
			throw new IllegalArgumentException(
					"El archivo JSON no tiene provincias.");
		}

		Integer k = archivo.obtenerCantidadRegiones();
		int cantidadProvincias = archivo.obtenerProvincias().size();

		if (k != null && (k < 1 || k > cantidadProvincias)) {
			throw new IllegalArgumentException(
					"La cantidad de regiones debe estar entre 1 y la cantidad de provincias.");
		}

		// La ubicación es opcional en el archivo: o vienen latitud y longitud, o
		// no viene ninguna (la provincia queda sin ubicar y se ubica en el mapa).
		// Se valida antes de agregar nada para no dejar el modelo a medio cargar.
		for (ProvinciaJSON provincia : archivo.obtenerProvincias()) {
			if ((provincia.obtenerLatitud() == null) != (provincia.obtenerLongitud() == null)) {
				throw new IllegalArgumentException("La provincia " + provincia.obtenerNombre()
						+ " debe tener latitud y longitud, o ninguna de las dos.");
			}
		}

		for (ProvinciaJSON provincia : archivo.obtenerProvincias()) {
			if (provincia.obtenerLatitud() == null) {
				disenioRegiones.agregarProvincia(provincia.obtenerNombre());
			} else {
				disenioRegiones.agregarProvincia(
						provincia.obtenerNombre(),
						provincia.obtenerLatitud(),
						provincia.obtenerLongitud());
			}
		}

		for (AristaJSON conexion : archivo.obtenerConexiones()) {
			disenioRegiones.agregarConexion(
					conexion.obtenerProvinciaOrigen(),
					conexion.obtenerProvinciaDestino(),
					conexion.obtenerSimilaridad());
		}

		return k;
	}

	public void agregarProvincia(String provincia) {
		disenioRegiones.agregarProvincia(provincia);
	}

	public boolean todasLasProvinciasUbicadas() {
		return disenioRegiones.todasLasProvinciasUbicadas();
	}

	public boolean existenProvincias() {
		return disenioRegiones.existenProvincias();
	}

}
