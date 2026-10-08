package presentador;

import modelo.DisenioRegiones;
import modelo.ObservadorDisenioRegiones;
import vista.VentanaConfigGrafo;

public class PresentadorVentanaConfigGrafo implements ObservadorDisenioRegiones {

	private final DisenioRegiones disenioRegiones;
	private final VentanaConfigGrafo ventana;

	// Nombre que espera un click en el mapa para ser ubicado.
	private String provinciaPendiente;

	public PresentadorVentanaConfigGrafo(VentanaConfigGrafo ventana) {
		this.disenioRegiones = new DisenioRegiones();
		this.ventana = ventana;

		// Registro este presentador (que es un observador) para que sea notificado
		// cuando el modelo cambie.
		this.disenioRegiones.registrar(this);
	}

	// ---------------------------------------------------------------
	// Notificación del modelo
	// ---------------------------------------------------------------

	@Override
	public void notificar(DisenioRegiones disenioRegiones) {
		ventana.mostrarProvinciasYConexiones(disenioRegiones.obtenerProvincias(),
				disenioRegiones.obtenerConexiones());
	}

	// ---------------------------------------------------------------
	// Acciones del usuario
	// ---------------------------------------------------------------

	// El usuario quiere cambiar el mapa.
	public void cambiarMapa() {
		if (!disenioRegiones.obtenerProvincias().isEmpty()) {
			ventana.mostrarMensaje("Eliminá las provincias antes de cambiar el mapa.");
			return;
		}

		ventana.elegirImagen();
	}

	// La vista terminó de cargar una imagen nueva.
	public void mapaCargado() {
		provinciaPendiente = null;
		ventana.mostrarEstado("Mapa cargado. Escribí una provincia y presioná Ubicar.");
	}

	// El usuario escribió un nombre y pidió ubicarlo en el mapa.
	public void prepararUbicacion(String nombre) {
		if (!ventana.tieneImagen()) {
			ventana.mostrarMensaje("Primero cargá una imagen del mapa.");
			return;
		}

		if (nombre == null || nombre.trim().isEmpty()) {
			ventana.mostrarMensaje("Escribí el nombre de la provincia.");
			return;
		}

		if (disenioRegiones.existeProvincia(nombre)) {
			ventana.mostrarMensaje("La provincia ya existe.");
			return;
		}

		provinciaPendiente = nombre.trim();
		ventana.mostrarEstado("Hacé click sobre el mapa para ubicar: " + provinciaPendiente);
	}

	// El usuario hizo click en el mapa (coordenadas de la imagen original).
	public void ubicarProvincia(int x, int y) {
		if (provinciaPendiente == null) {
			return;
		}

		try {
			disenioRegiones.agregarProvincia(provinciaPendiente, x, y);

			provinciaPendiente = null;
			ventana.limpiarNombre();
			ventana.mostrarEstado("Provincia agregada. Podés ubicar otra o crear conexiones.");

		} catch (IllegalArgumentException e) {
			ventana.mostrarMensaje(e.getMessage());
		}
	}

	// El usuario quiere eliminar la provincia seleccionada (null si no seleccionó ninguna).
	public void eliminarProvincia(String nombre) {
		if (nombre == null) {
			ventana.mostrarMensaje("Seleccioná una provincia de la lista.");
			return;
		}

		try {
			disenioRegiones.eliminarProvincia(nombre);

		} catch (IllegalArgumentException e) {
			ventana.mostrarMensaje(e.getMessage());
		}
	}

	// El usuario quiere agregar una conexión. Peso es null si no ingresó un entero válido.
	public void agregarConexion(String origen, String destino, Integer peso) {
		if (origen == null || destino == null) {
			ventana.mostrarMensaje("Primero agregá las provincias que querés conectar.");
			return;
		}

		if (peso == null) {
			ventana.mostrarMensaje("Ingresá un peso entero válido.");
			return;
		}

		try {
			disenioRegiones.agregarConexion(origen, destino, peso);

		} catch (IllegalArgumentException e) {
			ventana.mostrarMensaje(e.getMessage());
		}
	}

	// El usuario quiere eliminar la conexión seleccionada (null si no seleccionó ninguna).
	public void eliminarConexion(String origen, String destino) {
		if (origen == null || destino == null) {
			ventana.mostrarMensaje("Seleccioná una conexión de la tabla.");
			return;
		}

		try {
			disenioRegiones.eliminarConexion(origen, destino);

		} catch (IllegalArgumentException e) {
			ventana.mostrarMensaje(e.getMessage());
		}
	}

	// El usuario quiere pasar a la configuración de regiones.
	public void continuar() {
		if (!ventana.tieneImagen() || disenioRegiones.obtenerProvincias().isEmpty()) {
			ventana.mostrarMensaje("Cargá un mapa y ubicá al menos una provincia.");
			return;
		}

		if (provinciaPendiente != null) {
			ventana.mostrarMensaje("Ubicá la provincia pendiente antes de continuar.");
			return;
		}

		ventana.abrirConfigRegiones(disenioRegiones);
	}
}
