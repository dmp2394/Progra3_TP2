package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
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
	private final VentanaBienvenida ventanaAnterior;
	private final ControladorVentanaConfigGrafo controlador;
	private final DisenioRegiones disenioRegiones;

	private Mapa mapa;

	private JTextField txtProvincia;
	private JLabel lblEstado;

	private DefaultListModel<String> modeloProvincias;
	private JList<String> listaProvincias;

	private String provinciaPendiente;
	private JComboBox<String> comboProvincia1;
	private JComboBox<String> comboProvincia2;
	private JSpinner spinnerSimilaridad;

	private DefaultTableModel modeloConexiones;
	private JTable tablaConexiones;
	private PanelUbicacionMapa capaUbicacion;
	private Integer cargarDatosDesdeJSON;

	public VentanaConfigGrafo(VentanaBienvenida ventanaAnterior) {
		this.ventanaAnterior = ventanaAnterior;
		this.disenioRegiones = new DisenioRegiones();
		this.controlador = new ControladorVentanaConfigGrafo(disenioRegiones);

		initialize();

		disenioRegiones.registrar(this);

	}

	// Estructura de la ventana:
	// - Izquierda: importación y carga manual.
	// - Centro: estado y mapa.
	// - Abajo: navegación (modo oscuro, volver y continuar).
	private void initialize() {
		frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();

		JPanel panelProvinciasYConexiones = crearPanelProvinciasYConexiones(panelContenido);
		crearSeccionProvincias(panelProvinciasYConexiones);
		crearSeccionConexiones(panelProvinciasYConexiones);

		crearPanelMapa(panelContenido);
		configurarCapaUbicacion();

		JPanel panelNavegacion = crearPanelNavegacion(panelContenido);
		crearBotonModoOscuro(panelNavegacion);
		crearBotonVolver(panelNavegacion);
		crearBotonContinuar(panelNavegacion);
	}

	private void configurarVentana() {
		frame.setTitle("Carga de datos");
		frame.setSize(1366, 768);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setBorder(new EmptyBorder(10, 10, 10, 10));
		panelContenido.setLayout(new BorderLayout(10, 10));
		frame.setContentPane(panelContenido);
		return panelContenido;
	}

	private void mostrarMensaje(String mensaje) {
		JOptionPane.showMessageDialog(frame, mensaje);
	}

	private void mostrarEstado(String estado) {
		lblEstado.setText(estado);
	}

	private void crearEtiquetaEstado(JPanel panelSuperior) {
		lblEstado = new JLabel("Agregá una provincia y ubicala en el mapa. Zoom con la rueda, mover con clic derecho.");
		panelSuperior.add(lblEstado, BorderLayout.CENTER);
	}

	private void crearBotonModoOscuro(JPanel panelNavegacion) {
		panelNavegacion.add(Tema.crearBotonTema(), BorderLayout.WEST);
	}

	// Vuelve a la bienvenida y descarta esta ventana con lo cargado: al
	// presionar Comenzar se empieza de cero.
	private void crearBotonVolver(JPanel panelNavegacion) {
		JButton btnVolver = new JButton("← Volver");

		btnVolver.addActionListener(e -> {
			frame.dispose();

			ventanaAnterior.mostrar();
		});

		JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panelVolver.add(btnVolver);
		panelNavegacion.add(panelVolver, BorderLayout.CENTER);
	}

	private JPanel crearPanelProvinciasYConexiones(JPanel panelContenido) {
		JPanel panelLateral = new JPanel(new BorderLayout(0, 10));
		panelLateral.setPreferredSize(new Dimension(390, 500));
		panelContenido.add(panelLateral, BorderLayout.WEST);
		crearSeccionCargaDatos(panelLateral);

		JPanel panelManual = new JPanel(new BorderLayout(0, 6));
		panelManual.setBorder(new TitledBorder("Carga manual"));
		panelLateral.add(panelManual, BorderLayout.CENTER);

		JPanel secciones = new JPanel(new GridBagLayout());
		panelManual.add(secciones, BorderLayout.CENTER);
		return secciones;
	}

	private void crearSeccionCargaDatos(JPanel panelLateral) {
		JPanel panelDatos = new JPanel(new BorderLayout(0, 6));
		panelDatos.setBorder(new TitledBorder("Carga desde archivo"));
		JButton boton = new JButton("Importar datos desde JSON…");
		boton.setFocusPainted(false);
		boton.addActionListener(e -> cargarProvinciasDesdeJSON());
		panelDatos.add(boton, BorderLayout.NORTH);
		panelDatos.add(new JLabel(
				"<html>Cargá provincias y conexiones desde un archivo<br>" + "o agregalas manualmente abajo.</html>"),
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

		try {
			controlador.agregarProvincia(nombre);
			txtProvincia.setText("");
			mostrarEstado("Provincia agregada. Seleccionala y tocá \"Ubicar en mapa\".");

		} catch (IllegalArgumentException e) {
			mostrarMensaje(e.getMessage());
		}
	}

	private void cargarProvinciasDesdeJSON() {
		String ruta = seleccionarRutaJSON();

		if (ruta == null) {
			return;
		}

		try {
			cargarDatosDesdeJSON = controlador.cargarProvinciasDesdeJSON(ruta);

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
		String seleccionada = listaProvincias.getSelectedValue();
		if (seleccionada == null) {
			mostrarMensaje("Seleccioná una provincia de la lista.");
			return;
		}

		provinciaPendiente = seleccionada;

		iniciarUbicacion("Hacé clic en el mapa para ubicar: " + seleccionada);
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
		crearEtiquetaEstado(barraMapa);
		contenedor.add(barraMapa, BorderLayout.NORTH);

		mapa = new Mapa();
		contenedor.add(mapa, BorderLayout.CENTER);
		panelContenido.add(contenedor, BorderLayout.CENTER);
		mapa.setAccionClick((latitud, longitud) -> ubicarProvincia(latitud, longitud));
	}

	private void configurarCapaUbicacion() {
		capaUbicacion = new PanelUbicacionMapa(this::areaMapaEnCapa, punto -> {
			java.awt.Point puntoPanel = SwingUtilities.convertPoint(capaUbicacion, punto, mapa);
			mapa.procesarClickEnPanel(puntoPanel.x, puntoPanel.y);
		}, this::cancelarUbicacion);
		frame.setGlassPane(capaUbicacion);
		reenviarZoomYMovimientoAlMapa();
	}

	// Mientras se ubica una provincia, la capa tapa al mapa y recibe el mouse.
	// Se le reenvían al mapa la rueda (zoom) y el arrastre con clic derecho
	// (mover), para poder acercarse antes de hacer clic. El clic izquierdo lo
	// sigue manejando la capa.
	private void reenviarZoomYMovimientoAlMapa() {
		MouseAdapter reenvio = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent evento) {
				reenviarAlMapa(evento);
			}

			@Override
			public void mouseReleased(MouseEvent evento) {
				reenviarAlMapa(evento);
			}

			@Override
			public void mouseDragged(MouseEvent evento) {
				reenviarAlMapa(evento);
			}

			@Override
			public void mouseWheelMoved(MouseWheelEvent evento) {
				reenviarAlMapa(evento);
			}
		};

		capaUbicacion.addMouseListener(reenvio);
		capaUbicacion.addMouseMotionListener(reenvio);
		capaUbicacion.addMouseWheelListener(reenvio);
	}

	private void reenviarAlMapa(MouseEvent evento) {
		if (!SwingUtilities.isLeftMouseButton(evento)) {
			mapa.dispatchEvent(SwingUtilities.convertMouseEvent(capaUbicacion, evento, mapa));
		}
	}

	private Rectangle areaMapaEnCapa() {
		return SwingUtilities.convertRectangle(mapa, mapa.obtenerAreaMapa(), capaUbicacion);
	}

	private void iniciarUbicacion(String mensaje) {
		mostrarEstado(mensaje);
		capaUbicacion.activar(mensaje);
	}

	private void cancelarUbicacion() {
		provinciaPendiente = null;
		capaUbicacion.desactivar();
		mostrarEstado("Ubicación cancelada. Podés seleccionar otra provincia.");
	}

	private void ubicarProvincia(double latitud, double longitud) {
		if (provinciaPendiente == null) {
			return;
		}

		try {
			controlador.ubicarProvincia(provinciaPendiente, latitud, longitud);

			provinciaPendiente = null;
			listaProvincias.clearSelection();
			capaUbicacion.desactivar();

			mostrarEstado("Provincia ubicada.");
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
		if (!controlador.existenProvincias()) {
			mostrarMensaje("Agregá al menos una provincia.");
			return;
		}

		if (!controlador.todasLasProvinciasUbicadas()) {
			mostrarMensaje("Ubicá todas las provincias en el mapa antes de continuar.");
			return;
		}

		if (provinciaPendiente != null) {
			mostrarMensaje("Ubicá la provincia pendiente antes de continuar.");
			return;
		}

		abrirConfigRegiones(disenioRegiones);
	}

	private void abrirConfigRegiones(DisenioRegiones disenioRegiones) {
		VentanaConfigRegiones ventanaRegiones = new VentanaConfigRegiones(this, disenioRegiones, cargarDatosDesdeJSON);

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

		mapa.setDatos(provincias, conexiones);
	}

	public void mostrar() {
		Tema.aplicar(frame);

		// Centra la ventana en el monitor cada vez que se muestra.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
