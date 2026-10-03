package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import presentador.Presentador;

public class VentanaPrincipal {

	// Presentador para MVP
	// Esta vista solo le pide cosas al presentador
	private Presentador presentador;

	private JFrame frame;

	// =========================
	// PROVINCIAS
	// =========================

	private JTextField txtProvincia;

	private JButton btnAgregarProvincia;
	private JButton btnEliminarProvincia;

	private JList<String> listaProvincias;
	private DefaultListModel<String> modeloListaProvincias;

	// =========================
	// CONEXIONES
	// =========================

	private JComboBox<String> comboProvincia1;
	private JComboBox<String> comboProvincia2;

	private JSpinner spinnerPeso;

	private JButton btnAgregarConexion;
	private JButton btnEliminarConexion;

	private JTable tablaConexiones;
	private DefaultTableModel modeloTablaConexiones;

	// =========================
	// NAVEGACION
	// =========================

	private JButton btnContinuar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {

			public void run() {

				try {

					VentanaPrincipal window = new VentanaPrincipal();

					window.frame.setVisible(true);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {

		initialize();

		presentador = new Presentador(this);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		// =====================================================
		// VENTANA
		// =====================================================

		frame = new JFrame();

		frame.setTitle("Diseño de regiones");

		frame.setBounds(100, 100, 1000, 650);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout(10, 10));

		// Margen general de la ventana

		JPanel contentPanel = new JPanel(new BorderLayout(10, 10));

		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		frame.getContentPane().add(contentPanel, BorderLayout.CENTER);

		// =====================================================
		// TITULO
		// =====================================================

		JLabel lblTitulo = new JLabel("Diseño de regiones");

		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		contentPanel.add(lblTitulo, BorderLayout.NORTH);

		// =====================================================
		// PANEL PRINCIPAL
		// =====================================================

		JPanel panelPrincipal = new JPanel();

		panelPrincipal.setLayout(new GridLayout(1, 2, 10, 0));

		contentPanel.add(panelPrincipal, BorderLayout.CENTER);

		// =====================================================
		// PANEL IZQUIERDO
		// PROVINCIAS
		// =====================================================

		JPanel panelProvincias = new JPanel();

		panelProvincias.setLayout(new BorderLayout(0, 10));

		panelProvincias.setBorder(new TitledBorder("Provincias"));

		panelPrincipal.add(panelProvincias);

		// -----------------------------------------------------
		// Parte superior
		// -----------------------------------------------------

		JPanel panelCargaProvincia = new JPanel();

		panelCargaProvincia.setLayout(new GridLayout(3, 1, 0, 5));

		panelProvincias.add(panelCargaProvincia, BorderLayout.NORTH);

		JLabel lblProvincia = new JLabel("Nombre de provincia:");

		panelCargaProvincia.add(lblProvincia);

		txtProvincia = new JTextField();

		txtProvincia.setColumns(10);

		panelCargaProvincia.add(txtProvincia);

		btnAgregarProvincia = new JButton("Agregar provincia");

		panelCargaProvincia.add(btnAgregarProvincia);

		// -----------------------------------------------------
		// Lista de provincias
		// -----------------------------------------------------

		modeloListaProvincias = new DefaultListModel<>();

		listaProvincias = new JList<>(modeloListaProvincias);

		JScrollPane scrollProvincias = new JScrollPane(listaProvincias);

		panelProvincias.add(scrollProvincias, BorderLayout.CENTER);

		// -----------------------------------------------------
		// Eliminar provincia
		// -----------------------------------------------------

		btnEliminarProvincia = new JButton("Eliminar provincia");

		panelProvincias.add(btnEliminarProvincia, BorderLayout.SOUTH);

		// =====================================================
		// PANEL DERECHO
		// CONEXIONES
		// =====================================================

		JPanel panelConexiones = new JPanel();

		panelConexiones.setLayout(new BorderLayout(0, 10));

		panelConexiones.setBorder(new TitledBorder("Conexiones"));

		panelPrincipal.add(panelConexiones);

		// -----------------------------------------------------
		// Parte superior
		// -----------------------------------------------------

		JPanel panelCargaConexion = new JPanel();

		panelCargaConexion.setLayout(new GridLayout(7, 1, 0, 5));

		panelConexiones.add(panelCargaConexion, BorderLayout.NORTH);

		// Provincia 1

		JLabel lblProvincia1 = new JLabel("Provincia 1:");

		panelCargaConexion.add(lblProvincia1);

		comboProvincia1 = new JComboBox<>();

		panelCargaConexion.add(comboProvincia1);

		// Provincia 2

		JLabel lblProvincia2 = new JLabel("Provincia 2:");

		panelCargaConexion.add(lblProvincia2);

		comboProvincia2 = new JComboBox<>();

		panelCargaConexion.add(comboProvincia2);

		// Peso

		JLabel lblPeso = new JLabel("Peso:");

		panelCargaConexion.add(lblPeso);

		spinnerPeso = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

		panelCargaConexion.add(spinnerPeso);

		// Agregar conexión

		btnAgregarConexion = new JButton("Agregar conexión");

		panelCargaConexion.add(btnAgregarConexion);

		// -----------------------------------------------------
		// Tabla de conexiones
		// -----------------------------------------------------

		String[] columnas = { "Provincia 1", "Provincia 2", "Peso" };

		modeloTablaConexiones = new DefaultTableModel(columnas, 0) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				return false;
			}
		};

		tablaConexiones = new JTable(modeloTablaConexiones);

		tablaConexiones.setFillsViewportHeight(true);

		JScrollPane scrollConexiones = new JScrollPane(tablaConexiones);

		panelConexiones.add(scrollConexiones, BorderLayout.CENTER);

		// -----------------------------------------------------
		// Eliminar conexión
		// -----------------------------------------------------

		btnEliminarConexion = new JButton("Eliminar conexión");

		panelConexiones.add(btnEliminarConexion, BorderLayout.SOUTH);

		// =====================================================
		// NAVEGACION
		// =====================================================

		JPanel panelNavegacion = new JPanel(new BorderLayout());

		contentPanel.add(panelNavegacion, BorderLayout.SOUTH);

		btnContinuar = new JButton("Continuar →");
		cargarMapaAlClickear(btnContinuar);

		panelNavegacion.add(btnContinuar, BorderLayout.EAST);
	}

	private void cargarMapaAlClickear(JButton btnContinuar) {
		btnContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				presentador.cargarMapa();
			}
		});
	}
}