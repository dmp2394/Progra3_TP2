package modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DisenioRegiones {

    private Grafo<String> provinciasYSimilaridades;
    private Map<String, Provincia> provincias;

    public DisenioRegiones() {
        provinciasYSimilaridades = new Grafo<>();
        provincias = new LinkedHashMap<>();
    }

    // Agrega los datos de la provincia y su vértice en el grafo.
    public void agregarProvincia(String nombre, int x, int y) {
        Provincia provincia = new Provincia(nombre, x, y);
        String nombreProvincia = provincia.getNombre();

        if (provincias.containsKey(nombreProvincia)) {
            throw new IllegalArgumentException(
                    "La provincia ya existe.");
        }

        provinciasYSimilaridades.agregarVertice(nombreProvincia);
        provincias.put(nombreProvincia, provincia);
    }

    // Elimina la provincia, su posición y todas sus conexiones.
    public void eliminarProvincia(String nombre) {
        Provincia provincia = getProvincia(nombre);
        String nombreProvincia = provincia.getNombre();

        provinciasYSimilaridades.eliminarVertice(nombreProvincia);
        provincias.remove(nombreProvincia);
    }

    // Reemplaza la posición conservando el nombre y las conexiones.
    public void setPosicionProvincia(String nombre, int x, int y) {
        Provincia provinciaActual = getProvincia(nombre);

        Provincia provinciaActualizada = new Provincia(
                provinciaActual.getNombre(), x, y);

        provincias.put(
                provinciaActualizada.getNombre(),
                provinciaActualizada);
    }

    // Devuelve los datos de una provincia existente.
    public Provincia getProvincia(String nombre) {
        String nombreProvincia = validarNombre(nombre);
        Provincia provincia = provincias.get(nombreProvincia);

        if (provincia == null) {
            throw new IllegalArgumentException(
                    "La provincia no existe.");
        }

        return provincia;
    }

    // Devuelve una copia de la colección de provincias.
    public ArrayList<Provincia> getProvincias() {
        return new ArrayList<>(provincias.values());
    }

    // Agrega una conexión entre dos provincias existentes.
    public void agregarConexion(
            String provinciaOrigen,
            String provinciaDestino,
            Integer similaridad) {

        if (similaridad == null) {
            throw new IllegalArgumentException(
                    "El peso de la conexión no puede ser nulo.");
        }

        Provincia origen = getProvincia(provinciaOrigen);
        Provincia destino = getProvincia(provinciaDestino);

        provinciasYSimilaridades.agregarArista(
                origen.getNombre(),
                destino.getNombre(),
                similaridad);
    }

    // Elimina la conexión entre dos provincias existentes.
    public void eliminarConexion(
            String provinciaOrigen,
            String provinciaDestino) {

        Provincia origen = getProvincia(provinciaOrigen);
        Provincia destino = getProvincia(provinciaDestino);

        provinciasYSimilaridades.eliminarArista(
                origen.getNombre(),
                destino.getNombre());
    }

    // Devuelve una copia de las conexiones del grafo.
    public ArrayList<Arista<String>> getConexiones() {
        return provinciasYSimilaridades.obtenerAristas();
    }

    // Pendiente de completar en la etapa de integración con Kruskal.
    public void separarEnRegionesConexas(int cantidadRegionesConexas) {
        throw new UnsupportedOperationException(
                "La generación de regiones todavía no está implementada.");
    }

    // Valida el nombre y elimina espacios al principio y al final.
    private String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la provincia no puede estar vacío.");
        }

        return nombre.trim();
    }
}