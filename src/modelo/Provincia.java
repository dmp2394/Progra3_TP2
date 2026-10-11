package modelo;

// Una provincia puede estar sin ubicar (recién agregada, o cargada desde un
// archivo sin coordenadas). En ese caso latitud y longitud son null; si está
// ubicada, ambas tienen un valor válido. Nunca hay una sola de las dos.
public class Provincia {

	private final String nombre;
	private final Double latitud;
	private final Double longitud;

	// Provincia sin ubicar.
	public Provincia(String nombre) {
		this.nombre = validarNombre(nombre);
		this.latitud = null;
		this.longitud = null;
	}

	public Provincia(String nombre, double latitud, double longitud) {
		this.nombre = validarNombre(nombre);

		if (Double.isNaN(latitud) || latitud < -90 || latitud > 90) {
			throw new IllegalArgumentException("La latitud debe estar entre -90 y 90.");
		}

		if (Double.isNaN(longitud) || longitud < -180 || longitud > 180) {
			throw new IllegalArgumentException("La longitud debe estar entre -180 y 180.");
		}

		this.latitud = latitud;
		this.longitud = longitud;
	}

	private static String validarNombre(String nombre) {
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la provincia no puede ser nulo o estar vacío.");
		}

		return nombre.trim();
	}

	public String obtenerNombre() {
		return nombre;
	}

	public double obtenerLatitud() {
		verificarUbicada();
		return latitud;
	}

	public double obtenerLongitud() {
		verificarUbicada();
		return longitud;
	}

	private void verificarUbicada() {
		if (!estaUbicada()) {
			throw new IllegalStateException("La provincia " + nombre + " no está ubicada.");
		}
	}

	public boolean estaUbicada() {
		return latitud != null;
	}
}
