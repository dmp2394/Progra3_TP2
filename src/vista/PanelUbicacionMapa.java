package vista;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/** Capa de la vista que destaca el área disponible al ubicar una provincia. */
public final class PanelUbicacionMapa extends JComponent {

	private static final long serialVersionUID = 1L;
	private final Supplier<Rectangle> obtenerAreaMapa;
	private final Consumer<Point> accionClick;
	private final Runnable accionCancelar;
	private final JLabel instruccion = new JLabel();
	private Component focoAnterior;

	public PanelUbicacionMapa(Supplier<Rectangle> obtenerAreaMapa,
			Consumer<Point> accionClick, Runnable accionCancelar) {
		this.obtenerAreaMapa = obtenerAreaMapa;
		this.accionClick = accionClick;
		this.accionCancelar = accionCancelar;
		setOpaque(false);
		setLayout(new BorderLayout());
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		crearIndicacion();
		escucharMouse();
		escucharTeclado();
		setVisible(false);
	}

	private void crearIndicacion() {
		JPanel barra = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 5));
		barra.add(instruccion);
		JButton cancelar = new JButton("Cancelar (Esc)");
		cancelar.setFocusable(false);
		cancelar.addActionListener(e -> accionCancelar.run());
		barra.add(cancelar);
		add(barra, BorderLayout.NORTH);
	}

	private void escucharMouse() {
		MouseAdapter escucha = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evento) {
				if (SwingUtilities.isLeftMouseButton(evento)
						&& obtenerAreaMapa.get().contains(evento.getPoint())) {
					accionClick.accept(evento.getPoint());
					if (isVisible()) {
						requestFocusInWindow();
					}
				}
			}

			@Override
			public void mouseMoved(MouseEvent evento) {
				boolean dentro = obtenerAreaMapa.get().contains(evento.getPoint());
				setCursor(Cursor.getPredefinedCursor(dentro
						? Cursor.CROSSHAIR_CURSOR : Cursor.DEFAULT_CURSOR));
			}
		};
		addMouseListener(escucha);
		addMouseMotionListener(escucha);
	}

	private void escucharTeclado() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evento) {
				if (evento.getKeyCode() == KeyEvent.VK_ESCAPE) {
					accionCancelar.run();
				}
				evento.consume();
			}

			@Override
			public void keyTyped(KeyEvent evento) {
				evento.consume();
			}

			@Override
			public void keyReleased(KeyEvent evento) {
				evento.consume();
			}
		});
	}

	public void activar(String mensaje) {
		focoAnterior = java.awt.KeyboardFocusManager
				.getCurrentKeyboardFocusManager().getFocusOwner();
		instruccion.setText(mensaje);
		setVisible(true);
		requestFocusInWindow();
		repaint();
	}

	public void desactivar() {
		setVisible(false);
		setCursor(Cursor.getDefaultCursor());
		if (focoAnterior != null) {
			focoAnterior.requestFocusInWindow();
			focoAnterior = null;
		}
	}

	@Override
	protected void paintComponent(Graphics grafico) {
		super.paintComponent(grafico);
		Graphics2D dibujo = (Graphics2D) grafico.create();
		try {
			Rectangle mapa = obtenerAreaMapa.get();
			Area sombra = new Area(new Rectangle(0, 0, getWidth(), getHeight()));
			sombra.subtract(new Area(mapa));
			dibujo.setColor(new Color(35, 40, 45, 125));
			dibujo.fill(sombra);
			if (!mapa.isEmpty()) {
				dibujo.setColor(new Color(45, 115, 210));
				dibujo.setStroke(new BasicStroke(2));
				dibujo.drawRect(mapa.x, mapa.y, mapa.width - 1, mapa.height - 1);
			}
		} finally {
			dibujo.dispose();
		}
	}
}
