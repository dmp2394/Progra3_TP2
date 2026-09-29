 package modelo;

public class Ruta implements Comparable <Ruta>{
	private Provincia origen;
	private Provincia destino;
	private int distancia;
	
	public Ruta(Provincia origen, Provincia destino, int distancia) {
		this.distancia = distancia;
		this.origen = origen;
		this.destino = destino;
	}
	
	public int devolverPeso() {
		return distancia;
	}
	public boolean cambiarDistancia(int nuevaDistancia) {
		if (nuevaDistancia > 0) {
			this.distancia = nuevaDistancia;
			return true;
		}
		return false;
	}
	
	@Override
	public int compareTo(Ruta otraRuta) {
		return Integer.compare(this.distancia, otraRuta.distancia);
	}
	
	
	//lo hizo eclipse no se si esta bien 
	 public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Ruta ruta = (Ruta) obj;
	        return distancia == ruta.distancia;
	    }

	
	
}
