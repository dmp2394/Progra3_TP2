package modelo;

public class Provincia {

	private final String nombre;
	private final int x;
	private final int y;

	public Provincia(String nombre, int x, int y) {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede ser nulo o estar vacío.");
		}

		if (x < 0 || y < 0) {
			throw new IllegalArgumentException("Las coordenadas no pueden ser negativas.");
		}

		this.nombre = nombre.trim();
		this.x = x;
		this.y = y;
	}

	public String obtenerNombre() {
		return nombre;
	}

	public int obtenerX() {
		return x;
	}

	public int obtenerY() {
		return y;
	}
}