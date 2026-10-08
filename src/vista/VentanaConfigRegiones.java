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

import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.Provincia;
import presentador.PresentadorVentanaConfigRegiones;

public class VentanaConfigRegiones {

	private JFrame frame;
	private VentanaConfigGrafo ventanaAnterior;
	private PanelGrafo panelGrafo;
	private JSpinner spinnerK;
	private final PresentadorVentanaConfigRegiones presentador;

	public VentanaConfigRegiones(VentanaConfigGrafo ventanaAnterior, DisenioRegiones disenioRegiones) {

		this.ventanaAnterior = ventanaAnterior;
		this.presentador = new PresentadorVentanaConfigRegiones(this, disenioRegiones);

		initialize();

		presentador.mostrarGrafoCargado();
	}

	private void initialize() {

		frame = new JFrame();

		frame.setTitle("Configuración de regiones");

		frame.setBounds(100, 100, 850, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout());

		// =========================
		// TITULO
		// =========================

		JLabel lblTitulo = new JLabel("Configuración de regiones");

		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		frame.getContentPane().add(lblTitulo, BorderLayout.NORTH);

		// =========================
		// PANEL CENTRAL
		// =========================

		JPanel panelCentral = new JPanel();

		frame.getContentPane().add(panelCentral, BorderLayout.CENTER);

		panelCentral.setLayout(new GridLayout(1, 2, 10, 0));

		panelGrafo = new PanelGrafo();
		panelGrafo.setBorder(new TitledBorder("Mapa y conexiones"));

		panelGrafo.setImagen(ventanaAnterior.getImagen());

		JPanel panelMapa = new JPanel(new BorderLayout());
		panelMapa.add(panelGrafo, BorderLayout.CENTER);
		panelCentral.add(panelMapa);

		// =========================
		// PANEL IZQUIERDO
		// =========================

		JPanel panelIzquierdo = new JPanel();

		panelCentral.add(panelIzquierdo);

		panelIzquierdo.setLayout(new BorderLayout(0, 10));

		panelIzquierdo.setBorder(new TitledBorder("Configuración"));

		// =========================
		// CONFIGURACIÓN DE K
		// =========================

		JPanel panelConfig = new JPanel();

		panelIzquierdo.add(panelConfig, BorderLayout.NORTH);

		panelConfig.setLayout(new GridLayout(3, 1, 0, 8));

		// Etiqueta
		JLabel lblK = new JLabel("Cantidad de regiones K:");

		panelConfig.add(lblK);

		// Spinner
		spinnerK = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

		panelConfig.add(spinnerK);

		// Botón generar
		JButton btnGenerarRegiones = new JButton("Generar regiones");

		btnGenerarRegiones.addActionListener(e -> presentador.generarRegiones(leerK()));

		panelConfig.add(btnGenerarRegiones);

		// =========================
		// PANEL DE NAVEGACION
		// =========================

		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BorderLayout());

		frame.getContentPane().add(panelNavegacion, BorderLayout.SOUTH);

		// VOLVER
		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		panelNavegacion.add(btnVolver, BorderLayout.WEST);

		// MODO OSCURO

		JToggleButton btnTema = Tema.crearBotonTema();
		panelNavegacion.add(btnTema, BorderLayout.EAST);

	}

	// Lee k del spinner. Devuelve null si el texto ingresado no es un entero
	// válido.
	private Integer leerK() {
		try {
			spinnerK.commitEdit();
			return ((Number) spinnerK.getValue()).intValue();

		} catch (java.text.ParseException e) {
			return null;
		}
	}

	public void mostrarProvinciasYConexiones(ArrayList<Provincia> provincias, ArrayList<Arista<String>> conexiones) {
		panelGrafo.setDatos(provincias, conexiones);
	}

	public void mostrarError(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje, "No se pudieron generar las regiones", JOptionPane.ERROR_MESSAGE);
	}

	public void abrirResultado(List<List<String>> regiones, List<Provincia> provincias,
			List<Arista<String>> conexiones) {
		VentanaResultado resultado = new VentanaResultado(this, regiones, ventanaAnterior.getImagen(), provincias,
				conexiones);

		resultado.mostrar();
		frame.setVisible(false);
	}

	public void mostrar() {
		Tema.aplicar(frame);
		frame.setVisible(true);
	}
}