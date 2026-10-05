package vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

    private BiConsumer<Integer, Integer> accionClick;

    public PanelGrafo() {
        provincias = new ArrayList<>();
        conexiones = new ArrayList<>();

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

        int xImagen = (int) (
                (xPanel - area.x)
                * (double) imagen.getWidth() / area.width);

        int yImagen = (int) (
                (yPanel - area.y)
                * (double) imagen.getHeight() / area.height);

        accionClick.accept(xImagen, yImagen);
    }

    // Calcula dónde mostrar la imagen, conservando su proporción.
    private Rectangle getAreaImagen() {
        if (imagen == null || getWidth() <= 0 || getHeight() <= 0) {
            return new Rectangle();
        }

        double escalaX =
                (double) getWidth() / imagen.getWidth();

        double escalaY =
                (double) getHeight() / imagen.getHeight();

        double escala = Math.min(escalaX, escalaY);

        int ancho = Math.max(
                1, (int) (imagen.getWidth() * escala));

        int alto = Math.max(
                1, (int) (imagen.getHeight() * escala));

        int x = (getWidth() - ancho) / 2;
        int y = (getHeight() - alto) / 2;

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
                dibujo.drawString(
                        "Cargá una imagen PNG o JPG para comenzar.",
                        20, 30);
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

        } finally {
            dibujo.dispose();
        }
    }

    // Dibuja las líneas y sus pesos antes de dibujar los nodos.
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

            int x1 = convertirX(origen.getX(), area);
            int y1 = convertirY(origen.getY(), area);
            int x2 = convertirX(destino.getX(), area);
            int y2 = convertirY(destino.getY(), area);

            dibujo.setColor(Color.DARK_GRAY);
            dibujo.drawLine(x1, y1, x2, y2);

            String peso = String.valueOf(conexion.devolverPeso());

            int centroX = (x1 + x2) / 2;
            int centroY = (y1 + y2) / 2;

            int anchoTexto = dibujo.getFontMetrics().stringWidth(peso);
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
                    peso,
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

            dibujo.setColor(new Color(30, 100, 200));
            dibujo.fillOval(x - 6, y - 6, 12, 12);

            dibujo.setColor(Color.BLACK);
            dibujo.drawOval(x - 6, y - 6, 12, 12);

            String nombre = provincia.getNombre();

            int anchoTexto =
                    dibujo.getFontMetrics().stringWidth(nombre);

            int altoTexto = dibujo.getFontMetrics().getHeight();
            int ascenso = dibujo.getFontMetrics().getAscent();

            // Mantiene el nombre dentro del panel cuando es posible.
            int xTexto = Math.max(
                    2,
                    Math.min(x + 10, getWidth() - anchoTexto - 2));

            int yTexto = Math.max(
                    ascenso + 2,
                    Math.min(y, getHeight() - 4));

            dibujo.setColor(Color.WHITE);
            dibujo.fillRect(
                    xTexto - 2,
                    yTexto - ascenso,
                    anchoTexto + 4,
                    altoTexto);

            dibujo.setColor(Color.BLACK);
            dibujo.drawString(nombre, xTexto, yTexto);
        }
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