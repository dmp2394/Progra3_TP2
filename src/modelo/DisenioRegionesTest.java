package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class DisenioRegionesTest {

	public static class AgregarProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test(expected = IllegalArgumentException.class)
		public void agregarProvinciaNombreNuloTest() {
			disenio.agregarProvincia(null, -31.42, -64.18);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarProvinciaNombreVacioTest() {
			disenio.agregarProvincia("", -31.42, -64.18);
		}

		@Test
		public void agregarProvinciaConEspaciosTest() {
			disenio.agregarProvincia("  Córdoba  ", -31.42, -64.18);

			assertEquals("Córdoba", disenio.obtenerProvincia(" Córdoba ").obtenerNombre());
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarProvinciaYaExistenteTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia(" Córdoba ", -31.0, -64.0);
		}

	}

	public static class AgregarProvinciaCasoFeliz {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void agregarProvinciaGuardaDatosTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			Provincia provincia = disenio.obtenerProvincia("Córdoba");

			assertEquals(Arrays.asList("Córdoba", -31.42, -64.18), datosDe(provincia));
		}

		@Test
		public void agregarProvinciaSumaUnaProvinciaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			assertEquals(1, disenio.obtenerProvincias().size());
		}
	}

	public static class ObtenerProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que consultar una provincia inexistente dé error.
		@Test(expected = IllegalArgumentException.class)
		public void obtenerProvinciaInexistenteTest() {
			disenio.obtenerProvincia("Córdoba");
		}
	}

	public static class SetPosicionProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test(expected = IllegalArgumentException.class)
		public void setPosicionLatitudFueraDeRangoTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			disenio.setPosicionProvincia("Córdoba", -91.0, -64.5);
		}

		@Test
		public void cambiarPosicionInvalidaNoCambiaDatosPreviosTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			try {
				disenio.setPosicionProvincia("Córdoba", -91.0, -64.5);
			} catch (IllegalArgumentException esperada) {
				// La excepción se prueba en setPosicionLatitudFueraDeRangoTest.
			}

			assertEquals(Arrays.asList("Córdoba", -31.42, -64.18), datosDe(disenio.obtenerProvincia("Córdoba")));
		}

		// Comprueba que no se pueda mover una provincia que no existe.
		@Test(expected = IllegalArgumentException.class)
		public void setPosicionProvinciaInexistenteTest() {
			disenio.setPosicionProvincia("Córdoba", -31.0, -64.5);
		}
	}

	public static class SetPosicionProvinciaCasoFeliz {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que cambiar la posición conserve las conexiones.
		@Test
		public void cambiarPosicionConservaConexionTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.setPosicionProvincia("Córdoba", -31.0, -64.5);

			assertEquals(Arrays.asList(Arrays.asList("Córdoba", "Santa Fe", 4)), datosDeConexiones(disenio));
		}

		@Test
		public void cambiarPosicionActualizaCoordenadasTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			disenio.setPosicionProvincia("Córdoba", -31.0, -64.5);

			assertEquals(Arrays.asList("Córdoba", -31.0, -64.5), datosDe(disenio.obtenerProvincia("Córdoba")));
		}

		@Test
		public void cambiarPosicionNoDuplicaLaProvinciaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.setPosicionProvincia("Córdoba", -31.0, -64.5);

			assertEquals(2, disenio.obtenerProvincias().size());
		}
	}

	public static class AgregarConexionExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que ambos extremos deban existir.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionProvinciaInexistenteTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
		}

		// Comprueba que el peso no pueda ser nulo.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionPesoNuloTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.agregarConexion("Córdoba", "Santa Fe", null);
		}

		// Comprueba que una conexión no pueda repetirse al revés.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionDuplicadaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
			disenio.agregarConexion("Santa Fe", "Córdoba", 9);
		}

		// Comprueba que una provincia no pueda conectarse consigo misma.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionMismaProvinciaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			disenio.agregarConexion("Córdoba", "Córdoba", 4);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionPesoCeroTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.agregarConexion("Córdoba", "Santa Fe", 0);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionPesoNegativoTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.agregarConexion("Córdoba", "Santa Fe", -4);
		}
	}

	public static class AgregarConexionCasoFeliz {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que la conexión conserve sus extremos y peso.
		@Test
		public void agregarConexionGuardaDatosTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			assertEquals(Arrays.asList(Arrays.asList("Córdoba", "Santa Fe", 4)), datosDeConexiones(disenio));
		}
	}

	public static class EliminarConexionExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que no se pueda eliminar una conexión que no existe.
		@Test(expected = IllegalArgumentException.class)
		public void eliminarConexionInexistenteTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.eliminarConexion("Córdoba", "Santa Fe");
		}

		// Comprueba que ambos extremos deban existir.
		@Test(expected = IllegalArgumentException.class)
		public void eliminarConexionProvinciaInexistenteTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			disenio.eliminarConexion("Córdoba", "Santa Fe");
		}
	}

	public static class EliminarConexionCasoFeliz {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que eliminar una conexión conserve las provincias.
		@Test
		public void eliminarConexionConservaProvinciasTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.eliminarConexion("Santa Fe", "Córdoba");

			assertEquals(2, disenio.obtenerProvincias().size());
		}

		// Comprueba que la conexión ya no esté.
		@Test
		public void eliminarConexionLaQuitaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.eliminarConexion("Santa Fe", "Córdoba");

			assertTrue(disenio.obtenerConexiones().isEmpty());
		}
	}

	public static class EliminarProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que los datos de una provincia eliminada
		// ya no puedan consultarse.
		@Test(expected = IllegalArgumentException.class)
		public void provinciaEliminadaNoSePuedeConsultarTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			disenio.eliminarProvincia("Córdoba");

			disenio.obtenerProvincia("Córdoba");
		}

		// Comprueba que no se pueda eliminar una provincia que no existe.
		@Test(expected = IllegalArgumentException.class)
		public void eliminarProvinciaInexistenteTest() {
			disenio.eliminarProvincia("Córdoba");
		}
	}

	public static class EliminarProvinciaCasoFeliz {
		
		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que eliminar una provincia quite sus conexiones
		// y conserve las conexiones entre las demás provincias.
		@Test
		public void eliminarProvinciaEliminaSusConexionesTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
			disenio.agregarProvincia("Entre Ríos", -31.73, -60.53);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
			disenio.agregarConexion("Santa Fe", "Entre Ríos", 3);

			disenio.eliminarProvincia("Córdoba");

			assertEquals(Arrays.asList(Arrays.asList("Santa Fe", "Entre Ríos", 3)), datosDeConexiones(disenio));
		}

		// Comprueba que eliminar una provincia conserve las demás.
		@Test
		public void eliminarProvinciaConservaLasDemasTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
			disenio.agregarProvincia("Entre Ríos", -31.73, -60.53);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
			disenio.agregarConexion("Santa Fe", "Entre Ríos", 3);

			disenio.eliminarProvincia("Córdoba");

			assertEquals(2, disenio.obtenerProvincias().size());
		}
	}

	public static class ConsultasExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que inicialmente no haya provincias ni conexiones.
		@Test
		public void inicialmenteSinProvinciasTest() {
			assertTrue(disenio.obtenerProvincias().isEmpty());
		}

		@Test
		public void inicialmenteSinConexionesTest() {
			assertTrue(disenio.obtenerConexiones().isEmpty());
		}

		// Comprueba que modificar las listas devueltas
		// no modifique las colecciones internas del modelo.
		@Test
		public void obtenerProvinciasDevuelveCopiaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);

			disenio.obtenerProvincias().clear();

			assertEquals(2, disenio.obtenerProvincias().size());
		}

		@Test
		public void obtenerConexionesDevuelveCopiaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.obtenerConexiones().clear();

			assertEquals(1, disenio.obtenerConexiones().size());
		}
	}

	public static class SepararEnRegionesConexasExcepcionesYCasosBorde {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		@Test(expected = IllegalArgumentException.class)
		public void separarConKCeroTest() {
			disenio.separarEnRegionesConexas(0);
		}

		@Test(expected = IllegalArgumentException.class)
		public void separarConKNegativoTest() {
			disenio.separarEnRegionesConexas(-1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void separarConKMayorALaCantidadDeProvinciasTest() {
			disenio.separarEnRegionesConexas(5);
		}

		// Sin provincias, ningún k es válido.
		@Test(expected = IllegalArgumentException.class)
		public void separarSinProvinciasTest() {
			DisenioRegiones vacio = new DisenioRegiones();

			vacio.separarEnRegionesConexas(1);
		}

		// Si hay provincias sin conectar no se puede armar el AGM.
		@Test(expected = IllegalArgumentException.class)
		public void separarConProvinciasSinConectarTest() {
			DisenioRegiones sinConectar = new DisenioRegiones();
			sinConectar.agregarProvincia("Córdoba", -31.42, -64.18);
			sinConectar.agregarProvincia("Santa Fe", -31.63, -60.7);
			sinConectar.agregarProvincia("Corrientes", -27.47, -58.83);
			sinConectar.agregarConexion("Córdoba", "Santa Fe", 4);

			sinConectar.separarEnRegionesConexas(2);
		}

	
		// Con k = 1 todas las provincias quedan en una sola región.
		@Test
		public void separarConKUnoTest() {
			disenio.separarEnRegionesConexas(1);

			Set<Set<String>> esperadas = new HashSet<>();
			esperadas.add(new HashSet<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos", "Corrientes")));

			assertEquals(esperadas, compararRegiones(disenio.obtenerRegiones()));
		}

		// Con k = cantidad de provincias cada provincia queda sola.
		@Test
		public void separarConKIgualALaCantidadDeProvinciasTest() {
			disenio.separarEnRegionesConexas(4);

			Set<Set<String>> esperadas = new HashSet<>();
			esperadas.add(new HashSet<>(Arrays.asList("Córdoba")));
			esperadas.add(new HashSet<>(Arrays.asList("Santa Fe")));
			esperadas.add(new HashSet<>(Arrays.asList("Entre Ríos")));
			esperadas.add(new HashSet<>(Arrays.asList("Corrientes")));

			assertEquals(esperadas, compararRegiones(disenio.obtenerRegiones()));
		}

		// Una sola provincia con k = 1 forma una región.
		@Test
		public void separarUnaSolaProvinciaTest() {
			DisenioRegiones unaProvincia = new DisenioRegiones();
			unaProvincia.agregarProvincia("Córdoba", -31.42, -64.18);

			unaProvincia.separarEnRegionesConexas(1);

			Set<Set<String>> esperadas = new HashSet<>();
			esperadas.add(new HashSet<>(Arrays.asList("Córdoba")));

			assertEquals(esperadas, compararRegiones(unaProvincia.obtenerRegiones()));
		}
	}

	public static class SepararEnRegionesConexasCasoFeliz {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		// AGM: Santa Fe-Entre Ríos (3), Córdoba-Santa Fe (4), Entre Ríos-Corrientes
		// (10).
		// Con k = 2 se saca la arista de mayor peso (10).
		@Test
		public void separarEnDosRegionesTest() {
			disenio.separarEnRegionesConexas(2);

			Set<Set<String>> esperadas = new HashSet<>();
			esperadas.add(new HashSet<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos")));
			esperadas.add(new HashSet<>(Arrays.asList("Corrientes")));

			assertEquals(esperadas, compararRegiones(disenio.obtenerRegiones()));
		}

		// Con k = 3 se sacan las dos aristas de mayor peso (10 y 4).
		@Test
		public void separarEnTresRegionesTest() {
			disenio.separarEnRegionesConexas(3);

			Set<Set<String>> esperadas = new HashSet<>();
			esperadas.add(new HashSet<>(Arrays.asList("Santa Fe", "Entre Ríos")));
			esperadas.add(new HashSet<>(Arrays.asList("Córdoba")));
			esperadas.add(new HashSet<>(Arrays.asList("Corrientes")));

			assertEquals(esperadas, compararRegiones(disenio.obtenerRegiones()));
		}

		// Separar no tiene que modificar las provincias ni las conexiones cargadas.
		@Test
		public void separarNoModificaLasProvinciasTest() {
			disenio.separarEnRegionesConexas(3);

			assertEquals(4, disenio.obtenerProvincias().size());
		}

		@Test
		public void separarNoModificaLasConexionesTest() {
			disenio.separarEnRegionesConexas(3);

			assertEquals(4, disenio.obtenerConexiones().size());
		}

		// Volver a separar reemplaza las regiones anteriores.
		@Test
		public void separarDosVecesReemplazaLasRegionesTest() {
			disenio.separarEnRegionesConexas(3);
			disenio.separarEnRegionesConexas(2);

			assertEquals(2, disenio.obtenerRegiones().size());
		}
	}

	public static class ObtenerRegionesExcepcionesYCasosBorde {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		// Antes de separar no hay regiones.
		@Test
		public void obtenerRegionesAntesDeSepararTest() {
			assertTrue(disenio.obtenerRegiones().isEmpty());
		}

		// Modificar lo devuelto no modifica las regiones internas.
		@Test
		public void obtenerRegionesDevuelveCopiaTest() {
			disenio.separarEnRegionesConexas(2);

			List<List<String>> regiones = disenio.obtenerRegiones();
			regiones.get(0).clear();
			regiones.clear();

			Set<Set<String>> regionesInternas = compararRegiones(disenio.obtenerRegiones());

			assertTrue(regionesInternas.contains(new HashSet<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos"))));
		}
	}

	public static class ObtenerRegionesCasoFeliz {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		// Entre todas las regiones están todas las provincias, sin repetir.
		@Test
		public void obtenerRegionesContieneTodasLasProvinciasTest() {
			disenio.separarEnRegionesConexas(2);

			List<String> obtenidas = new ArrayList<>();
			for (List<String> region : disenio.obtenerRegiones())
				obtenidas.addAll(region);
			Collections.sort(obtenidas);

			List<String> esperadas = new ArrayList<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos", "Corrientes"));
			Collections.sort(esperadas);

			assertEquals(esperadas, obtenidas);
		}
	}

	public static class RegistrarExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test(expected = IllegalArgumentException.class)
		public void registrarObservadorNullTest() {
			disenio.registrar(null);
		}
	}

	public static class ExisteProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test(expected = IllegalArgumentException.class)
		public void existeProvinciaNombreNuloTest() {
			disenio.existeProvincia(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeProvinciaNombreVacioTest() {
			disenio.existeProvincia("   ");
		}

		@Test
		public void existeProvinciaSinProvinciasTest() {
			assertFalse(disenio.existeProvincia("Córdoba"));
		}

		@Test
		public void existeProvinciaConEspaciosTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			assertTrue(disenio.existeProvincia("  Córdoba  "));
		}

		@Test
		public void existeProvinciaDistingueMayusculasTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			assertFalse(disenio.existeProvincia("córdoba"));
		}

		@Test
		public void existeProvinciaEliminadaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.eliminarProvincia("Córdoba");

			assertFalse(disenio.existeProvincia("Córdoba"));
		}
	}

	public static class ExisteProvinciaCasoFeliz {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void existeProvinciaAgregadaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			assertTrue(disenio.existeProvincia("Córdoba"));
		}

		@Test
		public void existeProvinciaNoAgregadaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			assertFalse(disenio.existeProvincia("Santa Fe"));
		}
	}

	public static class ExistenProvinciasExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void existenProvinciasSinProvinciasTest() {
			assertFalse(disenio.existenProvincias());
		}

		@Test
		public void existenProvinciasDespuesDeEliminarLaUnicaTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);
			disenio.eliminarProvincia("Córdoba");

			assertFalse(disenio.existenProvincias());
		}
	}

	public static class ExistenProvinciasCasoFeliz {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void existenProvinciasConProvinciasTest() {
			disenio.agregarProvincia("Córdoba", -31.42, -64.18);

			assertTrue(disenio.existenProvincias());
		}
	}

	// Córdoba-Santa Fe (4), Santa Fe-Entre Ríos (3),
	// Córdoba-Entre Ríos (8), Entre Ríos-Corrientes (10).
	private static DisenioRegiones crearDisenioDeCuatroProvincias() {
		DisenioRegiones disenio = new DisenioRegiones();
		disenio.agregarProvincia("Córdoba", -31.42, -64.18);
		disenio.agregarProvincia("Santa Fe", -31.63, -60.7);
		disenio.agregarProvincia("Entre Ríos", -31.73, -60.53);
		disenio.agregarProvincia("Corrientes", -27.47, -58.83);
		disenio.agregarConexion("Córdoba", "Santa Fe", 4);
		disenio.agregarConexion("Santa Fe", "Entre Ríos", 3);
		disenio.agregarConexion("Córdoba", "Entre Ríos", 8);
		disenio.agregarConexion("Entre Ríos", "Corrientes", 10);
		return disenio;
	}

	// Nombre, latitud y longitud de una provincia, para comparar todo junto.
	private static List<Object> datosDe(Provincia provincia) {
		List<Object> datos = new ArrayList<>();
		datos.add(provincia.obtenerNombre());
		datos.add(provincia.obtenerLatitud());
		datos.add(provincia.obtenerLongitud());
		return datos;
	}

	// Extremos y peso de cada conexión, para comparar todo junto.
	private static List<List<Object>> datosDeConexiones(DisenioRegiones disenio) {
		List<List<Object>> datos = new ArrayList<>();
		for (Arista<String> conexion : disenio.obtenerConexiones()) {
			List<Object> datosConexion = new ArrayList<>();
			datosConexion.add(conexion.obtenerExtremo1());
			datosConexion.add(conexion.obtenerExtremo2());
			datosConexion.add(conexion.devolverPeso());
			datos.add(datosConexion);
		}
		return datos;
	}

	// Las regiones no tienen un orden garantizado:
	// se comparan como conjuntos de conjuntos.
	private static Set<Set<String>> compararRegiones(List<List<String>> regiones) {
		Set<Set<String>> conjuntos = new HashSet<>();
		for (List<String> region : regiones)
			conjuntos.add(new HashSet<>(region));

		return conjuntos;
	}

}
