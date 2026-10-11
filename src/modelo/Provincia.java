package modelo;

public class Provincia {

	private final String nombre;
	private final double latitud;
	private final double longitud;

	public Provincia(String nombre, double latitud, double longitud) {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede ser nulo o estar vacío.");
		}

		if (Double.isNaN(latitud) || latitud < -90 || latitud > 90) {
			throw new IllegalArgumentException("La latitud debe estar entre -90 y 90.");
		}

		if (Double.isNaN(longitud) || longitud < -180 || longitud > 180) {
			throw new IllegalArgumentException("La longitud debe estar entre -180 y 180.");
		}

		this.nombre = nombre.trim();
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public String obtenerNombre() {
		return nombre;
	}

	public double obtenerLatitud() {
		return latitud;
	}

	public double obtenerLongitud() {
		return longitud;
	}
}