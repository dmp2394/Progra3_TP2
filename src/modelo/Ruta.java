 package modelo;

public class Ruta {
	private int distancia;
	
	public Ruta(int distancia) {
		this.distancia = distancia;
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
	
	//lo hizo eclipse no se si esta bien 
	 public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        Ruta ruta = (Ruta) obj;
	        return distancia == ruta.distancia;
	    }
	
}
