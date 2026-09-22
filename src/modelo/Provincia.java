package modelo;

public class Provincia {
	private String nombre;
	
	public Provincia(String nombre) {
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
