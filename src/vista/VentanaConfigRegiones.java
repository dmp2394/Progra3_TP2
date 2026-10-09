package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import controlador.ControladorVentanaConfigRegiones;
import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.Provincia;

public class VentanaConfigRegiones {
	private JFrame frame;
	private VentanaConfigGrafo ventanaAnterior;
	private PanelGrafo panelGrafo;
	private JSpinner spinnerK;
	private final ControladorVentanaConfigRegiones controlador;

	public VentanaConfigRegiones(VentanaConfigGrafo ventanaAnterior, DisenioRegiones disenioRegiones) {

		this.ventanaAnterior = ventanaAnterior;
		this.controlador = new ControladorVentanaConfigRegiones(disenioRegiones);

		initialize();

		mostrarProvinciasYConexiones(controlador.obtenerProvincias(), controlador.obtenerConexiones());
	}

	// Estructura de la ventana:
	// - Arriba: título.
	// - Centro: dos columnas, a la izquierda el mapa con el grafo y a la derecha
	// la configuración de la cantidad de regiones.
	// - Abajo: navegación (volver y modo oscuro).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		crearTitulo();

		JPanel panelMapaYConfiguracion = crearPanelMapaYConfiguracion();
		crearPanelMapa(panelMapaYConfiguracion);

		JPanel panelConfiguracionRegiones = crearPanelConfiguracionRegiones(panelMapaYConfiguracion);
		crearControlesCantidadDeRegiones(panelConfiguracionRegiones);

		JPanel panelNavegacion = crearPanelNavegacion();
		crearBotonVolver(panelNavegacion);
		crearBotonModoOscuro(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Configuración de regiones");

		frame.setSize(850, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout());
	}

	private void crearTitulo() {
		JLabel lblTitulo = new JLabel("Configuración de regiones");

		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		frame.getContentPane().add(lblTitulo, BorderLayout.NORTH);
	}

	private JPanel crearPanelMapaYConfiguracion() {
		JPanel panelMapaYConfiguracion = new JPanel();

		panelMapaYConfiguracion.setLayout(new GridLayout(1, 2, 10, 0));

		frame.getContentPane().add(panelMapaYConfiguracion, BorderLayout.CENTER);
		return panelMapaYConfiguracion;
	}

	private void crearPanelMapa(JPanel panelMapaYConfiguracion) {
		panelGrafo = new PanelGrafo();
		panelGrafo.setBorder(new TitledBorder("Mapa y conexiones"));

		panelGrafo.setImagen(ventanaAnterior.getImagen());

		JPanel panelMapa = new JPanel(new BorderLayout());
		panelMapa.add(panelGrafo, BorderLayout.CENTER);
		panelMapaYConfiguracion.add(panelMapa);
	}

	private JPanel crearPanelConfiguracionRegiones(JPanel panelMapaYConfiguracion) {
		JPanel panelConfiguracionRegiones = new JPanel();

		panelConfiguracionRegiones.setLayout(new BorderLayout(0, 10));

		panelConfiguracionRegiones.setBorder(new TitledBorder("Configuración"));

		panelMapaYConfiguracion.add(panelConfiguracionRegiones);
		return panelConfiguracionRegiones;
	}

	private void crearControlesCantidadDeRegiones(JPanel panelConfiguracionRegiones) {
		JPanel panelCantidadDeRegiones = new JPanel();

		panelCantidadDeRegiones.setLayout(new GridLayout(3, 1, 0, 8));

		panelConfiguracionRegiones.add(panelCantidadDeRegiones, BorderLayout.NORTH);

		JLabel lblK = new JLabel("Cantidad de regiones K:");

		panelCantidadDeRegiones.add(lblK);

		spinnerK = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

		panelCantidadDeRegiones.add(spinnerK);

		JButton btnGenerarRegiones = new JButton("Generar regiones");

		btnGenerarRegiones.addActionListener(e -> generarRegiones());

		panelCantidadDeRegiones.add(btnGenerarRegiones);
	}

	private JPanel crearPanelNavegacion() {
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BorderLayout());

		frame.getContentPane().add(panelNavegacion, BorderLayout.SOUTH);
		return panelNavegacion;
	}

	private void crearBotonVolver(JPanel panelNavegacion) {
		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		panelNavegacion.add(btnVolver, BorderLayout.WEST);
	}

	private void crearBotonModoOscuro(JPanel panelNavegacion) {
		JToggleButton btnTema = Tema.crearBotonTema();
		panelNavegacion.add(btnTema, BorderLayout.EAST);
	}

	private void generarRegiones() {
		Integer k = leerK();
		if (k == null) {
			mostrarError("Ingresá una cantidad de regiones válida.");
			return;
		}

		try {
			controlador.separarEnRegionesConexas(k);

			abrirResultado(controlador.obtenerRegiones(), controlador.obtenerProvincias(),
					controlador.obtenerConexiones());

		} catch (IllegalArgumentException | IllegalStateException e) {
			mostrarError(e.getMessage());
		}
	}

	private void abrirResultado(List<List<String>> regiones, List<Provincia> provincias,
			List<Arista<String>> conexiones) {
		VentanaResultado resultado = new VentanaResultado(this, regiones, ventanaAnterior.getImagen(), provincias,
				conexiones);

		resultado.mostrar();
		frame.setVisible(false);
	}

	public void mostrar() {
		Tema.aplicar(frame);

		// Centra la ventana en el monitor cada vez que se muestra.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "No se pudieron generar las regiones", JOptionPane.ERROR_MESSAGE);
	}

	private Integer leerK() {
		try {
			spinnerK.commitEdit();
			return ((Number) spinnerK.getValue()).intValue();

		} catch (java.text.ParseException e) {
			return null;
		}
	}

	private void mostrarProvinciasYConexiones(ArrayList<Provincia> provincias, ArrayList<Arista<String>> conexiones) {
		panelGrafo.setDatos(provincias, conexiones);
	}

}
