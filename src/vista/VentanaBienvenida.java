package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VentanaBienvenida {

	// Instrucciones
	private static final String[] PASOS = { "1. Cargar una imagen del mapa en formato PNG o JPG.",
			"2. Escribir los nombres de las provincias o unidades.", "3. Ubicarlas haciendo click sobre el mapa.",
			"4. Crear conexiones e indicar su similaridad.", "5. Elegir la cantidad de regiones K.",
			"6. Generar y consultar las regiones resultantes." };

	private JFrame frame;
	private VentanaConfigGrafo ventanaPrincipal;

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
	// - Centro: los pasos a seguir.
	// - Abajo: navegación (modo oscuro y comenzar).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();
		crearTitulo(panelContenido);
		crearListaDePasos(panelContenido);

		JPanel panelNavegacion = crearPanelNavegacion(panelContenido);
		crearBotonModoOscuro(panelNavegacion);
		crearBotonComenzar(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Bienvenido - Diseño de regiones");
		frame.setSize(760, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(25, 30, 25, 30));
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

	private void crearListaDePasos(JPanel panelContenido) {
		JPanel panelPasos = new JPanel();
		panelPasos.setLayout(new GridLayout(PASOS.length, 1, 0, 8));

		for (String paso : PASOS) {
			panelPasos.add(new JLabel(paso));
		}

		panelContenido.add(panelPasos, BorderLayout.CENTER);
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

	// La ventana principal se crea una sola vez: asi, si se vuelve a esta pantalla,
	// no se pierde lo cargado
	private void abrirVentanaPrincipal() {
		if (ventanaPrincipal == null) {
			ventanaPrincipal = new VentanaConfigGrafo();
		}

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
