package modelo;


public class Arista <V> implements Comparable <Arista<V>> {
	private V extremo1;
	private V extremo2;
	private int peso;
	
	public Arista(V extremo1, V extremo2, int distancia) {
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
	
	public V obtenerExtremo1() {
		return extremo1;		}
	
	
	public V obtenerExtremo2() {
		return extremo2;
	}
	
	
	@Override
	public int compareTo(Arista otraRuta) {
		return Integer.compare(this.peso, otraRuta.peso);
	}
	
	

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}
	
		if (objeto == null || getClass() != objeto.getClass()) {
			return false;
		}
	
	Arista<?> otra = (Arista<?>) objeto;
	
	boolean extremosEnOrden = extremo1.equals(otra.extremo1) && extremo2.equals(otra.extremo2);
	
	boolean extremosAlReves =extremo1.equals(otra.extremo2) && extremo2.equals(otra.extremo1);
	
	return extremosEnOrden || extremosAlReves;
	}

	
	
}
