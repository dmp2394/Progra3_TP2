package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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
			disenio.agregarProvincia(null, 320, 180);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarProvinciaNombreVacioTest() {
			disenio.agregarProvincia("", 320, 180);
		}

		@Test
		public void agregarProvinciaConEspaciosTest() {
			disenio.agregarProvincia("  Córdoba  ", 320, 180);

			assertEquals("Córdoba", disenio.obtenerProvincia(" Córdoba ").getNombre());
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarProvinciaYaExistenteTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia(" Córdoba ", 400, 200);
		}

	}

	public static class AgregarProvinciaHappyPath {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void agregarProvinciaTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			Provincia provincia = disenio.obtenerProvincia("Córdoba");

			assertEquals("Córdoba", provincia.getNombre());
			assertEquals(320, provincia.getX());
			assertEquals(180, provincia.getY());
			assertEquals(1, disenio.obtenerProvincias().size());
		}
	}

	public static class obtenerProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que consultar una provincia inexistente dé error.
		@Test(expected = IllegalArgumentException.class)
		public void obtenerProvinciaInexistenteTest() {
			disenio.obtenerProvincia("Córdoba");
		}
	}

	public static class SetPosicionProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void cambiarPosicionInvalidaNoCambiaDatosPreviosTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			assertThrowsPosicionInvalida();

			Provincia provincia = disenio.obtenerProvincia("Córdoba");

			assertEquals(320, provincia.getX());
			assertEquals(180, provincia.getY());
		}

		// Comprueba que no se pueda mover una provincia que no existe.
		@Test(expected = IllegalArgumentException.class)
		public void setPosicionProvinciaInexistenteTest() {
			disenio.setPosicionProvincia("Córdoba", 350, 200);
		}

		private void assertThrowsPosicionInvalida() {
			try {
				disenio.setPosicionProvincia("Córdoba", -1, 200);
				org.junit.Assert.fail("Se esperaba una IllegalArgumentException.");
			} catch (IllegalArgumentException esperada) {
				// La excepción es el comportamiento esperado.
			}
		}
	}

	public static class SetPosicionProvinciaHappyPath {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que cambiar la posición conserve las conexiones.
		@Test
		public void cambiarPosicionConservaConexionTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.setPosicionProvincia("Córdoba", 350, 200);

			Provincia provincia = disenio.obtenerProvincia("Córdoba");
			Arista<String> conexion = disenio.obtenerConexiones().get(0);

			assertEquals(350, provincia.getX());
			assertEquals(200, provincia.getY());
			assertEquals(2, disenio.obtenerProvincias().size());
			assertEquals(1, disenio.obtenerConexiones().size());
			assertEquals("Córdoba", conexion.obtenerExtremo1());
			assertEquals("Santa Fe", conexion.obtenerExtremo2());
			assertEquals(4, conexion.devolverPeso());
		}
	}

	public static class AgregarConexionExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que ambos extremos deban existir.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionProvinciaInexistenteTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
		}

		// Comprueba que el peso no pueda ser nulo.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionPesoNuloTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);

			disenio.agregarConexion("Córdoba", "Santa Fe", null);
		}

		// Comprueba que una conexión no pueda repetirse al revés.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionDuplicadaTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
			disenio.agregarConexion("Santa Fe", "Córdoba", 9);
		}

		// Comprueba que una provincia no pueda conectarse consigo misma.
		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionMismaProvinciaTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			disenio.agregarConexion("Córdoba", "Córdoba", 4);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionPesoCeroTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);

			disenio.agregarConexion("Córdoba", "Santa Fe", 0);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarConexionPesoNegativoTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);

			disenio.agregarConexion("Córdoba", "Santa Fe", -4);
		}
	}

	public static class AgregarConexionHappyPath {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que la conexión conserve sus extremos y peso.
		@Test
		public void agregarConexionGuardaDatosTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			Arista<String> conexion = disenio.obtenerConexiones().get(0);

			assertEquals("Córdoba", conexion.obtenerExtremo1());
			assertEquals("Santa Fe", conexion.obtenerExtremo2());
			assertEquals(4, conexion.devolverPeso());
		}
	}

	public static class EliminarConexionExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que no se pueda eliminar una conexión que no existe.
		@Test(expected = IllegalArgumentException.class)
		public void eliminarConexionInexistenteTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);

			disenio.eliminarConexion("Córdoba", "Santa Fe");
		}

		// Comprueba que ambos extremos deban existir.
		@Test(expected = IllegalArgumentException.class)
		public void eliminarConexionProvinciaInexistenteTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			disenio.eliminarConexion("Córdoba", "Santa Fe");
		}
	}

	public static class EliminarConexionHappyPath {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que eliminar una conexión conserve las provincias.
		@Test
		public void eliminarConexionConservaProvinciasTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.eliminarConexion("Santa Fe", "Córdoba");

			assertTrue(disenio.obtenerConexiones().isEmpty());
			assertEquals(2, disenio.obtenerProvincias().size());
		}
	}

	public static class EliminarProvinciaExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que los datos de una provincia eliminada
		// ya no puedan consultarse.
		@Test(expected = IllegalArgumentException.class)
		public void provinciaEliminadaNoSePuedeConsultarTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			disenio.eliminarProvincia("Córdoba");

			disenio.obtenerProvincia("Córdoba");
		}

		// Comprueba que no se pueda eliminar una provincia que no existe.
		@Test(expected = IllegalArgumentException.class)
		public void eliminarProvinciaInexistenteTest() {
			disenio.eliminarProvincia("Córdoba");
		}
	}

	public static class EliminarProvinciaHappyPath {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que eliminar una provincia quite sus conexiones
		// y conserve las conexiones entre las demás provincias.
		@Test
		public void eliminarProvinciaEliminaSusConexionesTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);
			disenio.agregarProvincia("Entre Ríos", 500, 150);

			disenio.agregarConexion("Córdoba", "Santa Fe", 4);
			disenio.agregarConexion("Santa Fe", "Entre Ríos", 3);

			disenio.eliminarProvincia("Córdoba");

			assertEquals(2, disenio.obtenerProvincias().size());
			assertEquals(1, disenio.obtenerConexiones().size());

			Arista<String> conexion = disenio.obtenerConexiones().get(0);

			assertEquals("Santa Fe", conexion.obtenerExtremo1());
			assertEquals("Entre Ríos", conexion.obtenerExtremo2());
			assertEquals(3, conexion.devolverPeso());
		}
	}

	public static class ConsultasExcepcionesYCasosBorde {

		private DisenioRegiones disenio = new DisenioRegiones();

		// Comprueba que inicialmente no haya provincias ni conexiones.
		@Test
		public void inicialmenteVacioTest() {
			assertTrue(disenio.obtenerProvincias().isEmpty());
			assertTrue(disenio.obtenerConexiones().isEmpty());
		}

		// Comprueba que modificar las listas devueltas
		// no modifique las colecciones internas del modelo.
		@Test
		public void consultasDevuelvenCopiasTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.agregarProvincia("Santa Fe", 420, 150);
			disenio.agregarConexion("Córdoba", "Santa Fe", 4);

			disenio.obtenerProvincias().clear();
			disenio.obtenerConexiones().clear();

			assertEquals(2, disenio.obtenerProvincias().size());
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

		// Si el grafo no es conexo no existe el AGM.
		@Test(expected = IllegalArgumentException.class)
		public void separarGrafoNoConexoTest() {
			DisenioRegiones noConexo = new DisenioRegiones();
			noConexo.agregarProvincia("Córdoba", 320, 180);
			noConexo.agregarProvincia("Santa Fe", 420, 150);
			noConexo.agregarProvincia("Entre Ríos", 500, 150);
			noConexo.agregarConexion("Córdoba", "Santa Fe", 4);

			noConexo.separarEnRegionesConexas(1);
		}

		// Con k = 1 todas las provincias quedan en una sola región.
		@Test
		public void separarConKUnoTest() {
			disenio.separarEnRegionesConexas(1);

			Set<Set<String>> regiones = compararRegiones(disenio.getRegiones());

			assertEquals(1, regiones.size());
			assertTrue(
					regiones.contains(new HashSet<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos", "Corrientes"))));
		}

		// Con k = cantidad de provincias cada provincia queda sola.
		@Test
		public void separarConKIgualALaCantidadDeProvinciasTest() {
			disenio.separarEnRegionesConexas(4);

			Set<Set<String>> regiones = compararRegiones(disenio.getRegiones());

			assertEquals(4, regiones.size());
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Córdoba"))));
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Santa Fe"))));
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Entre Ríos"))));
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Corrientes"))));
		}

		// Una sola provincia con k = 1 forma una región.
		@Test
		public void separarUnaSolaProvinciaTest() {
			DisenioRegiones unaProvincia = new DisenioRegiones();
			unaProvincia.agregarProvincia("Córdoba", 320, 180);

			unaProvincia.separarEnRegionesConexas(1);

			Set<Set<String>> regiones = compararRegiones(unaProvincia.getRegiones());

			assertEquals(1, regiones.size());
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Córdoba"))));
		}
	}

	public static class SepararEnRegionesConexasHappyPath {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		// AGM: Santa Fe-Entre Ríos (3), Córdoba-Santa Fe (4), Entre Ríos-Corrientes
		// (10).
		// Con k = 2 se saca la arista de mayor peso (10).
		@Test
		public void separarEnDosRegionesTest() {
			disenio.separarEnRegionesConexas(2);

			Set<Set<String>> regiones = compararRegiones(disenio.getRegiones());

			assertEquals(2, regiones.size());
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos"))));
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Corrientes"))));
		}

		// Con k = 3 se sacan las dos aristas de mayor peso (10 y 4).
		@Test
		public void separarEnTresRegionesTest() {
			disenio.separarEnRegionesConexas(3);

			Set<Set<String>> regiones = compararRegiones(disenio.getRegiones());

			assertEquals(3, regiones.size());
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Santa Fe", "Entre Ríos"))));
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Córdoba"))));
			assertTrue(regiones.contains(new HashSet<>(Arrays.asList("Corrientes"))));
		}

		// Separar no tiene que modificar las provincias ni las conexiones cargadas.
		@Test
		public void separarNoModificaLasConexionesTest() {
			disenio.separarEnRegionesConexas(3);

			assertEquals(4, disenio.obtenerProvincias().size());
			assertEquals(4, disenio.obtenerConexiones().size());
		}

		// Volver a separar reemplaza las regiones anteriores.
		@Test
		public void separarDosVecesReemplazaLasRegionesTest() {
			disenio.separarEnRegionesConexas(3);
			disenio.separarEnRegionesConexas(2);

			assertEquals(2, disenio.getRegiones().size());
		}
	}

	public static class GetRegionesExcepcionesYCasosBorde {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		// Antes de separar no hay regiones.
		@Test
		public void getRegionesAntesDeSepararTest() {
			assertTrue(disenio.getRegiones().isEmpty());
		}

		// Modificar lo devuelto no modifica las regiones internas.
		@Test
		public void getRegionesDevuelveCopiaTest() {
			disenio.separarEnRegionesConexas(2);

			List<List<String>> regiones = disenio.getRegiones();
			regiones.get(0).clear();
			regiones.clear();

			Set<Set<String>> regionesInternas = compararRegiones(disenio.getRegiones());

			assertEquals(2, regionesInternas.size());
			assertTrue(regionesInternas.contains(new HashSet<>(Arrays.asList("Córdoba", "Santa Fe", "Entre Ríos"))));
			assertTrue(regionesInternas.contains(new HashSet<>(Arrays.asList("Corrientes"))));
		}
	}

	public static class GetRegionesHappyPath {

		private DisenioRegiones disenio = crearDisenioDeCuatroProvincias();

		// Entre todas las regiones están todas las provincias, sin repetir.
		@Test
		public void getRegionesContieneTodasLasProvinciasTest() {
			disenio.separarEnRegionesConexas(2);

			int cantidad = 0;
			Set<String> provincias = new HashSet<>();
			for (List<String> region : disenio.getRegiones()) {
				cantidad += region.size();
				provincias.addAll(region);
			}

			assertEquals(4, cantidad);
			assertEquals(4, provincias.size());
		}
	}

	// Córdoba-Santa Fe (4), Santa Fe-Entre Ríos (3),
	// Córdoba-Entre Ríos (8), Entre Ríos-Corrientes (10).
	private static DisenioRegiones crearDisenioDeCuatroProvincias() {
		DisenioRegiones disenio = new DisenioRegiones();
		disenio.agregarProvincia("Córdoba", 320, 180);
		disenio.agregarProvincia("Santa Fe", 420, 150);
		disenio.agregarProvincia("Entre Ríos", 500, 150);
		disenio.agregarProvincia("Corrientes", 520, 80);
		disenio.agregarConexion("Córdoba", "Santa Fe", 4);
		disenio.agregarConexion("Santa Fe", "Entre Ríos", 3);
		disenio.agregarConexion("Córdoba", "Entre Ríos", 8);
		disenio.agregarConexion("Entre Ríos", "Corrientes", 10);
		return disenio;
	}

	// Las regiones no tienen un orden garantizado:
	// se comparan como conjuntos de conjuntos.
	private static Set<Set<String>> compararRegiones(List<List<String>> regiones) {
		Set<Set<String>> conjuntos = new HashSet<>();
		for (List<String> region : regiones)
			conjuntos.add(new HashSet<>(region));

		return conjuntos;
	}

	// nuevo: a chequear
	public static class ExisteProvincia {

		private DisenioRegiones disenio = new DisenioRegiones();

		@Test
		public void existeProvinciaAgregadaTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			assertTrue(disenio.existeProvincia("Córdoba"));
		}

		@Test
		public void existeProvinciaNoAgregadaTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			assertFalse(disenio.existeProvincia("Santa Fe"));
		}

		@Test
		public void existeProvinciaSinProvinciasTest() {
			assertFalse(disenio.existeProvincia("Córdoba"));
		}

		@Test
		public void existeProvinciaConEspaciosTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			assertTrue(disenio.existeProvincia("  Córdoba  "));
		}

		@Test
		public void existeProvinciaDistingueMayusculasTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);

			assertFalse(disenio.existeProvincia("córdoba"));
		}

		@Test
		public void existeProvinciaEliminadaTest() {
			disenio.agregarProvincia("Córdoba", 320, 180);
			disenio.eliminarProvincia("Córdoba");

			assertFalse(disenio.existeProvincia("Córdoba"));
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeProvinciaNombreNuloTest() {
			disenio.existeProvincia(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeProvinciaNombreVacioTest() {
			disenio.existeProvincia("   ");
		}
	}

}
