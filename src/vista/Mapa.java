package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import modelo.Arista;
import modelo.Provincia;

public class Mapa extends JMapViewer {

	private static final long serialVersionUID = 1L;

	private static final int RADIO_NODO = 6;

	// Centro del mundo
	private static final Coordinate CENTRO_INICIAL = new Coordinate(-38.4, -63.6);
	private static final int ZOOM_INICIAL = 4;

	private static final Color[] COLORES_REGION = { new Color(46, 139, 87), // verde
			new Color(45, 105, 190), // azul
			new Color(205, 55, 55), // rojo
			new Color(220, 145, 35), // naranja
			new Color(135, 80, 170) // violeta
	};

	private List<Provincia> provincias;
	private List<Arista<String>> conexiones;
	private List<List<String>> regiones;

	private final Map<String, Integer> regionPorProvincia = new HashMap<>();

	private boolean filtrarAristasPorRegion;
	private boolean posicionInicialAplicada;

	private BiConsumer<Double, Double> accionClick;

	public Mapa() {
		provincias = new ArrayList<>();
		conexiones = new ArrayList<>();
		regiones = new ArrayList<>();
		filtrarAristasPorRegion = false;
		posicionInicialAplicada = false;

		setZoomControlsVisible(true);
		setPreferredSize(new Dimension(650, 450));

		centrarAlMostrarse();
		escucharClicks();
	}

	private void centrarAlMostrarse() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (!posicionInicialAplicada && getWidth() > 0 && getHeight() > 0) {
					setDisplayPosition(CENTRO_INICIAL, ZOOM_INICIAL);
					posicionInicialAplicada = true;
				}
			}
		});
	}

	private void escucharClicks() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 1 Click
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					procesarClick(e.getX(), e.getY());
				}
			}
		});
	}

	public void procesarClickEnPanel(int xPanel, int yPanel) {
		procesarClick(xPanel, yPanel);
	}

	public void setDatos(List<Provincia> provincias, List<Arista<String>> conexiones) {
		this.provincias = new ArrayList<>(provincias);
		this.conexiones = new ArrayList<>(conexiones);

		repaint();
	}

	private void procesarClick(int xPanel, int yPanel) {
		if (accionClick == null) {
			return;
		}

		if (!obtenerAreaMapa().contains(xPanel, yPanel)) {
			return;
		}

		ICoordinate coordenada = getPosition(new Point(xPanel, yPanel));

		accionClick.accept(coordenada.getLat(), coordenada.getLon());
	}

	public void setRegiones(List<List<String>> regiones) {
		this.regiones = new ArrayList<>();
		regionPorProvincia.clear();

		if (regiones != null) {
			for (int i = 0; i < regiones.size(); i++) {
				agregarRegion(i, regiones.get(i));
			}
		}

		filtrarAristasPorRegion = regiones != null;

		repaint();
	}

	private void agregarRegion(int indice, List<String> region) {
		List<String> copia = new ArrayList<>(region);
		this.regiones.add(copia);

		for (String provincia : copia) {
			regionPorProvincia.put(provincia, indice);
		}
	}

	public void setAccionClick(BiConsumer<Double, Double> accionClick) {
		this.accionClick = accionClick;
	}

	// Swing llama a este método cada vez que necesita redibujar el mapa.
	@Override
	protected void paintComponent(Graphics g) {
		// JMapViewer dibuja las imágenes del mapa.
		super.paintComponent(g);

		Graphics2D dibujo = (Graphics2D) g.create();
		dibujo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		dibujarGrafo(dibujo, obtenerAreaMapa());

		dibujo.dispose();
	}

	private void dibujarGrafo(Graphics2D dibujo, Rectangle area) {
		Map<String, Provincia> provinciasPorNombre = indexarProvinciasPorNombre();

		dibujarConexiones(dibujo, provinciasPorNombre);
		dibujarProvincias(dibujo, area);
		dibujarEtiquetasRegiones(dibujo, provinciasPorNombre);
	}

	public Rectangle obtenerAreaMapa() {
		Insets bordes = getInsets();

		int ancho = Math.max(0, getWidth() - bordes.left - bordes.right);
		int alto = Math.max(0, getHeight() - bordes.top - bordes.bottom);

		return new Rectangle(bordes.left, bordes.top, ancho, alto);
	}

	private Map<String, Provincia> indexarProvinciasPorNombre() {
		Map<String, Provincia> provinciasPorNombre = new HashMap<>();

		for (Provincia provincia : provincias) {
			provinciasPorNombre.put(provincia.obtenerNombre(), provincia);
		}

		return provinciasPorNombre;
	}

	private void dibujarConexiones(Graphics2D dibujo, Map<String, Provincia> provinciasPorNombre) {
		dibujo.setStroke(new BasicStroke(2));

		for (Arista<String> conexion : conexiones) {
			Provincia origen = provinciasPorNombre.get(conexion.obtenerExtremo1());
			Provincia destino = provinciasPorNombre.get(conexion.obtenerExtremo2());

			if (origen != null && destino != null && origen.estaUbicada() && destino.estaUbicada()
					&& debeDibujarse(conexion)) {
				dibujarConexion(dibujo, conexion, origen, destino);
			}
		}
	}

	private boolean debeDibujarse(Arista<String> conexion) {
		if (!filtrarAristasPorRegion) {
			return true;
		}

		Integer regionOrigen = regionPorProvincia.get(conexion.obtenerExtremo1());
		Integer regionDestino = regionPorProvincia.get(conexion.obtenerExtremo2());

		return regionOrigen != null && regionOrigen.equals(regionDestino);
	}

	private void dibujarConexion(Graphics2D dibujo, Arista<String> conexion, Provincia origen, Provincia destino) {
		Point puntoOrigen = posicionEnPantalla(origen);
		Point puntoDestino = posicionEnPantalla(destino);

		dibujo.setColor(colorDeConexion(conexion));
		dibujo.drawLine(puntoOrigen.x, puntoOrigen.y, puntoDestino.x, puntoDestino.y);

		dibujarSimilaridad(dibujo, conexion.devolverPeso(), (puntoOrigen.x + puntoDestino.x) / 2,
				(puntoOrigen.y + puntoDestino.y) / 2);
	}

	private Color colorDeConexion(Arista<String> conexion) {
		if (!filtrarAristasPorRegion) {
			return Color.DARK_GRAY;
		}

		return colorDeRegion(regionPorProvincia.get(conexion.obtenerExtremo1()));
	}

	private void dibujarSimilaridad(Graphics2D dibujo, int similaridad, int centroX, int centroY) {
		String texto = String.valueOf(similaridad);

		FontMetrics metricas = dibujo.getFontMetrics();
		int anchoTexto = metricas.stringWidth(texto);
		int altoTexto = metricas.getHeight();
		int ascenso = metricas.getAscent();

		dibujo.setColor(Color.WHITE);
		dibujo.fillRect(centroX - anchoTexto / 2 - 3, centroY - ascenso, anchoTexto + 6, altoTexto);

		dibujo.setColor(Color.BLACK);
		dibujo.drawString(texto, centroX - anchoTexto / 2, centroY);
	}

	private void dibujarProvincias(Graphics2D dibujo, Rectangle area) {
		for (Provincia provincia : provincias) {
			if (provincia.estaUbicada()) {
				dibujarProvincia(dibujo, area, provincia);
			}
		}
	}

	private void dibujarProvincia(Graphics2D dibujo, Rectangle area, Provincia provincia) {
		Point punto = posicionEnPantalla(provincia);

		Color colorRegion = colorDeProvincia(provincia.obtenerNombre());

		dibujarNodo(dibujo, punto.x, punto.y, colorRegion);
		dibujarNombreProvincia(dibujo, area, provincia.obtenerNombre(), punto.x, punto.y, colorRegion);
	}

	private Color colorDeProvincia(String nombre) {
		Integer indice = regionPorProvincia.get(nombre);

		if (indice == null) {
			return Color.GRAY;
		}

		return colorDeRegion(indice);
	}

	private void dibujarNodo(Graphics2D dibujo, int x, int y, Color color) {
		int diametro = RADIO_NODO * 2;

		dibujo.setColor(color);
		dibujo.fillOval(x - RADIO_NODO, y - RADIO_NODO, diametro, diametro);

		dibujo.setColor(Color.BLACK);
		dibujo.drawOval(x - RADIO_NODO, y - RADIO_NODO, diametro, diametro);
	}

	private void dibujarNombreProvincia(Graphics2D dibujo, Rectangle area, String nombre, int x, int y, Color color) {

		FontMetrics metricas = dibujo.getFontMetrics();
		int anchoTexto = metricas.stringWidth(nombre);
		int altoTexto = metricas.getHeight();
		int ascenso = metricas.getAscent();

		int xTexto = Math.max(area.x + 2, Math.min(x + 10, area.x + area.width - anchoTexto - 2));
		int yTexto = Math.max(area.y + ascenso + 2, Math.min(y, area.y + area.height - 4));

		dibujo.setColor(Color.WHITE);
		dibujo.fillRect(xTexto - 2, yTexto - ascenso, anchoTexto + 4, altoTexto);

		dibujo.setColor(color);
		dibujo.drawString(nombre, xTexto, yTexto);
	}

	private void dibujarEtiquetasRegiones(Graphics2D dibujo, Map<String, Provincia> provinciasPorNombre) {

		for (int i = 0; i < regiones.size(); i++) {
			Point centro = calcularCentroRegion(regiones.get(i), provinciasPorNombre);

			if (centro != null) {
				dibujarEtiquetaRegion(dibujo, "Región " + (i + 1), centro.x, centro.y, colorDeRegion(i));
			}
		}
	}

	private Point calcularCentroRegion(List<String> region, Map<String, Provincia> provinciasPorNombre) {
		int sumaX = 0;
		int sumaY = 0;
		int cantidad = 0;

		for (String nombre : region) {
			Provincia provincia = provinciasPorNombre.get(nombre);

			if (provincia != null && provincia.estaUbicada()) {
				Point punto = posicionEnPantalla(provincia);
				sumaX += punto.x;
				sumaY += punto.y;
				cantidad++;
			}
		}

		if (cantidad == 0) {
			return null;
		}

		return new Point(sumaX / cantidad, sumaY / cantidad);
	}

	private Color colorDeRegion(int indice) {
		return COLORES_REGION[indice % COLORES_REGION.length];
	}

	private void dibujarEtiquetaRegion(Graphics2D dibujo, String texto, int centroX, int centroY, Color color) {
		FontMetrics metricas = dibujo.getFontMetrics();
		int margen = 6;
		int ancho = metricas.stringWidth(texto) + margen * 2;
		int alto = metricas.getHeight() + margen * 2;

		int x = centroX - ancho / 2;
		int y = centroY - alto / 2;

		dibujo.setColor(new Color(255, 255, 255, 220));
		dibujo.fillRoundRect(x, y, ancho, alto, 10, 10);

		dibujo.setColor(color);
		dibujo.setStroke(new BasicStroke(2));
		dibujo.drawRoundRect(x, y, ancho, alto, 10, 10);

		dibujo.drawString(texto, x + margen, y + margen + metricas.getAscent());
	}

	// Posición en pantalla de la provincia según el zoom y la posición actuales.
	private Point posicionEnPantalla(Provincia provincia) {
		return getMapPosition(provincia.obtenerLatitud(), provincia.obtenerLongitud(), false);
	}
}
