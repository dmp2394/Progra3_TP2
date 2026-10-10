package modelo;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DisenioRegiones {

	private Grafo<String> provinciasYSimilaridades;
	private Map<String, Provincia> provincias;
	private List<List<String>> regiones = new ArrayList<>();
	private ArrayList<ObservadorDisenioRegiones> observadores;

	public DisenioRegiones() {
		provinciasYSimilaridades = new Grafo<>();
		provincias = new LinkedHashMap<>();
		observadores = new ArrayList<>();
	}

	public void registrar(ObservadorDisenioRegiones observador) {
		observadores.add(observador);
	}

	public void agregarProvincia(String nombre, int x, int y) {
		Provincia provincia = new Provincia(nombre, x, y);
		String nombreProvincia = provincia.obtenerNombre();

		if (provincias.containsKey(nombreProvincia)) {
			throw new IllegalArgumentException("La provincia ya existe.");
		}

		provinciasYSimilaridades.agregarVertice(nombreProvincia);
		provincias.put(nombreProvincia, provincia);

		notificarObservadores();
	}

	private void notificarObservadores() {
		for (ObservadorDisenioRegiones observador : observadores)
			observador.notificar(this);

	}

	public void eliminarProvincia(String nombre) {
		Provincia provincia = obtenerProvincia(nombre);
		String nombreProvincia = provincia.obtenerNombre();

		provinciasYSimilaridades.eliminarVertice(nombreProvincia);
		provincias.remove(nombreProvincia);

		notificarObservadores();
	}

	public void setPosicionProvincia(String nombre, int x, int y) {
		Provincia provinciaActual = obtenerProvincia(nombre);

		Provincia provinciaActualizada = new Provincia(provinciaActual.obtenerNombre(), x, y);

		provincias.put(provinciaActualizada.obtenerNombre(), provinciaActualizada);
		notificarObservadores();
	}

	public Provincia obtenerProvincia(String nombre) {
		String nombreProvincia = validarNombre(nombre);
		Provincia provincia = provincias.get(nombreProvincia);

		if (provincia == null) {
			throw new IllegalArgumentException("La provincia no existe.");
		}

		return provincia;
	}

	public boolean existeProvincia(String nombre) {
		return provincias.containsKey(validarNombre(nombre));
	}

	public ArrayList<Provincia> obtenerProvincias() {
		return new ArrayList<>(provincias.values());
	}

	public void agregarConexion(String provinciaOrigen, String provinciaDestino, Integer similaridad) {

		if (similaridad == null) {
			throw new IllegalArgumentException("La similaridad de la conexión no puede ser nula.");
		}

		Provincia origen = obtenerProvincia(provinciaOrigen);
		Provincia destino = obtenerProvincia(provinciaDestino);

		provinciasYSimilaridades.agregarArista(origen.obtenerNombre(), destino.obtenerNombre(), similaridad);

		notificarObservadores();
	}

	public void eliminarConexion(String provinciaOrigen, String provinciaDestino) {

		Provincia origen = obtenerProvincia(provinciaOrigen);
		Provincia destino = obtenerProvincia(provinciaDestino);

		provinciasYSimilaridades.eliminarArista(origen.obtenerNombre(), destino.obtenerNombre());

		notificarObservadores();
	}

	public ArrayList<Arista<String>> obtenerConexiones() {
		return provinciasYSimilaridades.obtenerAristas();
	}

	public void separarEnRegionesConexas(int k) {
		int cantidadProvincias = provinciasYSimilaridades.obtenerVertices().size();

		if (k < 1 || k > cantidadProvincias) {
			throw new IllegalArgumentException("K debe estar entre 1 y la cantidad de provincias.");
		}

		Grafo<String> agm = Kruskal.crearArbolGeneradorMinimo(provinciasYSimilaridades);

		List<Arista<String>> aristas = agm.obtenerAristas();

		// Las aristas están ordenadas de menor a mayor peso.
		for (int i = aristas.size() - 1; i >= aristas.size() - (k - 1); i--) {
			Arista<String> arista = aristas.get(i);
			agm.eliminarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());
		}

		regiones = buscarComponentes(agm);
	}

	private List<List<String>> buscarComponentes(Grafo<String> grafo) {

	    Set<String> visitadas = new HashSet<>();
	    List<List<String>> resultado = new ArrayList<>();

	    for (String inicio : grafo.obtenerVertices()) {

	        if (!visitadas.add(inicio)) {
	            continue;
	        }

	        List<String> region = new ArrayList<>();
	        ArrayDeque<String> pendientes = new ArrayDeque<>();

	        pendientes.add(inicio);

	        while (!pendientes.isEmpty()) {

	            String actual = pendientes.remove();

	            region.add(actual);

	            for (String vecino : grafo.obtenerVecinos(actual)) {

	                if (visitadas.add(vecino)) {
	                    pendientes.add(vecino);
	                }
	            }
	        }

	        resultado.add(region);
	    }

	    return resultado;
	}
	

	public List<List<String>> obtenerRegiones() {
		List<List<String>> copia = new ArrayList<>();

		for (List<String> region : regiones) {
			copia.add(new ArrayList<>(region));
		}

		return copia;
	}

	// Valida el nombre y elimina espacios al principio y al final.
	private String validarNombre(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede estar vacío.");
		}

		return nombre.trim();
	}

	public boolean existenProvincias() {
		return !provincias.isEmpty();

	}
}