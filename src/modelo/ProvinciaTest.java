package modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProvinciaTest {

    // Comprueba que el constructor guarde el nombre y la posición.
    @Test
    public void constructorGuardaDatosTest() {
        Provincia provincia = new Provincia("Córdoba", 320, 180);

        assertEquals("Córdoba", provincia.getNombre());
        assertEquals(320, provincia.getX());
        assertEquals(180, provincia.getY());
    }

    // Comprueba que se eliminen los espacios externos del nombre.
    @Test
    public void nombreConEspaciosTest() {
        Provincia provincia = new Provincia("  Córdoba  ", 320, 180);

        assertEquals("Córdoba", provincia.getNombre());
    }

    // Comprueba que el nombre no pueda ser nulo.
    @Test(expected = IllegalArgumentException.class)
    public void nombreNuloTest() {
        new Provincia(null, 320, 180);
    }

    // Comprueba que el nombre no pueda estar vacío.
    @Test(expected = IllegalArgumentException.class)
    public void nombreVacioTest() {
        new Provincia("", 320, 180);
    }

    // Comprueba que un nombre formado solo por espacios sea inválido.
    @Test(expected = IllegalArgumentException.class)
    public void nombreSoloEspaciosTest() {
        new Provincia("   ", 320, 180);
    }

    // Comprueba que la coordenada X no pueda ser negativa.
    @Test(expected = IllegalArgumentException.class)
    public void coordenadaXNegativaTest() {
        new Provincia("Córdoba", -1, 180);
    }

    // Comprueba que la coordenada Y no pueda ser negativa.
    @Test(expected = IllegalArgumentException.class)
    public void coordenadaYNegativaTest() {
        new Provincia("Córdoba", 320, -1);
    }

    // Comprueba que el origen de la imagen sea una posición válida.
    @Test
    public void coordenadasCeroTest() {
        Provincia provincia = new Provincia("Córdoba", 0, 0);

        assertEquals(0, provincia.getX());
        assertEquals(0, provincia.getY());
    }
}