package modelo;


public class Arista implements Comparable <Arista> {
	private Vertice extremo1;
	private Vertice extremo2;
	private int peso;
	
	public Arista(Vertice extremo1, Vertice extremo2, int distancia) {
		this.peso = distancia;
		this.extremo1= extremo1;
		this.extremo2 = extremo2;
	}
	
	

	public int devolverPeso() {
		return peso;
	}
	public boolean cambiarDistancia(int nuevoPeso) {
		if (nuevoPeso > 0) {
			this.peso = nuevoPeso;
			return true;
		}
		return false;
	}
	
	public Vertice obtenerExtremo1() {
		return extremo1;		}
	
	
	public Vertice obtenerExtremo2() {
		return extremo2;
	}
	
	
	@Override
	public int compareTo(Arista otraRuta) {
		return Integer.compare(this.peso, otraRuta.peso);
	}
	
	
	//lo hizo eclipse no se si esta bien 
	 public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Arista ruta = (Arista) obj;
	        return peso == ruta.peso;
	    }

	
	
}
