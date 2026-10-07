package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class DisenioRegionesTest {

    public static class AgregarProvinciaExcepcionesYCasosBorde {

        private DisenioRegiones disenio = new DisenioRegiones();

        // Comprueba que los espacios externos no generen otro nombre.
        @Test
        public void agregarProvinciaEliminaEspaciosTest() {
            disenio.agregarProvincia("  Córdoba  ", 320, 180);

            assertEquals(
                    "Córdoba",
                    disenio.getProvincia(" Córdoba ").getNombre());
        }

        // Comprueba que no se pueda agregar una provincia repetida.
        @Test(expected = IllegalArgumentException.class)
        public void agregarProvinciaDuplicadaTest() {
            disenio.agregarProvincia("Córdoba", 320, 180);
            disenio.agregarProvincia(" Córdoba ", 400, 200);
        }
    }

    public static class AgregarProvinciaHappyPath {

        private DisenioRegiones disenio = new DisenioRegiones();

        // Comprueba que se guarden el nombre y las coordenadas.
        @Test
        public void agregarProvinciaGuardaDatosTest() {
            disenio.agregarProvincia("Córdoba", 320, 180);

            Provincia provincia = disenio.getProvincia("Córdoba");

            assertEquals("Córdoba", provincia.getNombre());
            assertEquals(320, provincia.getX());
            assertEquals(180, provincia.getY());
            assertEquals(1, disenio.getProvincias().size());
        }
    }

    public static class GetProvinciaExcepcionesYCasosBorde {

        private DisenioRegiones disenio = new DisenioRegiones();

        // Comprueba que consultar una provincia inexistente dé error.
        @Test(expected = IllegalArgumentException.class)
        public void getProvinciaInexistenteTest() {
            disenio.getProvincia("Córdoba");
        }
    }

    public static class SetPosicionProvinciaExcepcionesYCasosBorde {

        private DisenioRegiones disenio = new DisenioRegiones();

        // Comprueba que una posición inválida no cambie los datos previos.
        @Test
        public void cambiarPosicionInvalidaConservaDatosTest() {
            disenio.agregarProvincia("Córdoba", 320, 180);

            assertThrowsPosicionInvalida();

            Provincia provincia = disenio.getProvincia("Córdoba");

            assertEquals(320, provincia.getX());
            assertEquals(180, provincia.getY());
        }

        private void assertThrowsPosicionInvalida() {
            try {
                disenio.setPosicionProvincia("Córdoba", -1, 200);
                org.junit.Assert.fail(
                        "Se esperaba una IllegalArgumentException.");
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

            Provincia provincia = disenio.getProvincia("Córdoba");
            Arista<String> conexion = disenio.getConexiones().get(0);

            assertEquals(350, provincia.getX());
            assertEquals(200, provincia.getY());
            assertEquals(2, disenio.getProvincias().size());
            assertEquals(1, disenio.getConexiones().size());
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
    }

    public static class AgregarConexionHappyPath {

        private DisenioRegiones disenio = new DisenioRegiones();

        // Comprueba que la conexión conserve sus extremos y peso.
        @Test
        public void agregarConexionGuardaDatosTest() {
            disenio.agregarProvincia("Córdoba", 320, 180);
            disenio.agregarProvincia("Santa Fe", 420, 150);

            disenio.agregarConexion("Córdoba", "Santa Fe", 4);

            Arista<String> conexion = disenio.getConexiones().get(0);

            assertEquals("Córdoba", conexion.obtenerExtremo1());
            assertEquals("Santa Fe", conexion.obtenerExtremo2());
            assertEquals(4, conexion.devolverPeso());
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

            assertTrue(disenio.getConexiones().isEmpty());
            assertEquals(2, disenio.getProvincias().size());
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

            disenio.getProvincia("Córdoba");
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

            assertEquals(2, disenio.getProvincias().size());
            assertEquals(1, disenio.getConexiones().size());

            Arista<String> conexion = disenio.getConexiones().get(0);

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
            assertTrue(disenio.getProvincias().isEmpty());
            assertTrue(disenio.getConexiones().isEmpty());
        }

        // Comprueba que modificar las listas devueltas
        // no modifique las colecciones internas del modelo.
        @Test
        public void consultasDevuelvenCopiasTest() {
            disenio.agregarProvincia("Córdoba", 320, 180);
            disenio.agregarProvincia("Santa Fe", 420, 150);
            disenio.agregarConexion("Córdoba", "Santa Fe", 4);

            disenio.getProvincias().clear();
            disenio.getConexiones().clear();

            assertEquals(2, disenio.getProvincias().size());
            assertEquals(1, disenio.getConexiones().size());
        }
    }

}
