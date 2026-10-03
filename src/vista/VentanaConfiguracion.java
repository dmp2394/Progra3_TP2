package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class VentanaConfiguracion {

	private JFrame frame;
	private VentanaPrincipal ventanaAnterior;

	public VentanaConfiguracion(VentanaPrincipal ventanaAnterior) {

		this.ventanaAnterior = ventanaAnterior;

		initialize();
	}

	private void initialize() {

		frame = new JFrame();

		frame.setTitle("Configuración de regiones");

		frame.setBounds(100, 100, 850, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(
				new BorderLayout()
		);

		// =========================
		// TITULO
		// =========================

		JLabel lblTitulo =
				new JLabel("Configuración de regiones");

		lblTitulo.setHorizontalAlignment(
				SwingConstants.CENTER
		);

		frame.getContentPane().add(
				lblTitulo,
				BorderLayout.NORTH
		);

		// =========================
		// PANEL CENTRAL
		// =========================

		JPanel panelCentral = new JPanel();

		frame.getContentPane().add(
				panelCentral,
				BorderLayout.CENTER
		);

		panelCentral.setLayout(
				new GridLayout(1, 2, 10, 0)
		);

		// =========================
		// PANEL IZQUIERDO
		// =========================

		JPanel panelIzquierdo = new JPanel();

		panelCentral.add(panelIzquierdo);

		panelIzquierdo.setLayout(
				new BorderLayout(0, 10)
		);

		panelIzquierdo.setBorder(
				new TitledBorder("Configuración")
		);

		// =========================
		// PANEL DE NAVEGACION
		// =========================

		JPanel panelNavegacion = new JPanel();

		frame.getContentPane().add(
				panelNavegacion,
				BorderLayout.SOUTH
		);

		JButton btnVolver =
				new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		panelNavegacion.add(btnVolver);
	}

	public void mostrar() {

		frame.setVisible(true);
	}
}