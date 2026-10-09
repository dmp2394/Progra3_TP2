package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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

public class PanelGrafo extends JPanel {

    private static final long serialVersionUID = 1L;

    private BufferedImage imagen;

    private List<Provincia> provincias;
    private List<Arista<String>> conexiones;
    private List<List<String>> regiones;
    private boolean filtrarAristasPorRegion;

    private BiConsumer<Integer, Integer> accionClick;

    public PanelGrafo() {
        provincias = new ArrayList<>();
        conexiones = new ArrayList<>();
        regiones = new ArrayList<>();
        filtrarAristasPorRegion = false;

        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(650, 450));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                procesarClick(e.getX(), e.getY());
            }
        });
    }

    // Recibe la imagen que se utilizará como fondo.
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
        repaint();
    }

    // Recibe copias de los datos que debe dibujar.
    public void setDatos(
            List<Provincia> provincias,
            List<Arista<String>> conexiones) {

        this.provincias = new ArrayList<>(provincias);
        this.conexiones = new ArrayList<>(conexiones);

        repaint();
    }

    // Recibe copias de las regiones que debe dibujar.

    public void setRegiones(List<List<String>> regiones) {
        this.regiones = new ArrayList<>();
        regionPorProvincia.clear();

        if (regiones != null) {
            for (int i = 0; i < regiones.size(); i++) {
                List<String> region = new ArrayList<>(regiones.get(i));
                this.regiones.add(region);

                for (String provincia : region) {
                    regionPorProvincia.put(provincia, i);
                }
            }
        }

        filtrarAristasPorRegion = regiones != null;

        repaint();
    }

    private final Map<String, Integer> regionPorProvincia = new HashMap<>();

    // Recibe la acción que debe ejecutarse ante un click válido.
    public void setAccionClick(
            BiConsumer<Integer, Integer> accionClick) {

        this.accionClick = accionClick;
    }

    // Informa si el panel tiene una imagen cargada.
    public boolean tieneImagen() {
        return imagen != null;
    }

    // Convierte el click a coordenadas de la imagen original.
    private void procesarClick(int xPanel, int yPanel) {
        if (imagen == null || accionClick == null) {
            return;
        }

        Rectangle area = getAreaImagen();

        if (!area.contains(xPanel, yPanel)) {
            return;
        }

        int xImagen = (int) ((xPanel - area.x)
                * (double) imagen.getWidth() / area.width);

        int yImagen = (int) ((yPanel - area.y)
                * (double) imagen.getHeight() / area.height);

        accionClick.accept(xImagen, yImagen);
    }

    // Calcula dónde mostrar la imagen, conservando su proporción y sin pisar
    // el borde del panel (por ejemplo, el título "Mapa y conexiones").
    private Rectangle getAreaImagen() {
        Insets bordes = getInsets();

        int anchoDisponible = getWidth() - bordes.left - bordes.right;
        int altoDisponible = getHeight() - bordes.top - bordes.bottom;

        if (imagen == null || anchoDisponible <= 0 || altoDisponible <= 0) {
            return new Rectangle();
        }

        double escalaX = (double) anchoDisponible / imagen.getWidth();

        double escalaY = (double) altoDisponible / imagen.getHeight();

        double escala = Math.min(escalaX, escalaY);

        int ancho = Math.max(
                1, (int) (imagen.getWidth() * escala));

        int alto = Math.max(
                1, (int) (imagen.getHeight() * escala));

        int x = bordes.left + (anchoDisponible - ancho) / 2;
        int y = bordes.top + (altoDisponible - alto) / 2;

        return new Rectangle(x, y, ancho, alto);
    }

    // Swing llama a este método cuando necesita dibujar el panel.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D dibujo = (Graphics2D) g.create();

        try {
            dibujo.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            if (imagen == null) {
                dibujo.setColor(Color.GRAY);
                Insets bordes = getInsets();

                dibujo.drawString(
                        "Cargá una imagen PNG o JPG para comenzar.",
                        bordes.left + 20, bordes.top + 30);
                return;
            }

            Rectangle area = getAreaImagen();

            if (area.isEmpty()) {
                return;
            }

            dibujo.drawImage(
                    imagen,
                    area.x,
                    area.y,
                    area.width,
                    area.height,
                    this);

            dibujarConexiones(dibujo, area);
            dibujarProvincias(dibujo, area);
            dibujarEtiquetasRegiones(dibujo, area);

        } finally {
            dibujo.dispose();
        }
    }

    // Dibuja las líneas y su similaridad antes de dibujar los nodos.
    private void dibujarConexiones(
            Graphics2D dibujo,
            Rectangle area) {

        Map<String, Provincia> provinciasPorNombre = new HashMap<>();

        for (Provincia provincia : provincias) {
            provinciasPorNombre.put(
                    provincia.getNombre(), provincia);
        }

        dibujo.setStroke(new BasicStroke(2));

        for (Arista<String> conexion : conexiones) {
            Provincia origen = provinciasPorNombre.get(
                    conexion.obtenerExtremo1());

            Provincia destino = provinciasPorNombre.get(
                    conexion.obtenerExtremo2());

            if (origen == null || destino == null) {
                continue;
            }

            Integer regionOrigen = null;

            if (filtrarAristasPorRegion) {
                regionOrigen = regionPorProvincia.get(
                        conexion.obtenerExtremo1());

                Integer regionDestino = regionPorProvincia.get(
                        conexion.obtenerExtremo2());

                if (regionOrigen == null || !regionOrigen.equals(regionDestino)) {
                    continue;
                }
            }

            int x1 = convertirX(origen.getX(), area);
            int y1 = convertirY(origen.getY(), area);
            int x2 = convertirX(destino.getX(), area);
            int y2 = convertirY(destino.getY(), area);

            Color colorArista = filtrarAristasPorRegion
                    ? colorDeRegion(regionOrigen)
                    : Color.DARK_GRAY;

            dibujo.setColor(colorArista);
            dibujo.drawLine(x1, y1, x2, y2);

            dibujo.setColor(Color.DARK_GRAY);
            dibujo.drawLine(x1, y1, x2, y2);

            String similaridad = String.valueOf(conexion.devolverPeso());

            int centroX = (x1 + x2) / 2;
            int centroY = (y1 + y2) / 2;

            int anchoTexto = dibujo.getFontMetrics().stringWidth(similaridad);
            int altoTexto = dibujo.getFontMetrics().getHeight();
            int ascenso = dibujo.getFontMetrics().getAscent();

            dibujo.setColor(Color.WHITE);
            dibujo.fillRect(
                    centroX - anchoTexto / 2 - 3,
                    centroY - ascenso,
                    anchoTexto + 6,
                    altoTexto);

            dibujo.setColor(Color.BLACK);
            dibujo.drawString(
                    similaridad,
                    centroX - anchoTexto / 2,
                    centroY);
        }
    }

    // Dibuja un círculo y el nombre de cada provincia.
    private void dibujarProvincias(
            Graphics2D dibujo,
            Rectangle area) {

        for (Provincia provincia : provincias) {
            int x = convertirX(provincia.getX(), area);
            int y = convertirY(provincia.getY(), area);

            Color colorRegion = colorDeProvincia(provincia.getNombre());

            dibujo.setColor(colorRegion);
            dibujo.fillOval(x - 6, y - 6, 12, 12);

            dibujo.setColor(Color.BLACK);
            dibujo.drawOval(x - 6, y - 6, 12, 12);

            String nombre = provincia.getNombre();

            int anchoTexto = dibujo.getFontMetrics().stringWidth(nombre);

            int altoTexto = dibujo.getFontMetrics().getHeight();
            int ascenso = dibujo.getFontMetrics().getAscent();

            // Mantiene el nombre dentro de la imagen cuando es posible.
            int xTexto = Math.max(
                    area.x + 2,
                    Math.min(x + 10, area.x + area.width - anchoTexto - 2));

            int yTexto = Math.max(
                    area.y + ascenso + 2,
                    Math.min(y, area.y + area.height - 4));

            dibujo.setColor(Color.WHITE);
            dibujo.fillRect(
                    xTexto - 2,
                    yTexto - ascenso,
                    anchoTexto + 4,
                    altoTexto);

            dibujo.setColor(colorRegion);
            dibujo.drawString(nombre, xTexto, yTexto);
        }
    }

    // Dibuja un rectángulo con el nombre de cada región en el centro de sus
    // provincias.

    private void dibujarEtiquetasRegiones(
            Graphics2D dibujo,
            Rectangle area) {

        Map<String, Provincia> provinciasPorNombre = new HashMap<>();

        for (Provincia provincia : provincias) {
            provinciasPorNombre.put(provincia.getNombre(), provincia);
        }

        for (int i = 0; i < regiones.size(); i++) {
            int sumaX = 0;
            int sumaY = 0;
            int cantidad = 0;

            for (String nombre : regiones.get(i)) {
                Provincia provincia = provinciasPorNombre.get(nombre);

                if (provincia != null) {
                    sumaX += provincia.getX();
                    sumaY += provincia.getY();
                    cantidad++;
                }
            }

            if (cantidad == 0) {
                continue;
            }

            int centroX = convertirX(sumaX / cantidad, area);
            int centroY = convertirY(sumaY / cantidad, area);
            String texto = "Región " + (i + 1);

            FontMetrics metricas = dibujo.getFontMetrics();
            int ancho = metricas.stringWidth(texto);
            int alto = metricas.getHeight();
            int margen = 6;

            int x = centroX - (ancho + margen * 2) / 2;
            int y = centroY - (alto + margen * 2) / 2;

            dibujo.setColor(new Color(255, 255, 255, 220));
            dibujo.fillRoundRect(
                    x, y, ancho + margen * 2, alto + margen * 2, 10, 10);

            dibujo.setColor(new Color(30, 70, 130));
            dibujo.setStroke(new BasicStroke(2));
            dibujo.drawRoundRect(
                    x, y, ancho + margen * 2, alto + margen * 2, 10, 10);

            Color colorRegion = colorDeRegion(i);

            dibujo.setColor(new Color(255, 255, 255, 220));
            dibujo.fillRoundRect(
                    x, y, ancho + margen * 2, alto + margen * 2, 10, 10);

            dibujo.setColor(colorRegion);
            dibujo.setStroke(new BasicStroke(2));
            dibujo.drawRoundRect(
                    x, y, ancho + margen * 2, alto + margen * 2, 10, 10);

            dibujo.drawString(
                    texto,
                    x + margen,
                    y + margen + metricas.getAscent());
        }
    }

    private static final Color[] COLORES_REGION = {
            new Color(46, 139, 87), // verde
            new Color(45, 105, 190), // azul
            new Color(205, 55, 55), // rojo
            new Color(220, 145, 35), // naranja
            new Color(135, 80, 170) // violeta
    };

    // Devuelve un color para la provincia según la región a la que pertenece.

    private Color colorDeProvincia(String nombre) {
        Integer indice = regionPorProvincia.get(nombre);

        if (indice == null) {
            return Color.GRAY;
        }

        return colorDeRegion(indice);
    }

    // Devuelve un color para la región según su índice.

    private Color colorDeRegion(int indice) {
        return COLORES_REGION[indice % COLORES_REGION.length];
    }

    // Convierte una coordenada original a una posición del panel.
    private int convertirX(int xImagen, Rectangle area) {
        return area.x + (int) Math.round(
                xImagen * (double) area.width / imagen.getWidth());
    }

    private int convertirY(int yImagen, Rectangle area) {
        return area.y + (int) Math.round(
                yImagen * (double) area.height / imagen.getHeight());
    }
}