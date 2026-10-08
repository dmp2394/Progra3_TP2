package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class ProvinciaJSON {

	private ArrayList<Provincia> provincias;

	public ProvinciaJSON() {
		new ArrayList<Provincia>();
	}

	public static ProvinciaJSON leerJSON(String archivo) {
		Gson gson = new Gson();
		ProvinciaJSON ret = null;

		try {
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, ProvinciaJSON.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return ret;
	}

	public ArrayList<Provincia> obtenerProvincias() {
		return provincias;
	}

}
