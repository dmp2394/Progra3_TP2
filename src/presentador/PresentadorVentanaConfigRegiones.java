package presentador;

import modelo.DisenioRegiones;
import vista.VentanaConfigRegiones;

public class PresentadorVentanaConfigRegiones {

	private final DisenioRegiones disenioRegiones;
	private final VentanaConfigRegiones ventana;

	// Recibe el mismo modelo que se cargó en la ventana anterior.
	public PresentadorVentanaConfigRegiones(VentanaConfigRegiones ventana, DisenioRegiones disenioRegiones) {
		this.ventana = ventana;
		this.disenioRegiones = disenioRegiones;
	}

	// Muestra en la ventana el grafo cargado hasta el momento.
	public void mostrarGrafoCargado() {
		ventana.mostrarProvinciasYConexiones(disenioRegiones.obtenerProvincias(), disenioRegiones.obtenerConexiones());
	}

	public void generarRegiones(Integer k) {
		if (k == null) {
			ventana.mostrarError("Ingresá una cantidad de regiones válida.");
			return;
		}

		try {
			disenioRegiones.separarEnRegionesConexas(k);

			ventana.abrirResultado(disenioRegiones.getRegiones(), disenioRegiones.obtenerProvincias(),
					disenioRegiones.obtenerConexiones());

		} catch (IllegalArgumentException | IllegalStateException e) {
			ventana.mostrarError(e.getMessage());
		}
	}
}
