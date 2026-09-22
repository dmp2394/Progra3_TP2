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
	
}
