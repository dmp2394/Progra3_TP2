package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class ProvinciaJSON {

	private ArrayList<Provincia> provincias;
	private ArrayList<ConexionJSON> conexiones;
	private Integer cantidadRegiones;

	public Integer obtenerCantidadRegiones() {
		return cantidadRegiones;
	}

	public ArrayList<Provincia> obtenerProvincias() {
		return provincias;
	}

	public ArrayList<ConexionJSON> obtenerConexiones() {
		return conexiones == null ? new ArrayList<>() : conexiones;
	}

	public static ProvinciaJSON leerJSON(String archivo) {
		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
			return new Gson().fromJson(lector, ProvinciaJSON.class);
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se pudo leer el archivo JSON: " + archivo,
					e);
		}
	}

	public static class ConexionJSON {
		private String provinciaOrigen;
		private String provinciaDestino;
		private Integer similaridad;

		public String getProvinciaOrigen() {
			return provinciaOrigen;
		}

		public String getProvinciaDestino() {
			return provinciaDestino;
		}

		public Integer getSimilaridad() {
			return similaridad;
		}
	}
}
