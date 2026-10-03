package vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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

		JLabel lblTitulo =
				new JLabel("Configuración de regiones");

		lblTitulo.setHorizontalAlignment(
				SwingConstants.CENTER
		);

		frame.getContentPane().add(
				lblTitulo,
				BorderLayout.NORTH
		);

		// BOTÓN VOLVER

		JButton btnVolver =
				new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		frame.getContentPane().add(
				btnVolver,
				BorderLayout.SOUTH
		);
	}

	public void mostrar() {

		frame.setVisible(true);
	}
}