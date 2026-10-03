package presentador;

import modelo.DisenioRegiones;
import vista.VentanaPrincipal;

public class Presentador {

	// Todos estos metodos los ejecuta la vista para hacer lo que quiera hacer

	// Clase principal, todo opera sobre este
	private DisenioRegiones disenioRegiones;
	private VentanaPrincipal ventanaPrincipal;

	// Constructor
	public Presentador(VentanaPrincipal ventanaPrincipal) {
		this.disenioRegiones = new DisenioRegiones();
		this.ventanaPrincipal = ventanaPrincipal;
	}

	// Carga el grafo en memoria
	public void cargarMapa() {
		leerYCargarConexiones();
	}

	private void leerYCargarConexiones() {
		// Lee los vertices y aristas cargados en la vista y los carga en la clase ppal
		// leer y cargar en un for

		this.disenioRegiones.agregarProvincia();
		this.disenioRegiones.agregarArista();

	}

	public void ejecutarAlgoritmo(int k) {
		this.disenioRegiones.separarEnRegionesConexas(k);
	}

}
