package modelo;

public class Ruta {
	private int peso;
	
	public Ruta(int peso) {
		this.peso = peso;
	}
	
	public int devolverPeso() {
		return peso;
	}
	public boolean cambiarPeso(int nuevoPeso) {
		if (nuevoPeso > 0) {
			peso = nuevoPeso;
			return true;
		}
		return false;
	}
	
}
