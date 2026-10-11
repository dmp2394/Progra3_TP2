package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaBienvenida {

	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				VentanaBienvenida ventana = new VentanaBienvenida();

				ventana.mostrar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public VentanaBienvenida() {
		initialize();
	}

	// Estructura de la ventana:
	// - Arriba: título.
	// - Centro: instrucciones en dos columnas (carga manual y carga desde archivo).
	// - Abajo: navegación (modo oscuro y comenzar).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();
		crearTitulo(panelContenido);
		crearInstrucciones(panelContenido);

		JPanel panelNavegacion = crearPanelNavegacion(panelContenido);
		crearBotonModoOscuro(panelNavegacion);
		crearBotonComenzar(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Bienvenido - Diseño de regiones");
		frame.setSize(800, 400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(25, 10, 10, 10));
		panelContenido.setLayout(new BorderLayout(0, 20));

		frame.setContentPane(panelContenido);
		return panelContenido;
	}

	private void crearTitulo(JPanel panelContenido) {
		JLabel lblTitulo = new JLabel("Diseño de regiones");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));

		panelContenido.add(lblTitulo, BorderLayout.NORTH);
	}

	// Se usa html para insertar saltos de linea y cursiva en el hint del mapa
	private void crearInstrucciones(JPanel panelContenido) {
		JLabel lblPasos = new JLabel("<html>" + "1. Agregá las provincias por nombre.<br><br>"
				+ "2. Seleccioná una, tocá \"Ubicar en mapa\" y hacé doble clic en su posición.<br><br>"
				+ "3. Creá las conexiones entre provincias con su similaridad.<br><br>"
				+ "<i>Opcional, carga desde archivo:</i> en lugar de los pasos 1 a 3, importá un archivo JSON "
				+ "con las provincias (nombre y, opcionalmente, latitud y longitud), sus conexiones (origen, destino y "
				+ "similaridad) y, si querés, podés precargar la cantidad de regiones K.<br><br>"
				+ "4. Elegí la cantidad de regiones K y generá las regiones.</html>");
		lblPasos.setVerticalAlignment(SwingConstants.TOP);

		JLabel lblAyudaMapa = new JLabel(
				"<html><i>Mapa: rueda del mouse para zoom, clic derecho y arrastrar para moverte.</i></html>");

		JPanel panelInstrucciones = new JPanel(new BorderLayout());
		panelInstrucciones.add(lblPasos, BorderLayout.CENTER);
		panelInstrucciones.add(lblAyudaMapa, BorderLayout.SOUTH);

		panelContenido.add(panelInstrucciones, BorderLayout.CENTER);
	}

	private JPanel crearPanelNavegacion(JPanel panelContenido) {
		JPanel panelNavegacion = new JPanel();
		panelNavegacion.setLayout(new BorderLayout());

		panelContenido.add(panelNavegacion, BorderLayout.SOUTH);
		return panelNavegacion;
	}

	private void crearBotonModoOscuro(JPanel panelNavegacion) {
		panelNavegacion.add(Tema.crearBotonTema(), BorderLayout.WEST);
	}

	private void crearBotonComenzar(JPanel panelNavegacion) {
		JButton btnComenzar = new JButton("Comenzar →");

		btnComenzar.addActionListener(e -> abrirVentanaPrincipal());

		panelNavegacion.add(btnComenzar, BorderLayout.EAST);
	}

	// Cada vez que se presiona Comenzar se arranca de cero, con una ventana y un
	// modelo nuevos.
	private void abrirVentanaPrincipal() {
		VentanaConfigGrafo ventanaPrincipal = new VentanaConfigGrafo(this);

		ventanaPrincipal.mostrar();
		frame.setVisible(false);
	}

	public void mostrar() {
		Tema.aplicar(frame);

		// Centra la ventana en el monitor cada vez que se muestra
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
