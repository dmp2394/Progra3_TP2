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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.swing.JPanel;

import modelo.Arista;
import modelo.Provincia;

// Dibuja la imagen del mapa y, encima, las provincias, sus conexiones y (si ya
// se calcularon) las regiones. Las coordenadas de las provincias están en
// píxeles de la imagen original; el panel las convierte a su propio tamaño.
public class PanelGrafo extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int RADIO_NODO = 6;

	private static final Color[] COLORES_REGION = { new Color(46, 139, 87), // verde
			new Color(45, 105, 190), // azul
			new Color(205, 55, 55), // rojo
			new Color(220, 145, 35), // naranja
			new Color(135, 80, 170) // violeta
	};

	private BufferedImage imagen;

	private List<Provincia> provincias;
	private List<Arista<String>> conexiones;
	private List<List<String>> regiones;

	private final Map<String, Integer> regionPorProvincia = new HashMap<>();

	private boolean filtrarAristasPorRegion;

	private BiConsumer<Integer, Integer> accionClick;

	public PanelGrafo() {
		provincias = new ArrayList<>();
		conexiones = new ArrayList<>();
		regiones = new ArrayList<>();
		filtrarAristasPorRegion = false;

		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(650, 450));

		escucharClicks();
	}

	private void escucharClicks() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					procesarClick(e.getX(), e.getY());
				}
			}
		});
	}

	private void procesarClick(int xPanel, int yPanel) {
		if (imagen == null || accionClick == null) {
			return;
		}

		Rectangle area = getAreaImagen();

		if (!area.contains(xPanel, yPanel)) {
			return;
		}

		int xImagen = (int) ((xPanel - area.x) * (double) imagen.getWidth() / area.width);
		int yImagen = (int) ((yPanel - area.y) * (double) imagen.getHeight() / area.height);

		accionClick.accept(xImagen, yImagen);
	}

	private Rectangle getAreaImagen() {
		Insets bordes = getInsets();

		int anchoDisponible = getWidth() - bordes.left - bordes.right;
		int altoDisponible = getHeight() - bordes.top - bordes.bottom;

		if (imagen == null || anchoDisponible <= 0 || altoDisponible <= 0) {
			return new Rectangle();
		}

		double escala = Math.min((double) anchoDisponible / imagen.getWidth(),
				(double) altoDisponible / imagen.getHeight());

		int ancho = Math.max(1, (int) (imagen.getWidth() * escala));
		int alto = Math.max(1, (int) (imagen.getHeight() * escala));

		int x = bordes.left + (anchoDisponible - ancho) / 2;
		int y = bordes.top + (altoDisponible - alto) / 2;

		return new Rectangle(x, y, ancho, alto);
	}

	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
		repaint();
	}

	public void setDatos(List<Provincia> provincias, List<Arista<String>> conexiones) {
		this.provincias = new ArrayList<>(provincias);
		this.conexiones = new ArrayList<>(conexiones);

		repaint();
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

	public boolean tieneImagen() {
		return imagen != null;
	}

	public void setAccionClick(BiConsumer<Integer, Integer> accionClick) {
		this.accionClick = accionClick;
	}

	// Swing llama a este método cada vez que necesita redibujar el panel.
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Se dibuja sobre una copia para que el color y el grosor de línea que
		// cambiamos no afecten al borde del panel.
		Graphics2D dibujo = (Graphics2D) g.create();
		dibujo.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (imagen == null) {
			dibujarMensajeSinImagen(dibujo);
		} else {
			dibujarMapa(dibujo, getAreaImagen());
		}

		dibujo.dispose();
	}

	private void dibujarMensajeSinImagen(Graphics2D dibujo) {
		Insets bordes = getInsets();

		dibujo.setColor(Color.GRAY);
		dibujo.drawString("Cargá una imagen PNG o JPG para comenzar.", bordes.left + 20, bordes.top + 30);
	}

	private void dibujarMapa(Graphics2D dibujo, Rectangle area) {
		dibujo.drawImage(imagen, area.x, area.y, area.width, area.height, this);

		Map<String, Provincia> provinciasPorNombre = indexarProvinciasPorNombre();

		dibujarConexiones(dibujo, area, provinciasPorNombre);
		dibujarProvincias(dibujo, area);
		dibujarEtiquetasRegiones(dibujo, area, provinciasPorNombre);
	}

	private Map<String, Provincia> indexarProvinciasPorNombre() {
		Map<String, Provincia> provinciasPorNombre = new HashMap<>();

		for (Provincia provincia : provincias) {
			provinciasPorNombre.put(provincia.obtenerNombre(), provincia);
		}

		return provinciasPorNombre;
	}

	private void dibujarConexiones(Graphics2D dibujo, Rectangle area, Map<String, Provincia> provinciasPorNombre) {
		dibujo.setStroke(new BasicStroke(2));

		for (Arista<String> conexion : conexiones) {
			Provincia origen = provinciasPorNombre.get(conexion.obtenerExtremo1());
			Provincia destino = provinciasPorNombre.get(conexion.obtenerExtremo2());

			if (origen != null && destino != null && debeDibujarse(conexion)) {
				dibujarConexion(dibujo, area, conexion, origen, destino);
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

	private void dibujarConexion(Graphics2D dibujo, Rectangle area, Arista<String> conexion, Provincia origen,
			Provincia destino) {

		int x1 = convertirX(origen.obtenerX(), area);
		int y1 = convertirY(origen.obtenerY(), area);
		int x2 = convertirX(destino.obtenerX(), area);
		int y2 = convertirY(destino.obtenerY(), area);

		dibujo.setColor(colorDeConexion(conexion));
		dibujo.drawLine(x1, y1, x2, y2);

		dibujarSimilaridad(dibujo, conexion.devolverPeso(), (x1 + x2) / 2, (y1 + y2) / 2);
	}

	private int convertirX(int xImagen, Rectangle area) {
		return area.x + (int) Math.round(xImagen * (double) area.width / imagen.getWidth());
	}

	private int convertirY(int yImagen, Rectangle area) {
		return area.y + (int) Math.round(yImagen * (double) area.height / imagen.getHeight());
	}

	private Color colorDeConexion(Arista<String> conexion) {
		if (!filtrarAristasPorRegion) {
			return Color.DARK_GRAY;
		}

		return colorDeRegion(regionPorProvincia.get(conexion.obtenerExtremo1()));
	}

	private Color colorDeRegion(int indice) {
		return COLORES_REGION[indice % COLORES_REGION.length];
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
			dibujarProvincia(dibujo, area, provincia);
		}
	}

	private void dibujarProvincia(Graphics2D dibujo, Rectangle area, Provincia provincia) {
		int x = convertirX(provincia.obtenerX(), area);
		int y = convertirY(provincia.obtenerY(), area);

		Color colorRegion = colorDeProvincia(provincia.obtenerNombre());

		dibujarNodo(dibujo, x, y, colorRegion);
		dibujarNombreProvincia(dibujo, area, provincia.obtenerNombre(), x, y, colorRegion);
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

	private void dibujarEtiquetasRegiones(Graphics2D dibujo, Rectangle area,
			Map<String, Provincia> provinciasPorNombre) {

		for (int i = 0; i < regiones.size(); i++) {
			Point centro = calcularCentroRegion(regiones.get(i), provinciasPorNombre);

			if (centro != null) {
				dibujarEtiquetaRegion(dibujo, "Región " + (i + 1), convertirX(centro.x, area),
						convertirY(centro.y, area), colorDeRegion(i));
			}
		}
	}

	private Point calcularCentroRegion(List<String> region, Map<String, Provincia> provinciasPorNombre) {
		int sumaX = 0;
		int sumaY = 0;
		int cantidad = 0;

		for (String nombre : region) {
			Provincia provincia = provinciasPorNombre.get(nombre);

			if (provincia != null) {
				sumaX += provincia.obtenerX();
				sumaY += provincia.obtenerY();
				cantidad++;
			}
		}

		if (cantidad == 0) {
			return null;
		}

		return new Point(sumaX / cantidad, sumaY / cantidad);
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
}
