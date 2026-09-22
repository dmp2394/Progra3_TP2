package modelo;

public class DiseñoRegiones {

	private Grafo grafoDeProvinciasYSimilaridades;
	private Grafo agmDeProvinciasYSimilaridades;

	public DiseñoRegiones() {
		// no se que sentido tiene un constructor vacio, pero seria la clase principal
	}

	public void separarEnRegionesConexas(int cantidadRegionesConexas) {

		crearAGM(this.grafoDeProvinciasYSimilaridades);

		eliminarAristasDeMayorPeso(cantidadRegionesConexas - 1);

	}

	private void eliminarAristasDeMayorPeso(int cantidad) {
		// TODO Auto-generated method stub

	}

	private void crearAGM(Grafo grafo) {
		// TODO Auto-generated method stub
		this.agmDeProvinciasYSimilaridades = null;
	}

	public void crearGrafoDeProvinciasYSimilaridades() {

		this.grafoDeProvinciasYSimilaridades = null;
	}

}
