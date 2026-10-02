package modelo;

public class DisenioRegiones {

	private GrafoConPesos<String> provinciasYSimilaridades;
	private GrafoConPesos<String> agmProvinciasYSimilaridades;
	private GrafoConPesos<String> disenioRegiones;

	public DisenioRegiones(GrafoConPesos<String> grafo) {
		this.provinciasYSimilaridades = grafo;
		this.agmProvinciasYSimilaridades = null;
		this.disenioRegiones = null;
	}

	public void separarEnRegionesConexas(int cantidadRegionesConexas) {

		crearArbolGeneradorMinimo(this.provinciasYSimilaridades);

		eliminarAristasDeMayorPeso(cantidadRegionesConexas - 1);

	}

	private void crearArbolGeneradorMinimo(GrafoConPesos<String> GrafoConPesos) {
		this.agmProvinciasYSimilaridades = Kruskal.crearArbolGeneradorMinimo(provinciasYSimilaridades);
	}

	private void eliminarAristasDeMayorPeso(int cantidad) {
		// opera directamente sobre el atributo agmProvinciasYSimilaridades, nose si
		// esta bien esto

	}

}
