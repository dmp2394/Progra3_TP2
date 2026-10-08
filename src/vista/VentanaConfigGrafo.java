package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.Provincia;
import presentador.PresentadorVentanaConfigGrafo;

public class VentanaConfigGrafo {

	private JFrame frame;
	private final PresentadorVentanaConfigGrafo presentador;

	private BufferedImage imagen;
	private PanelGrafo panelGrafo;

	private JTextField txtProvincia;
	private JLabel lblEstado;

	private DefaultListModel<String> modeloProvincias;
	private JList<String> listaProvincias;

	private JComboBox<String> comboProvincia1;
	private JComboBox<String> comboProvincia2;
	private JSpinner spinnerPeso;

	private DefaultTableModel modeloConexiones;
	private JTable tablaConexiones;

	public VentanaConfigGrafo() {
		presentador = new PresentadorVentanaConfigGrafo(this);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Carga del mapa y del grafo");
		frame.setBounds(100, 100, 1150, 700);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelContenido.setLayout(new BorderLayout(10, 10));
		frame.setContentPane(panelContenido);

		JPanel panelSuperior = new JPanel(new BorderLayout(10, 0));
		panelContenido.add(panelSuperior, BorderLayout.NORTH);

		JButton btnCargarMapa = new JButton("Cargar mapa PNG/JPG");
		panelSuperior.add(btnCargarMapa, BorderLayout.WEST);
		btnCargarMapa.addActionListener(e -> presentador.cambiarMapa());

		lblEstado = new JLabel("Cargá una imagen para comenzar.");
		panelSuperior.add(lblEstado, BorderLayout.CENTER);

		JPanel panelControles = new JPanel(new GridLayout(2, 1, 0, 10));
		panelControles.setPreferredSize(new Dimension(350, 500));
		panelContenido.add(panelControles, BorderLayout.WEST);

		JToggleButton btnTema = Tema.crearBotonTema();
		btnTema.setPreferredSize(btnCargarMapa.getPreferredSize());
		panelSuperior.add(btnTema, BorderLayout.EAST);

		// PROVINCIAS

		JPanel panelProvincias = new JPanel(new BorderLayout(0, 8));
		panelProvincias.setBorder(new TitledBorder("Provincias"));
		panelControles.add(panelProvincias);

		JPanel panelCargaProvincia = new JPanel(new GridLayout(3, 1, 0, 5));
		panelProvincias.add(panelCargaProvincia, BorderLayout.NORTH);

		panelCargaProvincia.add(new JLabel("Nombre de provincia:"));

		txtProvincia = new JTextField();
		panelCargaProvincia.add(txtProvincia);

		JPanel panelBotonesProvincia = new JPanel(new GridLayout(1, 2, 5, 0));
		panelCargaProvincia.add(panelBotonesProvincia);

		JButton btnCargarJson = new JButton("Cargar desde JSON");
		panelBotonesProvincia.add(btnCargarJson);
		btnCargarJson.addActionListener(e -> presentador.cargarProvinciasDesdeJSON());

		JButton btnUbicar = new JButton("Ubicar en mapa");
		panelBotonesProvincia.add(btnUbicar);
		btnUbicar.addActionListener(e -> presentador.prepararUbicacion(txtProvincia.getText()));

		modeloProvincias = new DefaultListModel<>();
		listaProvincias = new JList<>(modeloProvincias);

		panelProvincias.add(new JScrollPane(listaProvincias), BorderLayout.CENTER);

		JButton btnEliminarProvincia = new JButton("Eliminar provincia");
		panelProvincias.add(btnEliminarProvincia, BorderLayout.SOUTH);
		btnEliminarProvincia.addActionListener(e -> presentador.eliminarProvincia(listaProvincias.getSelectedValue()));

		// CONEXIONES

		JPanel panelConexiones = new JPanel(new BorderLayout(0, 8));
		panelConexiones.setBorder(new TitledBorder("Conexiones"));
		panelControles.add(panelConexiones);

		JPanel panelCargaConexion = new JPanel(new GridLayout(4, 2, 5, 5));
		panelConexiones.add(panelCargaConexion, BorderLayout.NORTH);

		panelCargaConexion.add(new JLabel("Provincia 1:"));
		comboProvincia1 = new JComboBox<>();
		panelCargaConexion.add(comboProvincia1);

		panelCargaConexion.add(new JLabel("Provincia 2:"));
		comboProvincia2 = new JComboBox<>();
		panelCargaConexion.add(comboProvincia2);

		panelCargaConexion.add(new JLabel("Peso:"));
		spinnerPeso = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panelCargaConexion.add(spinnerPeso);

		panelCargaConexion.add(new JLabel());

		JButton btnAgregarConexion = new JButton("Agregar conexión");
		panelCargaConexion.add(btnAgregarConexion);
		btnAgregarConexion
				.addActionListener(e -> presentador.agregarConexion((String) comboProvincia1.getSelectedItem(),
						(String) comboProvincia2.getSelectedItem(), leerPeso()));

		modeloConexiones = new DefaultTableModel(new String[] { "Provincia 1", "Provincia 2", "Peso" }, 0) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};

		tablaConexiones = new JTable(modeloConexiones);
		tablaConexiones.setFillsViewportHeight(true);

		panelConexiones.add(new JScrollPane(tablaConexiones), BorderLayout.CENTER);

		JButton btnEliminarConexion = new JButton("Eliminar conexión");
		panelConexiones.add(btnEliminarConexion, BorderLayout.SOUTH);
		btnEliminarConexion.addActionListener(
				e -> presentador.eliminarConexion(valorDeFilaSobreColumna(0), valorDeFilaSobreColumna(1)));

		// MAPA

		panelGrafo = new PanelGrafo();
		panelGrafo.setBorder(new TitledBorder("Mapa"));
		panelContenido.add(panelGrafo, BorderLayout.CENTER);

		panelGrafo.setAccionClick((x, y) -> presentador.ubicarProvincia(x, y));

		// NAVEGACIÓN

		JPanel panelNavegacion = new JPanel(new BorderLayout());
		panelContenido.add(panelNavegacion, BorderLayout.SOUTH);

		JButton btnContinuar = new JButton("Continuar →");
		panelNavegacion.add(btnContinuar, BorderLayout.EAST);
		btnContinuar.addActionListener(e -> presentador.continuar());
	}

	// Abre un selector de archivos y carga la imagen elegida.
	// El presentador decide si se puede cambiar el mapa antes de llamar a este
	// método.
	public void elegirImagen() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccionar imagen del mapa");
		selector.setAcceptAllFileFilterUsed(false);
		selector.setFileFilter(new FileNameExtensionFilter("Imágenes PNG y JPG", "png", "jpg", "jpeg"));

		if (selector.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File archivo = selector.getSelectedFile();

		try {
			BufferedImage imagenCargada = ImageIO.read(archivo);

			if (imagenCargada == null) {
				mostrarMensaje("El archivo no contiene una imagen válida.");
				return;
			}

			imagen = imagenCargada;
			panelGrafo.setImagen(imagen);

			presentador.mapaCargado();

		} catch (IOException e) {
			mostrarMensaje("No se pudo leer la imagen seleccionada.");
		}
	}

	// Lee el peso del spinner. Devuelve null si el texto ingresado no es un entero
	// válido.
	private Integer leerPeso() {
		try {
			spinnerPeso.commitEdit();
			return ((Number) spinnerPeso.getValue()).intValue();

		} catch (java.text.ParseException e) {
			return null;
		}
	}

	// Devuelve el valor de la columna indicada en la fila seleccionada de la tabla,
	// o null si no hay ninguna fila seleccionada.
	private String valorDeFilaSobreColumna(int columna) {
		int filaVista = tablaConexiones.getSelectedRow();

		if (filaVista == -1) {
			return null;
		}

		int filaModelo = tablaConexiones.convertRowIndexToModel(filaVista);
		return (String) modeloConexiones.getValueAt(filaModelo, columna);
	}

	// Actualiza todos los componentes con los datos recibidos.
	public void mostrarProvinciasYConexiones(ArrayList<Provincia> provincias, ArrayList<Arista<String>> conexiones) {
		String seleccion1 = (String) comboProvincia1.getSelectedItem();
		String seleccion2 = (String) comboProvincia2.getSelectedItem();

		modeloProvincias.clear();
		comboProvincia1.removeAllItems();
		comboProvincia2.removeAllItems();

		for (Provincia provincia : provincias) {
			String nombre = provincia.getNombre();

			modeloProvincias.addElement(nombre);
			comboProvincia1.addItem(nombre);
			comboProvincia2.addItem(nombre);
		}

		if (modeloProvincias.contains(seleccion1)) {
			comboProvincia1.setSelectedItem(seleccion1);
		}

		if (modeloProvincias.contains(seleccion2)) {
			comboProvincia2.setSelectedItem(seleccion2);
		}

		modeloConexiones.setRowCount(0);

		for (Arista<String> conexion : conexiones) {
			modeloConexiones.addRow(
					new Object[] { conexion.obtenerExtremo1(), conexion.obtenerExtremo2(), conexion.devolverPeso() });
		}

		panelGrafo.setDatos(provincias, conexiones);
	}

	public void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	public boolean tieneImagen() {
		return panelGrafo.tieneImagen();
	}

	public void mostrarEstado(String estado) {
		lblEstado.setText(estado);
	}

	public void limpiarNombre() {
		txtProvincia.setText("");
	}

	// Recibe el modelo solo para entregárselo a la próxima pantalla.
	public void abrirConfigRegiones(DisenioRegiones disenioRegiones) {
		VentanaConfigRegiones ventanaRegiones = new VentanaConfigRegiones(this, disenioRegiones);

		ventanaRegiones.mostrar();
		frame.setVisible(false);
	}

	// Permite mostrar la misma imagen en las próximas ventanas.
	public BufferedImage getImagen() {
		return imagen;
	}

	public void mostrar() {
		Tema.aplicar(frame);
		frame.setVisible(true);
	}

	public String seleccionarYDevolverRutaAJSONDeProvincias() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccionar archivo de provincias");
		selector.setAcceptAllFileFilterUsed(false);
		selector.setFileFilter(new FileNameExtensionFilter("Archivos JSON o de texto", "json", "txt"));

		if (selector.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		return selector.getSelectedFile().getAbsolutePath();
	}

}