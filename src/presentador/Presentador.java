package presentador;

import java.util.ArrayList;

import modelo.DisenioRegiones;
import vista.VentanaPrincipal;

public class Presentador {

	// Todos estos metodos los ejecuta la vista para hacer lo que quiera hacer

	private DisenioRegiones disenioRegiones;
	private VentanaPrincipal ventanaPrincipal;

	// Constructor
	public Presentador(VentanaPrincipal ventanaPrincipal) {
		this.disenioRegiones = new DisenioRegiones();
		this.ventanaPrincipal = ventanaPrincipal;
	}

	public void ejecutarAlgoritmo(int k) {
		this.disenioRegiones.separarEnRegionesConexas(k);
	}

	// Carga el grafo en memoria
	public void cargarMapa() {
		leerYCargarConexiones();
	}

	private void leerYCargarConexiones() {
		// Lee los vertices y aristas cargados en la vista y los carga en la clase ppal
		// leer y cargar en un for
		ArrayList<String> provinciasOrigen = ventanaPrincipal.obtenerProvinciasOrigenDeConexiones();
		ArrayList<String> provinciasDestino = ventanaPrincipal.obtenerProvinciasDestinoDeConexiones();
		ArrayList<Integer> similaridadesOrigen = ventanaPrincipal.obtenerSimilaridadDeConexiones();

		// ciclo por todas las filas y voy agregando la conexion
		for (int i = 0; i < provinciasOrigen.size(); i++) {
			this.disenioRegiones.agregarConexion(provinciasOrigen.get(i), provinciasDestino.get(i),
					similaridadesOrigen.get(i));
		}

	}
}
