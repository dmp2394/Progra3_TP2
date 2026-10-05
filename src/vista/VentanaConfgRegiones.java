package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

public class VentanaConfgRegiones {

	private JFrame frame;
	private ventanaConfgGrafo ventanaAnterior;
	private JMapViewer mapa;

	public VentanaConfgRegiones(ventanaConfgGrafo ventanaAnterior) {

		this.ventanaAnterior = ventanaAnterior;

		initialize();
	}

	private void initialize() {

		frame = new JFrame();

		frame.setTitle("Configuración de regiones");

		frame.setBounds(100, 100, 850, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(
				new BorderLayout());

		// =========================
		// TITULO
		// =========================

		JLabel lblTitulo = new JLabel("Configuración de regiones");

		lblTitulo.setHorizontalAlignment(
				SwingConstants.CENTER);

		frame.getContentPane().add(
				lblTitulo,
				BorderLayout.NORTH);

		// =========================
		// PANEL CENTRAL
		// =========================

		JPanel panelCentral = new JPanel();

		frame.getContentPane().add(
				panelCentral,
				BorderLayout.CENTER);

		panelCentral.setLayout(
				new GridLayout(1, 2, 10, 0));

		mapa = new JMapViewer();
		mapa.setDisplayPosition(new Coordinate(-34.52, -58.70), 5);

		JPanel panelMapa = new JPanel(new BorderLayout());
		panelMapa.add(mapa, BorderLayout.CENTER);
		panelCentral.add(panelMapa);

		// =========================
		// PANEL IZQUIERDO
		// =========================

		JPanel panelIzquierdo = new JPanel();

		panelCentral.add(panelIzquierdo);

		panelIzquierdo.setLayout(
				new BorderLayout(0, 10));

		panelIzquierdo.setBorder(
				new TitledBorder("Configuración"));

		// =========================
		// CONFIGURACIÓN DE K
		// =========================

		JPanel panelConfig = new JPanel();

		panelIzquierdo.add(
				panelConfig,
				BorderLayout.NORTH);

		panelConfig.setLayout(
				new GridLayout(3, 1, 0, 8));

		// Etiqueta
		JLabel lblK = new JLabel("Cantidad de regiones K:");

		panelConfig.add(lblK);

		// Spinner
		JSpinner spinnerK = new JSpinner(
				new SpinnerNumberModel(
						1,
						1,
						Integer.MAX_VALUE,
						1));

		panelConfig.add(spinnerK);

		// Botón generar
		JButton btnGenerarRegiones = new JButton("Generar regiones");

		btnGenerarRegiones.addActionListener(e -> {

			VentanaResultado ventanaResultado = new VentanaResultado(this);

			ventanaResultado.mostrar();

			frame.setVisible(false);
		});

		panelConfig.add(btnGenerarRegiones);

		// =========================
		// PANEL DE NAVEGACION
		// =========================

		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BorderLayout());

		frame.getContentPane().add(
				panelNavegacion,
				BorderLayout.SOUTH);

		// VOLVER
		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		panelNavegacion.add(
				btnVolver,
				BorderLayout.WEST);

	}

	public void mostrar() {

		frame.setVisible(true);
	}
}