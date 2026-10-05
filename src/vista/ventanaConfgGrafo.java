package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import modelo.Arista;
import modelo.Provincia;
import presentador.Presentador;

public class ventanaConfgGrafo {

    private JFrame frame;
    private final Presentador presentador;

    private BufferedImage imagen;
    private PanelGrafo panelGrafo;

    private JTextField txtProvincia;
    private JLabel lblEstado;
    private String provinciaPendiente;

    private DefaultListModel<String> modeloProvincias;
    private JList<String> listaProvincias;

    private JComboBox<String> comboProvincia1;
    private JComboBox<String> comboProvincia2;
    private JSpinner spinnerPeso;

    private DefaultTableModel modeloConexiones;
    private JTable tablaConexiones;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ventanaConfgGrafo ventana = new ventanaConfgGrafo();
                    ventana.mostrar();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ventanaConfgGrafo() {
        presentador = new Presentador();
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
        btnCargarMapa.addActionListener(e -> cargarImagen());

        lblEstado = new JLabel("Cargá una imagen para comenzar.");
        panelSuperior.add(lblEstado, BorderLayout.CENTER);

        JPanel panelControles = new JPanel(new GridLayout(2, 1, 0, 10));
        panelControles.setPreferredSize(new Dimension(350, 500));
        panelContenido.add(panelControles, BorderLayout.WEST);

        // PROVINCIAS

        JPanel panelProvincias = new JPanel(new BorderLayout(0, 8));
        panelProvincias.setBorder(new TitledBorder("Provincias"));
        panelControles.add(panelProvincias);

        JPanel panelCargaProvincia = new JPanel(
                new GridLayout(3, 1, 0, 5));
        panelProvincias.add(panelCargaProvincia, BorderLayout.NORTH);

        panelCargaProvincia.add(new JLabel("Nombre de provincia:"));

        txtProvincia = new JTextField();
        panelCargaProvincia.add(txtProvincia);

        JButton btnUbicar = new JButton("Ubicar en mapa");
        panelCargaProvincia.add(btnUbicar);
        btnUbicar.addActionListener(e -> prepararUbicacion());

        modeloProvincias = new DefaultListModel<>();
        listaProvincias = new JList<>(modeloProvincias);

        panelProvincias.add(
                new JScrollPane(listaProvincias),
                BorderLayout.CENTER);

        JButton btnEliminarProvincia = new JButton("Eliminar provincia");
        panelProvincias.add(btnEliminarProvincia, BorderLayout.SOUTH);
        btnEliminarProvincia.addActionListener(e -> eliminarProvincia());

        // CONEXIONES

        JPanel panelConexiones = new JPanel(new BorderLayout(0, 8));
        panelConexiones.setBorder(new TitledBorder("Conexiones"));
        panelControles.add(panelConexiones);

        JPanel panelCargaConexion = new JPanel(
                new GridLayout(4, 2, 5, 5));
        panelConexiones.add(panelCargaConexion, BorderLayout.NORTH);

        panelCargaConexion.add(new JLabel("Provincia 1:"));
        comboProvincia1 = new JComboBox<>();
        panelCargaConexion.add(comboProvincia1);

        panelCargaConexion.add(new JLabel("Provincia 2:"));
        comboProvincia2 = new JComboBox<>();
        panelCargaConexion.add(comboProvincia2);

        panelCargaConexion.add(new JLabel("Peso:"));
        spinnerPeso = new JSpinner(
                new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        panelCargaConexion.add(spinnerPeso);

        panelCargaConexion.add(new JLabel());

        JButton btnAgregarConexion = new JButton("Agregar conexión");
        panelCargaConexion.add(btnAgregarConexion);
        btnAgregarConexion.addActionListener(e -> agregarConexion());

        modeloConexiones = new DefaultTableModel(
                new String[] { "Provincia 1", "Provincia 2", "Peso" }, 0) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaConexiones = new JTable(modeloConexiones);
        tablaConexiones.setFillsViewportHeight(true);

        panelConexiones.add(
                new JScrollPane(tablaConexiones),
                BorderLayout.CENTER);

        JButton btnEliminarConexion = new JButton("Eliminar conexión");
        panelConexiones.add(btnEliminarConexion, BorderLayout.SOUTH);
        btnEliminarConexion.addActionListener(e -> eliminarConexion());

        // MAPA

        panelGrafo = new PanelGrafo();
        panelGrafo.setBorder(new TitledBorder("Mapa"));
        panelContenido.add(panelGrafo, BorderLayout.CENTER);

        panelGrafo.setAccionClick((x, y) -> ubicarProvincia(x, y));

        // NAVEGACIÓN

        JPanel panelNavegacion = new JPanel(new BorderLayout());
        panelContenido.add(panelNavegacion, BorderLayout.SOUTH);

        JButton btnContinuar = new JButton("Continuar →");
        panelNavegacion.add(btnContinuar, BorderLayout.EAST);
        btnContinuar.addActionListener(e -> continuar());
    }

    // Abre un selector de archivos y carga la imagen elegida.
    private void cargarImagen() {
        if (presentador.getCantidadProvincias() > 0) {
            mostrarMensaje(
                    "Eliminá las provincias antes de cambiar el mapa.");
            return;
        }

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar imagen del mapa");
        selector.setAcceptAllFileFilterUsed(false);
        selector.setFileFilter(new FileNameExtensionFilter(
                "Imágenes PNG y JPG", "png", "jpg", "jpeg"));

        if (selector.showOpenDialog(frame)
                != JFileChooser.APPROVE_OPTION) {
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

            provinciaPendiente = null;
            lblEstado.setText(
                    "Mapa cargado. Escribí una provincia y presioná Ubicar.");

        } catch (IOException e) {
            mostrarMensaje("No se pudo leer la imagen seleccionada.");
        }
    }

    // Guarda el nombre que se utilizará en el próximo click.
    private void prepararUbicacion() {
        if (!panelGrafo.tieneImagen()) {
            mostrarMensaje("Primero cargá una imagen del mapa.");
            return;
        }

        String nombre = txtProvincia.getText().trim();

        if (nombre.isEmpty()) {
            mostrarMensaje("Escribí el nombre de la provincia.");
            return;
        }

        for (Provincia provincia : presentador.getProvincias()) {
            if (provincia.getNombre().equals(nombre)) {
                mostrarMensaje("La provincia ya existe.");
                return;
            }
        }

        provinciaPendiente = nombre;
        lblEstado.setText(
                "Hacé click sobre el mapa para ubicar: " + nombre);
    }

    // Recibe las coordenadas originales calculadas por PanelGrafo.
    private void ubicarProvincia(int x, int y) {
        if (provinciaPendiente == null) {
            return;
        }

        try {
            presentador.agregarProvincia(provinciaPendiente, x, y);

            provinciaPendiente = null;
            txtProvincia.setText("");
            lblEstado.setText(
                    "Provincia agregada. Podés ubicar otra o crear conexiones.");

            actualizarVista();

        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void eliminarProvincia() {
        String nombre = listaProvincias.getSelectedValue();

        if (nombre == null) {
            mostrarMensaje("Seleccioná una provincia de la lista.");
            return;
        }

        try {
            presentador.eliminarProvincia(nombre);
            actualizarVista();

        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void agregarConexion() {
        String origen = (String) comboProvincia1.getSelectedItem();
        String destino = (String) comboProvincia2.getSelectedItem();

        if (origen == null || destino == null) {
            mostrarMensaje("Primero agregá las provincias que querés conectar.");
            return;
        }

        try {
            spinnerPeso.commitEdit();
            int peso = ((Number) spinnerPeso.getValue()).intValue();

            presentador.agregarConexion(origen, destino, peso);
            actualizarVista();

        } catch (java.text.ParseException e) {
            mostrarMensaje("Ingresá un peso entero válido.");

        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    private void eliminarConexion() {
        int filaVista = tablaConexiones.getSelectedRow();

        if (filaVista == -1) {
            mostrarMensaje("Seleccioná una conexión de la tabla.");
            return;
        }

        int filaModelo = tablaConexiones.convertRowIndexToModel(filaVista);

        String origen = (String) modeloConexiones.getValueAt(filaModelo, 0);
        String destino = (String) modeloConexiones.getValueAt(filaModelo, 1);

        try {
            presentador.eliminarConexion(origen, destino);
            actualizarVista();

        } catch (IllegalArgumentException e) {
            mostrarMensaje(e.getMessage());
        }
    }

    // Actualiza todos los componentes con los datos del modelo.
    private void actualizarVista() {
        String seleccion1 = (String) comboProvincia1.getSelectedItem();
        String seleccion2 = (String) comboProvincia2.getSelectedItem();

        modeloProvincias.clear();
        comboProvincia1.removeAllItems();
        comboProvincia2.removeAllItems();

        for (Provincia provincia : presentador.getProvincias()) {
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

        for (Arista<String> conexion : presentador.getConexiones()) {
            modeloConexiones.addRow(new Object[] {
                    conexion.obtenerExtremo1(),
                    conexion.obtenerExtremo2(),
                    conexion.devolverPeso()
            });
        }

        panelGrafo.setDatos(
                presentador.getProvincias(),
                presentador.getConexiones());
    }

    private void continuar() {
        if (!panelGrafo.tieneImagen()
                || presentador.getCantidadProvincias() == 0) {

            mostrarMensaje("Cargá un mapa y ubicá al menos una provincia.");
            return;
        }

        if (provinciaPendiente != null) {
            mostrarMensaje(
                    "Ubicá la provincia pendiente antes de continuar.");
            return;
        }

        VentanaConfiguracion ventana =
                new VentanaConfiguracion(this);

        ventana.mostrar();
        frame.setVisible(false);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje);
    }

    // Las próximas ventanas utilizarán el mismo presentador.
    public Presentador getPresentador() {
        return presentador;
    }

    // Permite mostrar la misma imagen en las próximas ventanas.
    public BufferedImage getImagen() {
        return imagen;
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}