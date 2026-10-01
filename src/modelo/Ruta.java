 package modelo;

public class Ruta implements Comparable <Ruta>{
	private Provincia extremo1;
	private Provincia extremo2;
	private int distancia;
	
	public Ruta(Provincia extremo1, Provincia extremo2, int distancia) {
		this.distancia = distancia;
		this.extremo1= extremo1;
		this.extremo2 = extremo2;
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
	
	public Provincia obtenerExtremoDestino(Provincia origen) {
		if (origen.equals(extremo1)) {
			return extremo2;
		} else if (origen.equals(extremo2)) {
			return extremo1;
		} else {
			throw new IllegalArgumentException("La provincia de origen no es un extremo de la ruta.");
		}
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
