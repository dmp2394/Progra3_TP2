package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.Arista;
import modelo.Provincia;

public class VentanaResultado {

	// cant max de provincias visibles por región sin hacer scroll.
	private static final int FILAS_VISIBLES_POR_REGION = 5;

	private JFrame frame;
	private VentanaConfigRegiones ventanaAnterior;
	private List<List<String>> regiones;
	private BufferedImage imagen;
	private List<Provincia> provincias;
	private List<Arista<String>> conexiones;

	public VentanaResultado(VentanaConfigRegiones ventanaAnterior, List<List<String>> regiones, BufferedImage imagen,
			List<Provincia> provincias, List<Arista<String>> conexiones) {
		this.ventanaAnterior = ventanaAnterior;
		this.regiones = regiones;
		this.imagen = imagen;
		this.provincias = provincias;
		this.conexiones = conexiones;

		initialize();
	}

	// Estructura de la ventana:
	// - Arriba: título.
	// - Centro: a la izquierda el mapa con las regiones coloreadas y a la derecha
	// la lista de provincias de cada región.
	// - Abajo: navegación (volver, modo oscuro y salir).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		crearTitulo();
		crearPanelMapaYRegiones();

		JPanel panelNavegacion = crearPanelNavegacion();
		crearBotonVolver(panelNavegacion);
		crearBotonModoOscuro(panelNavegacion);
		crearBotonSalir(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Regiones generadas");

		frame.setSize(850, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout());
	}

	private void crearTitulo() {
		JLabel lblTitulo = new JLabel("Regiones generadas");

		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		frame.getContentPane().add(lblTitulo, BorderLayout.NORTH);
	}

	private void crearPanelMapaYRegiones() {
		JPanel panelMapaYRegiones = new JPanel(new BorderLayout(8, 0));

		panelMapaYRegiones.add(crearPanelMapa(), BorderLayout.CENTER);
		panelMapaYRegiones.add(crearListaDeRegiones(), BorderLayout.EAST);

		frame.getContentPane().add(panelMapaYRegiones, BorderLayout.CENTER);
	}

	private PanelGrafo crearPanelMapa() {
		PanelGrafo panelGrafo = new PanelGrafo();

		panelGrafo.setImagen(imagen);
		panelGrafo.setDatos(provincias, conexiones);
		panelGrafo.setRegiones(regiones);

		return panelGrafo;
	}

	private JScrollPane crearListaDeRegiones() {
		JPanel panelRegiones = new JPanel();
		panelRegiones.setLayout(new BoxLayout(panelRegiones, BoxLayout.Y_AXIS));

		for (int i = 0; i < regiones.size(); i++) {
			panelRegiones.add(crearPanelRegion(i, regiones.get(i)));
			panelRegiones.add(Box.createVerticalStrut(8));
		}

		JScrollPane scrollRegiones = new JScrollPane(panelRegiones);
		scrollRegiones.setPreferredSize(new Dimension(240, 0));

		return scrollRegiones;
	}

	private JPanel crearPanelRegion(int indice, List<String> provinciasDeLaRegion) {
		JList<String> listaProvincias = new JList<>(provinciasDeLaRegion.toArray(new String[0]));
		listaProvincias.setVisibleRowCount(Math.min(provinciasDeLaRegion.size(), FILAS_VISIBLES_POR_REGION));

		JPanel panelRegion = new JPanel(new BorderLayout());
		panelRegion.setBorder(BorderFactory.createTitledBorder("Región " + (indice + 1)));
		panelRegion.add(new JScrollPane(listaProvincias), BorderLayout.CENTER);
		panelRegion.setAlignmentX(Component.LEFT_ALIGNMENT);

		return panelRegion;
	}

	private JPanel crearPanelNavegacion() {
		JPanel panelNavegacion = new JPanel();

		frame.getContentPane().add(panelNavegacion, BorderLayout.SOUTH);
		return panelNavegacion;
	}

	private void crearBotonVolver(JPanel panelNavegacion) {
		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		panelNavegacion.add(btnVolver);
	}

	private void crearBotonModoOscuro(JPanel panelNavegacion) {
		panelNavegacion.add(Tema.crearBotonTema());
	}

	private void crearBotonSalir(JPanel panelNavegacion) {
		JButton btnSalir = new JButton("Salir");

		btnSalir.addActionListener(e -> System.exit(0));

		panelNavegacion.add(btnSalir);
	}

	public void mostrar() {
		Tema.aplicar(frame);

		// centra la ventana en el monitor cada vez que se muestra
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
