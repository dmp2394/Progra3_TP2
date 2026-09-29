package modelo;

import static org.junit.Assert.*;

import org.junit.Test;

public class GrafoTest {

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeNulotest() {
		Grafo grafo = new Grafo();
		grafo.agregarVertice(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarVerticeRepetest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia1);
	}
	
	@Test
	public void agregarVerticeExitosotest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		grafo.agregarVertice(provincia1);
		assertTrue(grafo.existeVertice(provincia1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarSinProvinciasParaAristatest() {
		Grafo grafo = new Grafo();
		grafo.agregarArista(null, null, new Ruta(100));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarBucletest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		grafo.agregarVertice(provincia1);
		grafo.agregarArista(provincia1, provincia1, new Ruta(100));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void agregarAristaMultipletest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		Provincia provincia2 = new Provincia("Córdoba");
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		Ruta ruta = new Ruta(100);
		grafo.agregarArista(provincia1, provincia2, ruta);
		grafo.agregarArista(provincia1, provincia2, ruta);
	}
	
	@Test
	public void agregarAristaExitosotest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		Provincia provincia2 = new Provincia("Córdoba");
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		Ruta ruta = new Ruta(100);
		grafo.agregarArista(provincia1, provincia2, ruta);
		assertTrue(grafo.existeArista(provincia1, provincia2));
	}
	
	
	
	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaConFaltaDeVerticetest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		Provincia provincia2 = new Provincia("Córdoba");
		grafo.agregarVertice(provincia1);
		grafo.eliminarArista(provincia1, provincia2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void eliminarAristaInexistentest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		Provincia provincia2 = new Provincia("Córdoba");
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		grafo.eliminarArista(provincia1, provincia2);
	}
	
	@Test
	public void eliminarAristaExitosotest() {
		Grafo grafo = new Grafo();
		Provincia provincia1 = new Provincia("Buenos Aires");
		Provincia provincia2 = new Provincia("Córdoba");
		grafo.agregarVertice(provincia1);
		grafo.agregarVertice(provincia2);
		Ruta ruta = new Ruta(100);
		grafo.agregarArista(provincia1, provincia2, ruta);
		grafo.eliminarArista(provincia1, provincia2);
		assertFalse(grafo.existeArista(provincia1, provincia2));
	}
	
	

	

}
