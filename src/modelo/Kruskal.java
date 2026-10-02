package modelo;

public class Kruskal {
	// Implementación del algoritmo de Kruskal para encontrar el árbol generador
	// mínimo
	// Este es un ejemplo simple y no funcional, solo para ilustrar la estructura de
	// la clase.

	// ET := ∅
	// i := 1
	// mientras i ≤ n − 1 hacer {
	// elegir e ∈ E tal que l(e) sea mínima entre las aristas
	// que no forman circuito con las aristas que ya están en ET
	// ET := ET ∪ {e}
	// i := i + 1
	// }
	// retornar T = (V, ET )

	public static GrafoConPesos<String> crearArbolGeneradorMinimo(GrafoConPesos<String> grafo) {
		GrafoConPesos<String> et = new GrafoConPesos<>();

		int i = 1;

		while (i <= grafo.tamano() - 1) {
			elegirYAgregarAristaLongMinimaNoCircuito(et);

			i++;
		}

		GrafoConPesos<String> agm = new GrafoConPesos<>();
		return agm;
	}

	private static void elegirYAgregarAristaLongMinimaNoCircuito(GrafoConPesos<String> et) {
		// busco arista de longitud minima, la agrego y despues si forma circuito la
		// borro

	}

}
