package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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

import controlador.ControladorVentanaConfigGrafo;
import modelo.Arista;
import modelo.DisenioRegiones;
import modelo.ObservadorDisenioRegiones;
import modelo.Provincia;

public class VentanaConfigGrafo implements ObservadorDisenioRegiones {

	private JFrame frame;
	private final ControladorVentanaConfigGrafo controlador;
	private final DisenioRegiones disenioRegiones;

	private BufferedImage imagen;
	private PanelGrafo panelGrafo;

	private JTextField txtProvincia;
	private JLabel lblEstado;

	private DefaultListModel<String> modeloProvincias;
	private JList<String> listaProvincias;
	private final Set<String> provinciasSinUbicar = new HashSet<>();

	private String provinciaPendiente;
	private JComboBox<String> comboProvincia1;
	private JComboBox<String> comboProvincia2;
	private JSpinner spinnerSimilaridad;

	private DefaultTableModel modeloConexiones;
	private JTable tablaConexiones;
	private boolean reubicandoProvincia;
	private PanelUbicacionMapa capaUbicacion;
	private Integer cantidadRegionesImportada;

	public VentanaConfigGrafo() {
		this.disenioRegiones = new DisenioRegiones();
		this.controlador = new ControladorVentanaConfigGrafo(disenioRegiones);

		initialize();

		disenioRegiones.registrar(this);

	}

	// Estructura de la ventana:
	// - Arriba: modo oscuro.
	// - Izquierda: importación y carga manual.
	// - Centro: carga de imagen, estado y mapa.
	// - Abajo: navegación (continuar).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();
		crearBarraSuperior(panelContenido);

		JPanel panelProvinciasYConexiones = crearPanelProvinciasYConexiones(panelContenido);
		crearSeccionProvincias(panelProvinciasYConexiones);
		crearSeccionConexiones(panelProvinciasYConexiones);

		crearPanelMapa(panelContenido);
		configurarCapaUbicacion();

		JPanel panelNavegacion = crearPanelNavegacion(panelContenido);
		crearBotonContinuar(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Carga del mapa y del grafo");
		frame.setSize(1280, 720);
		frame.setResizable(true);
		frame.setMinimumSize(new Dimension(1050, 650));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelContenido.setLayout(new BorderLayout(10, 10));
		frame.setContentPane(panelContenido);
		return panelContenido;
	}

	private void crearBarraSuperior(JPanel panelContenido) {
		JPanel panelSuperior = new JPanel(new BorderLayout(10, 0));
		panelContenido.add(panelSuperior, BorderLayout.NORTH);

		crearBotonModoOscuro(panelSuperior, new Dimension(140, 30));
	}

	private JButton crearBotonCargarMapa(JPanel panelSuperior) {
		JButton btnCargarMapa = new JButton("Cargar imagen del mapa…");
		panelSuperior.add(btnCargarMapa, BorderLayout.WEST);
		btnCargarMapa.addActionListener(e -> cargarMapa());
		return btnCargarMapa;
	}

	private void cargarMapa() {
		if (!controlador.puedeCargarMapa()) {
			mostrarMensaje("Eliminá las provincias antes de cargar el mapa.");
			return;
		}

		elegirImagen();
	}

	private void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	private void elegirImagen() {
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

			mapaCargado();

		} catch (IOException e) {
			mostrarMensaje("No se pudo leer la imagen seleccionada.");
		}
	}

	private void mapaCargado() {
		provinciaPendiente = null;
		mostrarEstado("Mapa cargado. Escribí una provincia y presioná Ubicar.");
	}

	private void mostrarEstado(String estado) {
		lblEstado.setText(estado);
	}

	private void crearEtiquetaEstado(JPanel panelSuperior) {
		lblEstado = new JLabel("Cargá una imagen para comenzar.");
		panelSuperior.add(lblEstado, BorderLayout.CENTER);
	}

	private void crearBotonModoOscuro(JPanel panelSuperior, Dimension tamanio) {
		JToggleButton btnTema = Tema.crearBotonTema();
		btnTema.setPreferredSize(tamanio);
		panelSuperior.add(btnTema, BorderLayout.EAST);
	}

	private JPanel crearPanelProvinciasYConexiones(JPanel panelContenido) {
		JPanel panelLateral = new JPanel(new BorderLayout(0, 10));
		panelLateral.setPreferredSize(new Dimension(390, 500));
		panelContenido.add(panelLateral, BorderLayout.WEST);
		crearSeccionCargaDatos(panelLateral);

		JPanel panelManual = new JPanel(new BorderLayout(0, 6));
		JLabel titulo = new JLabel("Carga manual");
		titulo.setFont(titulo.getFont().deriveFont(Font.BOLD));
		panelManual.add(titulo, BorderLayout.NORTH);
		panelLateral.add(panelManual, BorderLayout.CENTER);

		JPanel secciones = new JPanel(new GridBagLayout());
		panelManual.add(secciones, BorderLayout.CENTER);
		return secciones;
	}

	private void crearSeccionCargaDatos(JPanel panelLateral) {
		JPanel panelDatos = new JPanel(new BorderLayout(0, 6));
		panelDatos.setBorder(new TitledBorder("Carga de datos"));
		JButton boton = new JButton("Importar datos desde JSON…");
		boton.setFocusPainted(false);
		boton.addActionListener(e -> cargarProvinciasDesdeJSON());
		panelDatos.add(boton, BorderLayout.NORTH);
		panelDatos.add(new JLabel("<html>Cargá provincias y conexiones desde un archivo<br>"
				+ "o agregalas manualmente abajo.<br>Cargá primero la imagen del mapa.</html>"),
				BorderLayout.CENTER);
		panelLateral.add(panelDatos, BorderLayout.NORTH);
	}

	private void crearSeccionProvincias(JPanel panelProvinciasYConexiones) {
		JPanel panelProvincias = new JPanel(new BorderLayout(0, 8));
		panelProvincias.setBorder(new TitledBorder("Provincias"));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.weightx = 1;
		constraints.weighty = 0.40;
		constraints.fill = GridBagConstraints.BOTH;

		panelProvinciasYConexiones.add(panelProvincias, constraints);
		;

		crearFormularioProvincia(panelProvincias);
		crearListaProvincias(panelProvincias);
		crearBotonEliminarProvincia(panelProvincias);
	}

	private void crearFormularioProvincia(JPanel panelProvincias) {
		JPanel formulario = new JPanel(new BorderLayout(0, 5));
		formulario.add(new JLabel("Nombre de provincia:"), BorderLayout.NORTH);
		JPanel fila = new JPanel(new BorderLayout(6, 0));
		txtProvincia = new JTextField();
		fila.add(txtProvincia, BorderLayout.CENTER);
		JButton agregar = new JButton("Agregar provincia");
		agregar.addActionListener(e -> agregarProvincia());
		fila.add(agregar, BorderLayout.EAST);
		formulario.add(fila, BorderLayout.CENTER);
		panelProvincias.add(formulario, BorderLayout.NORTH);
	}

	private void agregarProvincia() {
		String nombre = txtProvincia.getText().trim();

		if (nombre.isEmpty()) {
			mostrarMensaje("Escribí el nombre de la provincia.");
			return;
		}

		if (controlador.existeProvincia(nombre)
				|| provinciasSinUbicar.contains(nombre)) {
			mostrarMensaje("La provincia ya existe.");
			return;
		}

		provinciasSinUbicar.add(nombre);
		modeloProvincias.addElement(nombre);
		txtProvincia.setText("");
	}

	private void cargarProvinciasDesdeJSON() {
		String ruta = seleccionarRutaJSON();

		if (ruta == null) {
			return;
		}

		try {
			cantidadRegionesImportada = controlador.cargarProvinciasDesdeJSON(ruta);

		} catch (IllegalArgumentException e) {
			mostrarMensaje(e.getMessage());
		}
	}

	private String seleccionarRutaJSON() {
		JFileChooser selector = new JFileChooser();
		selector.setDialogTitle("Seleccionar archivo de datos");
		selector.setAcceptAllFileFilterUsed(false);
		selector.setFileFilter(new FileNameExtensionFilter("Archivos JSON o de texto", "json", "txt"));

		if (selector.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		return selector.getSelectedFile().getAbsolutePath();
	}

	private void prepararUbicacion() {
		if (!tieneImagen()) {
			mostrarMensaje("Primero cargá una imagen del mapa.");
			return;
		}

		String seleccionada = listaProvincias.getSelectedValue();
		if (seleccionada == null) {
			mostrarMensaje("Seleccioná una provincia de la lista.");
			return;
		}

		provinciaPendiente = seleccionada;
		reubicandoProvincia = !provinciasSinUbicar.contains(seleccionada);

		String mensaje = reubicandoProvincia
				? "Hacé clic en el mapa para reubicar: "
				: "Hacé clic en el mapa para ubicar: ";

		iniciarUbicacion(mensaje + seleccionada);
	}

	private boolean tieneImagen() {
		return panelGrafo.tieneImagen();
	}

	private void crearListaProvincias(JPanel panelProvincias) {
		modeloProvincias = new DefaultListModel<>();
		listaProvincias = new JList<>(modeloProvincias);

		panelProvincias.add(new JScrollPane(listaProvincias), BorderLayout.CENTER);
	}

	private void crearBotonEliminarProvincia(JPanel panelProvincias) {
		JPanel acciones = new JPanel(new GridLayout(1, 2, 6, 0));
		JButton ubicar = new JButton("Ubicar en mapa");
		ubicar.addActionListener(e -> prepararUbicacion());
		acciones.add(ubicar);
		JButton btnEliminarProvincia = new JButton("Eliminar provincia");
		acciones.add(btnEliminarProvincia);
		panelProvincias.add(acciones, BorderLayout.SOUTH);
		btnEliminarProvincia.addActionListener(e -> eliminarProvincia());
	}

	private void eliminarProvincia() {
		String nombre = listaProvincias.getSelectedValue();
		if (nombre == null) {
			mostrarMensaje("Seleccioná una provincia de la lista.");
			return;
		}

		try {
			controlador.eliminarProvincia(nombre);

		} catch (IllegalArgumentException e) {
			mostrarMensaje(e.getMessage());
		}
	}

	private void crearSeccionConexiones(JPanel panelProvinciasYConexiones) {
		JPanel panelConexiones = new JPanel(new BorderLayout(0, 8));
		panelConexiones.setBorder(new TitledBorder("Conexiones"));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 1;
		constraints.weighty = 0.60;
		constraints.fill = GridBagConstraints.BOTH;

		panelProvinciasYConexiones.add(panelConexiones, constraints);

		crearFormularioConexion(panelConexiones);
		crearTablaConexiones(panelConexiones);
		crearBotonEliminarConexion(panelConexiones);
	}

	private void crearFormularioConexion(JPanel panelConexiones) {
		JPanel panelCargaConexion = new JPanel(new GridLayout(4, 2, 5, 5));
		panelConexiones.add(panelCargaConexion, BorderLayout.NORTH);

		panelCargaConexion.add(new JLabel("Provincia 1:"));
		comboProvincia1 = new JComboBox<>();
		panelCargaConexion.add(comboProvincia1);

		panelCargaConexion.add(new JLabel("Provincia 2:"));
		comboProvincia2 = new JComboBox<>();
		panelCargaConexion.add(comboProvincia2);

		panelCargaConexion.add(new JLabel("Similaridad:"));
		spinnerSimilaridad = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		panelCargaConexion.add(spinnerSimilaridad);

		panelCargaConexion.add(new JLabel());

		crearBotonAgregarConexion(panelCargaConexion);
	}

	private void crearBotonAgregarConexion(JPanel panelCargaConexion) {
		JButton btnAgregarConexion = new JButton("Agregar conexión");
		panelCargaConexion.add(btnAgregarConexion);
		btnAgregarConexion.addActionListener(e -> agregarConexion());
	}

	private void agregarConexion() {
		String provincia1 = (String) comboProvincia1.getSelectedItem();
		String provincia2 = (String) comboProvincia2.getSelectedItem();
		Integer similaridad = leerSimilaridad();

		if (provincia1 == null || provincia2 == null) {
			mostrarMensaje("Primero agregá las provincias que querés conectar.");
			return;
		}

		if (similaridad == null) {
			mostrarMensaje("Ingresá una similaridad entera válida.");
			return;
		}

		try {
			controlador.agregarConexion(provincia1, provincia2, similaridad);

		} catch (IllegalArgumentException e) {
			mostrarMensaje(e.getMessage());
		}
	}

	private Integer leerSimilaridad() {
		try {
			spinnerSimilaridad.commitEdit();
			return ((Number) spinnerSimilaridad.getValue()).intValue();

		} catch (java.text.ParseException e) {
			return null;
		}
	}

	private void crearTablaConexiones(JPanel panelConexiones) {
		modeloConexiones = crearModeloTablaConexiones();

		tablaConexiones = new JTable(modeloConexiones);
		tablaConexiones.setFillsViewportHeight(true);

		panelConexiones.add(new JScrollPane(tablaConexiones), BorderLayout.CENTER);
	}

	private DefaultTableModel crearModeloTablaConexiones() {
		return new DefaultTableModel(new String[] { "Provincia 1", "Provincia 2", "Similaridad" }, 0) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int fila, int columna) {
				return false;
			}
		};
	}

	private void crearBotonEliminarConexion(JPanel panelConexiones) {
		JButton btnEliminarConexion = new JButton("Eliminar conexión");
		panelConexiones.add(btnEliminarConexion, BorderLayout.SOUTH);
		btnEliminarConexion.addActionListener(e -> eliminarConexion());
	}

	private void eliminarConexion() {

		String provincia1 = valorDeFilaSobreColumna(0);
		String provincia2 = valorDeFilaSobreColumna(1);
		if (provincia1 == null || provincia2 == null) {
			mostrarMensaje("Seleccioná una conexión de la tabla.");
			return;
		}

		try {
			controlador.eliminarConexion(provincia1, provincia2);

		} catch (IllegalArgumentException e) {
			mostrarMensaje(e.getMessage());
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

	private void crearPanelMapa(JPanel panelContenido) {
		JPanel contenedor = new JPanel(new BorderLayout(0, 8));
		contenedor.setBorder(new TitledBorder("Mapa"));
		JPanel barraMapa = new JPanel(new BorderLayout(10, 0));
		crearBotonCargarMapa(barraMapa);
		crearEtiquetaEstado(barraMapa);
		contenedor.add(barraMapa, BorderLayout.NORTH);

		panelGrafo = new PanelGrafo();
		contenedor.add(panelGrafo, BorderLayout.CENTER);
		panelContenido.add(contenedor, BorderLayout.CENTER);
		panelGrafo.setAccionClick((x, y) -> ubicarProvincia(x, y));
	}

	private void configurarCapaUbicacion() {
		capaUbicacion = new PanelUbicacionMapa(this::areaMapaEnCapa,
				punto -> {
					java.awt.Point puntoPanel = SwingUtilities.convertPoint(
							capaUbicacion, punto, panelGrafo);
					panelGrafo.procesarClickEnPanel(puntoPanel.x, puntoPanel.y);
				}, this::cancelarUbicacion);
		frame.setGlassPane(capaUbicacion);
	}

	private Rectangle areaMapaEnCapa() {
		return SwingUtilities.convertRectangle(panelGrafo,
				panelGrafo.obtenerAreaImagen(), capaUbicacion);
	}

	private void iniciarUbicacion(String mensaje) {
		mostrarEstado(mensaje);
		capaUbicacion.activar(mensaje);
	}

	private void cancelarUbicacion() {
		provinciaPendiente = null;
		reubicandoProvincia = false;
		capaUbicacion.desactivar();
		mostrarEstado("Ubicación cancelada. Podés seleccionar otra provincia.");
	}

	private void ubicarProvincia(int x, int y) {
		if (provinciaPendiente == null) {
			return;
		}

		boolean eraReubicacion = reubicandoProvincia;

		try {
			if (eraReubicacion) {
				controlador.reubicarProvincia(provinciaPendiente, x, y);
			} else {
				String nombreUbicado = provinciaPendiente;
				controlador.ubicarProvincia(nombreUbicado, x, y);
				provinciasSinUbicar.remove(nombreUbicado);
				txtProvincia.setText("");
			}

			provinciaPendiente = null;
			reubicandoProvincia = false;
			listaProvincias.clearSelection();
			capaUbicacion.desactivar();

			mostrarEstado(eraReubicacion
					? "Provincia reubicada."
					: "Provincia agregada y ubicada.");
		} catch (IllegalArgumentException e) {
			mostrarMensaje(e.getMessage());
		}
	}

	private JPanel crearPanelNavegacion(JPanel panelContenido) {
		JPanel panelNavegacion = new JPanel(new BorderLayout());
		panelContenido.add(panelNavegacion, BorderLayout.SOUTH);
		return panelNavegacion;
	}

	private void crearBotonContinuar(JPanel panelNavegacion) {
		JButton btnContinuar = new JButton("Continuar →");
		panelNavegacion.add(btnContinuar, BorderLayout.EAST);
		btnContinuar.addActionListener(e -> continuar());
	}

	private void continuar() {
		if (!tieneImagen() || !controlador.existenProvincias()) {
			mostrarMensaje("Cargá un mapa y ubicá al menos una provincia.");
			return;
		}

		if (provinciaPendiente != null) {
			mostrarMensaje("Ubicá la provincia pendiente antes de continuar.");
			return;
		}

		abrirConfigRegiones(disenioRegiones);
	}

	private void abrirConfigRegiones(DisenioRegiones disenioRegiones) {
		VentanaConfigRegiones ventanaRegiones = new VentanaConfigRegiones(
				this,
				disenioRegiones,
				cantidadRegionesImportada);

		ventanaRegiones.mostrar();
		frame.setVisible(false);
	}

	@Override
	public void notificar(DisenioRegiones disenioRegiones) {
		mostrarProvinciasYConexiones(disenioRegiones.obtenerProvincias(), disenioRegiones.obtenerConexiones());
	}

	// Actualiza todos los componentes con los datos recibidos.
	private void mostrarProvinciasYConexiones(ArrayList<Provincia> provincias, ArrayList<Arista<String>> conexiones) {
		String seleccion1 = (String) comboProvincia1.getSelectedItem();
		String seleccion2 = (String) comboProvincia2.getSelectedItem();

		modeloProvincias.clear();
		comboProvincia1.removeAllItems();
		comboProvincia2.removeAllItems();

		for (Provincia provincia : provincias) {
			String nombre = provincia.obtenerNombre();

			modeloProvincias.addElement(nombre);
			comboProvincia1.addItem(nombre);
			comboProvincia2.addItem(nombre);
		}

		for (String pendiente : provinciasSinUbicar) {
			if (!modeloProvincias.contains(pendiente)) {
				modeloProvincias.addElement(pendiente);
			}
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

	public void mostrar() {
		Tema.aplicar(frame);

		// Centra la ventana en el monitor cada vez que se muestra.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public BufferedImage getImagen() {
		return imagen;
	}

}