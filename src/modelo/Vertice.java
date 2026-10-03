package modelo;

public class Vertice {
	private String nombre;
	
	public Vertice(String nombre) {
		this.nombre = nombre;
	}
	
	public String devolverNombre() {
		return nombre;
	}
	
	public boolean cambiarNombre(String nuevoNombre) {
		if (nuevoNombre != null && !nuevoNombre.isEmpty()) {
			nombre = nuevoNombre;
			return true;
		}
		return false;
	}
}
