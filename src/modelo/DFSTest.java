package modelo;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
@RunWith(Enclosed.class)

public  class DFSTest {
	private static String provincia1 = "Buenos Aires";
	private static String provincia2 = "Santa Fé";
	private static String provincia3 = "La Pampa";
	private static String provincia4 = "Entre Ríos";

		
		
		public static class DFSExcepcionesYCasosBordeTest {

			private Grafo<String> grafo = new Grafo<>();

			@Test (expected = IllegalArgumentException.class)
			public  void dfsListaNulaTest() {
				grafo.agregarVertice(provincia1);
				grafo.agregarVertice(provincia2);
				grafo.agregarVertice(provincia3);
				grafo.agregarVertice(provincia4);

				grafo.agregarArista(provincia1, provincia2, 10);
				grafo.agregarArista(provincia2, provincia3, 20);
				grafo.agregarArista(provincia3, provincia4, 30);
				
				grafo.dfs(provincia1, null);
				
			}
			
			@Test (expected = IllegalArgumentException.class)
			public  void dfsVerticeNoExistenteTest() {
				grafo.agregarVertice(provincia1);
				grafo.agregarVertice(provincia2);
				grafo.agregarVertice(provincia3);
				grafo.agregarVertice(provincia4);

				grafo.agregarArista(provincia1, provincia2, 10);
				grafo.agregarArista(provincia2, provincia3, 20);
				grafo.agregarArista(provincia3, provincia4, 30);
				
				Set<String> visitados = new HashSet<>();
				grafo.dfs("Córdoba", visitados);
				
			}
			
			@Test (expected = IllegalArgumentException.class)
			public  void dfsVerticeNullTest() {
				grafo.agregarVertice(provincia1);
				grafo.agregarVertice(provincia2);
				grafo.agregarVertice(provincia3);
				grafo.agregarVertice(provincia4);

				grafo.agregarArista(provincia1, provincia2, 10);
				grafo.agregarArista(provincia2, provincia3, 20);
				grafo.agregarArista(provincia3, provincia4, 30);
				
				Set<String> visitados = new HashSet<>();
				grafo.dfs(null, visitados);
				
			}
		}
		public static class DFSFuncionaTest {
			
			private Grafo<String> grafo = new Grafo<>();
		@Test
		public void dfsRecorreTodosLosVerticesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);

			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia2, provincia3, 20);
			grafo.agregarArista(provincia3, provincia4, 30);
			
			Set<String> visitados = new HashSet<>();
			grafo.dfs(provincia1, visitados);
			
			assertEquals(4, visitados.size());
		}
		}
		public static class esConexoCasosBordeTest {
			
			private Grafo<String> grafo = new Grafo<>();
			@Test
			public void esDisconexoConVerticesSinAristasTest() {
				grafo.agregarVertice(provincia1);
				grafo.agregarVertice(provincia2);
				assertFalse(DFS.esConexo(grafo));
			}
		}
		
		public static class esConexoTest {	
			
			private Grafo<String> grafo = new Grafo<>();
			@Test
			public void esVerdadQueConexoTest() {
				
				grafo.agregarVertice(provincia1);
				grafo.agregarVertice(provincia2);
				grafo.agregarVertice(provincia3);
				grafo.agregarVertice(provincia4);

				grafo.agregarArista(provincia1, provincia2, 10);
				grafo.agregarArista(provincia2, provincia3, 20);
				grafo.agregarArista(provincia3, provincia4, 30);
				
				assertTrue(DFS.esConexo(grafo));
			}
			
			@Test
			public void esConexoSinVerticesTest() {
				Grafo<String> grafoVacio = new Grafo<>();
				assertTrue(DFS.esConexo(grafoVacio));
			}
			
			
			@Test
			public void esInconexoTest() {
				grafo.agregarVertice(provincia1);
				grafo.agregarVertice(provincia2);
				grafo.agregarVertice(provincia3);
				grafo.agregarVertice(provincia4);

				grafo.agregarArista(provincia1, provincia2, 10);
				grafo.agregarArista(provincia3, provincia4, 30);
				
				assertFalse(DFS.esConexo(grafo));
			}
		}
		
		public static class RecorrerExcepcionesTest {

		    @Test(expected = IllegalArgumentException.class)
		    public void recorrerGrafoNullTest() {
		        DFS.recorrer(null, "Buenos Aires");
		    }

		    @Test(expected = IllegalArgumentException.class)
		    public void recorrerOrigenNullTest() {
		        Grafo<String> grafo = new Grafo<>();
		        grafo.agregarVertice("Buenos Aires");

		        DFS.recorrer(grafo, null);
		    }

		    @Test(expected = IllegalArgumentException.class)
		    public void recorrerVerticeNoPerteneceAlGrafoTest() {
		        Grafo<String> grafo = new Grafo<>();
		        grafo.agregarVertice("Buenos Aires");

		        DFS.recorrer(grafo, "Cordoba");
		    }
		}
		public static class RecorrerFuncionaTest {

		    private Grafo<String> grafo = new Grafo<>();

		    @Test
		    public void recorrerDevuelveUnSoloVerticeCuandoEstaAisladoTest() {

		        grafo.agregarVertice(provincia1);

		        Set<String> visitados = DFS.recorrer(grafo, provincia1);

		        assertEquals(1, visitados.size());
		    }

		    @Test
		    public void recorrerVisitaTodosLosVerticesConectadosTest() {

		        grafo.agregarVertice(provincia1);
		        grafo.agregarVertice(provincia2);
		        grafo.agregarVertice(provincia3);
		        grafo.agregarVertice(provincia4);

		        grafo.agregarArista(provincia1, provincia2, 10);
		        grafo.agregarArista(provincia2, provincia3, 20);
		        grafo.agregarArista(provincia3, provincia4, 30);

		        Set<String> visitados = DFS.recorrer(grafo, provincia1);

		        assertEquals(4, visitados.size());
		    }

		    @Test
		    public void recorrerNoVisitaVerticesDeOtraComponenteTest() {

		        grafo.agregarVertice(provincia1);
		        grafo.agregarVertice(provincia2);
		        grafo.agregarVertice(provincia3);
		        grafo.agregarVertice(provincia4);

		        grafo.agregarArista(provincia1, provincia2, 10);
		        grafo.agregarArista(provincia3, provincia4, 20);

		        Set<String> visitados = DFS.recorrer(grafo, provincia1);

		        assertEquals(2, visitados.size());
		    }
		}
	}
		


		
	