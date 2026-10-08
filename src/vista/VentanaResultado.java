package vista;

import java.awt.BorderLayout;
//import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
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
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import modelo.Arista;
import modelo.Provincia;

public class VentanaResultado {

	private JFrame frame;
	private VentanaConfigRegiones ventanaAnterior;
	private List<List<String>> regiones;
	private BufferedImage imagen;
	private List<Provincia> provincias;
	private List<Arista<String>> conexiones;

	public VentanaResultado(VentanaConfigRegiones ventanaAnterior, List<List<String>> regiones, BufferedImage imagen,
			List<Provincia> provincias, List<Arista<String>> conexiones) {
		this.ventanaAnterior = ventanaAnterior;
		this.regiones = regiones;
		this.imagen = imagen;
		this.provincias = provincias;
		this.conexiones = conexiones;

		initialize();
	}

	private void initialize() {

		frame = new JFrame();

		frame.setTitle("Regiones generadas");

		frame.setBounds(100, 100, 850, 550);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.getContentPane().setLayout(new BorderLayout());

		// TITULO

		JLabel lblTitulo = new JLabel("Regiones generadas");

		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

		frame.getContentPane().add(lblTitulo, BorderLayout.NORTH);

		// CENTRO

		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));

		for (int i = 0; i < regiones.size(); i++) {
			JList<String> listaProvincias = new JList<>(regiones.get(i).toArray(new String[0]));
			listaProvincias.setVisibleRowCount(Math.min(regiones.get(i).size(), 5));

			JPanel panelRegion = new JPanel(new BorderLayout());
			panelRegion.setBorder(BorderFactory.createTitledBorder("Región " + (i + 1)));
			panelRegion.add(new JScrollPane(listaProvincias), BorderLayout.CENTER);

			panelRegion.setAlignmentX(Component.LEFT_ALIGNMENT);
			panelCentral.add(panelRegion);
			panelCentral.add(Box.createVerticalStrut(8));
		}

		PanelGrafo panelGrafo = new PanelGrafo();
		panelGrafo.setImagen(imagen);
		panelGrafo.setDatos(provincias, conexiones);
		panelGrafo.setRegiones(regiones);

		JPanel panelVista = new JPanel(new BorderLayout(8, 0));
		panelVista.add(panelGrafo, BorderLayout.CENTER);

		JScrollPane scrollRegiones = new JScrollPane(panelCentral);
		scrollRegiones.setPreferredSize(new Dimension(240, 0));
		panelVista.add(scrollRegiones, BorderLayout.EAST);

		frame.getContentPane().add(panelVista, BorderLayout.CENTER);

		// NAVEGACION

		JPanel panelNavegacion = new JPanel();

		frame.getContentPane().add(panelNavegacion, BorderLayout.SOUTH);

		// VOLVER

		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {

			frame.setVisible(false);

			ventanaAnterior.mostrar();
		});

		panelNavegacion.add(btnVolver);

		// MODO OSCURO

		JToggleButton btnTema = Tema.crearBotonTema();
		panelNavegacion.add(btnTema, BorderLayout.EAST);

		// SALIR

		JButton btnSalir = new JButton("Salir");

		btnSalir.addActionListener(e -> {

			System.exit(0);
		});

		panelNavegacion.add(btnSalir);
	}

	public void mostrar() {
		Tema.aplicar(frame);
		frame.setVisible(true);
	}
}