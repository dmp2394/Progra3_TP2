package modelo;

public class DisenioRegiones {

	private GrafoConPesos GrafoConPesosDeProvinciasYSimilaridades;
	private GrafoConPesos agmDeProvinciasYSimilaridades;

	public DisenioRegiones() {
		// no se que sentido tiene un constructor vacio, pero seria la clase principal
	}

	public void separarEnRegionesConexas(int cantidadRegionesConexas) {

		crearAGM(this.GrafoConPesosDeProvinciasYSimilaridades);

		eliminarAristasDeMayorPeso(cantidadRegionesConexas - 1);

	}

	private void eliminarAristasDeMayorPeso(int cantidad) {
		// TODO Auto-generated method stub

	}

	private void crearAGM(GrafoConPesos GrafoConPesos) {
		// TODO Auto-generated method stub
		this.agmDeProvinciasYSimilaridades = null;
	}

	public void crearGrafoConPesosDeProvinciasYSimilaridades() {

		this.GrafoConPesosDeProvinciasYSimilaridades = null;
	}

}
