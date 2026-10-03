package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal {

	private JFrame frame;
	private JTextField textField;

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

	public VentanaPrincipal() {
		initialize();
	}

	private void initialize() {

		frame = new JFrame();
		frame.setBounds(100, 100, 850, 550);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));


		JLabel lblNewLabel = new JLabel("Diseño de regiones");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);

	
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(1, 2, 5, 0));

		// =========================
		//
		// PANEL IZQUIERDO
		//
		// =========================

		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 5));

		// Parte superior:
		// nombre + textbox + agregar
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);

	
		//GridLayout
		panel_3.setLayout(new GridLayout(3, 1, 0, 5));

		JLabel lblNewLabel_1 = new JLabel("Nombre de provincia:");
		panel_3.add(lblNewLabel_1);

		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Agregar provincia");
		panel_3.add(btnNewButton);

		// Lista de provincias
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);

		DefaultListModel<String> modeloLista = new DefaultListModel<>();
		JList<String> listaProvincias = new JList<>(modeloLista);

		scrollPane.setViewportView(listaProvincias);

		// Botón inferior
		JButton btnEliminarProvincia =
				new JButton("Eliminar provincia");

		panel_1.add(
				btnEliminarProvincia,
				BorderLayout.SOUTH
		);

		// =========================
		//
		// PANEL DERECHO
		// 
		// =========================

		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 5));

		// Parte superior
		JPanel panelConexionesSuperior = new JPanel();
		panel_2.add(panelConexionesSuperior, BorderLayout.NORTH);
		panelConexionesSuperior.setLayout(new GridLayout(7, 1, 0, 5));

		JLabel lblProvincia1 = new JLabel("Provincia 1:");
		panelConexionesSuperior.add(lblProvincia1);

		JComboBox<String> comboProvincia1 = new JComboBox<>();
		panelConexionesSuperior.add(comboProvincia1);

		JLabel lblProvincia2 = new JLabel("Provincia 2:");
		panelConexionesSuperior.add(lblProvincia2);

		JComboBox<String> comboProvincia2 = new JComboBox<>();
		panelConexionesSuperior.add(comboProvincia2);

		JLabel lblPeso = new JLabel("Peso:");
		panelConexionesSuperior.add(lblPeso);

		JSpinner spinnerPeso = new JSpinner();
		panelConexionesSuperior.add(spinnerPeso);

		JButton btnAgregarConexion = new JButton("Agregar conexión");
		panelConexionesSuperior.add(btnAgregarConexion);

		// Tabla de conexiones
		String[] columnas = {
				"Provincia 1",
				"Provincia 2",
				"Peso"
		};

		DefaultTableModel modeloTabla =
				new DefaultTableModel(columnas, 0);

		JTable tablaConexiones = new JTable(modeloTabla);

		JScrollPane scrollTabla =
				new JScrollPane(tablaConexiones);

		panel_2.add(scrollTabla, BorderLayout.CENTER);

		// Botón inferior
		JButton btnEliminarConexion =
				new JButton("Eliminar conexión");

		panel_2.add(
				btnEliminarConexion,
				BorderLayout.SOUTH
		);
	}

}