package controlador;

import java.util.ArrayList;
import java.util.List;

import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.Provincia;

public class ControladorVentanaConfigRegiones {

	private final DisenioRegiones disenioRegiones;

	// Recibe el mismo modelo que se cargó en la ventana anterior.
	public ControladorVentanaConfigRegiones(DisenioRegiones disenioRegiones) {
		this.disenioRegiones = disenioRegiones;
	}

	public void separarEnRegionesConexas(Integer k) {
		disenioRegiones.separarEnRegionesConexas(k);
	}

	public ArrayList<Provincia> obtenerProvincias() {
		return disenioRegiones.obtenerProvincias();
	}

	public ArrayList<Arista<String>> obtenerConexiones() {
		return disenioRegiones.obtenerConexiones();
	}

	public List<List<String>> obtenerRegiones() {
		return disenioRegiones.obtenerRegiones();

	}
}
