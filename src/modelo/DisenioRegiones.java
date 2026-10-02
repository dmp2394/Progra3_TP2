package modelo;

public class DisenioRegiones {

	private GrafoConPesos provinciasYSimilaridades;
	private GrafoConPesos agmProvinciasYSimilaridades;
	private GrafoConPesos disenioRegiones;

	public DisenioRegiones(GrafoConPesos<String> grafo) {
		this.provinciasYSimilaridades = grafo;
		this.agmProvinciasYSimilaridades = null;
		this.disenioRegiones = null;
	}

	public void separarEnRegionesConexas(int cantidadRegionesConexas) {

		crearAGM(this.provinciasYSimilaridades);

		eliminarAristasDeMayorPeso(cantidadRegionesConexas - 1);

	}

	private void crearAGM(GrafoConPesos GrafoConPesos) {
		this.agmProvinciasYSimilaridades = null;
	}

	private void eliminarAristasDeMayorPeso(int cantidad) {
		// opera directamente sobre el atributo agmProvinciasYSimilaridades, nose si
		// esta bien esto

	}

}
