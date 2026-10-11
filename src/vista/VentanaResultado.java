package vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.border.EmptyBorder;

import modelo.Arista;
import modelo.Provincia;

public class VentanaResultado {

	// cant max de provincias visibles por región sin hacer scroll.
	private static final int FILAS_VISIBLES_POR_REGION = 5;

	private JFrame frame;
	private VentanaConfigRegiones ventanaAnterior;
	private List<List<String>> regiones;
	private List<Provincia> provincias;
	private List<Arista<String>> conexiones;

	public VentanaResultado(VentanaConfigRegiones ventanaAnterior, List<List<String>> regiones,
			List<Provincia> provincias, List<Arista<String>> conexiones) {
		this.ventanaAnterior = ventanaAnterior;
		this.regiones = regiones;
		this.provincias = provincias;
		this.conexiones = conexiones;

		initialize();
	}

	// Estructura de la ventana:
	// - Arriba: título.
	// - Centro: a la izquierda el mapa con las regiones coloreadas y a la derecha
	// la lista de provincias de cada región.
	// - Abajo: navegación (modo oscuro, volver y salir).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		crearTitulo();
		crearPanelMapaYRegiones();

		JPanel panelNavegacion = crearPanelNavegacion();
		crearBotonModoOscuro(panelNavegacion);
		crearBotonVolver(panelNavegacion);
		crearBotonSalir(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Regiones generadas");

		frame.setSize(1366, 768);
		frame.setResizable(false);

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

	private Mapa crearPanelMapa() {
		Mapa mapa = new Mapa();

		mapa.setDatos(provincias, conexiones);
		mapa.setRegiones(regiones);

		return mapa;
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
		JPanel panelNavegacion = new JPanel(new BorderLayout());
		panelNavegacion.setBorder(new EmptyBorder(10, 10, 10, 10));

		frame.getContentPane().add(panelNavegacion, BorderLayout.SOUTH);
		return panelNavegacion;
	}

	private void crearBotonVolver(JPanel panelNavegacion) {
		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panelVolver.add(btnVolver);
		panelNavegacion.add(panelVolver, BorderLayout.CENTER);
	}

	private void crearBotonModoOscuro(JPanel panelNavegacion) {
		panelNavegacion.add(Tema.crearBotonTema(), BorderLayout.WEST);
	}

	private void crearBotonSalir(JPanel panelNavegacion) {
		JButton btnSalir = new JButton("Salir");

		btnSalir.addActionListener(e -> System.exit(0));

		panelNavegacion.add(btnSalir, BorderLayout.EAST);
	}

	public void mostrar() {
		Tema.aplicar(frame);

		// centra la ventana en el monitor cada vez que se muestra
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
